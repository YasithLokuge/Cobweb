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

import com.orientechnologies.orient.core.sql.OCommandSQL;

// TODO: Auto-generated Javadoc
/**
 * The Class UpdateService.
 *
 * @author Yasith Lokuge
 */

public class UpdateService implements AbstractService{

	/**
	 * Sets the user device subscription.
	 *
	 * @param userId the user id
	 * @param deviceId the device id
	 * @return true, if successful
	 */
	public boolean setUserDeviceSubscription(String userId, String deviceId){
		graph.command(new OCommandSQL("UPDATE UserSubscribesDevice SET isAccepted=true WHERE outV().idValue = '"+userId+"' AND inV().idValue ='"+deviceId+"'")).execute();
		return true;	
	}
	
	/**
	 * Sets the user sensor subscription.
	 *
	 * @param userId the user id
	 * @param sensorId the sensor id
	 * @return true, if successful
	 */
	public boolean setUserSensorSubscription(String userId, String sensorId){
		graph.command(new OCommandSQL("UPDATE UserSubscribesSensor SET isAccepted=true WHERE outV().idValue = '"+userId+"' AND inV().idValue ='"+sensorId+"'")).execute();
		return true;	
	}
	
	/**
	 * Sets the user follows user.
	 *
	 * @param friendId the friend id
	 * @param userId the user id
	 * @return true, if successful
	 */
	public boolean setUserFollowsUser(String friendId, String userId){
		graph.command(new OCommandSQL("UPDATE UserFollowsUser SET isAccepted=true WHERE outV().idValue = '"+friendId+"' AND inV().idValue ='"+userId+"'")).execute();
		return true;	
	}
	
	/**
	 * Activate user.
	 *
	 * @param userId the user id
	 * @return true, if successful
	 */
	public boolean activateUser(String userId){		
		graph.command(new OCommandSQL("UPDATE User SET isDeleted=false WHERE idValue = '"+userId+"'")).execute();
		return true;
	}
}
