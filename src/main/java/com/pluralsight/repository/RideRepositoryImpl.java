package com.pluralsight.repository;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	static RowMapper<Ride> rideRowMapper = (resultSet,rowNum) -> {
		Ride ride = new Ride();
		ride.setId(resultSet.getInt("id"));
		ride.setName(resultSet.getString("name"));
		ride.setDuration(resultSet.getInt("duration"));
		return ride;
		};
	
	@Override
	public List<Ride> getRides() {
		List<Ride> allRides = jdbcTemplate.query("select * from ride", rideRowMapper);
		return allRides;
	}
	
	@Override
	public Ride getRide(int id){
		Ride ride = jdbcTemplate.queryForObject("select * from ride where id = ?", rideRowMapper,id);
		return ride;
	}

	@Override
	public Ride createRide(Ride ride) {
		//jdbcTemplate.update("insert into ride(name,duration) values (?,?)", ride.getName(),ride.getDuration());
		KeyHolder keyHolder = new GeneratedKeyHolder(); 
		jdbcTemplate.update(con -> {
			PreparedStatement pstmt = con.prepareStatement("insert into ride(name,duration) values (?,?)", new String[]{"id"});
			pstmt.setString(1, ride.getName());
			pstmt.setInt(2, ride.getDuration());
			return pstmt;
		}, keyHolder);
		
		return getRide(keyHolder.getKey().intValue());
	}

	@Override
	public Ride updateRide(Ride ride) {
		jdbcTemplate.update("update ride set duration = ? where id = ?", ride.getDuration(),ride.getId());
		return ride;
	}

	@Override
	public void updateBatch(List<Object[]> pairs) {
		jdbcTemplate.batchUpdate("update ride set ride_date = ? where id = ?", pairs);
		
	}
	
}
