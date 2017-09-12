package com.mkyong.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("hello")
public class TestWS {

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		String output =  msg;
		return Response.status(200).entity(output).build();
		

	}

} 
