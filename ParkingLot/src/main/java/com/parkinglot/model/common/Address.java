/**
 * 
 */
package com.parkinglot.model.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author arun
 *
 */
@Setter
@Getter
public class Address {
	
	private String addressline1;
	private String addressline2;
	private String addressline3;
	private String street;
	private String city;
	private String state;
	private String country;
	private String pincode;
}
