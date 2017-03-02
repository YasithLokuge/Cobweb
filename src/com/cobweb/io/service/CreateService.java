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

import com.cobweb.io.meta.Device;
import com.cobweb.io.meta.DeviceHasPayload;
import com.cobweb.io.meta.DeviceHasSensors;
import com.cobweb.io.meta.Payload;
import com.cobweb.io.meta.Sensor;
import com.cobweb.io.meta.SensorHasPayload;
import com.cobweb.io.meta.User;
import com.cobweb.io.meta.UserFollowsUser;
import com.cobweb.io.meta.UserHasDevices;
import com.cobweb.io.meta.UserSubscribesDevice;
import com.cobweb.io.meta.UserSubscribesSensor;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateService.
 *
 * @author Yasith Lokuge
 */

public class CreateService implements AbstractService{
	
	/** The name. */
	private final static String NAME 			= "name";
	
	/** The firstname. */
	private final static String FIRSTNAME 		= "firstname";
	
	/** The lastname. */
	private final static String LASTNAME 		= "lastname";
	
	/** The password. */
	private final static String PASSWORD 		= "password";
	
	/** The email. */
	private final static String EMAIL 			= "email";
	
	/** The salt. */
	private final static String SALT 			= "salt";	
	
	/** The id. */
	private final static String ID 				= "idValue";	
	
	/** The role. */
	private final static String ROLE			= "role";
	
	/** The description. */
	private final static String DESCRIPTION 	= "description";	
	
	/** The devicetype. */
	private final static String DEVICETYPE 		= "deviceType";	
	
	/** The sensortype. */
	private final static String SENSORTYPE 		= "sensorType";	
	
	/** The isdeleted. */
	private final static String ISDELETED 		= "isDeleted";	
	
	/** The Constant PRIVATEKEY. */
	private final static String PRIVATEKEY 		= "privateKey";

	/** The other type. */
	private final static String OTHERTYPE 		= "otherType";
	
	/** The isAccepted. */
	private final static String ISACCEPTED 		= "isAccepted";
	
	/** The message. */
	private final static String MESSAGE 		= "message";	
	
	/** The datetime. */
	private final static String DATETIME 		= "dateTime";	

	/** The userfollowsuser. */
	private final static String USERFOLLOWSUSER	= "UserFollowsUser";	
	
	/** The usersubscribesdevice. */
	private final static String USERSUBSCRIBESDEVICE	= "UserSubscribesDevice";	
	
	/** The usersubscribessensor. */
	private final static String USERSUBSCRIBESSENSOR	= "UserSubscribesSensor";		
	
	/** The userhasdevices. */
	private final static String USERHASDEVICES 			= "UserHasDevices";
	
	/** The devicehassensors. */
	private final static String DEVICEHASSENSORS 		= "DeviceHasSensors";
	
	/** The devicehaspayload. */
	private final static String DEVICEHASPAYLOAD 		= "DeviceHasPayload";
	
	/** The sensorhaspayload. */
	private final static String SENSORHASPAYLOAD 		= "SensorHasPayload";
	
	/**
	 * Creates the device.
	 *
	 * @param device the device
	 * @return the vertex
	 */
	public Vertex CreateDevice(Device device){			
		
		Vertex v = graph.command(new OCommandSQL("insert into Device ("+ NAME 			+","
															+ ID			+","
															+ DESCRIPTION	+","
															+ DEVICETYPE	+","
															+ ISDELETED		+","
															+ OTHERTYPE		+","
															+ PRIVATEKEY		+"	) values ('" 	
															
															+ device.getName()			+"'"+","+"'"
															+ device.getId()			+"'"+","+"'"
															+ device.getDescription()  	+"'"+","+"'"
															+ device.getDeviceType()			+"'"+","+"'"
															+ device.isDeleted()		+"'"+","+"'"
															+ device.getOtherType()		+"'"+","+"'"
															+ device.getPrivateKey()		+"')")).execute();
		
		graph.commit();
		return v;
	}
	
	/**
	 * Creates the sensor.
	 *
	 * @param sensor the sensor
	 * @return the vertex
	 */
	public Vertex CreateSensor(Sensor sensor){	
				
		Vertex v = graph.command(new OCommandSQL("insert into Sensor ("+ NAME 			+","
															+ ID			+","
															+ DESCRIPTION	+","
															+ SENSORTYPE	+","
															+ ISDELETED		+","
															+ OTHERTYPE		+","
															+ PRIVATEKEY		+"	) values ('" 	
															
															+ sensor.getName()			+"'"+","+"'"
															+ sensor.getId()			+"'"+","+"'"
															+ sensor.getDescription()  	+"'"+","+"'"
															+ sensor.getSensorType()	+"'"+","+"'"
															+ sensor.isDeleted()		+"'"+","+"'"
															+ sensor.getOtherType()		+"'"+","+"'"
															+ sensor.getPrivateKey()		+"')")).execute();
		graph.commit();
		return v;
	}

	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @return the vertex
	 */
	public Vertex CreateUser(User user){			
		
		Vertex v = graph.command(new OCommandSQL("insert into User (" 	+ FIRSTNAME 	+","
															+ LASTNAME		+","
															+ PASSWORD		+","
															+ EMAIL			+","
															+ SALT			+","
															+ ROLE			+","
															+ ID			+","
															+ ISDELETED		+"	) values ('" 	
															
															+ user.getFirstName()	+"'"+","+"'"
															+ user.getLastName()	+"'"+","+"'"
															+ user.getPassword()  	+"'"+","+"'"
															+ user.getEmail()		+"'"+","+"'"
															+ user.getSalt()		+"'"+","+"'"
															+ user.getRole()		+"'"+","+"'"
															+ user.getUid()			+"'"+","+"'"
															+ user.isDeleted()		+"')")).execute();
	
		graph.commit();
		return v;
	}
	
	
	
	/**
	 * Creates the payload.
	 *
	 * @param payload the payload
	 * @return the vertex
	 */
	public Vertex CreatePayload(Payload payload){				
		
		Vertex v = graph.command(new OCommandSQL("insert into Payload (" 	+ MESSAGE 	+","
																			+ DATETIME	+","
																			+ ISDELETED +","
																			+ ID		+") values ('" 
																			
																			+ payload.getMessage()		+"'"+","+"'"
																			+ payload.getTimeStamp()	+"'"+","+"'"
																			+ payload.isDeleted()		+"'"+","+"'"
																			+ payload.getId()+ "')")).execute();	
				
		graph.commit();
		return v;		
	}
	
	/**
	 * Creates the userSubscribes.
	 *
	 * @param userSubscribes the userSubscribes
	 * @return the edge
	 */
	public Edge CreateUserFollowsUser(UserFollowsUser userFollowsUser){
		
		Edge e = graph.addEdge(null, userFollowsUser.getUserOut()	, userFollowsUser.getUserIn(), USERFOLLOWSUSER);
		e.setProperty(ISACCEPTED, false);
		
		graph.commit();
		return e;
	}
	
	/**
	 * Creates the user subscribes device.
	 *
	 * @param userSubscribesDevice the user subscribes device
	 * @return the edge
	 */
	public Edge CreateUserSubscribesDevice(UserSubscribesDevice userSubscribesDevice){
		
		Edge e = graph.addEdge(null, userSubscribesDevice.getUser()	, userSubscribesDevice.getDevice(), USERSUBSCRIBESDEVICE);
		e.setProperty(ISACCEPTED, false);
		
		graph.commit();
		return e;
	}
	
	/**
	 * Creates the user subscribes sensor.
	 *
	 * @param userSubscribesSensor the user subscribes sensor
	 * @return the edge
	 */
	public Edge CreateUserSubscribesSensor(UserSubscribesSensor userSubscribesSensor){
		
		Edge e = graph.addEdge(null, userSubscribesSensor.getUser()	, userSubscribesSensor.getSensor(), USERSUBSCRIBESSENSOR);
		e.setProperty(ISACCEPTED, false);
		
		graph.commit();
		return e;
	}

	/**
	 * Creates the user has devices.
	 *
	 * @param userHasDevices the user has devices
	 * @return the edge
	 */
	public Edge CreateUserHasDevices(UserHasDevices userHasDevices){
		
		Edge e = graph.addEdge(null, userHasDevices.getUser(), userHasDevices.getDevice(), USERHASDEVICES);		
		
		graph.commit();
		return e;		
	}
	
	/**
	 * Creates the device has sensors.
	 *
	 * @param deviceHasSensors the device has sensors
	 * @return the edge
	 */
	public Edge CreateDeviceHasSensors(DeviceHasSensors deviceHasSensors){
		
		Edge e = graph.addEdge(null, deviceHasSensors.getDevice(), deviceHasSensors.getSensor(), DEVICEHASSENSORS);		
		
		graph.commit();
		return e;		
	}
	
	/**
	 * Creates the sensor has payload.
	 *
	 * @param sensorHasPayload the sensor has payload
	 * @return the edge
	 */
	public Edge CreateSensorHasPayload(SensorHasPayload sensorHasPayload){
		
		Edge e = graph.addEdge(null, sensorHasPayload.getSensor(), sensorHasPayload.getPayload(), SENSORHASPAYLOAD);		
		
		graph.commit();
		return e;		
	}

	
	/**
	 * Creates the device has payload.
	 *
	 * @param deviceHasPayload the device has payload
	 * @return the edge
	 */
	public Edge CreateDeviceHasPayload(DeviceHasPayload deviceHasPayload){
		
		Edge e = graph.addEdge(null, deviceHasPayload.getDevice(), deviceHasPayload.getPayload(), DEVICEHASPAYLOAD);
		
		graph.commit();
		return e;		
	}
	
}
