package com.papitomarket.model.cart;

import com.papitomarket.model.products.android.Product;

public class Order {

	Product p;
	int amount;
	float price;
	boolean delivery;
	
	public Order(Product p, int amount,boolean delivery){
		this.p = p;
		this.amount = amount;
		this.delivery = delivery;
		this.price = (amount * Float.valueOf(p.getPrice()));
	}
	
	
	public boolean isDelivery() {
		return delivery;
	}


	public void setDelivery(boolean delivery) {
		this.delivery = delivery;
	}


	public Product getP() {
		return p;
	}
	public void setP(Product p) {
		this.p = p;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

	
	
}
