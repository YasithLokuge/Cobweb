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
package com.cobweb.io.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.codehaus.jettison.json.JSONObject;

import com.cobweb.io.service.DeleteService;
import com.cobweb.io.service.ReadService;
import com.cobweb.io.service.UpdateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;



/**
 * The Class RestManageDevice.
 * @author Yasith Lokuge
 */
@Path("/device/subscriptions/manage")
public class RestManageDevice {

	/** The Constant JSON_ERROR. */
	private static final String JSON_ERROR				= "{\"status\":\"JSON Parsing error\"}";	
	
	/** The Constant SUCCESS. */
	private static final String SUCCESS					= "SUCCESS";
	
	/** The Constant ERROR. */
	private static final String ERROR					= "ERROR";
	
	/** The Constant UNKNOWN_DEVICE_ID. */
	private static final String UNKNOWN_DEVICE_ID 		= "Unknown Device Id or Unauthorized Device";
	
	
	/**
	 * Gets the requests.
	 *
	 * @return the requests
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getRequests(){
		
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		ReadService readService = new ReadService();
		
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		
		List<String> deviceIdList = readService.getDeviceIdList(userId);		
		Map<String,List<String>> subscriptionMap = new HashMap<String,List<String>>();
	
		for (String deviceId : deviceIdList) {
			List<String> subscriptionIdList = readService.getDeviceSubscriberRequestList(deviceId);
			
			if(!subscriptionIdList.isEmpty())
				subscriptionMap.put(deviceId,subscriptionIdList);				
		}
		
		try {
			return objectWriter.writeValueAsString(subscriptionMap);
		} catch (JsonProcessingException e) {
			return JSON_ERROR;
		}
		
	}	
	
	/**
	 * Adds the subscription.
	 *
	 * @param jsonData the json data
	 * @return the string
	 */
	@POST	
	@Consumes(MediaType.APPLICATION_JSON)	
	public String addSubscription(InputStream jsonData) {		
		
		String subscriberId 	= null;
		String deviceId	= null;
		boolean accept	= false;		
		
		StringBuilder stringData = new StringBuilder();
		JSONObject incomingData;
		
		ReadService readService		= new ReadService();
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		List<String> deviceIdList	= readService.getDeviceIdList(userId);
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(jsonData));
			String line = null;
			while ((line = in.readLine()) != null) {
				stringData.append(line);
			}
			
			incomingData = new JSONObject(stringData.toString());
			subscriberId = (String) incomingData.get("subscriberId");
			deviceId 	 = (String) incomingData.get("deviceId");
			accept 		 = (boolean) incomingData.get("accept");				
			
		} catch (Exception e) {
			return ERROR;
		}
		
		if(!deviceIdList.contains(deviceId))					
			return UNKNOWN_DEVICE_ID;	
		
		
		if(accept){
			UpdateService updateService = new UpdateService();
			boolean status = updateService.setUserDeviceSubscription(subscriberId, deviceId);
			
			if(status)
				return SUCCESS;
			else
				return ERROR;
			
		}else{
			DeleteService deleteService = new DeleteService();
			boolean status = deleteService.deleteUserDeviceSubscription(subscriberId, deviceId);
			
			if(status)
				return SUCCESS;
			else
				return ERROR;
		}
	}
	
	/**
	 * Delete subscription.
	 *
	 * @param jsonData the json data
	 * @return the string
	 */
	@DELETE	
	@Consumes(MediaType.APPLICATION_JSON)	
	public String deleteSubscription(InputStream jsonData) {		
		
		String subscriberId = null;
		String deviceId	= null;
		
		StringBuilder stringData = new StringBuilder();
		JSONObject incomingData;
		
		ReadService readService		= new ReadService();
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		List<String> deviceIdList	= readService.getDeviceIdList(userId);
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(jsonData));
			String line = null;
			while ((line = in.readLine()) != null) {
				stringData.append(line);
			}
			
			incomingData = new JSONObject(stringData.toString());
			subscriberId = (String) incomingData.get("subscriberId");
			deviceId 	 = (String) incomingData.get("deviceId");
			
		} catch (Exception e) {
			return ERROR;
		}
		
		if(!deviceIdList.contains(deviceId))					
			return UNKNOWN_DEVICE_ID;			
		
		DeleteService deleteService = new DeleteService();
		boolean status = deleteService.deleteUserDeviceSubscription(subscriberId, deviceId);
		
		if(status)
			return SUCCESS;
		else
			return ERROR;			
	}	
}
