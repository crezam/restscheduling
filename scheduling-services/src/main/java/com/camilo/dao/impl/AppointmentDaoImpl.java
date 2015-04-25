package com.camilo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.camilo.dao.AppointmentDao;
import com.camilo.model.Appointment;

public class AppointmentDaoImpl implements AppointmentDao 
{
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		//jdbcTemplate =  new JdbcTemplate( dataSource );
	}	
	

	@Override
	public boolean createAppointment(Appointment a) {
		
		// in case the start date is greater than end date or dates in the past, do not insert
		if( a.getStartTime().after( a.getEndTime() ) || a.getStartTime().before( new Date() ) || a.getEndTime().before( new Date() )  ) return false;
		
		jdbcTemplate = new JdbcTemplate( dataSource );
		
		// checking if there is any overlap with the intended insert
		int numberOverlaps = jdbcTemplate.queryForObject(	"SELECT COUNT(*) FROM appointments WHERE starttime < ? AND endtime > ?", Integer.class, a.getEndTime(), a.getStartTime()	); 
		
		if( numberOverlaps > 0 ) return false;
		
		jdbcTemplate = new JdbcTemplate( dataSource );
		
		String sql="INSERT INTO appointments (firstname, lastname, comments, starttime, endtime) values (?, ?, ?, ?, ?)";
		
		jdbcTemplate.update(sql, new Object[]{ a.getFirstName(), a.getLastName(), a.getComments(), a.getStartTime(), a.getEndTime()  });
		
		return true;		
	}

	@Override
	public Appointment getAppointment(int id) {

		jdbcTemplate = new JdbcTemplate( dataSource );
		
		Appointment a = null;
		
		try{
		
		a = (Appointment) jdbcTemplate.queryForObject( 	"SELECT * FROM appointments WHERE id = ?",
		        													new Object[]{ id },
		        													new AppointmentMapper()		);
		}
		catch(Exception e){	}
		return a;
	}

	@Override
	public boolean updateAppointment(int id, Appointment a) {
		
		// in case the start date is greater than end date do not update
		if( a.getStartTime().after( a.getEndTime() )  ) return false;
		
		jdbcTemplate = new JdbcTemplate( dataSource );
		
		// checking if there is any overlap with the intended update, dont care about the record with the id being replaced
		int numberOverlaps = jdbcTemplate.queryForObject(	"SELECT COUNT(*) FROM appointments WHERE starttime < ? AND endtime > ? AND id != ?", Integer.class, a.getEndTime(), a.getStartTime(), id	);
		
		if( numberOverlaps > 0 ) return false; // would create data inconsistency, we dont want overlaps
		
		jdbcTemplate = new JdbcTemplate( dataSource );
		
		int existsAlready = jdbcTemplate.queryForObject(	"SELECT COUNT(1) FROM appointments WHERE id = ?", Integer.class, id	);
		
		jdbcTemplate = new JdbcTemplate( dataSource );
		
		if(existsAlready == 1) // update
		{		
			jdbcTemplate.update(	"UPDATE appointments SET firstname = ?, lastname= ?, comments = ?, starttime = ?, endtime = ? WHERE id = ?",
									a.getFirstName(), a.getLastName(), a.getComments(), a.getStartTime(), a.getEndTime(), id	);
		}
		else // create
		{
				
			jdbcTemplate.update(	"INSERT INTO appointments (id, firstname, lastname, comments, starttime, endtime) values (?, ?, ?, ?, ?, ?)", 
									new Object[]{ id, a.getFirstName(), a.getLastName(), a.getComments(), a.getStartTime(), a.getEndTime()  }	);			
		}
		
		return true;		
	}

	@Override
	public void deleteAppointment(int id) {
		
		jdbcTemplate = new JdbcTemplate( dataSource );
		
		if( id < 0 ) jdbcTemplate.update( "DELETE FROM appointments" );
		else jdbcTemplate.update( "DELETE FROM appointments WHERE id = ?", id );
		
	}

	@Override
	public List<Appointment> listAppointments(Date start, Date end) {
		
		jdbcTemplate = new JdbcTemplate( dataSource );
		List<Appointment> appts = null;
		
		// in case search parameters are passed
		if ( (start != null) && (end != null)  )
		{			
			appts = jdbcTemplate.query(	"SELECT * FROM appointments WHERE starttime < ? AND endtime > ?", 
					new Object[]{ end, start },
					new AppointmentMapper()		);			
		}
		else // all appts
		{
			appts = jdbcTemplate.query(	"SELECT * FROM appointments", 
					new AppointmentMapper()		);
		}

		return appts;
	}
	
	
	private static final class AppointmentMapper implements RowMapper<Appointment> 
	{															            
		
		public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException	
	    {
	    	Appointment app = new Appointment();
	        app.setFirstName(rs.getString("firstname"));
	        app.setLastName(rs.getString("lastname"));
	        app.setComments(rs.getString("comments"));
	        app.setStartTime(rs.getTimestamp("starttime"));
	        app.setEndTime(rs.getTimestamp("endtime"));
	        return app;
	    }
	}

}
