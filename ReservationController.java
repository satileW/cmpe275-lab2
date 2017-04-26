package edu.sjsu.cmpe275.lab2;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
//import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
@RequestMapping("/reservation")
public class ReservationController {
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private PassengerRepository passengerRepository;
	@Autowired
	private FlightRepository flightRepository;
	
	
	
	@RequestMapping(
			value = "/{number}", params = "json",
			method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody Object getReservationJson(
			@PathVariable String number, HttpServletResponse response) 
					throws BadRequestException {
		if(reservationRepository.exists(number)){
			Reservation reservation = reservationRepository.findOne(number);
			
			return removePassenger(reservation);
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw new BadRequestException(number, "Reservation with number"); //TODO
		}
	}
	
	@RequestMapping(
			value = "/{number}", 
			method = RequestMethod.GET,params = "xml",
			produces = "application/xml")
	public @ResponseBody Object getReservationXml(
			@PathVariable String number, HttpServletResponse response) 
					throws BadRequestException{
		if(reservationRepository.exists(number)){
			Reservation reservation = reservationRepository.findOne(number);
			
			return removePassenger(reservation);
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw new BadRequestException(number, "Reservation with number"); //TODO
		}
	}
	
	@RequestMapping(
			value = "",
			params= {"passengerId", "flightLists"},
			method = RequestMethod.POST,
			produces = "application/xml")
	public @ResponseBody Object createReservation(
			@RequestParam("passengerId") int passengerId, 
            @RequestParam("flightLists") String[] flightLists,
            HttpServletResponse response){
		
		Passenger passenger = passengerRepository.findOne(passengerId);
		if(passenger==null){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw new BadRequestException(passengerId, "Passenger with id");
		}
		Reservation reservation = new Reservation();
		return storeReservation(reservation, passenger, flightLists, response);
		//return reservationRepository.findOne(reservation.getOrderNumber());
	}
	
	@RequestMapping(
			value = "/{number}",
			params= {"flightsAdded", "flightsRemoved"}, 
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody Object updateReservation(
			@PathVariable String number,
			@RequestParam("flightsAdded") String[] flightsAdded, 
            @RequestParam("flightsRemoved") String[] flightsRemoved,
            HttpServletResponse response){
		if(reservationRepository.exists(number)){
			return storeReservation2(reservationRepository.findOne(number), flightsAdded, flightsRemoved, response);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw new BadRequestException(number, "Resevation with number");
		}
		
		//return reservationRepository.findOne(reservation.getOrderNumber());
	}
	
	@RequestMapping(
			value = "",
			params= {"passengerId", "from", "to", "flightNumber"},
			method = RequestMethod.GET,
			produces = "application/xml")
	public @ResponseBody Object searchReservation(  // TODO unfinished
			@RequestParam("passengerId") int passengerId, 
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("flightNumber") String flightNumber){
		
		Passenger passenger = passengerRepository.findOne(passengerId);
		Flight flight = flightRepository.findOne(flightNumber);
		/*
		for(Reservation reservation : passenger.getReservations()){
        	if(reservation.getFlights().contains(flight)){
        		return "Retrieve successfully!!!    Reservation Infomation : " + reservation.toString();
        	}
        }
        */
		return new Reservation(); // fake reponse
		//return reservationRepository.findOne(reservation.getOrderNumber());
	}
	
	//passengerId=XX&from=YY&to=ZZ&flightNumber=123
	
	@RequestMapping(
			value = "/{number}",
			method = RequestMethod.DELETE,
			produces = "application/XML")
	public @ResponseBody Object deleteReservation(
			@PathVariable String number,
			HttpServletResponse response){
		if(reservationRepository.exists(number)){
			//this is a delete method
			reservationRepository.delete(number);
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			return new Response(200, "Delete reservation success"); //TODO unfinished
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw new BadRequestException( number,"Reservation with number");//TODO
		}
		
		//return reservationRepository.findOne(reservation.getOrderNumber());
	}
	
	
	private Object storeReservation(Reservation reservation, Passenger passenger,
			String[] flightLists, HttpServletResponse response){
		if(flightLists.length==0){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw new BadRequestException("Passenger reserve no flights",400);
		}
		int price = 0;
		Set<Flight> f1 = new HashSet<Flight>();  // TODO for check overlapping
		Set<Flight> flights = new HashSet<Flight>();
		
		for(String f : flightLists){
			if(flightRepository.exists(f)){
				f1.add(flightRepository.findOne(f));
			}
		}
		if(f1.size()!=flightLists.length){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			throw new BadRequestException("Some flight in your reservation is not existing",404);
		}
		//DONE check overlapping
		if(!overlapping(f1,null,null)){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw new BadRequestException("Flights are overlapping",400);
		}
		//TODO judge if the data is duplicated
		for(String flightNum : flightLists){
			Flight flight = flightRepository.findOne(flightNum);
			Set<Passenger> passengers = flight.getPassengers();
			
			//bad request
			if(passengers.contains(passenger)){
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw new BadRequestException("You already in one flight of the reservation", 400); //bad request
			} else {
				int seat = flight.getSeatsLeft();
				System.out.println("seat" + seat);
				if(seat == 0) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw new BadRequestException("Some flight in your reservation is full, cannot reservate for you",400); //TODO bad request
				} else {
					seat--;
				}
				flight.setSeatsLeft(seat);
				passengers.add(passenger);
			}
			flight.setPassengers(passengers);
			
			price += flight.getPrice();
			flights.add(flight);
		}
		
		reservation.setPrice(price);
		reservation.setFlights(flights);
		reservation.setPassenger(passenger);
		
		Reservation reser = reservationRepository.save(reservation);

		return removePassenger(reser);
	}
	
	private Object storeReservation2(Reservation reservation, String[] flightsAdded, 
			String[] flightsRemoved, HttpServletResponse response){
		if(flightsAdded.length==0&&flightsRemoved.length==0){
			return reservation;
		}
		Set<Flight> fAdd = new HashSet<Flight>();  // TODO for check overlapping
		Set<Flight> fRem = new HashSet<Flight>();  // TODO for check overlapping
		Set<Flight> flights = reservation.getFlights();
		Passenger passenger = reservation.getPassenger();
		int price = reservation.getPrice();
		if(flightsAdded.length!=0){
			for(String f : flightsAdded){
				if(flightRepository.exists(f)){
					fAdd.add(flightRepository.findOne(f));
				}
			}
			if(fAdd.size()!=flightsAdded.length){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				throw new BadRequestException("some new flights you want to add are not existing",404);
			}	
		}
		if(flightsRemoved.length!=0){
			for(String f : flightsRemoved){
				if(flightRepository.exists(f)){
					fRem.add(flightRepository.findOne(f));
				}
			}
			//removed flights allow to not existed
//			if(fRem.size()==0){
//				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//				throw new BadRequestException("All the new flights you want to remove are not existing",404);
//			}
		}
		
		//TODO overlapping check
		if(!overlapping(fAdd, fRem, flights)){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			throw new BadRequestException("Existing overlapped flights",404);
		}
		
		//TODO judge if the flight,reservation data is duplicated
		for(String flightNum : flightsRemoved){
			Flight flight = flightRepository.findOne(flightNum);
			Set<Passenger> passengers = flight.getPassengers();
			
			if(!passengers.contains(passenger)){
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw new BadRequestException("passenger not in",400); // TODO bad request
			} else {
				int seat = flight.getSeatsLeft();
				System.out.println("seat" + seat);
				seat++;
				flight.setSeatsLeft(seat);
				passengers.remove(passenger);
			}
			flight.setPassengers(passengers);
			
			price -= flight.getPrice();
			flights.remove(flight);
		}
		
		for(String flightNum : flightsAdded){
			Flight flight = flightRepository.findOne(flightNum);
			Set<Passenger> passengers = flight.getPassengers();
			
			if(passengers.contains(passenger)){
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				throw new BadRequestException("passenger already in",400); // TODO bad request
			} else {
				int seat = flight.getSeatsLeft();
				System.out.println("seat" + seat);
				if(seat == 0) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					throw new BadRequestException("Flight is full",400);//TODO bad request
				} else {
					seat--;
				}
				flight.setSeatsLeft(seat);
				passengers.add(passenger);
			}
			flight.setPassengers(passengers);
			
			price += flight.getPrice();
			flights.add(flight);
		}
		//System.out.println("111111111");
		reservation.setPrice(price);
		reservation.setFlights(flights);
		reservation.setPassenger(passenger);
		//System.out.println("222222222");
		return removePassenger(reservationRepository.save(reservation));
		
	}
	//non-overlapping judgement
	private boolean overlapping(Set<Flight> addedFlights, Set<Flight> removedFlights, Set<Flight> reservatedFlights){
		//addedFlights is the list you want to add into a exited reservation or a new reservation
		PriorityQueue<Flight> queue = new PriorityQueue<>(new Comparator<Flight>(){
            public int compare(Flight a, Flight b){
                return a.getDepartureTime().compareTo(b.getDepartureTime());
            }
        });
		//addedFlights must be not null
		for(Flight flight: addedFlights){
			queue.add(flight);
		}
		//reservatedFlights may be null
		if(reservatedFlights!=null){
			for(Flight flight:reservatedFlights){
				//removedFlights may be null
				if(removedFlights==null || !removedFlights.contains(flight)){
					queue.add(flight);
				}
			}
		}
		
		Flight previousFlight = null;
		for(Flight flight: queue){
			System.out.println(flight.getDepartureTime()+"!!!"+flight.getArrivalTime());
			if(previousFlight!=null&&previousFlight.getArrivalTime().before(flight.getDepartureTime())){
				previousFlight = flight;
			}else if(previousFlight!=null&&!previousFlight.getArrivalTime().before(flight.getDepartureTime())){
				return false;
			}
		}
		return true;
	}
			
	private Reservation removePassenger(Reservation reservation){
		Set<Flight> flights = reservation.getFlights();
		for(Flight f : flights) {
			f.setPassengers(null);
		}
		reservation.setFlights(flights);
		
		return reservation;
	}
}
