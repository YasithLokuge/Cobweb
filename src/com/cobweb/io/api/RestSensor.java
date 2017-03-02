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
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.codehaus.jettison.json.JSONObject;

import com.cobweb.io.meta.Sensor;
import com.cobweb.io.meta.SensorType;
import com.cobweb.io.service.DeleteService;
import com.cobweb.io.service.ReadService;
import com.cobweb.io.utils.CobwebWeaver;
import com.cobweb.io.validator.SensorValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * @author Yasith Lokuge
 * The Class RestSensor.
 */
@Path("/sensor")
public class RestSensor {	
	
	/** The Constant SUCCESS. */
	private static final String SUCCESS					= "SUCCESS";
	
	/** The Constant ERROR. */
	private static final String ERROR					= "ERROR";
	
	/** The Constant INVALID_DEVICE_ID. */
	private static final String INVALID_DEVICE_ID		= "Invalid Device Id";
		
	/** The Constant JSON_ERROR. */
	private static final String JSON_ERROR				= "{\"status\":\"JSON Parsing error\"}";	

	/**
	 * Gets the sensor.
	 *
	 * @return the sensor
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getSensor(){
		
		ReadService readService = new ReadService();
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
				
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		List<Sensor> sensorList = new ArrayList<Sensor>(); 
		sensorList = readService.getSensorList(userId);
		
		try {
			return objectWriter.writeValueAsString(sensorList);
		} catch (JsonProcessingException e) {		
			return JSON_ERROR;
		}	
	} 	

	/**
	 * Creates the sensor.
	 *
	 * @param jsonData the json data
	 * @return the string
	 */
	@POST	
	@Consumes(MediaType.APPLICATION_JSON)	
	public String createSensor(InputStream jsonData) {

		
		
		String sensorName 	= null;
		String description	= null;
		String type 		= null;		
		String otherType	= null;
		String deviceId		= null;
		
		StringBuilder stringData = new StringBuilder();
		JSONObject incomingData;
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(jsonData));
			String line = null;
			while ((line = in.readLine()) != null) {
				stringData.append(line);
			}
			
			incomingData = new JSONObject(stringData.toString());
			sensorName 	= (String) incomingData.get("name");
			description = (String) incomingData.get("description");
			type 		= (String) incomingData.get("type");				
			otherType 	= (String) incomingData.get("otherType");
			deviceId	= (String) incomingData.get("deviceId");
			
		} catch (Exception e) {
			return ERROR;
		}
		
		
		SensorValidator sensorValidator = new SensorValidator();
		String validatorStatus = sensorValidator.validate(incomingData);
		
		if(!validatorStatus.equals(SUCCESS))	
			return validatorStatus;			
		
		
		ReadService readService = new ReadService();
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		
		List<String> deviceIdList = readService.getDeviceIdList(userId);
		
		if(!deviceIdList.contains(deviceId))
			return INVALID_DEVICE_ID;
		
		SensorType sensorType = SensorType.valueOf(type.toUpperCase());
				
		Sensor sensor = new Sensor(sensorName,description,sensorType);
		
		if(type.equalsIgnoreCase("OTHER")){
			sensor.setOtherType(otherType);
		}
		
		CobwebWeaver cobwebWeaver = new CobwebWeaver();		
		cobwebWeaver.addSensor(deviceId, sensor);

		return sensor.getId();
	}
	
	/**
	 * Delete sensor.
	 *
	 * @param sensorId the sensor id
	 * @return the response
	 */
	@DELETE
	@Consumes(MediaType.TEXT_PLAIN)
	public Response deleteSensor(String sensorId) {
		
		
		DeleteService deleteService = new DeleteService();
		ReadService readService = new ReadService();
		
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		
		List<String> sensorIdList = readService.getSensorIdList(userId);
			
		if(!sensorIdList.contains(sensorId))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		if(deleteService.deleteSensor(sensorId)){
			List<String> payloadIdList = readService.getSensorPayloadIdListFromSensor(sensorId);
			
			for (String payloadId : payloadIdList) {
				deleteService.deletePayload(payloadId);
			}
			
			return Response.status(Response.Status.OK).build();
		}else{
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
	}
}
