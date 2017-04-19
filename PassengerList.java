package edu.sjsu.cmpe275.lab2;

import java.util.List;

//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "passengers-list")


//this is a non-sense class file!!!!!!!!!!!!!!!!!
public class PassengerList {
	 List listOfPassengers;
	 
	 public PassengerList() {
	  super();
	 }
	 public PassengerList(List listOfPassengers) {
	  this.listOfPassengers=listOfPassengers;
	 }
	 public List getListOfCountries() {
	  return listOfPassengers;
	 }
	 
	 //@XmlElement(name = "passenger")
	 public void setListOfCountries(List listOfPassengers) {
	  this.listOfPassengers = listOfPassengers;
	 }
}
