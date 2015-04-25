package com.camilo.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.camilo.jaxb.DateAdapter;

@XmlRootElement
public class AppointmentSearch {
	
	protected Date startTime;
	protected Date endTime;
	
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
}
