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

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.cobweb.io.meta.FriendRequest;
import com.cobweb.io.service.DeleteService;
import com.cobweb.io.service.ReadService;
import com.cobweb.io.service.UpdateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


/**
 * The Class RestFriendsManage.
 * @author Yasith Lokuge
 */
@Path("/friends/manage")
public class RestFriendsManage {

	/** The Constant JSON_ERROR. */
	private static final String JSON_ERROR				= "{\"status\":\"JSON Parsing error\"}";	
	
	/** The Constant SUCCESS. */
	private static final String SUCCESS					= "SUCCESS";
	
	/** The Constant ERROR. */
	private static final String ERROR					= "ERROR";
	
	/** The Constant UNKNOWN_USER_ID. */
	private static final String UNKNOWN_USER_ID 		= "Unknown User Id";
	
	
	/**
	 * Gets the requests.
	 *
	 * @return the requests
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getRequests(){
		
		ReadService readService = new ReadService();
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		
		List<FriendRequest> friendRequestList = readService.getFriendRequestList(userId);
		
		try {
			return objectWriter.writeValueAsString(friendRequestList);
		} catch (JsonProcessingException e) {
			return JSON_ERROR;
		}
	}
	
	/**
	 * Approve request.
	 *
	 * @param friendId the friend id
	 * @return the string
	 */
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public String approveRequest(String friendId){
		
		ReadService readService = new ReadService();
		
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		
		if(!readService.checkUserNameExistsById(friendId))
			return UNKNOWN_USER_ID;
		
		UpdateService updateService = new UpdateService();
		boolean status = updateService.setUserFollowsUser(friendId, userId);
		
		if(status)
			return SUCCESS;
		else
			return ERROR;
	}
	
	
	/**
	 * Delete request.
	 *
	 * @param friendId the friend id
	 * @return the string
	 */
	@DELETE
	@Consumes(MediaType.TEXT_PLAIN)
	public String deleteRequest(String friendId){
		
		ReadService readService = new ReadService();
		
		Subject currentUser = SecurityUtils.getSubject();
		String email = (String) currentUser.getPrincipal();
		String userId = readService.getUserId(email);
		
		if(!readService.checkUserNameExistsById(friendId))
			return UNKNOWN_USER_ID;
		
		DeleteService deleteService = new DeleteService();
		boolean status = deleteService.deleteUserRequestUser(friendId, userId);
		
		if(status)
			return SUCCESS;
		else
			return ERROR;
	}
	
}
