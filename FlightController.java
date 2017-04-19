package edu.sjsu.cmpe275.lab2;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {
	@RequestMapping(value = "/flight/{flightNumber}", params = "json", method = RequestMethod.GET, produces = "application/json")
	public Flight getFlight(@PathVariable int flightNumber, @RequestParam Boolean json){
		Flight flight = new Flight();
		return flight;
	}
	@RequestMapping(value = "/flight/{flightNumber}", params = "xml", method = RequestMethod.GET, produces = "application/xml")
	public Flight getFlightII(@PathVariable int flightNumber, @RequestParam Boolean xml){
		Flight flight = new Flight();
		return flight;
	}
	//price=120&&from=AA&to=BB&departur
	//eTime=CC&arrivalTime=DD&description=EE&capacity=GG&model=HH&m
		//	anufacturer=II&&yearOfManufacture=1997
	@RequestMapping(value = "/flight/{flightNumber}", method = RequestMethod.POST, produces = "application/json")
	public Flight createFlight(@RequestParam("price") int price,@RequestParam("from") String from, 
			@RequestParam("to") String to,@RequestParam("departureTime") Date departureTime,@RequestParam("arrivalTime") Date arrivalTime,
			@RequestParam("description") String description,@RequestParam("capacity") int capacity,@RequestParam("model") String model,
			@RequestParam("manufacturer") String manufacturer, @RequestParam("yearOfManufacture")int year){
		Flight flight = new Flight();
		return flight;
	}
	@RequestMapping(value = "/airline/{flightNumber}", method = RequestMethod.DELETE, produces="application/json")
	public Response deleteAirline(@PathVariable int flightNumber, HttpServletResponse response)throws BadRequestException {
		//successDelete is get from searching of database
		boolean successDelete = false;
		if(successDelete){
			response.setStatus(HttpServletResponse.SC_OK);
			return new Response(HttpServletResponse.SC_OK, "Airline with flightNumber "+flightNumber+" is deleted successfully");
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		throw new BadRequestException(flightNumber, "Airline with flightNumber"); 
	}
}
