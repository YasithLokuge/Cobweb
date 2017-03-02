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


// TODO: Auto-generated Javadoc
/**
 * The Class ReadPropertyFile.
 *
 * @author Yasith Lokuge
 */

public class ReadPropertyFile {
	
	
	/** The database. */
	//private String DATABASE = "database_path";
	
	/** The db user. */
	//private String DB_USER  = "database_username";
	
	/** The db password. */
	//private String DB_PASSWORD = "database_password";
	
	/** The database url. */
	private String databaseUrl;
	
	/** The database user name. */
	private String databaseUserName;
	
	/** The database password. */
	private String databasePassword;
	
	/**
	 * Instantiates a new read property file.
	 */
	public ReadPropertyFile(){		
		
		//Properties settings = new Properties();
    	//InputStream input = null;
    	
    	databaseUrl = "remote:127.0.0.1/Cobweb";
	    databaseUserName = "root";
	    databasePassword = "C0bw3b105up3ru53r";
    	
    	/*try {
 
    		input = new FileInputStream("cobweb.properties");   		
    		settings.load(input);              
    		databaseUrl = settings.getProperty(DATABASE);
    	    databaseUserName = settings.getProperty(DB_USER);
    	    databasePassword = settings.getProperty(DB_PASSWORD);
 
    	} catch (IOException ex) {
    		
    		ex.printStackTrace();
        
    	} finally{
        	
        	if(input!=null){
        		try {
				input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }*/
		
	}
	
	/**
	 * Gets the DB url.
	 *
	 * @return the DB url
	 */
	public String getDBUrl(){		
		return databaseUrl;
	}
	
	/**
	 * Gets the DB user.
	 *
	 * @return the DB user
	 */
	public String getDBUser(){
		return databaseUserName;
	}
	
	/**
	 * Gets the DB pass.
	 *
	 * @return the DB pass
	 */
	public String getDBPass(){
		return databasePassword;
	}
	

}
