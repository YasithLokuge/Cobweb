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

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.cobweb.io.meta.Payload;
import com.cobweb.io.service.DeleteService;
import com.cobweb.io.service.GraphFactory;
import com.cobweb.io.service.ReadService;
import com.cobweb.io.transformers.VertexToPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tinkerpop.blueprints.Vertex;

@Path("/payload")
public class RestGetPayload {

	/** The Constant JSON_ERROR. */
	private static final String JSON_ERROR				= "{\"status\":\"JSON Parsing error\"}";	
	
	/** The Constant UNKNOWN_DEVICE_ID. */
	private static final String UNKNOWN_PAYLOAD_ID 		= "{\"status\":\"Unknown Payload Id or Unauthorized\"}";
	
	
	/**
	 * Gets the payload.
	 *
	 * @param payloadId the payload id
	 * @return the payload
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{payloadId}")
	public String getPayload(@PathParam("payloadId") String payloadId){
		
		
		ReadService readService = new ReadService();
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		
		List<String> userDevicePayloadIdList 	= readService.getDevicePayloadIdListFromUser(userId);
		List<String> userSensorPayloadIdList 	= readService.getSensorPayloadIdListFromUser(userId);
		List<String> friendDevicePayloadIdList	= readService.getSubscribedDevicePayloadIdList(userId);
		List<String> friendSensorPayloadIdList 	= readService.getSubscribedSensorPayloadIdList(userId);

		
		if(!(userDevicePayloadIdList.contains(payloadId) || userSensorPayloadIdList.contains(payloadId) || friendDevicePayloadIdList.contains(payloadId) || friendSensorPayloadIdList.contains(payloadId)))
			return UNKNOWN_PAYLOAD_ID;
		

		GraphFactory graphFactory = new GraphFactory();
		VertexToPayload toPayload = new VertexToPayload();
		Vertex payloadVertex = graphFactory.getPayloadVertex(payloadId);
		Payload payload = toPayload.transform(payloadVertex);
		
		String sensorId;
		String deviceId;

		try {
			sensorId = readService.getParentSensorIdFromPayload(payloadId);
		} catch (Exception e1) {
			sensorId = null;
		}
		
		try {
			deviceId = readService.getParentDeviceIdFromPayload(payloadId);
		} catch (Exception e1) {
			deviceId = null;
		}
		
		String ownerId = readService.getParentUserIdFromPayload(payloadId);
		
		payload.setSensorId(sensorId);
		payload.setDeviceId(deviceId);
		payload.setUserId(ownerId);
		
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

		try {
			return objectWriter.writeValueAsString(payload);
		} catch (JsonProcessingException e) {		
			return JSON_ERROR;
		}
			
	}
	
	
	/**
	 * Delete payload.
	 *
	 * @param payloadId the payload id
	 * @return the response
	 */
	@DELETE
	@Path("{payloadId}")
	public Response deletePayload(@PathParam("payloadId") String payloadId){
		
		ReadService readService = new ReadService();
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		
		List<String> userDevicePayloadIdList 	= readService.getDevicePayloadIdListFromUser(userId);
		List<String> userSensorPayloadIdList 	= readService.getSensorPayloadIdListFromUser(userId);
		
		if(!(userDevicePayloadIdList.contains(payloadId) || userSensorPayloadIdList.contains(payloadId)))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		DeleteService deleteService = new DeleteService();
		
		if(deleteService.deletePayload(payloadId))
			return Response.status(Response.Status.OK).build();
		else
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();		
	}
}
