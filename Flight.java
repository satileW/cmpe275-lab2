package edu.sjsu.cmpe275.lab2;

import java.util.Date;
import java.util.List;

import javax.persistence.EmbeddedId;

public class Flight {
	private String number; // Each flight has a unique flight number.
	private int price;
	private String from;
	private String to;
	/* Date format: yy-mm-dd-hh, do not include minutes and sceonds.
	** Example: 2017-03-22-19	
	*The system only needs to supports PST. You can ignore other time zones.
	*/ 
	private Date departureTime;
	private Date arrivalTime;
	private int seatsLeft;
	private String description;
	@EmbeddedId
	private Plane plane; // Embedded
	private List<Passenger> passengers;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Date getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	public Date getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getSeatsLeft() {
		return seatsLeft;
	}
	public void setSeatsLeft(int seatsLeft) {
		this.seatsLeft = seatsLeft;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
}