package org.robferguson.resteasy.examples.fatjar.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
public class MessageResource {

    @GET
    @Path("/{param}")
    public Response get(@PathParam("param") String msg) {
        String result = "Hello " + msg + "!";
        return Response.status(200).entity(result).build();
    }
    
    @POST
    @Path("/{param}")
    public Response create(@PathParam("param") String msg) {
        String result = "Hello " + msg + "!";
        return Response.status(200).entity(result).build();
    }
    
    @PUT
    @Path("/{param}")
    public Response put(@PathParam("param") String msg) {
        String result = "Hello " + msg + "!";
        return Response.status(200).entity(result).build();
    }
    
    @DELETE
    @Path("/{param}")
    public Response delete(@PathParam("param") String msg) {
        String result = "Hello " + msg + "!";
        return Response.status(200).entity(result).build();
    }
}