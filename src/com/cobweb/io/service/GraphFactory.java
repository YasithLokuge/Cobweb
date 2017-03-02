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
package com.cobweb.io.service;

import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Vertex;


/**
 * A factory for creating Graph objects.
 * @author YasithLokuge
 * 
 */
public class GraphFactory implements AbstractService{

	
	/**
	 * Gets the user vertex.
	 *
	 * @param userId the user id
	 * @return the user vertex
	 */
	public Vertex getUserVertex(String userId){		
		
		
		Iterable<Vertex> v 		= graph.command(new OCommandSQL("select from User where idValue=\""+userId+"\"")).execute();		
		return graph.getVertex(v.iterator().next().getId());		
	}
	

	/**
	 * Gets the device vertex.
	 *
	 * @param deviceId the device id
	 * @return the device vertex
	 */
	public Vertex getDeviceVertex(String deviceId){
		
		Iterable<Vertex> v 		= graph.command(new OCommandSQL("select from Device where idValue=\""+deviceId+"\"")).execute();		
		return graph.getVertex(v.iterator().next().getId());	
	}
	
	
	/**
	 * Gets the sensor vertex.
	 *
	 * @param sensorId the sensor id
	 * @return the sensor vertex
	 */
	public Vertex getSensorVertex(String sensorId){
		
		Iterable<Vertex> v 		= graph.command(new OCommandSQL("select from Sensor where idValue=\""+sensorId+"\"")).execute();		
		return graph.getVertex(v.iterator().next().getId());	
	}
		
	/**
	 * Gets the payload vertex.
	 *
	 * @param payloadId the payload id
	 * @return the payload vertex
	 */
	public Vertex getPayloadVertex(String payloadId){
		
		Iterable<Vertex> v 		= graph.command(new OCommandSQL("select from Payload where idValue=\""+payloadId+"\"")).execute();		
		return graph.getVertex(v.iterator().next().getId());	
	}

	
	
}
