package com.boot.bussiness;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*; 
public class GetInvestment {
	public String getData(String email,String activity, String year) throws IOException
	{  
		String result=""; 
		System.out.println("Email ID="+email +" Country_Id="+ activity +" Year="+year);
		if(!verifyMail(email))
			return "Sorry..!!!please enter valid email id.";

		//Year validation
		int userYear=Integer.valueOf(year);
		Date currentDate= new Date();
		int currentYear=1900+currentDate.getYear();
		System.out.println(currentYear);
		if(userYear >= currentYear)
		{
			result="Sorry..!! we are not able to show the Investment for future date.";  
			return result;
		}


		File file=new File("D:/ExcelFIles/Foriegn Investments- time Series- 13122016.xls");
		FileInputStream fin = new FileInputStream(file);

		HSSFWorkbook wb = new HSSFWorkbook(fin);
		HSSFSheet ws = wb.getSheetAt(2);
		HSSFCell cell=null;	
		HSSFRow rowHeader = ws.getRow(7);

		int rowNum = ws.getLastRowNum() + 1;
		int colNum = ws.getRow(0).getLastCellNum();
		int activityCodeIndexHeader=11;
		int yearIndex=-1;
		String data=null;
		boolean matchFound = false;

		System.out.println("Total Number of Columns in the excel is : "+colNum);
		System.out.println("Total Number of Rows in the excel is : "+rowNum);
		for (int j = 1; j < colNum; j++)
		{
			cell = rowHeader.getCell(j);
			String cellValue = cellToString(cell);
			if (year.equalsIgnoreCase(cellValue)) 
			{
				yearIndex=j;
				System.out.println("Index of selected year Index="+yearIndex);
				break;
			}	
		}
		if(yearIndex<0)
			return "Sorry! Investment of year "+year+" is not present.";

		for (int i = 8; i < rowNum; i++)
		{
			rowHeader = ws.getRow(i);
			String activityfromExcel  = cellToString(rowHeader.getCell(activityCodeIndexHeader));

			if(activityfromExcel.contains(activity) )
			{
				
				System.out.println("rowHeader.getCell(yearIndex) :"+rowHeader.getCell(yearIndex));
				data= cellToString(rowHeader.getCell(yearIndex));
				//  String data1=Double.valueOf(data).toString();
				System.out.println("Investment of "+activityfromExcel+" is "+data);

				matchFound = true;
				if(data.equals(""))
					result="Sorry! Investment of this activity is not available.";

				result=sendMail(email,data,activityfromExcel,year) ;
				return result;
			}
		}
		System.out.println("data:"+data);
		if (!matchFound) {

			System.out.println("Sorry...!! The given activity is not present.");
			return "Sorry! The given activity is not present.";
		}
		return "Sorry! Something is going wrong, please try again.";
	}


	public static String sendMail(String email_id,String data,String activity,String year)
	{
		final String from="kishorkaware.vyomlabs@gmail.com";//change accordingly  
		final String password="Vyomlabs@KK";//change accordingly  
		System.out.println("Sending mail to "+email_id);
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
					  +"\n      your requested detail for Investment in "+activity+" for year "+year+" is: "
			          +"\n"
					  +"\n          year       Investment"
			          +"\n          "+year+"      "+data
			          +"\n"
			          +"\nWarm Regard,"
			          +"\nForeign Investment Assistant";
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
			return "Done,Investment of "+activity+" in year "+year+" is "+data+" you should have received an email with the requested data. Please confirm?";

		}catch (MessagingException ex) {ex.printStackTrace();}  

		return "Sorry! Something is going wrong, please try again.";
	}


	private static String cellToString(HSSFCell cell) 
	{	
		Object result = null;
		switch (cell.getCellType()) 
		{
		case HSSFCell.CELL_TYPE_NUMERIC:
			result = Integer.valueOf( (int) cell.getNumericCellValue()).toString();
			break;

		case HSSFCell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			result = "";
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			result = cell.getCellFormula();
		}
		return result.toString();
	}	

	private static boolean verifyMail(String email)
	{

		int length = email.length();
		boolean flag1 = true;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		boolean flag5 = false;
		boolean flag6 = false;
		boolean flag7 = false;
		int count = 0;

		int temp = email.length();
		if(email.contains("@")){
			flag2=true;
			int a=email.indexOf("@");
			for(int i=a;i<temp;i++){
				if(email.charAt(i)=='.'){
					flag3=true; 
					count=count+1;
				}
			}
			if(count<1||count>2){
				flag4=false;
			}
			else{
				flag4=true;
			}
		}
		else{
			flag2 =false;
			System.out.println("No @ symbol present");
			return false;
		}


		//Condition 3
		if(email.matches("[A-Z a-z 0-9_]+@.*")) //Unable to get the right RegEx here!
			flag5 = true;
		else
			flag5 = false;

		//Condition4
		if(!Character.isUpperCase(email.charAt(0))==true)
			flag6 = true;
		else
			flag6=false;

		if(flag1==true && flag2==true && flag3==true && flag4==true && flag5==true &&flag6==true){
			System.out.println("Email ID is valid");
			return true;
		}	    else{
			if(flag1==false){
				System.out.println("Inavlid length of Email ID");
				return false;
			}
			if(flag2==false||flag3==false||flag4==false){
				System.out.println("Invalid Position of Special Characters");
				return false;
			}
			if(flag5==false){
				System.out.println("Invalid combination for username");
				return false;
			}
			if(flag6==false){
				System.out.println("Invalid case of first letter");
				return false;
			}
		}
		return true;
	}

}
