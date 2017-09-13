package com.boot.bussiness;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.boot.model.EmailStructure;
import com.boot.sendmail.SendMail;
import com.boot.sendmail.queue.EmailQueue;

import java.util.*;  
import javax.mail.*;  
import javax.mail.internet.*; 
public class GetInvestment {
	public static String getData(String email,String[] sector, String[] year) throws IOException, InterruptedException
	{  
		/*if(!VerifyMail.verifyMail(email))
			return "Sorry! Can you please reenter your Email ID in correct format? (abc@xyz.com)";*/
		String result ="",result2="";
		boolean matchFound = false,flag=false;
		String activityfromExcel  = "";
		String data= "";
		int cnt=-1;
		try
		{
			for(int j=0;j<sector.length;j++)
			{

				for(int i=0;i<year.length;i++)
				{
					cnt++;
					System.out.println("FDI searching for year="+year[i]);
					int userYear=Integer.valueOf(year[i]);
					Date currentDate= new Date();
					int currentYear=1900+currentDate.getYear();
					System.out.println(currentYear);
					if(userYear >= currentYear)
					{
						result=result+"\n FDI for "+year[i]+" is Not Available";
						flag = true; 
						continue;
						//return result;
					}


					File file=new File("C:/tools/workspace/ForeignInvester/src/main/resources/Foriegn Investments- time Series- 13122016.xls");
					FileInputStream fin = new FileInputStream(file);

					HSSFWorkbook wb = new HSSFWorkbook(fin);
					HSSFSheet ws = wb.getSheetAt(2);
					HSSFCell cell=null;	
					HSSFRow rowHeader = ws.getRow(7);

					int rowNum = ws.getLastRowNum() + 1;
					int colNum = ws.getRow(0).getLastCellNum();
					int activityCodeIndexHeader=11;
					int yearIndex=-1;

					System.out.println("Total Number of Columns in the excel is : "+colNum);
					System.out.println("Total Number of Rows in the excel is : "+rowNum);
					for (int k = 1; k < colNum; k++)
					{
						cell = rowHeader.getCell(k);
						String cellValue = cellToString(cell);
						if (year[i].equalsIgnoreCase(cellValue)) 
						{
							yearIndex=k;
							System.out.println("Index of selected year Index="+yearIndex);
							break;
						}
					}
					if(yearIndex<0){
						flag = true;
						result=result+"\n FDI of "+sector[j]+" for "+year[i]+" is Not Available";

					}

					for (int k = 8; k < rowNum; k++)
					{
						rowHeader = ws.getRow(k);
						activityfromExcel  = cellToString(rowHeader.getCell(activityCodeIndexHeader));

						if(activityfromExcel.contains(sector[j]) )
						{
							System.out.println("rowHeader.getCell(yearIndex) :"+rowHeader.getCell(yearIndex));
							if(rowHeader.getCell(yearIndex) == null)
								break;
							data= cellToString(rowHeader.getCell(yearIndex));
							cnt++;
							//  String data1=Double.valueOf(data).toString();
							result2=result2+"\nFDI in "+sector[j]+" for "+year[i]+" is "+data+" billion USD,"; 
							System.out.println("Investment of "+activityfromExcel+" is "+data);

							matchFound = true;
							if(data.equals("")){
								flag = true;
								result=result+"\n FDI of "+sector[j]+" for "+year[i]+" is Not Available";
							}
						}


					}
					System.out.println("data:"+data);
					if ((!matchFound) && !(i<year.length-1)) {
						System.out.println("Sorry...!! The given activity is not present.");
						return "Sorry...!! The given activity is not present.";
					}


				}
			}
		}catch(NullPointerException ne)
		{
			ne.printStackTrace();
			return "Sorry! I did't get you, Some required values is missing.";
		}
		catch(Exception e){
			e.printStackTrace();
			return "Sorry! I did't get you, Can you please type again.";
		}
		System.out.println("Email="+email);
		if(email != null && matchFound == true){
			EmailStructure e=new EmailStructure();
			e.setActivity(activityfromExcel);
			e.setEmail(email);
			e.setInvestment(result2);
			e.setYear(year);
			EmailQueue.emailq.add(e);
			result="Done, you should have received an email with the requested data. Please confirm?";
		}else if(flag == false ||(flag == true && cnt >0))
		{
			result=result+result2+"\n Do you want us to send this data over email?";
		}else{
			result ="Sorry!"+result+".If you have any other data requiremente, please let me know the details else please kindly type EXIT for quit.";
		}
		return result;
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


	/*private static String getGreeting(String time)
	{
		String result="";
		Date d= new Date();
		int localOffset = d.getTimezoneOffset() * 60000;
		long utc = d.getTime() + localOffset;
		long dubai = utc + (3600000*4);
		Date nd = new Date(dubai); 
		System.out.println("Current Time="+nd);
		int t=nd.getHours();
		if(t>4 && t<12){
			result="Good Morning! How may I help \nyou today?";
		}else if(t>12 && t< 16){
			result="Good Afternoon! How may I help \nyou?";
		}else if(t>16 && t<24){
			result="Good Evenning! How may I help \nyou?";
		}else if(t == 8 && t == 10 && t == 12 && t == 16 && t == 24 && t == 14 ){
			result="Nice To see you! How may I help \nyou?";
		}
		return result;
	}*/
}
