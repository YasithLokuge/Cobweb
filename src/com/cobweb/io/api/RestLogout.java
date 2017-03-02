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

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.shiro.SecurityUtils;

@Path ("/logout")
public class RestLogout {
		
	@GET	
	public Response logout(@Context UriInfo ui) {
		String baseUrl = ui.getBaseUri().toString();
		SecurityUtils.getSubject().logout();				
		URI targetURIForRedirection;
		
		if(baseUrl.contains("localhost"))
			targetURIForRedirection = UriBuilder.fromUri("http://localhost:8080/").build();
		else
			targetURIForRedirection = UriBuilder.fromUri("http://www.cobweb.io/").build();
		
		
		return Response.seeOther(targetURIForRedirection).build();
	}	
}
