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

import com.tinkerpop.blueprints.impls.orient.OrientGraph;

// TODO: Auto-generated Javadoc
/**
 * The Class ServiceSamurai.
 *
 * @author Yasith Lokuge
 */

public class ServiceSamurai {
	
	/** The dburl. */
	private String DBURL;
	
	/** The dbuser. */
	private String DBUSER;
	
	/** The dbpass. */
	private String DBPASS;
	
	/** The instance. */
	private static ServiceSamurai instance = null;
	
	/**
	 * Instantiates a new service samurai.
	 */
	protected ServiceSamurai() {
	      // Exists only to defeat instantiation.
	}
	
	/**
	 * Gets the single instance of ServiceSamurai.
	 *
	 * @return single instance of ServiceSamurai
	 */
	public static ServiceSamurai getInstance() {	  
		if(instance == null) {	     
			instance = new ServiceSamurai();	  
		}      
		return instance;
	}
	
	/**
	 * Gets the graph db.
	 *
	 * @return the graph db
	 */
	public OrientGraph getGraphDB(){		
		
		ReadPropertyFile readFile = new ReadPropertyFile();		
		
		DBURL 	= readFile.getDBUrl();
		DBUSER 	= readFile.getDBUser();
		DBPASS 	= readFile.getDBPass();
		
		OrientGraph graph = new OrientGraph(DBURL,DBUSER, DBPASS);
		
		return graph;
		
		
	}
	
	
	   
	   

}
