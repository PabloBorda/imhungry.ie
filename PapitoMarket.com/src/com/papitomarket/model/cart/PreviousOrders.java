package com.papitomarket.model.cart;

public class PreviousOrders {

	private long order_id;
	
	public PreviousOrders(String json) {
		super();
	
		this.json = json;
	}
	public long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	private String json;
	
	
	
}
