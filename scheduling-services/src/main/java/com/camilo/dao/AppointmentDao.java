package com.camilo.dao;

import java.util.Date;
import java.util.List;

import com.camilo.model.Appointment;

public interface AppointmentDao 
{
	public boolean createAppointment(Appointment a);
	public Appointment getAppointment(int id);
	public boolean updateAppointment(int id, Appointment a);
	public void deleteAppointment(int id);
	public List<Appointment> listAppointments(Date start, Date end);	

}
