package com.techelevator.tenmo.models;

public class User {

	private Integer id;
	private String username;
	private static final String USERHEADER_FORMAT = "%5s%-20s  %s";
	private static final String USER_FORMAT = "%-20s  %s";
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public static String userHeader() {
		return String.format(USERHEADER_FORMAT, "", "Name", "User ID");
	}
	
	public String toString() {
		return String.format(USER_FORMAT, this.username, this.id);
	}
}
