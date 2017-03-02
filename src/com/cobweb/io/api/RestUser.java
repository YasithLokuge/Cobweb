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

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.cobweb.io.meta.User;
import com.cobweb.io.service.GraphFactory;
import com.cobweb.io.service.ReadService;
import com.cobweb.io.transformers.VertexToUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tinkerpop.blueprints.Vertex;


/**
 * The Class RestUser.
 * @author Yasith Lokuge
 */
@Path("/user")
public class RestUser {
	
	/** The Constant JSON_ERROR. */
	private static final String JSON_ERROR				= "{\"status\":\"JSON Parsing error\"}";	
	
	
	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser(){
		
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		
		ReadService readService = new ReadService();
		GraphFactory graphFactory = new GraphFactory();
		VertexToUser vertexToUser = new VertexToUser();
		String userId = readService.getUserId(email); 
		Vertex userVertex = graphFactory.getUserVertex(userId);
		User user = vertexToUser.transform(userVertex);	
		 
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();		
		
		try {
			return objectWriter.writeValueAsString(user);
		} catch (JsonProcessingException e) {			
			return JSON_ERROR;
		}		
	}	
	
	
	/**
	 * Gets the search.
	 *
	 * @return the search
	 */
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSearch(){
		
		ReadService readService = new ReadService();
		List<User> userList = readService.getUserSearchList();
		
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();		
		
		try {
			return objectWriter.writeValueAsString(userList);
		} catch (JsonProcessingException e) {		
			return JSON_ERROR;
		}			
	}
}
