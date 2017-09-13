package com.boot.sendmail;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage; 
import javax.mail.internet.MimeMultipart;

public class SendMail
{
	public String sendMail(String email_id,String data,String activity,String[] year)
	{
		String y=year[0];
		
		for(int i=1;i<year.length;i++){
			y=y+","+year[i];
		}
		final String from="kishorkaware.vyomlabs@gmail.com";//change accordingly  
		final String password="Vyomlabs@KK";//change accordingly  
		System.out.println("Sending mail to "+email_id);
		System.out.println("data="+data+"\nActivity="+activity+"\n year="+year);
		Properties props = new Properties();    
		props.put("mail.smtp.host", "smtp.gmail.com");    
		props.put("mail.smtp.socketFactory.port", "465");    
		props.put("mail.smtp.socketFactory.class",    
				"javax.net.ssl.SSLSocketFactory");    
		props.put("mail.smtp.auth", "true");    
		props.put("mail.smtp.port", "587");    

		Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator()
		{  protected PasswordAuthentication getPasswordAuthentication()
		{ return new PasswordAuthentication(from,password); } } );


		try{  
			MimeMessage message = new MimeMessage(session);  
			message.setFrom(new InternetAddress(from));  
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(email_id));  
			message.setSubject("Regarding Economy Investment"); 

			BodyPart messageBodyPart1 = new MimeBodyPart();
			String mgs="Hello,"
					  +"\n      your requested detail for Investment in "+activity+" for year "+y+" is: "
			          +"\n         "+data
			          +"\n      Thanks for contacting us."
			          +"\n"
			          +"\nWarm Regard,"
			          +"\nFederel Statistics";
			System.out.println(mgs);
			messageBodyPart1.setText(mgs);  

			Multipart multipart = new MimeMultipart();  
			multipart.addBodyPart(messageBodyPart1);  

			messageBodyPart1 = new MimeBodyPart();
			// String file = "GDPGrowth.txt";//change accordingly  
			//  DataSource source = new FileDataSource(file);  
			//  messageBodyPart1.setDataHandler(new DataHandler(source));  
			//  messageBodyPart1.setFileName(file);  
			// multipart.addBodyPart(messageBodyPart1);  


			message.setContent(multipart );    
			Transport.send(message);
			System.out.println("Done,Investment of "+activity+" in year "+year+" is "+data+" you should have received an email with the requested data. Please confirm?");
			return "Done,Investment of "+activity+" in year "+year+" is "+data+" you should have received an email with the requested data. Please confirm?";

		}catch (MessagingException ex) {ex.printStackTrace();}  

		return "Sorry! Email is not sended correctly.";
	}

}
