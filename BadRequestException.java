package edu.sjsu.cmpe275.lab2;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class BadRequestException extends RuntimeException {
	private int passengerID;
	private String errorMsg;
	public BadRequestException(int id, String str) {
		// TODO Auto-generated constructor stub
		super();
		this.setPassengerID(id);
		this.errorMsg = str;
	}
	public int getPassengerID() {
		return passengerID;
	}
	public void setPassengerID(int passengerID) {
		this.passengerID = passengerID;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
