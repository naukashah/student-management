package org.robferguson.resteasy.examples.fatjar.resource;

import java.util.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Queue")
@Produces(MediaType.APPLICATION_JSON)
public class QueueExample {
	Queue<String> q1 =  new LinkedList<>();
	
	
	@GET
	@Path("/")
	public Result get() {
		String peek = q1.peek();
		//return Response.status(200).entity(peek+ " Top Data").build();
		return new Result(peek, 200);
	}

	@POST
	@Path("/{entry}")
	public Response create(@PathParam("entry") String id) {
		boolean entry = q1.add(id);
		return Response.status(200).entity(entry + "Added").build();
	}

	@DELETE
	@Path("/")
	public Response delete() {
		String previous = q1.remove();
		return Response.status(200).entity(previous+" has been removed").build();
	}
	
	public class Result {
		public String response;
		public int code;
		public Result(String response, int code) {
			super();
			this.response = response;
			this.code = code;
		}
	}

}