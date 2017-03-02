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
package com.cobweb.io.meta;

import com.tinkerpop.blueprints.Vertex;

// TODO: Auto-generated Javadoc
/**
 * The Class UserSubscribes.
 */
public class UserFollowsUser {
	
	
	/** The user in. */
	private Vertex userIn;
	
	/** The user out. */
	private Vertex userOut;
	
	/**
	 * Gets the user in.
	 *
	 * @return the user in
	 */
	public Vertex getUserIn() {
		return userIn;
	}

	/**
	 * Sets the user in.
	 *
	 * @param userIn the new user in
	 */
	public void setUserIn(Vertex userIn) {
		this.userIn = userIn;
	}

	/**
	 * Gets the user out.
	 *
	 * @return the user out
	 */
	public Vertex getUserOut() {
		return userOut;
	}

	/**
	 * Sets the user out.
	 *
	 * @param userOut the new user out
	 */
	public void setUserOut(Vertex userOut) {
		this.userOut = userOut;
	}	
}
