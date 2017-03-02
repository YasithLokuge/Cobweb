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
package com.cobweb.io.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServlet;

import com.cobweb.io.meta.Email;


/**
 * The Class SendMail.
 * @author Yasith Lokuge
 */
public class SendMail extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4012826357716915515L;
	
	/** The Constant SMTP_HOST_NAME. */
	private static final String SMTP_HOST_NAME = "HOST_NAME";
	
	/** The Constant SMTP_AUTH_USER. */
	private static final String SMTP_AUTH_USER = "USERNAME";
	
	/** The Constant SMTP_AUTH_PWD. */
	private static final String SMTP_AUTH_PWD = "PASSWORD";
	
	/** The Constant SMTP_PORT. */
	private static final int SMTP_PORT = 2525;

	/**
	 * Send.
	 *
	 * @param email the email
	 * @throws Exception the exception
	 */
	public void send(Email email) throws Exception {
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");

		Authenticator auth = new SMTPAuthenticator();
		Session mailSession = Session.getDefaultInstance(props, auth);
		Transport transport = mailSession.getTransport();

		MimeMessage message = new MimeMessage(mailSession);

		Multipart multipart = new MimeMultipart("alternative");

		// Sets up the contents of the email message
		BodyPart part1 = new MimeBodyPart();
		part1.setText(email.getBody());

		multipart.addBodyPart(part1);

		message.setContent(multipart);
		message.setFrom(new InternetAddress(email.getFrom(),"Cobweb IO"));
		message.setSubject(email.getSubject());
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));

		// Sends the email
		transport.connect(SMTP_HOST_NAME, SMTP_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();
	}

	/**
	 * The Class SMTPAuthenticator.
	 */
	// Authenticates to SendGrid
	private class SMTPAuthenticator extends javax.mail.Authenticator {
		
		/* (non-Javadoc)
		 * @see javax.mail.Authenticator#getPasswordAuthentication()
		 */
		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}
}
