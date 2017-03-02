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
package com.cobweb.io.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.cobweb.io.meta.SensorType;

/**
 * The Class SensorValidator.
 * @author Yasith Lokuge
 */
public class SensorValidator {

	/** The Constant NULL_SENSOR_NAME. */
	private static final String NULL_SENSOR_NAME 		= "Sensor name should not be null";
	
	/** The Constant NULL_DESCRIPTION. */
	private static final String NULL_DESCRIPTION 		= "Sensor description should not be null";
	
	/** The Constant NULL_SENSOR_TYPE. */
	private static final String NULL_SENSOR_TYPE 		= "Sensor type should not be null";
	
	/** The Constant NULL_OTHER_TYPE. */
	private static final String NULL_OTHER_TYPE 		= "Please submit the otherType parameter";	
	
	/** The Constant INVALID_SENSOR_NAME. */
	private static final String INVALID_SENSOR_NAME 	= "Invalid Sensor name";
	
	/** The Constant INVALID_DESCRIPTION. */
	private static final String INVALID_DESCRIPTION 	= "Invalid Description";
	
	/** The Constant INVALID_OTHER_TYPE. */
	private static final String INVALID_OTHER_TYPE 		= "Invalid Other type";
	
	/** The Constant SENSOR_TYPE_ERROR. */
	private static final String SENSOR_TYPE_ERROR 		= "Invalid Sensor type. user other type parameter";
	
	/** The Constant SENSOR_NAME_PATTERN. */
	private static final String SENSOR_NAME_PATTERN 	= "^[a-zA-Z0-9 ]*{3,15}$";
	
	/** The Constant OTHER_TYPE_PATTERN. */
	private static final String OTHER_TYPE_PATTERN 		= "^[a-zA-Z0-9 ]*{3,15}$";
	
	/** The Constant SENSOR_DESC_PATTERN. */
	private static final String SENSOR_DESC_PATTERN 	= "^[a-zA-Z0-9 ]*{3,200}$";
		
	/** The Constant SUCCESS. */
	private static final String SUCCESS					= "SUCCESS";
	


	/**
	 * Validate.
	 *
	 * @param jsonData the json data
	 * @return the string
	 */
	public String validate(JSONObject incomingData){
		
		String sensorName 	= null;
		String description	= null;
		String type 		= null;		
		String otherType	= null;			
		
		try {
			sensorName 	= (String) incomingData.get("name");
			description = (String) incomingData.get("description");
			type 		= (String) incomingData.get("type");			
			otherType 	= (String) incomingData.get("otherType");	
			
		} catch (JSONException e) {
			return e.toString();
		}				

		{	
			if(isNull(sensorName))
				return NULL_SENSOR_NAME;
			if(isNull(description))
				return NULL_DESCRIPTION;		
			if(isNull(type))
				return NULL_SENSOR_TYPE;
			if(type.equalsIgnoreCase("OTHER")){
				if(isNull(otherType))
					return NULL_OTHER_TYPE;
			}
		}
		
		{			
			if(!regexCheck(sensorName,SENSOR_NAME_PATTERN))				
				return INVALID_SENSOR_NAME;			
			if(!regexCheck(description,SENSOR_DESC_PATTERN))
				return INVALID_DESCRIPTION;		
			if(type.equalsIgnoreCase("OTHER")){
				if(!regexCheck(otherType,OTHER_TYPE_PATTERN))
					return INVALID_OTHER_TYPE;
			}			
			if(!contains(type))
				return SENSOR_TYPE_ERROR;
		}
		
		return SUCCESS;		
	}
	
	
	
	/**
	 * Contains.
	 *
	 * @param data the data
	 * @return true, if successful
	 */
	public boolean contains(String data) {
		
	    for (SensorType sensorType : SensorType.values()) {
	        if (sensorType.name().equals(data)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * Checks if is null.
	 *
	 * @param data the data
	 * @return true, if is null
	 */
	public boolean isNull(String data){		
		if(data == null || data.equalsIgnoreCase("") || data.equalsIgnoreCase("null"))
			return true;
		else
			return false;
	}
	
	 /**
 	 * Regex check.
 	 *
 	 * @param data the data
 	 * @param dataPattern the data pattern
 	 * @return true, if successful
 	 */
 	public boolean regexCheck(final String data, final String dataPattern){
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(dataPattern);
		matcher = pattern.matcher(data);
		return matcher.matches();
	 }
}
