package com.camilo.client;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.camilo.model.Appointment;



public class ActivityClientTest {	
	
	@Test
	public void testDelete() {
		AppointmentClient client = new AppointmentClient();
		
		client.delete("34");
	}
	
	@Test
	public void testPut() {
		
		Appointment app = new Appointment();
		app.setFirstName("Test");
		app.setLastName("Last");
		app.setComments("This is just a comment");
		app.setEndTime( new Date() );
		app.setStartTime(new Date());
		
		AppointmentClient client = new AppointmentClient();
		
		Response result = client.update("4444",app);
		
		if(result.getStatus() != 201) assertTrue( false );
	}
	
	@Test
	public void testCreate() {
		AppointmentClient client = new AppointmentClient();
		
		Appointment app = new Appointment();
		app.setFirstName("Test");
		app.setLastName("Last");
		app.setComments("This is just a comment");
		app.setEndTime( new Date() );
		app.setStartTime(new Date());
		

		Response result = client.create( app );
		
		if(result.getStatus() != 201) assertTrue( false );
	}
	
	@Test
	public void testGet() {
		AppointmentClient client = new AppointmentClient();
		
		Appointment appt = client.get("1234");
		
		System.out.println(appt);
		
		assertNotNull (appt );
	}
	

}
