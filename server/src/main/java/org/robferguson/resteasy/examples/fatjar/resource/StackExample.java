package org.robferguson.resteasy.examples.fatjar.resource;

import java.util.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Stack")
public class StackExample {

	Stack<String> s1 = new Stack<String>();

	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/")
	public Response get() {
		String peek = s1.peek();
		return Response.status(s1.isEmpty() ? 404 : 200).entity(peek+ " Top Data").build();
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{entry}")
	public Response create(@PathParam("entry") String id) {
		String entry = s1.push(id);
		return Response.status(200).entity(entry + "Added").build();
	}

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/")
	public Response delete() {
		String previous = s1.pop();
		return Response.status(200).entity(previous+" has been removed").build();
	}

}