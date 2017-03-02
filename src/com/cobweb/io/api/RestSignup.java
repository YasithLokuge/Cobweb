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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.cobweb.io.meta.Email;
import com.cobweb.io.meta.User;
import com.cobweb.io.service.CreateService;
import com.cobweb.io.service.ReadService;
import com.cobweb.io.service.UpdateService;
import com.cobweb.io.utils.FirstFriends;
import com.cobweb.io.utils.HashGenerator;
import com.cobweb.io.utils.SendMail;


/**
 * The Class RestSignup.
 * @author Yasith Lokuge
 */
@Path("/signup")
public class RestSignup {	
	
	private static Log log = LogFactory.getLog(RestSignup.class);
	
	/** The Constant idMap. */
	private static final Map<String, String> idMap = new HashMap<String, String>();
	
	/** The Constant AUTHENTICATION_ERROR. */
	private static final String AUTHENTICATION_ERROR 	= "{\"status\":\"logout to create new account\"}";	
	
	/** The Constant JSON_ERROR. */
	private static final String JSON_ERROR				= "{\"status\":\"JSON Parsing error\"}";	

	/** The Constant CONFIRMED_EMAIL. */
	private static final String CONFIRMED_EMAIL			= "{\"status\":\"Given email has an account already\"}";	
	
	/** The Constant UNCONFIRMED_EMAIL. */
	private static final String UNCONFIRMED_EMAIL		= "{\"status\":\"Check your email and confirm\"}";	
	
	/** The Constant UNCONFIRMED_SESSION. */
	private static final String UNCONFIRMED_SESSION		= "{\"status\":\"Unconfirmed session awaiting.Check your email and confirm\"}";	
		
	/** The Constant INTERNAL_ERROR. */
	private static final String INTERNAL_ERROR			= "{\"status\":\"Internal Error\"}";	
	
	/** The Constant SUCCESS. */
	private static final String SUCCESS					= "{\"status\":\"Success\"}";	
	
	public RestSignup() {
		BasicConfigurator.configure(); 
	}
	/**
	 * Signup.
	 *
	 * @param jsonData the json data
	 * @return the response
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String signup(InputStream jsonData, @Context UriInfo ui) {

		Subject currentUser = SecurityUtils.getSubject();
		
		if(currentUser.isAuthenticated())
			return AUTHENTICATION_ERROR;
		
		
		String firstName 	= null;
		String lastName 	= null;
		String email 		= null;
		String password 	= null;

		StringBuilder stringData = new StringBuilder();
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(jsonData));
			String line = null;
			while ((line = in.readLine()) != null) {
				stringData.append(line);
			}
		} catch (Exception e) {
			return JSON_ERROR;
		}		
		
		JSONObject incomingData;

		try {
			
			incomingData 	= new JSONObject(stringData.toString());
			firstName 		= (String) incomingData.get("firstname");
			lastName 		= (String) incomingData.get("lastname");
			email 			= (String) incomingData.get("email");
			password 		= (String) incomingData.get("password");
			
		} catch (JSONException e) {			
			return JSON_ERROR;				
		}
		
		ReadService readService = new ReadService();
		List<String> confirmedEmailList = readService.getConfirmedUserNamesList();
		List<String> unconfirmedEmailList = readService.getUnconfirmedUserNamesList();
		
		String sessionId = (String) currentUser.getSession().getId();
		
		if(confirmedEmailList.contains(email))
			return CONFIRMED_EMAIL;
		
		if(unconfirmedEmailList.contains(email))
			return UNCONFIRMED_EMAIL;
		
		if(idMap.values().contains(sessionId))
			return UNCONFIRMED_SESSION;
		
		HashGenerator hashGenerator = new HashGenerator();
		String salt = hashGenerator.generateSalt();		
		User userObj = new User(firstName,lastName, email, hashGenerator.saltHashPassword(password,salt) ,salt); 
    	CreateService createService = new CreateService();
    	userObj.setDeleted(true);
    	createService.CreateUser(userObj);
    	
		String userId	= userObj.getUid();
		
		
		idMap.put(userId, sessionId);
		
		SendMail sendMail = new SendMail();
		Email emailObj = new Email();
		
		String baseUrl = ui.getBaseUri().toString();
		
		emailObj.setTo(email);
		emailObj.setFrom("info@cobweb.io");
		emailObj.setSubject("Confirm Your Email Address for Cobweb IO account");
		emailObj.setBody("Hi "+firstName+" "+lastName+",\n \n Welcome to Cobweb.IO \n Congratulations, Your account has been successfully created, please click the following link to confirm your email address. \n "+baseUrl+"signup/"+userId+"\n \n Thank You!");
    	
		readService.syncDatabase(email);
		
		try {
			sendMail.send(emailObj);
		} catch (Exception e) {	
			log.error(e.toString());
			return INTERNAL_ERROR;
		}
		
		return SUCCESS;	
		
	}
	
	
	/**
	 * Validate.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("{userId}")
	public Response validate(@PathParam("userId") String userId, @Context UriInfo ui) {
		
		Subject currentUser = SecurityUtils.getSubject();
		String sessionId = (String) currentUser.getSession().getId();
		ReadService readService = new ReadService();
		URI targetURIForRedirection = null;
		String baseUrl = ui.getBaseUri().toString();
		
		
		if(!idMap.keySet().contains(userId) || !idMap.values().contains(sessionId)){
			
			String email = readService.getEmail(userId);
			readService.syncDatabase(email);

			List<String> unconfirmedEmailList = readService.getUnconfirmedUserNamesList();
			
			if(unconfirmedEmailList.contains(email)){				
				idMap.remove(userId);
				idMap.put(userId, sessionId);
				
				SendMail sendMail = new SendMail();
				Email emailObj = new Email();				
								
				emailObj.setTo(email);
				emailObj.setFrom("info@cobweb.io");
				emailObj.setSubject("Confirm Your Email Address for Cobweb IO account");
				emailObj.setBody("Please click the following link to confirm your email address. \n "+baseUrl+"signup/"+userId+"\n \n Thank You!");
		    	
				try {
					sendMail.send(emailObj);
				} catch (Exception e) {		
					targetURIForRedirection = UriBuilder.fromUri(baseUrl.replace("anon/", "web-anon/error.html?error=0x04")).build();
					return Response.seeOther(targetURIForRedirection).build();
				}				
				targetURIForRedirection = UriBuilder.fromUri(baseUrl.replace("anon/", "web-anon/error.html?error=0x02")).build();				
			}else{
				targetURIForRedirection = UriBuilder.fromUri(baseUrl.replace("anon/", "web-anon/error.html?error=0x03")).build();
			}
			
		}else{			
			UpdateService updateService = new UpdateService();			
			if(!updateService.activateUser(userId)){				
				targetURIForRedirection = UriBuilder.fromUri(baseUrl.replace("anon/", "web-anon/error.html?error=0x04")).build();
			}else{	
				idMap.remove(userId);	
				targetURIForRedirection = UriBuilder.fromUri(baseUrl.replace("anon/", "web-anon/error.html?error=0x01")).build();
				FirstFriends firstFriends = new FirstFriends();
				firstFriends.bootstrap(userId);
			}
		}	 		
		return Response.seeOther(targetURIForRedirection).build();
	}	
	
}
