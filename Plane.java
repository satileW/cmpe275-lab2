package edu.sjsu.cmpe275.lab2;

import javax.persistence.Embeddable;

@Embeddable
public class Plane {
	private int capacity;
	private String model;
	private String manufacturer;
	private int yearOfManufacture;
}
