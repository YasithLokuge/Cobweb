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

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// TODO: Auto-generated Javadoc
/**
 * The Class Sensor.
 */
@JsonIgnoreProperties({"deleted","deviceType","privateKey"})
public class Sensor implements Item{

	/** The name. */
	private String name;
	
	/** The id. */
	private String id;
	
	/** The description. */
	private String description;
	
	/** The sensortype. */
	private SensorType sensortype;
	
	/** The is deleted. */
	private boolean isDeleted=false;
	
	/** The other type. */
	private String otherType ="default";
	
	/** The parent device id. */
	private String parentDeviceId;	
	
	
	/** The parent user id. */
	private String parentUserId;	
	
	
	/** The private key. */
	private String privateKey;	
	
	/**
	 * Instantiates a new sensor.
	 *
	 * @param name the name
	 * @param description the description
	 * @param sensortype the sensortype
	 * @param otherType the other type
	 */
	public Sensor(String name, String description, SensorType sensortype, String otherType){
		
		this.name = name;		
		this.description = description;
		this.sensortype = sensortype;
		this.otherType = otherType;
			
		UUID uuid = UUID.randomUUID();
		id = uuid.toString();
		
		UUID pkey = UUID.randomUUID();
		privateKey = pkey.toString();		
	}
	
	
	/**
	 * Instantiates a new sensor.
	 *
	 * @param name the name
	 * @param description the description
	 * @param sensortype the sensortype
	 */
	public Sensor(String name, String description, SensorType sensortype){
		this.name = name;		
		this.description = description;
		this.sensortype = sensortype;
		
		UUID uuid = UUID.randomUUID();
		id = uuid.toString();
		
		UUID pkey = UUID.randomUUID();
		privateKey = pkey.toString();		
		
	}
	
	/* (non-Javadoc)
	 * @see com.cobweb.io.core.Item#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see com.cobweb.io.core.Item#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.cobweb.io.core.Item#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see com.cobweb.io.core.Item#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see com.cobweb.io.core.Item#setDevId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.cobweb.io.core.Item#getDevId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see com.cobweb.io.core.Item#setDeleted(boolean)
	 */
	@Override
	public void setDeleted(boolean deleted) {
		this.isDeleted = deleted;
	}

	/* (non-Javadoc)
	 * @see com.cobweb.io.core.Item#isDeleated()
	 */
	@Override
	public boolean isDeleted() {
		return isDeleted;
	}

	

	/* (non-Javadoc)
	 * @see com.cobweb.io.core.Item#setType(com.cobweb.io.core.SensorType)
	 */
	@Override
	public void setType(SensorType sensortype) {
		this.sensortype = sensortype;
	}
	

	/* (non-Javadoc)
	 * @see com.cobweb.io.core.Item#sensortype()
	 */
	@Override
	public SensorType getSensorType() {
		return sensortype;
	}

	/* (non-Javadoc)
	 * @see com.cobweb.io.core.Item#setOtherType(java.lang.String)
	 */
	@Override
	public void setOtherType(String type) {
		otherType = type;
	}

	/* (non-Javadoc)
	 * @see com.cobweb.io.core.Item#getOtherType()
	 */
	@Override
	public String getOtherType() {		
		return otherType;
	}

	/* (non-Javadoc)
	 * @see com.cobweb.io.meta.Item#setType(com.cobweb.io.meta.DeviceType)
	 */
	@Override
	public void setType(DeviceType devicetype) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see com.cobweb.io.meta.Item#getDeviceType()
	 */
	@Override
	public DeviceType getDeviceType() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * Gets the parent device id.
	 *
	 * @return the parent device id
	 */	
	public String getParentDeviceId() {
		return parentDeviceId;
	}


	/**
	 * Sets the parent device id.
	 *
	 * @param deviceId the new parent device id
	 */
	public void setParentDeviceId(String deviceId) {
		this.parentDeviceId = deviceId;
	}


	public String getParentUserId() {
		return parentUserId;
	}


	public void setParentUserId(String parentUserId) {
		this.parentUserId = parentUserId;
	}


	public String getPrivateKey() {
		return privateKey;
	}


	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

}
