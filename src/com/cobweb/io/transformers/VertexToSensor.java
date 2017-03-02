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

import com.cobweb.io.meta.Sensor;
import com.cobweb.io.meta.SensorType;
import com.tinkerpop.blueprints.Vertex;

// TODO: Auto-generated Javadoc
/**
 * The Class VertexToSensor.
 * @author Yasith Lokuge
 */
public class VertexToSensor {

	
	/**
	 * Transform.
	 *
	 * @param sensorVertex the sensor vertex
	 * @return the sensor
	 */
	public Sensor transform(Vertex sensorVertex){
		
		
		String name			= sensorVertex.getProperty("name");
		String id			= sensorVertex.getProperty("idValue");
		String sensorType	= sensorVertex.getProperty("sensorType");
		String description	= sensorVertex.getProperty("description");
		String otherType	= sensorVertex.getProperty("otherType");
		
		Sensor sensor = new Sensor(name, description, SensorType.valueOf(sensorType));
		sensor.setId(id);
		sensor.setOtherType(otherType);	
		
		
		return sensor;		
	}
}
