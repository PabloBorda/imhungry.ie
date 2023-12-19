/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.model.facebook.android;

import com.papitomarket.model.database.GlobalData;

/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class User {
	
    private static User usr;
	private String id;
	private String username;
	private String name;
	private String first_name;
	private String last_name;
	private String locale;
	private String link;
	private String email;
	private String gender;
	private String birthday; 
	private String lat;
	private String lng;
	private String addr;
	private String status = "OFFLINE";

	
	
	
	public static int scrwidth;
	public static int scrheight;
    


	public static User getInstance(){
		if (usr==null){
			usr = new User();
		}
		return usr;
	}
	
	
    private User(){
    	
    }
    
	
	
    public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFirst_name() {
		return first_name;
	}


	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}


	public String getLast_name() {
		return last_name;
	}


	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}


	public String getLocale() {
		return locale;
	}


	public void setLocale(String locale) {
		this.locale = locale;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getBirthday() {
		return birthday;
	}


	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public String getLat() {
		return lat;
	}


	public void setLat(String lat) {
		this.lat = lat;
	}


	public String getLng() {
		return lng;
	}


	public void setLng(String lng) {
		this.lng = lng;
	}


	public String getAddr() {
		return addr;
	}


	public void setAddr(String addr) {
		this.addr = addr;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public static void setInstance(User u){
      usr = u;
	}
    
}
