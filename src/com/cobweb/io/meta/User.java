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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The Class User.
 *
 * @author Yasith Lokuge
 */

@JsonIgnoreProperties({"salt","deleted","password","role","email"})
public class User{
	
	/** The firstname. */
	private String firstName 	= "User";
	
	/** The lastname. */
	private String lastName 	= "User";
	
	/** The uid. */
	private String uid 		= "00000";
	
	/** The email. */
	private String email 	= "info@cobweb.io";
		
	/** The email hash. */
	private String emailHash = "info@cobweb.io";
		
	/** The password. */
	private String password = "admin";
	
	/** The salt. */
	private String salt		= "admin";
	
	/** The is deleted. */
	private boolean isDeleted = false;	
	
	/** The role. */
	private String role = "standard";		
	
	
	
	/**
	 * Instantiates a new user.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param email the email
	 * @param password the password
	 * @param salt the salt
	 */
	public User(String firstName,String lastName,String email, String password, String salt){
		this.firstName=firstName;
		this.lastName=lastName;
		this.email=email;
		this.salt=salt;
		this.password=password;	
		
		UUID uuid = UUID.randomUUID();
		uid = uuid.toString();			
	}
	
	
	/**
	 * Instantiates a new user.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param email the email
	 */
	public User(String firstName,String lastName, String email){
		this.firstName=firstName;
		this.lastName=lastName;	
		this.email = email;
	}
		
	/**
	 * Gets the fisrtName.
	 *
	 * @return the name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the firstname.
	 *
	 * @param name the name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	/**
	 * Gets the lastName.
	 *
	 * @return the name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the lastname.
	 *
	 * @param name the name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the uid.
	 *
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * Sets the uid.
	 *
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	

	/**
	 * Checks if is deleted.
	 *
	 * @return the isDeleted
	 */
	public boolean isDeleted() {
		return isDeleted;
	}

	/**
	 * Sets the deleted.
	 *
	 * @param isDeleted the isDeleted to set
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * Gets the salt.
	 *
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * Sets the salt.
	 *
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Gets the email hash.
	 *
	 * @return the email hash
	 */
	public String getEmailHash() {			
		return emailHash;
	}

	/**
	 * Sets the email hash.
	 *
	 * @param emailHash the new email hash
	 */
	public void setEmailHash() {
		 
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			String hashedEmail = (new HexBinaryAdapter()).marshal(md5.digest(getEmail().getBytes()));			
			this.emailHash=hashedEmail.toLowerCase();
		} catch (NoSuchAlgorithmException e) {			
			e.printStackTrace();
		}
	}

	

	
}
