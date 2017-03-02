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

import com.cobweb.io.meta.User;
import com.tinkerpop.blueprints.Vertex;

// TODO: Auto-generated Javadoc
/**
 * The Class VertexToUser.
 * @author Yasith Lokuge
 */
public class VertexToUser {
	
	/**
	 * Transform.
	 *
	 * @param userVertex the user vertex
	 * @return the user
	 */
	public User transform(Vertex userVertex){
		
		String firstName 	= userVertex.getProperty("firstname");
		String lastName  	= userVertex.getProperty("lastname");
		String id        	= userVertex.getProperty("idValue");
		String email		= userVertex.getProperty("email");
		String password		= userVertex.getProperty("password");
		String salt 		= userVertex.getProperty("salt");
		String role			= userVertex.getProperty("role");
		
		User user = new User(firstName, lastName, email, password, salt);
		
		user.setRole(role);
		user.setUid(id);
		user.setDeleted(false);
		user.setEmailHash();
		
		return user;	
	}

}
