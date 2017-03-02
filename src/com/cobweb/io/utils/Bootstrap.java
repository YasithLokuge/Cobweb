/**
 * Run only once
 */
package com.cobweb.io.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cobweb.io.service.AbstractService;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.tinkerpop.blueprints.Vertex;

/**
 * The Class Bootstrap.
 *
 * @author Yasith Lokuge
 */
public class Bootstrap implements AbstractService {

	/** The firstname. */
	private final static String FIRSTNAME 		= "firstname";
	
	/** The lastname. */
	private final static String LASTNAME 		= "lastname";
	
	/** The name. */
	private final static String NAME 			= "name";
	
	/** The password. */
	private final static String PASSWORD 		= "password";
	
	/** The email. */
	private final static String EMAIL 			= "email";
	
	/** The salt. */
	private final static String SALT 			= "salt";	
		
	/** The Constant ROLE. */
	private final static String ROLE			= "role";
	
	/** The id. */
	private final static String ID 				= "idValue";	
	
	/** The Constant PRIVATEKEY. */
	private final static String PRIVATEKEY 		= "privateKey";	
	
	/** The description. */
	private final static String DESCRIPTION 	= "description";	
	
	/** The devicetype. */
	private final static String DEVICETYPE 		= "deviceType";	
	
	/** The sensortype. */
	private final static String SENSORTYPE 		= "sensorType";	
	
	/** The isdeleted. */
	private final static String ISDELETED 		= "isDeleted";	
	
	/** The other type. */
	private final static String OTHERTYPE 		= "otherType";
	
	/** The message. */
	private final static String MESSAGE 		= "message";
	
	/** The date and time. */
	private final static String DATETIME 		= "dateTime";
	
	/** The log. */
	private static Log log = LogFactory.getLog(Bootstrap.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		
			
		//------------------Creating Vertices----------------------------//
		log.debug("//------------------Creating Vertices----------------------------//");			
		/**
		 * Create User Class
		 */
		log.debug("--Creating User Vertex Class--");		
		graph.createVertexType("User");
		Vertex user = graph.addVertex("class:User");		
		user.setProperty(FIRSTNAME, OType.STRING	);		
		user.setProperty(LASTNAME, 	OType.STRING	);
		user.setProperty(PASSWORD, 	OType.STRING	);
		user.setProperty(EMAIL, 	OType.STRING	);
		user.setProperty(SALT, 		OType.STRING	);
		user.setProperty(ROLE, 		OType.STRING	);
		user.setProperty(ID, 		OType.STRING	);		
		user.setProperty(ISDELETED, OType.BOOLEAN	);
		
		
		/**
		 * Create Device Class
		 */
		log.debug("--Creating Device Vertex Class--");		
		graph.createVertexType("Device");
		Vertex device = graph.addVertex("class:Device");	
		
		device.setProperty(NAME, 		OType.STRING	);
		device.setProperty(DESCRIPTION,	OType.STRING	);
		device.setProperty(DEVICETYPE, 	OType.STRING	);
		device.setProperty(ID, 			OType.STRING	);
		device.setProperty(PRIVATEKEY, 	OType.STRING	);
		device.setProperty(ISDELETED,	OType.BOOLEAN	);
		device.setProperty(OTHERTYPE, 	OType.STRING	);
				
		/**
		 * Create Sensor Class
		 */		
		log.debug("--Creating Sensor Vertex Class--/");		
		graph.createVertexType("Sensor");
		Vertex sensor = graph.addVertex("class:Sensor");
		
		sensor.setProperty(NAME, 		OType.STRING	);
		sensor.setProperty(ID, 			OType.STRING	);
		sensor.setProperty(DESCRIPTION, OType.STRING	);
		sensor.setProperty(SENSORTYPE, 	OType.STRING	);		
		sensor.setProperty(PRIVATEKEY, 	OType.STRING	);
		sensor.setProperty(ISDELETED, 	OType.BOOLEAN	);
		sensor.setProperty(OTHERTYPE, 	OType.STRING	);

		/**
		 * Create Payload Class
		 */
		log.debug("--Creating Payload Vertex Class--");		
		graph.createVertexType("Payload");
		Vertex payload = graph.addVertex("class:Payload");
		
		payload.setProperty(MESSAGE, 	OType.STRING	);
		payload.setProperty(DATETIME, 	OType.DATETIME	);
		payload.setProperty(ID, 		OType.STRING	);
		payload.setProperty(ISDELETED, 	OType.BOOLEAN	);
		graph.commit();
		
		
		CreateFirstFriends createFirstFriends = new CreateFirstFriends();
		createFirstFriends.create();
		
	}

}
