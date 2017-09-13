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

public class GetGDP {
	public static String getGDP(String[] year,String email) throws IOException{
		/*if(!VerifyMail.verifyMail(email))
			return "Sorry! Can you please reenter your Email ID in correct format? (abc@xyz.com)";*/
		String result="",result2=""; 
		boolean matchFound = false,flag =false;
		String gdpfromExcel  = "";
		String data= "";
		int cnt=-1;
		System.out.println("Email ID="+email+" Year="+year);
		//Year validation
		System.out.println("length="+year.length);
		for(int i=0;i<year.length;i++)
		{
			System.out.println("GDP searching for year="+year[i]);
			int userYear=Integer.valueOf(year[i]);
			Date currentDate= new Date();
			int currentYear=1900+currentDate.getYear();
			System.out.println(currentYear);
			if(userYear >= currentYear)
			{
				result=result+"\n GDP for the year "+year[i]+" is Not Available";
				flag = true; 
				continue;
				//return result;
			}

			File file=new File("C:/tools/workspace/ForeignInvester/src/main/resources/Foriegn Investments- time Series- 13122016.xls");
			FileInputStream fin = new FileInputStream(file);

			HSSFWorkbook wb = new HSSFWorkbook(fin);
			HSSFSheet ws = wb.getSheet("gdpsheet");
			HSSFCell cell=null;	
			HSSFRow rowHeader = ws.getRow(0);

			int rowNum = ws.getLastRowNum() + 1;
			int colNum = ws.getRow(0).getLastCellNum();
			int countryIndexHeader = 0;
			int yearIndex=-1;
			System.out.println("Total Number of Columns in the excel is : "+colNum);
			System.out.println("Total Number of Rows in the excel is : "+rowNum);
			for (int j = 1; j < colNum; j++)
			{
				cell = rowHeader.getCell(j);
				String cellValue = cellToString(cell);
				if (year[i].equalsIgnoreCase(cellValue)) 
				{
					yearIndex=j;
					System.out.println("Index of selected year Index="+yearIndex);
					break;
				}	
			}
			if(yearIndex<0){
				result=result+"\n GDP for the year "+year[i]+" is Not Available";
				flag = true; 
				continue;
			}
			for (int j = 1; j < rowNum; j++)
			{
				rowHeader = ws.getRow(j);
				gdpfromExcel  = cellToString(rowHeader.getCell(countryIndexHeader));

				if(gdpfromExcel.contains("UAE") )
				{
					cnt++;
					System.out.println("rowHeader.getCell(yearIndex) :"+rowHeader.getCell(yearIndex));
					data= cellToString(rowHeader.getCell(yearIndex));
					//  String data1=Double.valueOf(data).toString();
					result2=result2+"\n GDP for the year "+year[i]+" is "+data+" billion USD,"; 
					System.out.println("Investment of "+gdpfromExcel+" is "+data);
					matchFound = true;
					if(data.equals(""))
					{
						result=result+"\n GDP for the year "+year[i]+"is Not Available";
						flag = true; 
						continue;
					}
				}
			}

			System.out.println("data:"+data);
			if (!matchFound) {

				System.out.println("Sorry...!! The given activity is not present.");
				return "Sorry! GDP is not present for given country.";
			}
		}
		System.out.println("Email="+email);
		if(email != null && matchFound == true){
			EmailStructure e=new EmailStructure();
			e.setActivity(gdpfromExcel);
			e.setEmail(email);
			e.setInvestment(result2);
			e.setYear(year);
			EmailQueue.emailq.add(e);
			result="Done, you should have received an email with the requested data. Please confirm?";
		}else if(flag == false ||(flag == true && cnt >=0))
		{
			result=result+result2+"\n Do you want us to send this data over email?";
		}else
		{
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
}
