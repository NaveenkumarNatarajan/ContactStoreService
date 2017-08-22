package com.tws.contactstore;

public class User {

	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString(){
		StringBuffer user =  new StringBuffer();
		user.append(this.firstName);
		user.append(", "+ this.lastName);
		user.append(", "+ this.userName);
		user.append(", "+ this.password);
		return user.toString();
		
	}
	
}
