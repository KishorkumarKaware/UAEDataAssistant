package com.boot.bussiness;

public class VerifyMail {
	public static boolean verifyMail(String email)
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
