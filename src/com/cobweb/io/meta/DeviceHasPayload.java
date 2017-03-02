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

import com.tinkerpop.blueprints.Vertex;

/**
 * The Class DeviceHasPayload.
 * @author YasithLokuge
 */
public class DeviceHasPayload {

	/** The device. */
	private Vertex device;
	
	/** The payload. */
	private Vertex payload;
	
	/**
	 * Gets the device.
	 *
	 * @return the device
	 */
	public Vertex getDevice() {
		return device;
	}
	
	/**
	 * Sets the device.
	 *
	 * @param device the new device
	 */
	public void setDevice(Vertex device) {
		this.device = device;
	}
	
	/**
	 * Gets the payload.
	 *
	 * @return the payload
	 */
	public Vertex getPayload() {
		return payload;
	}
	
	/**
	 * Sets the payload.
	 *
	 * @param payload the new payload
	 */
	public void setPayload(Vertex payload) {
		this.payload = payload;
	}
}
