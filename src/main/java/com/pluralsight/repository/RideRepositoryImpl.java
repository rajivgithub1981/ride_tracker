package com.pluralsight.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Ride> getRides() {
		
		
		List<Ride> allRides = jdbcTemplate.query("select * from ride", (resultSet,rowNum) -> {
			Ride ride = new Ride();
			ride.setId(resultSet.getInt("id"));
			ride.setName(resultSet.getString("name"));
			ride.setDuration(resultSet.getInt("duration"));
			return ride;
			}
		);
		
		return allRides;
	}

	@Override
	public Ride createRide(Ride ride) {
		jdbcTemplate.update("insert into ride(name,duration) values (?,?)", ride.getName(),ride.getDuration());
		return null;
	}
	
}
