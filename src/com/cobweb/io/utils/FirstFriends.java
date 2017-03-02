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

import com.cobweb.io.service.UpdateService;

/**
 * The Class FirstFriends.
 * @author YasithLokuge
 */
public class FirstFriends {

	/** The Constant USER1_ID. */
	private static final String USER1_ID 		= "2cc1bc0b-8faf-4537-86b8-e2390051fed5";
	
	/** The Constant USER2_ID. */
	private static final String USER2_ID 		= "db36b40d-dbb2-48a1-b4bb-a5660b1b04db";
	
	/** The Constant USER1_DEVICE_ID. */
	private static final String USER1_DEVICE_ID = "15f358a3-f2ac-4372-9948-e3081631d45b";
	
	/** The Constant USER1_SENSOR_ID. */
	private static final String USER1_SENSOR_ID = "23894fdb-a97d-4e81-9929-6977513de430";
	
	/** The Constant USER2_DEVICE_ID. */
	private static final String USER2_DEVICE_ID = "b4892ea8-d3b9-4b4b-8827-c5bd67874a6b";
	
	/** The Constant USER2_SENSOR_ID. */
	private static final String USER2_SENSOR_ID = "7046a84a-a8f5-4a57-bf00-9f4ad64cdcec";
	
	
	/** The cobweb weaver. */
	CobwebWeaver cobwebWeaver = new CobwebWeaver();
	UpdateService updateService = new UpdateService();
	
	/**
	 * Bootstrap.
	 *
	 * @param UserId the user id
	 */
	public void bootstrap(String UserId){
		setFriends(UserId);
		subscribeItems(UserId);
	}
	
	/**
	 * Sets the friends.
	 *
	 * @param UserId the new friends
	 */
	private void setFriends(String UserId){		
		cobwebWeaver.addFollowUser(UserId,USER1_ID);
		cobwebWeaver.addFollowUser(UserId,USER2_ID);
		updateService.setUserFollowsUser(UserId, USER1_ID);
		updateService.setUserFollowsUser(UserId, USER2_ID);
	}
	
	/**
	 * Subscribe items.
	 *
	 * @param UserId the user id
	 */
	private void subscribeItems(String UserId){
		cobwebWeaver.addDeviceSubscription(UserId, USER1_DEVICE_ID);
		cobwebWeaver.addDeviceSubscription(UserId, USER2_DEVICE_ID);
		cobwebWeaver.addSensorSubscription(UserId, USER1_SENSOR_ID);
		cobwebWeaver.addSensorSubscription(UserId, USER2_SENSOR_ID);
		
		updateService.setUserDeviceSubscription(UserId, USER1_DEVICE_ID);
		updateService.setUserDeviceSubscription(UserId, USER2_DEVICE_ID);
		updateService.setUserSensorSubscription(UserId, USER1_SENSOR_ID);
		updateService.setUserSensorSubscription(UserId, USER2_SENSOR_ID);
	}
}
