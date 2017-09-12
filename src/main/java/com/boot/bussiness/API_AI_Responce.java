package com.boot.bussiness;

import org.codehaus.jackson.map.ObjectMapper;

import com.boot.model.API_AI_Response_Mdl;

public class API_AI_Responce {
	public API_AI_Response_Mdl jsonToJava(String json) {
		API_AI_Response_Mdl apiAiResponse = null;
		try {
			System.out.println(json);
			ObjectMapper mapper= new ObjectMapper();
			apiAiResponse = mapper.readValue(json, API_AI_Response_Mdl.class);
			
			System.out.println(apiAiResponse);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apiAiResponse;
	}

}
