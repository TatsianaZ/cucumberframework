package com.gmail.pageobjects.utils;

public class GmailUser {

	private String userName;
	
	public GmailUser(){
		this.userName = null;
	}
	
	public static GmailUser firstUser(){
		GmailUser gmailUser = new GmailUser();
		gmailUser.setUserName(null);
		return gmailUser;
	}
	
	public void setUserName(final String userName){
		this.userName = userName;
	}
}
