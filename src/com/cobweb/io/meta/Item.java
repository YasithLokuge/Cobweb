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

// TODO: Auto-generated Javadoc
/**
 * The Interface Item.
 *
 * @author Yasith Lokuge
 */

public interface Item {
	
	
	/**
	 * Sets the name.
	 */
	public void setName(String name);
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();
	
	/**
	 * Sets the description.
	 *
	 * @param Description the new description
	 */
	public void setDescription(String Description);
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription();
	
	/**
	 * Sets the id.
	 *
	 * @param devId the new id
	 */
	public void setId(String id);
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId();
	
	/**
	 * Sets the deleted.
	 *
	 * @param deleted the new deleted
	 */
	public void setDeleted(boolean deleted);
	
	/**
	 * Checks if is deleated.
	 *
	 * @return true, if is deleated
	 */
	public boolean isDeleted();
	
	/**
	 * Sets the type.
	 *
	 * @param devicetype the new type
	 */
	public void setType(DeviceType devicetype);
	
	/**
	 * Sets the type.
	 *
	 * @param sensortype the new type
	 */
	public void setType(SensorType sensortype);
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public DeviceType getDeviceType();
	
	/**
	 * Sensortype.
	 *
	 * @return the sensor type
	 */
	public SensorType getSensorType();

	
	/**
	 * Sets the other type.
	 *
	 * @param type the new other type
	 */
	public void setOtherType(String type);
	
	/**
	 * Gets the other type.
	 *
	 * @return the other type
	 */
	public String getOtherType();
	
	
}
