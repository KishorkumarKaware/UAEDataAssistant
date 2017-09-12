package com.boot.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import com.boot.bussiness.API_AI_Responce;
import com.boot.bussiness.GetInvestment;
import com.boot.model.API_AI_Response_Mdl;
import com.boot.model.Parameters;
import com.boot.model.Response_Mdl;
import com.boot.model.Result;

@Path("getinfo")
public class ChatBotController
{

	@GET
	public Response GetMsg() throws IOException{
		System.out.println("GET Request recieved...");
		return Response.status(200).entity("Welcome User....!").build();
	}

	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getConf(String outputJSON) throws IOException, ParseException{
		Result rs=null;
		Parameters params=null;
		try{
			System.out.println("Request recieved");
			API_AI_Responce response = new API_AI_Responce();

			System.out.println("responceBO : "+response.toString());
			API_AI_Response_Mdl apiAiResponse = response.jsonToJava(outputJSON);

			System.out.println("apiAiResponse : " +apiAiResponse);

			rs=apiAiResponse.getResult();
			System.out.println("rs :"+rs.toString());
			params=rs.getParameters();
		}catch(Exception e){e.printStackTrace();}

		String email = params.getEmail();
		String year =params.getYear();
		String activity =params.getActivity();
		
		GetInvestment get=new GetInvestment();
		String str1 = get.getData(email, activity, year);

		Response_Mdl res=new Response_Mdl();
		res.setSource("policyWS");
		res.setSpeech(str1);
		res.setDisplayText(str1);


		ObjectMapper om=new ObjectMapper();
		String str2=om.writeValueAsString(res);

		return Response.status(200).entity(str2).header("Content-Type", "application/json").build();
	}


}
