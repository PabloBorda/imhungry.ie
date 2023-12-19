package com.papitomarket.model.shipping;

public class ShippingAddrs {

	private long id;
	private String address;
	private String ap;

	public String getAp() {
		return ap;
	}

	public void setAp(String ap) {
		this.ap = ap;
	}

	public ShippingAddrs(String address,String ap) {
		super();
		this.address = address;
		this.ap = ap;
	}
	
	public ShippingAddrs(long id, String address) {
		super();
		this.id = id;
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
