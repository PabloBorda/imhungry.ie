package com.papitomarket.notifications.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;


/*
 * Notification types: each type of notification renders to the attached Activity
 * When a message arrives, the json is parsed, so an instance of the matching notification
 * is created.
 * 
 * Author: Pablo Tomas Borda Di Berardino
 * 
 * */
public class SBNotification {

    protected static int count_id = 0;
	public int id;
	protected String body;
	protected String type;

	public SBNotification(String body, String type){
		this.body = body;
		this.type = type;
		this.id = count_id;
		SBNotification.count_id = SBNotification.count_id + 1;
	}
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SBNotification(String body){
		this.body = body;
	}

	public void setBody(String body){
		this.body = body;
	}
	public Class<? extends Activity> process() {
		
		return null;
	}
	
	
	public View getView(Context ctx,ViewGroup parent){
		return (new View(ctx));
	}
	
    public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	public boolean compare(SBNotification noti){
		return (this.hashCode()==noti.hashCode());
		
	}
	
	
}
