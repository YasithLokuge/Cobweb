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

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.cobweb.io.service.DeleteService;
import com.cobweb.io.service.ReadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Path("/device/subscribers")
public class RestDeviceSubscribers {

	/** The Constant JSON_ERROR. */
	private static final String JSON_ERROR				= "{\"status\":\"JSON Parsing error\"}";	
	
	/** The Constant UNKNOWN_DEVICE_ID. */
	private static final String UNKNOWN_DEVICE_ID 		= "{\"status\":\"Unknown Device Id or Unauthorized Device\"}";
	
	
	/**
	 * Gets the subscribers.
	 *
	 * @param deviceId the device id
	 * @return the subscribers
	 */
	@GET
	@Path("{deviceId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSubscribers(@PathParam("deviceId") String deviceId){
		
		ReadService readService = new ReadService();
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		
		List<String> userDeviceIdList = readService.getDeviceIdList(userId);
		
		if(!userDeviceIdList.contains(deviceId))
			return UNKNOWN_DEVICE_ID;

		List<String> subscribersIdList = readService.getDeviceSubscriptionList(deviceId);
		
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

		try {
			return objectWriter.writeValueAsString(subscribersIdList);
		} catch (JsonProcessingException e) {		
			return JSON_ERROR;
		}		
	}
	
	
	/**
	 * Delete device.
	 *
	 * @param deviceId the device id
	 * @param subscribersId the subscribers id
	 * @return the response
	 */
	@DELETE
	@Path("{deviceId}")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response deleteDevice(@PathParam("deviceId") String deviceId, String subscribersId) {
		
		ReadService readService = new ReadService();
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		
		List<String> userDeviceIdList = readService.getDeviceIdList(userId);
		List<String> subscribersIdList = readService.getDeviceSubscriptionList(deviceId);
		
		if(!userDeviceIdList.contains(deviceId))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		if(!subscribersIdList.contains(subscribersId))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		DeleteService deleteService = new DeleteService();
		
		if(deleteService.deleteUserDeviceSubscription(userId, deviceId))
			return Response.status(Response.Status.OK).build();
		else
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		
	}
}
