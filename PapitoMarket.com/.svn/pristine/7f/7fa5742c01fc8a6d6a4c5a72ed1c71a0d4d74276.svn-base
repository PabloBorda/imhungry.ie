package com.papitomarket.model.stores.android;

public class Info {
	

	private String storeid;
	private String companyname;
	private String companylogo;
	private String productname;
	private String prodorstore;
	private String sumallprods;
	private String webpage;
	private String email;
	private String address;
	private Location location;
	private String distance;
	private String usr_fb;
	private String lat;
	private String lng;
	private String phone;


	public Info(String storeid, String companyname, String companylogo,
			String productname, String prodorstore, String sumallprods,
			String webpage, String email, String address, Location location,
			String distance, String usr_fb,String phone) {
		super();
		this.storeid = storeid;
		this.companyname = companyname;
		this.companylogo = companylogo;
		this.productname = productname;
		this.prodorstore = prodorstore;
		this.sumallprods = sumallprods;
		this.webpage = webpage;
		this.email = email;
		this.address = address;
		this.location = location;
		this.distance = distance;
		this.usr_fb = usr_fb;
		this.phone = phone;
	}

	
	public String getStoreid() {
		return storeid;
	}
	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCompanylogo() {
		return companylogo;
	}
	public void setCompanylogo(String companylogo) {
		this.companylogo = companylogo;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getProdorstore() {
		return prodorstore;
	}
	public void setProdorstore(String prodorstore) {
		this.prodorstore = prodorstore;
	}
	public String getSumallprods() {
		return sumallprods;
	}
	public void setSumallprods(String sumallprods) {
		this.sumallprods = sumallprods;
	}
	public String getWebpage() {
		return webpage;
	}
	public void setWebpage(String webpage) {
		this.webpage = webpage;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		//this.location.coordinates.set(1,Float.parseFloat(lat));
		this.lat = location.getCoordinates().get(1).toString();
		this.lng = location.getCoordinates().get(0).toString();
		
		this.location = location;
	}
		
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getUsr_fb() {
		return usr_fb;
	}
	public void setUsr_fb(String usr_fb) {
		this.usr_fb = usr_fb;
	}
	
	public String getLat() {
		return lat;
	}


	public void setLat(String lat) {
		this.location.coordinates.set(1,Float.parseFloat(lat));
		this.lat = lat;
	}


	public String getLng() {
		return lng;
	}


	public void setLng(String lng) {
		this.location.coordinates.set(0,Float.parseFloat(lng));
		this.lng = lng;
	}

	
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


}
