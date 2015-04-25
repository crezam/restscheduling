package com.camilo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.camilo.dao.AppointmentDao;
import com.camilo.model.Appointment;
import com.camilo.model.AppointmentSearch;

@Component
@Path("search/appointments")
public class AppointmentSearchResource {

	
	AppointmentDao apptDao;
	
	@Autowired
	public AppointmentSearchResource(@Qualifier("appointmentDao") AppointmentDao apptDao)
	{
		this.apptDao = apptDao;		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	public Response searchForActivities(AppointmentSearch search) {
		
		/*
		System.out.println("Camilo " + search.getEndTime());
		System.out.println("Camilo " + search.getStartTime());		
		List<Appointment> appointments = new ArrayList<Appointment>();
		Appointment app = new Appointment();
		app.setFirstName("Test");
		app.setLastName("Last");
		app.setComments("This is just a comment");
		app.setEndTime( new Date() );
		app.setStartTime(new Date());
		appointments.add(app);
		return Response.ok().entity(new GenericEntity<List<Appointment>> (appointments) {}).build();
		*/

		List<Appointment> appointments = apptDao.listAppointments( search.getStartTime(), search.getEndTime() );
		
		if(appointments == null || appointments.size() <= 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok().entity(new GenericEntity<List<Appointment>> (appointments) {}).build();
		
	}

}
