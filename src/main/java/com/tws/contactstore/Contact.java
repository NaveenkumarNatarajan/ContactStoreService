package com.tws.contactstore;

public class Contact {

	private String firstName;
	private String lastName;
	private String email;
	private String streetAddress;
	private String notes;
	private String phoneNumber;
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public String toString(){
		StringBuffer user =  new StringBuffer();
		user.append(this.firstName);
		user.append(", "+ this.lastName);
		user.append(", "+ this.email);
		user.append(", "+ this.streetAddress);
		user.append(", "+ this.notes);
		return user.toString();	
	}	
}
