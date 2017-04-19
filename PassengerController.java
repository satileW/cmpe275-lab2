package edu.sjsu.cmpe275.lab2;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.jasper.tagplugins.jstl.core.Catch;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.sun.net.httpserver.Authenticator.Success;

@RestController
public class PassengerController {
//	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
//	public String hello(@PathVariable String name){
//		//original testing function
//		String result = "Hello ";
//		result += name;
//		return result;
//	}
	@RequestMapping(value = "/passenger/{id}",params="json", method = RequestMethod.GET,  produces = "application/json")
	public Passenger getPassenger(@PathVariable int id, @RequestParam Boolean json){
		Passenger test = null;
		if(json==true){
			test = new Passenger(id, "miao", "Wang", 26, "female", "4086464171");			
		}
		return test;
	}    
	@RequestMapping(value = "/passenger/{id}",params = "xml", method = RequestMethod.GET, produces = "application/xml")
	public Passenger getPassengerII(@PathVariable int id, @RequestParam Boolean xml){
		Passenger test = null;
		if(xml==true){
			test = new Passenger(id, "miao", "Wang", 26, "female", "4086464171");			
		}
		return test;
	}
	@RequestMapping(value = "/passenger", method = RequestMethod.POST, produces = "application/json")
	public Passenger createPassenger(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, 
			@RequestParam("age") int age, @RequestParam("gender") String gender, @RequestParam("phone") String phone){
		int id = 1000;//get from database
		Passenger passenger = new Passenger(id, firstname, lastname, age, gender, phone);
		return passenger;
	}
	@RequestMapping(value = "/passenger/{id}", method = RequestMethod.PUT, produces = "application/json")
	public Passenger updatePassenger(@PathVariable int id, @RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, 
			@RequestParam("age") int age, @RequestParam("gender") String gender, @RequestParam("phone") String phone){
		Passenger passenger = null;//get from database with id
		passenger = new Passenger(id, firstname, lastname, age, gender, phone);
		return passenger;
	}
	@RequestMapping(value = "/passenger/{id}", method = RequestMethod.DELETE, produces = "application/xml")
	public AbstractReponse deletePassenger(@PathVariable int id, HttpServletResponse response)throws BadRequestException {
		//successDelete is get from searching of database
		boolean successDelete = false;
		if(successDelete){
			response.setStatus(HttpServletResponse.SC_OK);
			return new Response(HttpServletResponse.SC_OK, "Passenger with id "+id+" is deleted successfully");
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		//return new BadRequest(HttpServletÍÍÍResponse.SC_NOT_FOUND, "!!");
		throw new BadRequestException(id, "Passenger with id"); 
	}
	
}
