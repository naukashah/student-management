package org.robferguson.resteasy.examples.fatjar.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;

import com.google.gson.GsonBuilder;

@Path("/students")
@Produces(MediaType.TEXT_PLAIN)
public class Students {
	HashMap<String, Student> m1 = new HashMap<>();
	PulsarClient pulsarClient;
	Producer<byte[]> producer;
	int idCounter = 1;

	public Students() {
		String id = Integer.toString(idCounter++);
		m1.put(id, new Student(id, "Nauka", "Spring", "ITU"));
		id = Integer.toString(idCounter++);
		m1.put(id, new Student(id, "SAM", "Spring", "ITU"));
		id = Integer.toString(idCounter++);
		m1.put(id, new Student(id, "David", "Fall", "ITU"));
		id = Integer.toString(idCounter++);
		m1.put(id, new Student(id, "John", "Fall", "ITU"));
		initPulsarClient();
	}

	private void initPulsarClient() {
		
		try {
			pulsarClient = PulsarClient.builder()
			        .serviceUrl("pulsar://localhost:6650")   //pulsar broker will run on this url
			        .build();	
			producer = pulsarClient.newProducer().enableBatching(false)
					.sendTimeout(5, TimeUnit.SECONDS)
					.topic("persistent://sample/standalone/ns1/student").create();	//produce will be created for this topic
		}catch(Throwable e) {
			e.printStackTrace();
		}
		
		
	}

	public static class Student {
		public String id;
		public String name;
		public String term;
		public String university;

		private Student(String id, String name, String term, String universityName) {
			super();
			this.id = id;
			this.name = name;
			this.term = term;
			this.university = universityName;
		}
	}

	@GET
	@Path("/{id}")
	public Response get(@PathParam("id") String id) {
		Student student = m1.get(id);
		return Response.status(student == null ? 404 : 200).entity(student == null ? "not found" : student).build();
	}

	@GET
	@Path("/all")
	public Response get() {
		System.out.println("all called");
		String val = new GsonBuilder().create().toJson(new ArrayList<Student>(m1.values()));
		return Response.status(200).entity(val).build();
	}

	@POST
	@Path("/{name}/{term}/{university}")
	public Response create(@PathParam("name") String name, @PathParam("term") String term,
			@PathParam("university") String university) {
		String id = Integer.toString(idCounter++);
		System.out.println("put = "+name);
		Student student = new Student(id, name, term, university);
		m1.put(id, student);
		publishMessage(student, "created"); 
		return Response.status(200).build();
	}

	@PUT
	@Path("/update/{id}/{name}/{term}/{university}")
	public Response update(@PathParam("id") String id, @PathParam("name") String name, @PathParam("term") String term,
			@PathParam("university") String university) {
		Student student = new Student(id, name, term, university);
		m1.put(id, student);
		publishMessage(student, "updated"); 
		return Response.status(200).build();
	}

	@DELETE
	@Path("/delete/{id}")
	public Response delete(@PathParam("id") String id) {
		Student previous = m1.remove(id);
		publishMessage(previous, "deleted");
		return Response.status(200).entity(previous + " has been removed").build();
	}

	private void publishMessage(Student student, String type) {
		try {
			if(student == null) {
				return;
			}
			String msg = String.format("Student %s: %s-%s-%s-%s", type, student.id, student.name, student.term,
					student.university);
			producer.send(msg.toString().getBytes());
		}catch(Throwable th) {
			th.printStackTrace();
		}
	}
}