package com.camilo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.camilo.jaxb.DateAdapter;

@XmlRootElement
public class Appointment {
	
	protected Date startTime;
	protected Date endTime;

	protected String firstName;
	protected String lastName;
	protected String comments;

	
	@XmlElement(name="start_time")
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	
	@XmlElement(name="end_time")
	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	@XmlElement(name="first_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement(name="last_name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlElement(name="comments")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


}
