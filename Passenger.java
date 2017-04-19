package edu.sjsu.cmpe275.lab2;

//import javax.xml.bind.annotation.XmlRootElement;


public class Passenger {
	public Passenger(){
		super();
	}
	public Passenger(int id, String firstname, String lastname, int age, String gender, String phone){
		this.id = id;
		this.firstName = firstname;
		this.lastName = lastname;
		this.setAge(age);
		this.setGender(gender);
		this.phone = phone;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	private int id;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private String phone;//must be unique
	
}
