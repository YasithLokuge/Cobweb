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

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.cobweb.io.meta.Payload;
import com.cobweb.io.service.DeleteService;
import com.cobweb.io.service.ReadService;
import com.cobweb.io.utils.CobwebWeaver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;




@Path("/device/message")
public class RestDevicePayload {

	/** The Constant UNKNOWN_DEVICE_ID. */
	private static final String UNKNOWN_DEVICE_ID 	= "{\"status\":\"Unknown Device Id or Unauthorized Device\"}";
	
	/** The Constant JSON_ERROR. */
	private static final String JSON_ERROR			= "{\"status\":\"JSON Parsing error\"}";	
		
	/** The Constant ERROR. */
	private static final String ERROR				= "ERROR";
	
	/**
	 * Gets the device payload.
	 *
	 * @param deviceId the device id
	 * @return the device payload
	 */
	@GET
	@Path("{deviceId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDevicePayload(@PathParam("deviceId") String deviceId){
		
		ReadService readService = new ReadService();
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		
		List<String> deviceIdList 			= readService.getDeviceIdList(userId);
		List<String> subscribedDeviceIdList = readService.getSubscribedDeviceIdList(userId);
		
		if(!(deviceIdList.contains(deviceId) || subscribedDeviceIdList.contains(deviceId)))					
			return UNKNOWN_DEVICE_ID;	
		
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		List<Payload> payloadList = new ArrayList<Payload>(); 
		payloadList = readService.getDevicePayloadList(deviceId);
		
		try {
			return objectWriter.writeValueAsString(payloadList);
		} catch (JsonProcessingException e) {		
			return JSON_ERROR;
		}
		
	}
	
	/**
	 * Creates the device payload.
	 *
	 * @param deviceId the device id
	 * @param message the message
	 * @return the response
	 */
	@POST	
	@Path("{deviceId}")
	@Consumes(MediaType.TEXT_PLAIN)	
	public String createDevicePayload(@PathParam("deviceId") String deviceId, String message) {
		
		Payload payload 			= new Payload(message);
		CobwebWeaver cobwebWeaver 	= new CobwebWeaver();
		ReadService readService 	= new ReadService();
		
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		List<String> deviceIdList = readService.getDeviceIdList(userId);
		
		if(!deviceIdList.contains(deviceId))					
			return ERROR;
		
		cobwebWeaver.addDevicePayload(deviceId, payload);
		
		return payload.getId();
	}
	
	/**
	 * Delete Device payload.
	 *
	 * @param payloadId the payload id
	 * @return the response
	 */
	@DELETE
	@Path("{payloadId}")
	public Response deleteDevicePayload(@PathParam("payloadId") String payloadId) {
		
		DeleteService deleteService = new DeleteService();
		ReadService readService = new ReadService();
		
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		
		List<String> devicePayloadIdList = readService.getDevicePayloadIdListFromUser(userId);
		
		if(!devicePayloadIdList.contains(payloadId))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		if(deleteService.deletePayload(payloadId))
			return Response.status(Response.Status.OK).build();
		else
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();				
	}
}
