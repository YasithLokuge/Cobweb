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
package com.cobweb.io.transformers;

import com.cobweb.io.meta.Device;
import com.cobweb.io.meta.DeviceType;
import com.tinkerpop.blueprints.Vertex;

// TODO: Auto-generated Javadoc
/**
 * The Class VertexToDevice.
 * @author Yasith Lokuge
 */
public class VertexToDevice {

	
	/**
	 * Transform.
	 *
	 * @param deviceVertex the device vertex
	 * @return the device
	 */
	public Device transform(Vertex deviceVertex){
		
		
		String name			= deviceVertex.getProperty("name");
		String id			= deviceVertex.getProperty("idValue");
		String deviceType	= deviceVertex.getProperty("deviceType");
		String description	= deviceVertex.getProperty("description");
		String otherType	= deviceVertex.getProperty("otherType");
		
		Device device = new Device(name, description, DeviceType.valueOf(deviceType));
		device.setId(id);
		device.setOtherType(otherType);		
		
		return device;		
	}
}
