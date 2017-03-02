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
package com.cobweb.io.meta;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// TODO: Auto-generated Javadoc
/**
 * The Class Payload.
 */
@JsonIgnoreProperties({"deleted"})
public class Payload {
	
	/** The id. */
	private String id;
	
	/** The message. */
	private String message = "default meaasage";
	
	/** The date time. */
	private Date dateTime;
	
	/** The is deleted. */
	private boolean isDeleted = false;	
	
	/** The device id. */
	private String deviceId;
	
	/** The sensor id. */
	private String sensorId;
	
	/** The user id. */
	private String userId;
	
	/**
	 * Instantiates a new payload.
	 *
	 * @param message the message
	 */
	public Payload(String message){
		this.message = message;		
		Date date = new Date();
		this.dateTime = date;
		
		UUID uuid = UUID.randomUUID();
		setId(uuid.toString());
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the date time.
	 *
	 * @return the date time
	 */
	public Date getDateTime() {
		return dateTime;
	}

	/**
	 * Sets the date time.
	 *
	 * @param dateTime the new date time
	 */
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * Checks if is deleted.
	 *
	 * @return true, if is deleted
	 */
	public boolean isDeleted() {
		return isDeleted;
	}

	/**
	 * Sets the deleted.
	 *
	 * @param isDeleted the new deleted
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	 * Gets the time stamp.
	 *
	 * @return the time stamp
	 */
	public String getTimeStamp(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(dateTime);
	}

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * Sets the device id.
	 *
	 * @param deviceId the new device id
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * Gets the sensor id.
	 *
	 * @return the sensor id
	 */
	public String getSensorId() {
		return sensorId;
	}

	/**
	 * Sets the sensor id.
	 *
	 * @param sensorId the new sensor id
	 */
	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
