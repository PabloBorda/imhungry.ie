package com.papitomarket.notifications.android;


/*
 * Notification types: each type of notification renders to the attached Activity
 * When a message arrives, the json is parsed, so an instance of the matching notification
 * is created.
 * 
 * Author: Pablo Tomas Borda Di Berardino
 * 
 * */
public class SBTagNotification extends SBNotification {

	public SBTagNotification(String body){
		super(body);
	}
	
	
}
