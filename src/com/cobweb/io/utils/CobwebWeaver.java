/*******************************************************************************
 * Copyright  (c) 2015-2016, Cobweb IO (http://www.cobweb.io) All Rights Reserved.
 *   
 * Cobweb IO licences this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.cobweb.io.utils;

import java.util.ArrayList;
import java.util.List;

import com.cobweb.io.meta.Device;
import com.cobweb.io.meta.DeviceHasPayload;
import com.cobweb.io.meta.DeviceHasSensors;
import com.cobweb.io.meta.Payload;
import com.cobweb.io.meta.Sensor;
import com.cobweb.io.meta.SensorHasPayload;
import com.cobweb.io.meta.User;
import com.cobweb.io.meta.UserHasDevices;
import com.cobweb.io.meta.UserFollowsUser;
import com.cobweb.io.meta.UserSubscribesDevice;
import com.cobweb.io.meta.UserSubscribesSensor;
import com.cobweb.io.service.CreateService;
import com.cobweb.io.service.GraphFactory;
import com.cobweb.io.service.ReadService;
import com.tinkerpop.blueprints.Vertex;

/**
 * The Class CobwebWeaver.
 * @author YasithLokuge
 * 
 */
public class CobwebWeaver extends GraphFactory{

	/** The create service. */
	CreateService createService = new CreateService();		
	
	/** The read service. */
	ReadService readService = new ReadService();
	
	/**
	 * Adds the user.
	 *
	 * @param user the user
	 */
	public void addUser(User user){
		createService.CreateUser(user);
	}
	
	/**
	 * Adds the device.
	 *
	 * @param userEmail the user email
	 * @param device the device
	 */
	public void addDevice(String userId, Device device){
		
		UserHasDevices userHasDevices = new UserHasDevices();		
		Vertex userVertex = getUserVertex(userId); 
		Vertex deviceVertex = createService.CreateDevice(device);		
		userHasDevices.setUser(userVertex);
		userHasDevices.setDevice(deviceVertex);
		createService.CreateUserHasDevices(userHasDevices);
	}
	
	/**
	 * Adds the sensor.
	 *
	 * @param deviceId the device id
	 * @param sensorId the sensor id
	 */
	public void addSensor(String deviceId, Sensor sensor){
		
		DeviceHasSensors deviceHasSensors = new DeviceHasSensors();		
		Vertex deviceVertex = getDeviceVertex(deviceId);
		Vertex sensorVertex = createService.CreateSensor(sensor);	
		deviceHasSensors.setDevice(deviceVertex);
		deviceHasSensors.setSensor(sensorVertex);		
		createService.CreateDeviceHasSensors(deviceHasSensors);
	}
	
	/**
	 * Adds the device payload.
	 *
	 * @param deviceId the device id
	 * @param payload the payload
	 */
	public void addDevicePayload(String deviceId, Payload payload){
		
		DeviceHasPayload deviceHasPayload = new DeviceHasPayload();
		Vertex deviceVertex  = getDeviceVertex(deviceId);
		Vertex payloadVertex = createService.CreatePayload(payload);
		deviceHasPayload.setDevice(deviceVertex);
		deviceHasPayload.setPayload(payloadVertex);
		createService.CreateDeviceHasPayload(deviceHasPayload);
	}
	
	/**
	 * Adds the sensor payload.
	 *
	 * @param sensorId the sensor id
	 * @param payload the payload
	 */
	public void addSensorPayload(String sensorId, Payload payload){

		SensorHasPayload sensorHasPayload = new SensorHasPayload();
		Vertex sensorVertex  = getSensorVertex(sensorId);
		Vertex payloadVertex = createService.CreatePayload(payload);
		sensorHasPayload.setPayload(payloadVertex);
		sensorHasPayload.setSensor(sensorVertex);
		createService.CreateSensorHasPayload(sensorHasPayload);		
	}
	
	/**
	 * Adds the follow user.
	 *
	 * @param userOutEmail the user out email
	 * @param userInEmail the user in email
	 */
	public void addFollowUser(String userOutId, String userInId ){
		
		UserFollowsUser userFollowsUser = new UserFollowsUser();
		Vertex userInVertex  = getUserVertex(userInId);
		Vertex userOutVertex = getUserVertex(userOutId);
		userFollowsUser.setUserIn(userInVertex);
		userFollowsUser.setUserOut(userOutVertex);
		createService.CreateUserFollowsUser(userFollowsUser);
		
	}
	
	/**
	 * Adds the device subscription.
	 *
	 * @param email the email
	 * @param deviceId the device id
	 */
	public void addDeviceSubscription(String userId, String deviceId ){
		
		UserSubscribesDevice userSubscribesDevice = new UserSubscribesDevice();
		Vertex user = getUserVertex(userId);
		Vertex device = getDeviceVertex(deviceId);
		userSubscribesDevice.setUser(user);
		userSubscribesDevice.setDevice(device);
		createService.CreateUserSubscribesDevice(userSubscribesDevice);
		
	}

	
	/**
	 * Adds the sensor subscription.
	 *
	 * @param email the email
	 * @param sensorId the sensor id
	 */
	public void addSensorSubscription(String userId, String sensorId ){
		
		UserSubscribesSensor userSubscribesSensor = new UserSubscribesSensor();
		Vertex user = getUserVertex(userId);
		Vertex sensor = getSensorVertex(sensorId);
		userSubscribesSensor.setUser(user);
		userSubscribesSensor.setSensor(sensor);
		createService.CreateUserSubscribesSensor(userSubscribesSensor);
		
	}

	/**
	 * Checks if is authorized device.
	 *
	 * @param userEmail the user email
	 * @param deviceId the device id
	 * @return true, if is authorized device
	 */
	public boolean isAuthorizedDevice(String userId, String deviceId){
		
		List<String> idList = new ArrayList<>();		
		idList = readService.getDeviceIdList(userId);		
		return idList.contains(deviceId);		
	}
}
