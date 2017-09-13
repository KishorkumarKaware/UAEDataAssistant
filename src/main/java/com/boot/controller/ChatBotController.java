package com.boot.controller;

import java.util.Map;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boot.bussiness.API_AI_Responce;
import com.boot.bussiness.GetGDP;
import com.boot.bussiness.GetInvestment;
import com.boot.bussiness.GetTopTenCountry;
import com.boot.model.API_AI_Response_Mdl;
import com.boot.model.Fulfillment;
import com.boot.model.Parameters;
import com.boot.model.Response_Mdl;
import com.boot.model.Result;

@RestController
@RequestMapping("api/v1/")
public class ChatBotController {
	@Autowired
	public Environment env;

	@RequestMapping(value = "getinfo", method = RequestMethod.POST)
	public Response_Mdl post(@RequestBody String inputJSON) throws Exception {
		System.out.println("Request recieved");
		/*if(true)
			return "POST request recieved..";*/

		Result rs=null;
		Parameters params=null;
		String result ="";
		try{
			System.out.println("Request recieved");
			API_AI_Responce response = new API_AI_Responce();

			System.out.println("responceBO : "+response.toString());
			API_AI_Response_Mdl apiAiResponse = response.jsonToJava(inputJSON);

			System.out.println("apiAiResponse : " +apiAiResponse);

			rs=apiAiResponse.getResult();
			System.out.println("rs :"+rs.toString());
			params=rs.getParameters();
		}catch(Exception e){e.printStackTrace();
		result="Sorry! I did't get you, please be more specific.";
		}

		try{
			String email = params.getEmail();
			String[] year =params.getYear();
			String[] sector =params.getSector();
			String table =params.getTable();
			switch(table){
			case "2":
				result = GetInvestment.getData(email, sector, year);
				break;
			case "6":
				result=GetTopTenCountry.getTopFDI(email, year);
				break;
			case "gdpsheet":
				result=GetGDP.getGDP(year, email);
				break;
			default:
				result="Sorry! I did't get you, please be more specific.";
			}
			System.out.println("Responce="+result);
		}catch(Exception e)
		{
			e.printStackTrace();
			Fulfillment f=rs.getFulfillment();
			result = f.getSpeech();
		}
		Response_Mdl res=new Response_Mdl();
		res.setSource("policyWS");
		res.setSpeech(result);
		res.setDisplayText(result);
		/*ObjectMapper om=new ObjectMapper();
			String str2=om.writeValueAsString(res);

			return Response.status(200).entity(str2).header("Content-Type", "application/json").build();*/
		return res;
	}


	@RequestMapping(value = {"getinfo" }, method = RequestMethod.GET)
	public String search(@RequestParam Map<String, String> allRequestParams, ModelMap model) {

		System.out.println("GET Request Recieved...");
		return "Welcome User...!";
	}

}
