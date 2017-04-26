package edu.sjsu.cmpe275.lab2;

import javax.servlet.http.HttpServletResponse;

//import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sjsu.cmpe275.lab2.Passenger;
import edu.sjsu.cmpe275.lab2.PassengerRepository;


@RestController
@RequestMapping(value = "/passenger")
public class PassengerController {
	//private static final String template = "Hello, %s!";
	//private final AtomicLong counter = new AtomicLong();
	@Autowired
	private PassengerRepository passengerRepository;
	
	private Object storePassenger(Passenger passenger, String firstname, String lastname, 
			Integer age, String gender, String phone,HttpServletResponse response) throws BadRequestException{
		//DONE BAD Request one
		if(firstname==null||lastname==null||firstname==""||lastname==""
				||age==null||gender==null||gender==""||phone==null||phone==""){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw new BadRequestException( "Lack of parameters", 400);
		}
		passenger.setFirstName(firstname);
		passenger.setLastName(lastname);
		passenger.setAge(age);
        passenger.setGender(gender);
		passenger.setPhone(phone);
		
		
		//TODO may have a bad request
		Object returnValue = null;
		try{
			returnValue = passengerRepository.save(passenger);
		 } catch(Exception e){
			 //DONE BAD request to database
			 response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			 throw new BadRequestException("Duplicate phone number",400);
		 }
		return returnValue;
	}
	
	@RequestMapping(
			value = "/{id}", 
			params="json", 
			method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody Object getPassengerJson(
			@PathVariable int id, 
			@RequestParam Boolean json,
			HttpServletResponse response) throws BadRequestException {
		if(json.equals(true)){
			Object findObject = passengerRepository.findOne(id);
			if(findObject!=null){
				return findObject; //new Passenger(id, "Bob", "Dylan", 56, "M", "733223423"); //String.format(template, name));
			}else{
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				throw new BadRequestException(id, "Passenger with id");//DONE
			}
		}
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		throw new BadRequestException("Parameter JSON error", 400);//DONE
		//return "bad request";//TODO
	}
	
	@RequestMapping(
			value = "/{id}", 
			params= "xml", 
			method = RequestMethod.GET,
			produces = "application/xml")
	public @ResponseBody Object getPassengerXml(
			@PathVariable int id, 
			@RequestParam Boolean xml,
			HttpServletResponse response) throws BadRequestException{
		
		if(xml.equals(true)){
			Object findObject = passengerRepository.findOne(id);
			if(findObject!=null){
				return findObject; //new Passenger(id, "Bob", "Dylan", 56, "M", "733223423"); //String.format(template, name));
			}else{
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				throw new BadRequestException(id, "Passenger with id");//DONE
			}
		}
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		throw new BadRequestException( "Parameter XML error", 400);//DONE
		//return "bad request"; //String.format(template, name)); //TODO
	}
	
	@RequestMapping(
			value = "",
			params= {"firstname", "lastname", "age", "gender", "phone"},
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Object createPassenger(
			@RequestParam("firstname") String firstname, 
            @RequestParam("lastname") String lastname,
            @RequestParam("age") int age,
            @RequestParam("gender") String gender, 
            @RequestParam("phone") String phone,
            HttpServletResponse response){
				
		Passenger passenger = new Passenger();
		return storePassenger(passenger, firstname, lastname, age, gender, phone, response);
		
		//return passengerRepository.findOne(passenger.getId());
	}
	
	@RequestMapping(
			value = "/{id}",
			params= {"firstname", "lastname", "age", "gender", "phone"},
			method = RequestMethod.PUT,
			produces = "application/json")
	public @ResponseBody Object updatePassenger(
			@PathVariable int id,
			@RequestParam("firstname") String firstname, 
            @RequestParam("lastname") String lastname,
            @RequestParam("age") int age,
            @RequestParam("gender") String gender, 
            @RequestParam("phone") String phone,
            HttpServletResponse response) throws BadRequestException{
				
		Passenger passenger = passengerRepository.findOne(id);
		if(passenger==null){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw new BadRequestException(id, "Passenger with id");
		}
		storePassenger(passenger, firstname, lastname, age, gender, phone, response);
		
		return passengerRepository.findOne(id);
	}
	
	@RequestMapping(
			value = "/{id}",
			method = RequestMethod.DELETE,
			produces = "application/json")
	public @ResponseBody ResponseEntity<?> deletePassenger(@PathVariable int id,
            HttpServletResponse response)throws BadRequestException{
		try{
			passengerRepository.delete(id);
		}catch(Exception e){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw new BadRequestException(id, "Passenger with id");
		}
		return new ResponseEntity<>(new Response(200, "Passenger with id "+id+" is deleted successfully "),HttpStatus.ACCEPTED);
		//TODO delete reservation, update leftseats, SUCCESSFUL response
		
	}
	/*
	@RequestMapping(value = "/passenger/{id}", params="json", method = RequestMethod.GET, produces = "application/json")
	public Passenger getPassenger(@PathVariable int id, @RequestParam Boolean json){
		Passenger test = null;
		if(json==true){
			test = new Passenger(id, "m", "W", 26, "female", "11111111");			
		}
		return test;
	}    
	@RequestMapping(value = "/passenger/{id}", params = "xml", method = RequestMethod.GET, produces = "application/xml")
	public Passenger getPassengerII(@PathVariable int id, @RequestParam Boolean xml){
		Passenger test = null;
		if(xml==true){
			test = new Passenger(id, "m", "w", 26, "female", "11122222");			
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
	*/
}
