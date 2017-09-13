package com.boot.bussiness;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.boot.model.EmailStructure;
import com.boot.sendmail.queue.EmailQueue;

public class GetTopTenCountry {

	public static String getTopFDI(String email,String[] year) throws IOException
	{
		/*if(!VerifyMail.verifyMail(email))
			return "Sorry! Can you please reenter your Email ID in correct format? (abc@xyz.com)";*/
		String result="",result2=""; 
		boolean matchFound = false,flag = false;
		String fdifromExcel  = "";
		String data= "";
		int cnt=-1;
		System.out.println("Email ID="+email+" Year="+year);
		//Year validation
		System.out.println("length="+year.length);
		for(int i=0;i<year.length;i++)
		{
			result="\n"+result+"\n FDI for the Top 10 Countries for the year "+year[i]+" is:";
			System.out.println("FDI searching for year="+year[i]);
			int userYear=Integer.valueOf(year[i]);
			Date currentDate= new Date();
			int currentYear=1900+currentDate.getYear();
			System.out.println(currentYear);
			if(userYear >= currentYear)
			{
				result=result+"\n FDI for year "+year[i]+" is Not Available.";
				flag = true;
				continue;
				//return result;
			}


			File file=new File("C:/tools/workspace/ForeignInvester/src/main/resources/Foriegn Investments- time Series- 13122016.xls");
			FileInputStream fin = new FileInputStream(file);

			HSSFWorkbook wb = new HSSFWorkbook(fin);
			HSSFSheet ws = wb.getSheet("6");
			HSSFRow rowHeader =null;
			int rowNum = 0;
			int startRowNum =0;
			switch(year[i])
			{
			case "2015":
				rowHeader = ws.getRow(8);
				rowNum=17;
				startRowNum =8;
				cnt++;
				break;
			case "2014":
				rowHeader = ws.getRow(26);
				startRowNum =26;
				rowNum=35;
				break;
			case "2013":
				rowHeader = ws.getRow(44);
				startRowNum =44;
				rowNum=53;
				cnt++;
				break;
			case "2012":
				rowHeader = ws.getRow(62);
				startRowNum =62;
				rowNum=71;
				cnt++;
				break;
			case "2011":
				rowHeader = ws.getRow(80);
				startRowNum =80;
				rowNum=89;
				cnt++;
				break;
			case "2010":
				rowHeader = ws.getRow(98);
				startRowNum =98;
				rowNum=107;
				cnt++;
				break;
			default:
				result=result+"\n\n Sorry! detailes are not Available this years.\n";
				flag = true; 
				continue;

			}

			int colNum = ws.getRow(7).getLastCellNum();
			int countryIndexHeader = 3;
			int yearIndex=2;
			String country="";
			System.out.println("Total Number of Columns in the excel is : "+colNum);
			System.out.println("Total Number of Rows in the excel is : "+rowNum);
			for (int j = startRowNum; j < rowNum; j++)
			{
				rowHeader = ws.getRow(j);
				country  = cellToString(rowHeader.getCell(countryIndexHeader));
				fdifromExcel  = cellToString(rowHeader.getCell(yearIndex));
				result2 = result2+"\n FDI for "+country+" was "+fdifromExcel+" billion USD,";
				matchFound = true;
			}

			System.out.println("data:"+data);
			if (!matchFound) {
				System.out.println("Sorry...!! The given year is not present.");
				return "Sorry! FDI is not present for given year.";
			}
		}
		System.out.println("Email="+email);
		if(email != null && matchFound == true){
			EmailStructure e=new EmailStructure();
			e.setActivity(fdifromExcel);
			e.setEmail(email);
			e.setInvestment(result2);
			e.setYear(year);
			EmailQueue.emailq.add(e);
			result="Done, you should have received an email with the requested data. Please confirm?";
		}else if(flag == false ||(flag == true && cnt >=0))
		{
			result=result+result2+"\n Do you want us to send this data over email?";
		}else{
			result ="Sorry!"+result+".If you have any other data requiremente, please let me know the details else please kindly type EXIT for quit";
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
}
