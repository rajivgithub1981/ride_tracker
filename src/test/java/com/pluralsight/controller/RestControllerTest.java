package com.pluralsight.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pluralsight.model.Ride;

import org.junit.Test;

public class RestControllerTest {
	@Test(timeout=3000)
	public void testCreateRide() {
		RestTemplate restTemplate = new RestTemplate();
		Ride ride = new Ride();
		ride.setName("ride 2");
		ride.setDuration(2);
		ride = restTemplate.postForObject("http://localhost:8080/ride_tracker/ride", ride,Ride.class);
		System.out.println(ride);
	}

	@Test(timeout=3000)
	public void testGetRides() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Ride>> ridesResponse = restTemplate.exchange(
				"http://localhost:8080/ride_tracker/rides", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Ride>>() {
				});
		List<Ride> rides = ridesResponse.getBody();

		for (Ride ride : rides) {
			System.out.println("Ride name: " + ride.getName());
		}
	}
	
	@Test(timeout=3000)
	public void testGetRide() {
		RestTemplate restTemplate = new RestTemplate();

		Ride ride  = restTemplate.getForObject("http://localhost:8080/ride_tracker/ride/1",Ride.class);
		System.out.println("Ride name: " + ride.getName());
	}
	
	@Test(timeout=3000)
	public void testUpdateRide() {
		RestTemplate restTemplate = new RestTemplate();
		Ride ride  = restTemplate.getForObject("http://localhost:8080/ride_tracker/ride/1",Ride.class);
		ride.setDuration(ride.getDuration()+20);
		 ride  = restTemplate.postForObject("http://localhost:8080/ride_tracker/ride/update",ride,Ride.class);
		System.out.println("Ride name: " + ride.getName());
	}
	
	@Test(timeout=3000)
	public void testUpdateRideBatch() {
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getForObject("http://localhost:8080/ride_tracker/ride/updateBatch",Ride.class);
		
	}
}
