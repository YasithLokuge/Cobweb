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
package com.cobweb.io.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.cobweb.io.meta.Device;
import com.cobweb.io.meta.DeviceType;
import com.cobweb.io.meta.FriendRequest;
import com.cobweb.io.meta.LoggedUser;
import com.cobweb.io.meta.Payload;
import com.cobweb.io.meta.Sensor;
import com.cobweb.io.meta.SensorType;
import com.cobweb.io.meta.User;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.tinkerpop.blueprints.Vertex;


/**
 * The Class ReadService.
 *
 * @author Yasith Lokuge
 */

public class ReadService implements AbstractService{

	/** The invalid. */
	private final String INVALID = "Invalid user name or password"; 
	
	/**
	 * Read.
	 *
	 * @param loggedUser the logged user
	 * @param data the data
	 * @return the string
	 */
	public String read(LoggedUser loggedUser,String data){
	
		String email= loggedUser.getEmail();
		String password = loggedUser.getPassword();
		String info;
		
		List<ODocument> result =  graph.getRawGraph().query(new OSQLSynchQuery<Object>("select from User where email=\""+email+"\" and password=\""+password+"\""));
		 		
		if(!result.isEmpty()){			
			Vertex v = graph.getVertex(result.get(0).getIdentity());
			info = v.getProperty(data);
			return info;
		}else{
			return INVALID;
		}
		
	}
	
	/**
	 * Gets the confirmed user names list.
	 *
	 * @return the list
	 */
	public List<String> getConfirmedUserNamesList(){
		
		List<String> userNameList = new ArrayList<>();
		List<ODocument> result =  graph.getRawGraph().query(new OSQLSynchQuery<Object>("Select email from User WHERE isDeleted=false"));		
		
		for (ODocument oDocument : result) {
			userNameList.add(oDocument.field("email"));
		}		
		return userNameList;		
	}
	
	/**
	 * Gets the Unconfirmed user names list.
	 *
	 * @return the deleted user names list
	 */
	public List<String> getUnconfirmedUserNamesList(){
		
		List<String> userNameList = new ArrayList<>();
		List<ODocument> result =  graph.getRawGraph().query(new OSQLSynchQuery<Object>("Select email from User WHERE isDeleted=true"));		
		
		for (ODocument oDocument : result) {
			userNameList.add(oDocument.field("email"));
		}		
		return userNameList;		
	}
	
	
	/**
	 * Check user name exists.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	public boolean checkUserNameExistsByEmail(String email){				
		try {
			ODocument result = (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("Select email from User where email='"+email+"' AND isDeleted=false")).get(0);
			String data = result.field("email");
			return data.equals(email);
		} catch (Exception e) {			
			return false;
		}	
	}
	
	/**
	 * Check user name exists by id.
	 *
	 * @param userId the user id
	 * @return true, if successful
	 */
	public boolean checkUserNameExistsById(String userId){				
		try {
			ODocument result = (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("Select idValue from User where idValue='"+userId+"' AND isDeleted=false")).get(0);
			String data = result.field("idValue");
			return data.equals(userId);
		} catch (Exception e) {			
			return false;
		}	
	}
	
	/**
	 * Check password exists.
	 *
	 * @param password the password
	 * @return true, if successful
	 */
	public boolean checkPasswordExists(String password){				
		try {
			ODocument result = (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("Select password from User where password='"+password+"' AND isDeleted=false")).get(0);
			String data = result.field("password");
			return data.equals(password);
		} catch (Exception e) {			
			return false;
		}	
	}
	
	/**
	 * Gets the user id.
	 *
	 * @param email the email
	 * @return the user id
	 */
	public String getUserId(String email){
		ODocument result;
		String idValue = null;
		try {
			result = (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("Select idValue from User where email='"+email+"' AND isDeleted=false")).get(0);
			idValue = result.field("idValue");		
		} catch (Exception e) {
			idValue = null;
		}
		return idValue;
	}
	
	
	/**
	 * Gets the email.
	 *
	 * @param userId the user id
	 * @return the email
	 */
	public String getEmail(String userId){
		ODocument result;
		String email = null;
		try {
			result = (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("Select email from User where idValue='"+userId+"'")).get(0);
			email = result.field("email");		
		} catch (Exception e) {
			email = null;
		}
		return email;
	}

	/**
	 * Gets the salt.
	 *
	 * @param userId the user id
	 * @return the salt
	 */
	public String getSalt(String userId){
		
		String salt = null;
		try {
			ODocument result =  (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("Select salt from User where idValue='"+userId+"' AND isDeleted=false")).get(0);
			salt = result.field("salt");
		} catch (Exception e) {
			salt = null;
		}
		return salt;
	}
	
	
	/**
	 * Gets the device id list.
	 *
	 * @param userId the user id
	 * @return the device id list
	 */
	public List<String> getDeviceIdList(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(out('UserHasDevices')[isDeleted = false]) from User where idValue='"+userId+"' AND isDeleted=false"));
		List<String>	deviceIdList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			deviceIdList.add(result.field("idValue"));			
		}		
		return deviceIdList;		
	}
	
	
	/**
	 * Gets the sensor id list.
	 *
	 * @param userId the user id
	 * @return the sensor id list
	 */
	public List<String> getSensorIdList(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(out('UserHasDevices')[isDeleted = false].out('DeviceHasSensors')[isDeleted = false]) from User where idValue='"+userId+"'"));
		List<String>	sensorIdList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			sensorIdList.add(result.field("idValue"));			
		}		
		return sensorIdList;		
	}
	
	
	
	/**
	 * Gets the sensor list.
	 *
	 * @param userId the user id
	 * @return the sensor list
	 */
	public List<Sensor> getSensorList(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(out('UserHasDevices')[isDeleted = false].out('DeviceHasSensors')[isDeleted = false]) from User where idValue='"+ userId+"'"));
		List<Sensor>	sensorList = new ArrayList<Sensor>();
		
		for (ODocument result:resultList) {
			
			String name 		= result.field("name"); 
			String id 			= result.field("idValue");
			String description 	= result.field("description");
			String sensorType 	= result.field("sensorType");
			String otherType 	= result.field("otherType");
			
			String parentDevice = getParentDeviceIdFromSensor(id);
			
			Sensor sensor = new Sensor(name, description, SensorType.valueOf(sensorType));
			sensor.setId(id);
			sensor.setOtherType(otherType);
			sensor.setParentDeviceId(parentDevice);			
									
			sensorList.add(sensor);
		}		
		return sensorList;				
	}
	
	
	/**
	 * Gets the device list.
	 *
	 * @param userId the user id
	 * @return the device list
	 */
	public List<Device> getDeviceList(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(out('UserHasDevices')[isDeleted = false]) from User where idValue='"+ userId+"'"));
		List<Device>	deviceList = new ArrayList<Device>();
		
		for (ODocument result:resultList) {
			
			String name 		= result.field("name"); 
			String id 			= result.field("idValue");
			String description 	= result.field("description");
			String deviceType 	= result.field("deviceType");
			String otherType 	= result.field("otherType");
			
			String parentUserId = getParentUserIdFromDevice(id);
			
			List<String> attachedSensorList = getAttachedSensorList(id);
			
			Device device = new Device(name, description, DeviceType.valueOf(deviceType));
			device.setId(id);
			device.setOtherType(otherType);
			device.setSensorIdList(attachedSensorList);
			device.setParentUserId(parentUserId);
			
								
			deviceList.add(device);
		}		
		return deviceList;				
	}
	
	/**
	 * Check device exists.
	 *
	 * @param deviceId the device id
	 * @return true, if successful
	 */
	public boolean checkDeviceExists(String deviceId){
		try {
			ODocument result = (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("Select idValue from Device where idValue='"+deviceId+"'")).get(0);
			String data = result.field("idValue");
			return data.equals(deviceId);
		} catch (Exception e) {			
			return false;
		}
	}

	/**
	 * Gets the payload list.
	 *
	 * @param sensorId the sensor id
	 * @return the payload list
	 */
	public List<Payload> getDevicePayloadList(String deviceId){
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(out('DeviceHasPayload')[isDeleted = false]) from Device where idValue='"+deviceId+"'"));
		
		List<Payload>	payloadList = new ArrayList<Payload>();

		
		for (ODocument result:resultList) {
			
			String id = result.field("idValue");
			String message = result.field("message");
			String dateTime = result.field("dateTime");
			
			Payload payload = new Payload(message);
			payload.setId(id);
			payload.setMessage(message);
			
			payload.setDeviceId(deviceId);			
			String userId = getParentUserIdFromDevice(deviceId);		
			payload.setUserId(userId);			
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			
			
			try {
				Date date = format.parse(dateTime);
				payload.setDateTime(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			payloadList.add(payload);
		}
		
		return payloadList;		
	}
	
	
	/**
	 * Gets the sensor payload list.
	 *
	 * @param sensorId the sensor id
	 * @return the sensor payload list
	 */
	public List<Payload> getSensorPayloadList(String sensorId){
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(out('SensorHasPayload')[isDeleted = false]) from Sensor where idValue='"+sensorId+"'"));
		
		List<Payload>	payloadList = new ArrayList<Payload>();

		
		for (ODocument result:resultList) {
			
			String id = result.field("idValue");
			String message = result.field("message");
			String dateTime = result.field("dateTime");
			
			Payload payload = new Payload(message);
			payload.setId(id);
			payload.setMessage(message);
			payload.setSensorId(sensorId);
			
			String deviceId = getParentDeviceIdFromSensor(sensorId);
			String userId = getParentUserIdFromDevice(deviceId);
			
			payload.setDeviceId(deviceId);
			payload.setUserId(userId);
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			
			
			try {
				Date date = format.parse(dateTime);
				payload.setDateTime(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			payloadList.add(payload);
		}
		
		return payloadList;		
	}
	
	
	
	/**
	 * Gets the attached sensor list.
	 *
	 * @param deviceId the device id
	 * @return the attached sensor list
	 */
	public List<String> getAttachedSensorList(String deviceId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(out('DeviceHasSensors')[isDeleted = false]) from Device where idValue='"+deviceId+"'"));
		List<String>	sensorIdList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");			
			sensorIdList.add(id);
		}		
		return sensorIdList;		
	}
	
	
	/**
	 * Gets the parent device id.
	 *
	 * @param sensorId the sensor id
	 * @return the parent device id
	 */
	public String getParentDeviceIdFromSensor(String sensorId){
		
		ODocument result = (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(in('DeviceHasSensors')[isDeleted=false]) from Sensor where idValue='"+sensorId+"'")).get(0);
		String data = result.field("idValue");
		return data;		
	}
	
	/**
	 * Gets the parent device id from payload.
	 *
	 * @param payloadId the payload id
	 * @return the parent device id from payload
	 */
	public String getParentDeviceIdFromPayload(String payloadId){
		
		ODocument result = (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(in('DeviceHasPayload')[isDeleted=false]) from Payload where idValue='"+payloadId+"'")).get(0);
		String data = result.field("idValue");
		return data;		
	}
	
	/**
	 * Gets the parent sensor id from payload.
	 *
	 * @param payloadId the payload id
	 * @return the parent sensor id from payload
	 */
	public String getParentSensorIdFromPayload(String payloadId){
		
		ODocument result = (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(in('SensorHasPayload')[isDeleted=false]) from Payload where idValue='"+payloadId+"'")).get(0);
		String data = result.field("idValue");
		return data;		
	}
	
	
	
	/**
	 * Gets the parent user id.
	 *
	 * @param deviceId the device id
	 * @return the parent user id
	 */
	public String getParentUserIdFromDevice(String deviceId){
		
		ODocument result = (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(in('UserHasDevices')[isDeleted=false]) from Device where idValue='"+deviceId+"'")).get(0);
		String data = result.field("idValue");
		return data;		
	}
	
	/**
	 * Gets the parent user id from sensor.
	 *
	 * @param sensorId the sensor id
	 * @return the parent user id from sensor
	 */
	public String getParentUserIdFromSensor(String sensorId){
		
		ODocument result = (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(in('DeviceHasSensors')[isDeleted=false].in('UserHasDevices')[isDeleted=false]) from Sensor where idValue='"+sensorId+"'")).get(0);
		String data = result.field("idValue");
		return data;		
	}
	
	
	/**
	 * Gets the parent user id from payload.
	 *
	 * @param payloadId the payload id
	 * @return the parent user id from payload
	 */
	public String getParentUserIdFromPayload(String payloadId){
		
		ODocument result;
		
		try {
			result = (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(in('SensorHasPayload')[isDeleted=false].in('DeviceHasSensors')[isDeleted=false].in('UserHasDevices')[isDeleted=false]) from Payload where idValue='"+payloadId+"'")).get(0);
		} catch (Exception e) {				
			try {
				result = (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(in('DeviceHasPayload')[isDeleted=false].in('UserHasDevices')[isDeleted=false]) from Payload where idValue='"+payloadId+"'")).get(0);
			} catch (Exception c) {			
				return null;
			}
		}		
			String data= result.field("idValue");
			return data;
	}


	/**
	 * Gets the subscribed device payloads.
	 *
	 * @param userId the user id
	 * @return the subscribed device payloads
	 */
	public List<Payload> getSubscribedDevicePayloads(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(outE('UserSubscribesDevice')[isAccepted=true].inV().out('DeviceHasPayload')[isDeleted=false]) from User where idValue='"+userId+"'"));
		List<Payload>	payloadList = new ArrayList<Payload>();

		
		for (ODocument result:resultList) {
			
			String id = result.field("idValue");
			String message = result.field("message");
			String dateTime = result.field("dateTime");
			
			Payload payload = new Payload(message);
			payload.setId(id);
			payload.setMessage(message);
			
			String deviceId = getParentDeviceIdFromPayload(id);
			String ownerId = getParentUserIdFromDevice(deviceId);
			
			payload.setDeviceId(deviceId);
			payload.setUserId(ownerId);
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			
			
			try {
				Date date = format.parse(dateTime);
				payload.setDateTime(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			payloadList.add(payload);	
				
		}
		return payloadList;			
	}
	
	
	/**
	 * Gets the subscribed sensor payloads.
	 *
	 * @param userId the user id
	 * @return the subscribed sensor payloads
	 */
	public List<Payload> getSubscribedSensorPayloads(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(outE('UserSubscribesSensor')[isAccepted=true].inV().out('SensorHasPayload')[isDeleted=false]) from User where idValue='"+userId+"'"));
		List<Payload>	payloadList = new ArrayList<Payload>();

		
		for (ODocument result:resultList) {
			
			String id = result.field("idValue");
			String message = result.field("message");
			String dateTime = result.field("dateTime");
			String sensorId = getParentSensorIdFromPayload(id);
			
			Payload payload = new Payload(message);
			payload.setId(id);
			payload.setMessage(message);
			payload.setSensorId(sensorId);
			
			String deviceId = getParentDeviceIdFromSensor(sensorId);
			String ownerId = getParentUserIdFromDevice(deviceId);
			
			payload.setDeviceId(deviceId);
			payload.setUserId(ownerId);
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			
			
			try {
				Date date = format.parse(dateTime);
				payload.setDateTime(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			payloadList.add(payload);	
				
		}
		return payloadList;			
	}
	
	
	/**
	 * Gets the subscribed sensor payload id list.
	 *
	 * @param userId the user id
	 * @return the subscribed sensor payload id list
	 */
	public List<String> getSubscribedSensorPayloadIdList(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(outE('UserSubscribesSensor')[isAccepted=true].inV().out('SensorHasPayload')[isDeleted=false]) from User where idValue='"+userId+"'"));
		List<String>	payloadIdList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");			
			payloadIdList.add(id);					
		}
		return payloadIdList;			
	}
	
	
	/**
	 * Gets the subscribed device payload id list.
	 *
	 * @param userId the user id
	 * @return the subscribed device payload id list
	 */
	public List<String> getSubscribedDevicePayloadIdList(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(outE('UserSubscribesDevice')[isAccepted=true].inV().out('DeviceHasPayload')[isDeleted=false]) from User where idValue='"+userId+"'"));
		List<String>	payloadIdList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");			
			payloadIdList.add(id);					
		}
		return payloadIdList;			
	}


	/**
	 * Gets the subscribed sensor id list.
	 *
	 * @param userId the user id
	 * @return the subscribed sensor id list
	 */
	public List<String> getSubscribedSensorIdList(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(outE('UserSubscribesSensor')[isAccepted=true].inV()) from User where idValue='"+userId+"'"));
		List<String>	sensorIdList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");			
			sensorIdList.add(id);
		}		
		return sensorIdList;
	}
	
	
	/**
	 * Gets the subscribed device id list.
	 *
	 * @param userId the user id
	 * @return the subscribed device id list
	 */
	public List<String> getSubscribedDeviceIdList(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(outE('UserSubscribesDevice')[isAccepted=true].inV()) from User where idValue='"+userId+"'"));
		List<String>	deviceIdList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");			
			deviceIdList.add(id);
		}		
		return deviceIdList;
	}
	
	/**
	 * Gets the sensor payload id list.
	 *
	 * @param userId the user id
	 * @return the sensor payload id list
	 */
	public List<String> getSensorPayloadIdListFromUser(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(out('UserHasDevices')[isDeleted = false].out('DeviceHasSensors')[isDeleted = false].out('SensorHasPayload')[isDeleted = false]) from User where idValue='"+userId+"'"));
		List<String>	sensorPayloadIdList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");			
			sensorPayloadIdList.add(id);
		}		
		return sensorPayloadIdList;		
	}
	
	/**
	 * Gets the sensor payload id list from sensor.
	 *
	 * @param sensorId the sensor id
	 * @return the sensor payload id list from sensor
	 */
	public List<String> getSensorPayloadIdListFromSensor(String sensorId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(out('SensorHasPayload')[isDeleted = false]) from Sensor where idValue='"+sensorId+"'"));
		List<String>	sensorPayloadIdList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");			
			sensorPayloadIdList.add(id);
		}		
		return sensorPayloadIdList;		
	}
	
	
	/**
	 * Gets the device payload id list from user.
	 *
	 * @param userId the user id
	 * @return the device payload id list from user
	 */
	public List<String> getDevicePayloadIdListFromUser(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(out('UserHasDevices')[isDeleted=false].out('DeviceHasPayload')[isDeleted = false]) from User where idValue='"+userId+"'"));
		List<String>	devicePayloadIdList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");			
			devicePayloadIdList.add(id);
		}		
		return devicePayloadIdList;		
	}

	/**
	 * Gets the device payload id list.
	 *
	 * @param deviceId the device id
	 * @return the device payload id list
	 */
	public List<String> getDevicePayloadIdListFromDevice(String deviceId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(out('DeviceHasPayload')[isDeleted = false]) from Device where idValue='"+deviceId+"'"));
		List<String>	devicePayloadIdList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");			
			devicePayloadIdList.add(id);
		}		
		return devicePayloadIdList;		
	}
	

	/**
	 * Gets the device subscriber request list.
	 *
	 * @param deviceId the device id
	 * @return the device subscriber request list
	 */
	public List<String> getDeviceSubscriberRequestList(String deviceId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(inE('UserSubscribesDevice')[isAccepted=false].outV()) from Device where idValue='"+deviceId+"'"));
		List<String>	deviceSubscriberRequestList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");	
			deviceSubscriberRequestList.add(id);
		}		
		return deviceSubscriberRequestList;		
	}
	
	
	/**
	 * Gets the sensor subscriber request list.
	 *
	 * @param sensorId the sensor id
	 * @return the sensor subscriber request list
	 */
	public List<String> getSensorSubscriberRequestList(String sensorId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(inE('UserSubscribesSensor')[isAccepted=false].outV()) from Sensor where idValue='"+sensorId+"'"));
		List<String>	sensorSubscriberRequestList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");			
			sensorSubscriberRequestList.add(id);
		}		
		return sensorSubscriberRequestList;		
	}
	
	
	/**
	 * Gets the device subscription list.
	 *
	 * @param deviceId the device id
	 * @return the device subscription list
	 */
	public List<String> getDeviceSubscriptionList(String deviceId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(inE('UserSubscribesDevice')[isAccepted=true].outV()) from Device where idValue='"+deviceId+"'"));
		List<String>	deviceSubscriptionList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");	
			deviceSubscriptionList.add(id);
		}		
		return deviceSubscriptionList;		
	}
	
	
	/**
	 * Gets the sensor subscription list.
	 *
	 * @param sensorId the sensor id
	 * @return the sensor subscription list
	 */
	public List<String> getSensorSubscriptionList(String sensorId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(inE('UserSubscribesSensor')[isAccepted=true].outV()) from Sensor where idValue='"+sensorId+"'"));
		List<String>	sensorSubscriptionList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");			
			sensorSubscriptionList.add(id);
		}		
		return sensorSubscriptionList;		
	}
	
	
	/**
	 * Gets the user subscribed device list.
	 *
	 * @param userId the user id
	 * @return the user subscribed device list
	 */
	public List<String> getUserSubscribedDeviceList(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(outE('UserSubscribesDevice')[isAccepted=true].inV()[isDeleted=false]) from User where idValue='"+userId+"'"));
		List<String>	deviceSubscriptionList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");			
			deviceSubscriptionList.add(id);
		}		
		return deviceSubscriptionList;		
	}
	
	/**
	 * Gets the user subscribed sensor list.
	 *
	 * @param userId the user id
	 * @return the user subscribed sensor list
	 */
	public List<String> getUserSubscribedSensorList(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(outE('UserSubscribesSensor')[isAccepted=true].inV()[isDeleted=false]) from User where idValue='"+userId+"'"));
		List<String>	sensorSubscriptionList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			String id = result.field("idValue");			
			sensorSubscriptionList.add(id);
		}		
		return sensorSubscriptionList;		
	}

	
	/**
	 * Gets the friends id list.
	 *
	 * @param userId the user id
	 * @return the friends id list
	 */
	public List<String> getFriendsIdList(String userId){
		
		List<String>	followerIdList = new ArrayList<String>();
		List<ODocument> resultList1 = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(inE('UserFollowsUser')[isAccepted=true].outV()) from User where idValue='"+userId+"'"));
		List<ODocument> resultList2 = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(outE('UserFollowsUser')[isAccepted=true].inV()) from User where idValue='"+userId+"'"));
		
		for (ODocument result:resultList1) {			
			String id = result.field("idValue");			
			followerIdList.add(id);
		}		
		
		for (ODocument result:resultList2) {			
			String id = result.field("idValue");			
			followerIdList.add(id);
		}			
		return followerIdList;
	}
	
	/**
	 * Gets the friend request list.
	 *
	 * @param userId the user id
	 * @return the friend request list
	 */
	public List<FriendRequest> getFriendRequestList(String userId){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select expand(inE('UserFollowsUser')[isAccepted=false].outV()) from User where idValue='"+userId+"'"));
		List<FriendRequest>	friendRequestList = new ArrayList<FriendRequest>();
		
		for (ODocument result:resultList) {			
			
			FriendRequest friendRequest = new FriendRequest();
			String id 			= result.field("idValue");	
			String firstname 	= result.field("firstname");
			String lastname		= result.field("lastname");
			
			friendRequest.setFirstname(firstname);
			friendRequest.setLastname(lastname);
			friendRequest.setUserId(id);
			
			friendRequestList.add(friendRequest);
		}		
		return friendRequestList;	
	}
	
	
	/**
	 * Gets the sensor key list.
	 *
	 * @return the sensor key list
	 */
	public List<String> getSensorKeyList(){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select privateKey from Sensor where isDeleted=false"));
		List<String>	sensorKeyList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			sensorKeyList.add(result.field("privateKey"));			
		}		
		return sensorKeyList;		
	}
	
	/**
	 * Gets the sensor id list.
	 *
	 * @return the sensor id list
	 */
	public List<String> getSensorIdList(){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select idValue from Sensor where isDeleted=false"));
		List<String>	sensorIdList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			sensorIdList.add(result.field("idValue"));			
		}		
		return sensorIdList;	
	}

	/**
	 * Gets the device key list.
	 *
	 * @return the device key list
	 */
	public List<String> getDeviceKeyList(){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select privateKey from Device where isDeleted=false"));
		List<String>	deviceKeyList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			deviceKeyList.add(result.field("privateKey"));			
		}		
		return deviceKeyList;		
	}
	
	/**
	 * Gets the device id list.
	 *
	 * @return the device id list
	 */
	public List<String> getDeviceIdList(){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select idValue from Device where isDeleted=false"));
		List<String>	deviceIdList = new ArrayList<String>();
		
		for (ODocument result:resultList) {			
			deviceIdList.add(result.field("idValue"));			
		}		
		return deviceIdList;	
	}
	
	/**
	 * Gets the device id from key.
	 *
	 * @param key the key
	 * @return the device id from key
	 */
	public String getDeviceIdFromKey(String key){
		
		ODocument result =  (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("Select idValue from Device where privateKey='"+key+"' AND isDeleted=false")).get(0);
		String idValue = result.field("idValue");		
		
		return idValue;			
	}
	
	/**
	 * Gets the sensor id from key.
	 *
	 * @param key the key
	 * @return the sensor id from key
	 */
	public String getSensorIdFromKey(String key){
		
		ODocument result =  (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("Select idValue from Sensor where privateKey='"+key+"' AND isDeleted=false")).get(0);
		String idValue = result.field("idValue");		
		
		return idValue;			
	}
	
	
	/**
	 * Gets the key from sensor id.
	 *
	 * @param sensorId the sensor id
	 * @return the key from sensor id
	 */
	public String getKeyFromSensorId(String sensorId){
		
		ODocument result =  (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("Select privateKey from Sensor where idValue='"+sensorId+"' AND isDeleted=false")).get(0);
		String key = result.field("privateKey");		
		
		return key;			
	}
	
	
	/**
	 * Gets the key from device id.
	 *
	 * @param deviceId the device id
	 * @return the key from device id
	 */
	public String getKeyFromDeviceId(String deviceId){
		
		ODocument result =  (ODocument) graph.getRawGraph().query(new OSQLSynchQuery<Object>("Select privateKey from Device where idValue='"+deviceId+"' AND isDeleted=false")).get(0);
		String key = result.field("privateKey");		
		
		return key;			
	}

	
	/**
	 * Gets the user search list.
	 *
	 * @return the user search list
	 */
	public List<User> getUserSearchList(){
		
		List<ODocument> resultList = graph.getRawGraph().query(new OSQLSynchQuery<Object>("select from User where isDeleted = false"));
		List<User>	userList = new ArrayList<User>();
		
		
		for (ODocument result:resultList) {			
			
			String id 			= result.field("idValue");
			String email		= result.field("email");
			String firstname 	= result.field("firstname");
			String lastname		= result.field("lastname");
			
			User user = new User(firstname,lastname, email);
			user.setUid(id);
			user.setEmailHash();
			
			userList.add(user);			
		}	
		
		return userList;		
	}
	
	/**
	 * Sync database.
	 *
	 * @param email the email
	 */
	public void syncDatabase(String email){
		graph.getRawGraph().query(new OSQLSynchQuery<Object>("Select email from User where email='"+email+"'")).get(0);
	}
}
