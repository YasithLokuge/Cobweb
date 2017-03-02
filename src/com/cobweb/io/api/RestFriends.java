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

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.cobweb.io.meta.User;
import com.cobweb.io.service.DeleteService;
import com.cobweb.io.service.GraphFactory;
import com.cobweb.io.service.ReadService;
import com.cobweb.io.transformers.VertexToUser;
import com.cobweb.io.utils.CobwebWeaver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tinkerpop.blueprints.Vertex;


/**
 * The Class RestFriends.
 * @author Yasith Lokuge
 */
@Path("/friends")
public class RestFriends {

	/** The Constant JSON_ERROR. */
	private static final String JSON_ERROR				= "{\"status\":\"JSON Parsing error\"}";	
	
	/** The Constant SUCCESS. */
	private static final String SUCCESS					= "SUCCESS";
	
	/** The Constant ERROR. */
	private static final String ERROR					= "ERROR";
	
	/** The Constant UNKNOWN_SENSOR_ID. */
	private static final String UNKNOWN_USER_ID 		= "Unknown User Id";
	
	/** The Constant UNKNOWN_SENSOR_ID. */
	private static final String ALREADY_FRIEND 			= "Already a Friend";
	
	
	/**
	 * Gets the friends.
	 *
	 * @return the friends
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getFriends(){
		
		ReadService readService = new ReadService();
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		
		List<String> friendIdList	= readService.getFriendsIdList(userId);		
		List<User> friendObjList 	= new ArrayList<User>();
		GraphFactory graphFactory 	= new GraphFactory();
		VertexToUser vertexToUser 	= new VertexToUser();
		
		for (String friendId : friendIdList) {
			Vertex userVertex = graphFactory.getUserVertex(friendId);
			User user 		  = vertexToUser.transform(userVertex);
			friendObjList.add(user);
		}

		try {
			return objectWriter.writeValueAsString(friendObjList);
		} catch (JsonProcessingException e) {
			return JSON_ERROR;
		}
	}
	
	/**
	 * Creates the friend.
	 *
	 * @param friendId the friend id
	 * @return the string
	 */
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public String createFriend(String friendId){
		
		ReadService readService = new ReadService();
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		List<String> friendIdList = readService.getFriendsIdList(userId);
		
		if(friendIdList.contains(friendId))
			return ALREADY_FRIEND;
		
		if(!readService.checkUserNameExistsById(friendId))
			return UNKNOWN_USER_ID;
		
		CobwebWeaver cobwebWeaver = new CobwebWeaver();
		cobwebWeaver.addFollowUser(userId, friendId);
		
		return SUCCESS;		
	}
	
	/**
	 * Delete friend.
	 *
	 * @param friendId the friend id
	 * @return the string
	 */
	@DELETE
	@Consumes(MediaType.TEXT_PLAIN)
	public String deleteFriend(String friendId){
		
		ReadService readService = new ReadService();
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		List<String> friendIdList = readService.getFriendsIdList(userId);
		
		if(!friendIdList.contains(friendId))
			return ERROR;
		
		DeleteService deleteService = new DeleteService();
		
		if(deleteService.deleteUserFollowsUser(userId, friendId))
			return SUCCESS;
		else
			return ERROR;
	}
}
