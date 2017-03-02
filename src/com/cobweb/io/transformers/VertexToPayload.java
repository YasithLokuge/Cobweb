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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.cobweb.io.meta.Payload;
import com.tinkerpop.blueprints.Vertex;


/**
 * The Class VertexToPayload.
 * @author Yasith Lokuge
 */
public class VertexToPayload {

	
	/**
	 * Transform.
	 *
	 * @param payloadVertex the payload vertex
	 * @return the payload
	 */
	public Payload transform(Vertex payloadVertex){
		
		
		String message		= payloadVertex.getProperty("message");
		String id			= payloadVertex.getProperty("idValue");
		String dateTime		= payloadVertex.getProperty("dateTime");
		
		Payload payload = new Payload(message);
		payload.setId(id);
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		
		try {
			Date date = format.parse(dateTime);
			payload.setDateTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return payload;		
	}
}
