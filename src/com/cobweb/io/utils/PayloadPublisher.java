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

import java.util.List;

import org.apache.log4j.Logger;

import com.cobweb.io.meta.Payload;
import com.cobweb.io.service.ReadService;

/**
 * The Class PayloadPublisher.
 * @author Yasith Lokuge
 */
public class PayloadPublisher {

	/** The Constant INVALID. */
	private static final String INVALID = "Invalid Private Key";
	
	/** The Constant SUCCESS. */
	private static final String SUCCESS = "Success";

	/** The Constant logger. */
	final static Logger logger = Logger.getLogger(PayloadPublisher.class);
	
	/**
	 * Publish.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the string
	 */
	public String publishCoap(String key, String value) {
		
		ReadService readService = new ReadService();
		CobwebWeaver cobwebWeaver = new CobwebWeaver();
		Payload payload = new Payload(value);
		
		List<String> sensorKeyList = readService.getSensorKeyList();
		List<String> deviceKeyList = readService.getDeviceKeyList();
		
		if(sensorKeyList.contains(key)){
			
			String sensorId = readService.getSensorIdFromKey(key);
			cobwebWeaver.addSensorPayload(sensorId, payload);
			
			logger.debug("CoAP sensor ID 		: " + sensorId);
			logger.debug("CoAP sensor payload 	: " + payload.getMessage());
			
			return SUCCESS;
			
		}else if(deviceKeyList.contains(key)){
			
			String deviceId = readService.getDeviceIdFromKey(key);
			cobwebWeaver.addDevicePayload(deviceId, payload);
			
			logger.debug("CoAP device ID 		: " + deviceId);
			logger.debug("CoAP device payload 	: " + payload.getMessage());
			
			return SUCCESS;
			
		}else{			
			return INVALID;
		}
		
	}

	
	/**
	 * Publish mqtt.
	 *
	 * @param topic the topic
	 * @param msg the msg
	 * @return the string
	 */
	public String publishMqtt(String topic, String msg) {
		
		ReadService readService = new ReadService();
		CobwebWeaver cobwebWeaver = new CobwebWeaver();
		Payload payload = new Payload(msg);
		
		List<String> sensorIdList = readService.getSensorIdList();
		List<String> deviceIdList = readService.getDeviceIdList();
		
		if(sensorIdList.contains(topic)){
			
			cobwebWeaver.addSensorPayload(topic, payload);			
			return SUCCESS;
			
		}else if(deviceIdList.contains(topic)){			
			
			cobwebWeaver.addDevicePayload(topic, payload);			
			return SUCCESS;
			
		}else{
			return INVALID;
		}		
	}
}
