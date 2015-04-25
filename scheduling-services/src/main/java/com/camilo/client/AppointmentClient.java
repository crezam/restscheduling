package com.camilo.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.camilo.model.Appointment;

public class AppointmentClient {

	private Client client;

	public AppointmentClient () {
		client = ClientBuilder.newClient();
	}
	
	public void delete(String id) {
		WebTarget target = client.target("http://localhost:8080/scheduling-services/webapi/");
		
		Response response = target.path("appointments/" + id).request(MediaType.APPLICATION_JSON).delete();
		
		if(response.getStatus() != 200) {
			throw new RuntimeException(response.getStatus() + ": there was an error on the server.");
		}
	}
	
	public Appointment get(String id) {
		
		WebTarget target = client.target("http://localhost:8080/scheduling-services/webapi/");
		
		Response response = target.path("appointments/" + id).request(MediaType.APPLICATION_JSON).get(Response.class);
		
		if(response.getStatus() != 200) {
			throw new RuntimeException(response.getStatus() + ": there was an error on the server.");
		}
		
		return response.readEntity(Appointment.class);
	}
	
	public List<Appointment> get () {
		WebTarget target = client.target("http://localhost:8080/scheduling-services/webapi/");
		
		List<Appointment> response = target.path("appointments").request(MediaType.APPLICATION_JSON).get(new GenericType<List<Appointment>>() {});
		
		return response;
	}

	public Response create(Appointment Appointment) {
		
		WebTarget target = client.target("http://localhost:8080/scheduling-services/webapi/");
		
		Response response = target.path("appointments/appointment")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(Appointment, MediaType.APPLICATION_JSON));

		return response;
	}

	public Response update(String id, Appointment Appointment) {
		WebTarget target = client.target("http://localhost:8080/scheduling-services/webapi/");
		
		Response response = target.path("appointments/" + id )
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(Appointment, MediaType.APPLICATION_JSON));
		
		return response;
	}

	
	
}
