package com.camilo;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.camilo.dao.AppointmentDao;
import com.camilo.model.Appointment;



@Component
@Path("appointments") //http:localhost:8080/scheduling-services/webapi/appointments
public class AppointmentResource {

	AppointmentDao apptDao;
	
	@Autowired
	public AppointmentResource(@Qualifier("appointmentDao") AppointmentDao apptDao)
	{
		this.apptDao = apptDao;		
	}
	
	
	@DELETE
	@Path("{appointmentId}") //http:localhost:8080/scheduling-services/webapi/appointments/1234
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	@Transactional
	public Response delete (@PathParam ("appointmentId") String appointmentId) {
		
		apptDao.deleteAppointment( Integer.parseInt(appointmentId) );		
		return Response.ok().build();	

	}
	
	@PUT
	@Path("{appointmentId}") //http:localhost:8080/scheduling-services/webapi/appointments/1234
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	@Transactional
	public Response update(@PathParam ("appointmentId") String appointmentId, Appointment appointment) {
		
		boolean succeded = apptDao.updateAppointment( Integer.parseInt(appointmentId), appointment);	
		
		// handle failure
		if(!succeded) return Response.status( Response.Status.NOT_ACCEPTABLE ).entity("There was a problem updating the Appointment").build();
		
		// responding with status code 201 as per HTTP spec
		return Response.status( Response.Status.CREATED ).entity("The Appointment has been succesfully updated").build();
	}
	
	
	@POST
	@Path("appointment") // http:localhost:8080/scheduling-services/webapi/appointments/appointment
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	@Transactional
	public Response createAppointment(Appointment appointment) {
		

		boolean succeded = apptDao.createAppointment( appointment );
		
		// handle failure
		if(!succeded) return Response.status( Response.Status.NOT_ACCEPTABLE ).entity("There was a problem creating the Appointment").build();
		
		// responding with status code 201 as per HTTP spec
		return Response.status( Response.Status.CREATED ).entity("The Appointment has been succesfully created").build();	
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON}) //http:localhost:8080/scheduling-services/webapi/appointments
	public Response getAllActivities() {
		
		
		List<Appointment> apptList = apptDao.listAppointments(null, null);
		
		return Response.ok().entity(new GenericEntity<List<Appointment>> (apptList) {}).build();
	}

	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("{appointmentId}") //http:localhost:8080/scheduling-services/webapi/appointments/1234
	public Response getAppointment(@PathParam ("appointmentId") String appointmentId) {
		/*
		if(appointmentId == null || appointmentId.length() < 4) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		*/
		
		Appointment appointment = apptDao.getAppointment( Integer.parseInt( appointmentId ) );
		
		if(appointment == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok().entity(appointment).build();
	}
	
}
