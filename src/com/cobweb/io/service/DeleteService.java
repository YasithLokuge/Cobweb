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


/**
 * The Class DeleteService.
 *
 * @author Yasith Lokuge
 */

public class DeleteService implements AbstractService{

	/**
	 * Delete device.
	 *
	 * @param deviceId the device id
	 * @return true, if successful
	 */
	public boolean deleteDevice(String deviceId){		
		int result = graph.command(new OCommandSQL("UPDATE Device SET isDeleted=true WHERE idValue = '"+deviceId+"'")).execute();
		return result == 1;
	}
	
	
	/**
	 * Delete sensor.
	 *
	 * @param sensorId the sensor id
	 * @return true, if successful
	 */
	public boolean deleteSensor(String sensorId){		
		int result = graph.command(new OCommandSQL("UPDATE Sensor SET isDeleted=true WHERE idValue = '"+sensorId+"'")).execute();
		return result == 1;
	}
	
	/**
	 * Delete payload.
	 *
	 * @param payloadId the payload id
	 * @return true, if successful
	 */
	public boolean deletePayload(String payloadId){		
		int result = graph.command(new OCommandSQL("UPDATE Payload SET isDeleted=true WHERE idValue = '"+payloadId+"'")).execute();
		return result == 1;
	}
	
	
	/**
	 * Delete user device subscription.
	 *
	 * @param userId the user id
	 * @param deviceId the device id
	 * @return true, if successful
	 */
	public boolean deleteUserDeviceSubscription(String userId, String deviceId){
		int result = graph.command(new OCommandSQL("DELETE EDGE UserSubscribesDevice WHERE outV().idValue ='"+userId+"' AND inV().idValue ='"+deviceId+"'")).execute();
		return result == 1;	
	}
	
	
	/**
	 * Delete user sensor subscription.
	 *
	 * @param userId the user id
	 * @param sensorId the sensor id
	 * @return true, if successful
	 */
	public boolean deleteUserSensorSubscription(String userId, String sensorId){
		int result = graph.command(new OCommandSQL("DELETE EDGE UserSubscribesSensor WHERE outV().idValue ='"+userId+"' AND inV().idValue ='"+sensorId+"'")).execute();
		return result == 1;	
	}
	
	/**
	 * Delete user follows user.
	 *
	 * @param userId the user id
	 * @param friendId the friend id
	 * @return true, if successful
	 */
	public boolean deleteUserFollowsUser(String userId, String friendId){
		int result1 = graph.command(new OCommandSQL("DELETE EDGE UserFollowsUser WHERE outV().idValue ='"+userId+"' AND inV().idValue ='"+friendId+"'")).execute();
		int result2 = graph.command(new OCommandSQL("DELETE EDGE UserFollowsUser WHERE outV().idValue ='"+friendId+"' AND inV().idValue ='"+userId+"'")).execute();
		
		if(result1 == 0 && result2 == 0)
			return false;
		else
			return true;
	}
	
	/**
	 * Delete user request user.
	 *
	 * @param friendId the friend id
	 * @param userId the user id
	 * @return true, if successful
	 */
	public boolean deleteUserRequestUser(String friendId, String userId){
		int result = graph.command(new OCommandSQL("DELETE EDGE UserFollowsUser WHERE outV().idValue ='"+friendId+"' AND inV().idValue ='"+userId+"'")).execute();
		return result == 1;	
	}
}
