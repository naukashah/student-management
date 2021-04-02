package org.robferguson.resteasy.examples.fatjar.resource;

import java.util.HashMap;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/student")
@Produces(MediaType.TEXT_PLAIN)
public class StudentMap {
	HashMap<String, String> m1 = new HashMap<>();
	
	public StudentMap() {
		m1.put("1", "Nauka");
		m1.put("2", "John");
		m1.put("3", "Sam");
	}

	@GET
	@Path("/{id}")
	public Response get(@PathParam("id") String id) {
		String str = m1.get(id);
		return Response.status(str==null ? 404 : 200).entity(str==null ? "not found" : str).build();
	}
	
	@GET
	@Path("/all")
	public Response get() {
		return Response.status(200).entity(m1.toString()).build();
	}

	@POST
	@Path("/{id}/{name}")
	public Response create(@PathParam("id") String id,@PathParam("name") String name) {
		m1.put(id, name);
		return Response.status(200).build();
	}

	@PUT
	@Path("/{id}/{name}")
	public Response put(@PathParam("id") String id,@PathParam("name") String name) {
		m1.put(id, name);
		return Response.status(200).build();
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) {
		String previous = m1.remove(id);
		return Response.status(200).entity(previous+" has been removed").build();
	}

}