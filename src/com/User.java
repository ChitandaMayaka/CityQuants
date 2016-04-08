package com;

public class User {

	private String username;
	private String password;
	
	private Database database = new Database();
	
	public boolean login(String username, String password){
		boolean result = database.queryUser(username, password);
		if (result){
			this.username = username;
			this.password = password;
		}
		return result;
	}
}