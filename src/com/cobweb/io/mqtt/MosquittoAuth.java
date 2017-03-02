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
package com.cobweb.io.mqtt;

import java.util.List;

import com.cobweb.io.service.ReadService;
import com.cobweb.io.utils.HashGenerator;

/**
 * The Class MosquittoAuth.
 * @author Yasith Lokuge
 */
public class MosquittoAuth {

	/** The read service. */
	ReadService readService = new ReadService();
	
	/**
	 * Check user name.
	 *
	 * @param username the username
	 * @return true, if successful
	 */
	public boolean checkUserName(String username){
		return readService.checkUserNameExistsByEmail(username);
	}
	
	/**
	 * Check password.
	 *
	 * @param email the email
	 * @param password the password
	 * @return true, if successful
	 */
	public boolean authCheck(String email ,String password){
		
		HashGenerator hashGenerator = new HashGenerator();
		String userId = readService.getUserId(email);
		
		if(userId == null)
			return false;
		
		String salt = readService.getSalt(userId);
		String saltedPass = hashGenerator.saltHashPassword(password, salt);
		
		return readService.checkPasswordExists(saltedPass) && checkUserName(email);		 
	}
	
	
	/**
	 * Acl check.
	 *
	 * @param email the email
	 * @param topic the topic
	 * @return true, if successful
	 */
	public boolean aclCheck(String email ,String topic){
		
		String userId = readService.getUserId(email);
		
		List<String> sensorIdList = readService.getSensorIdList(userId);
		List<String> deviceIdList = readService.getDeviceIdList(userId);
		
		return sensorIdList.contains(topic) || deviceIdList.contains(topic);		
	}
}
