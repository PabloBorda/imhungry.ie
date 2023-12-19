/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.model.products.android;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class Product {

    public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getPrice() {
		StringBuffer currency = new StringBuffer();
		StringBuffer no_currency_price = new StringBuffer();
		for(int i=0;i<this.price.length();i++){
			if (!Character.isDigit(this.price.charAt(i))){
			  currency.append(this.price.charAt(i));	
			} else {
				no_currency_price.append(this.price.charAt(i));
			}
			
		}
		this.setPrice(no_currency_price.toString());
		this.setCurrency(currency.toString());
		return no_currency_price.toString();
		
	}



	public void setPrice(String price) {
		this.price = price;
	}



	public String getOrders() {
		return orders;
	}



	public void setOrders(String orders) {
		this.orders = orders;
	}



	public ArrayList<String> getPics() {
		return pics;
	}



	public void setPics(ArrayList<String> pics) {
		this.pics = pics;
	}



	public ArrayList<String> getTags() {
		return tags;
	}



	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}



	public String getDeliver() {
		return deliver;
	}



	public void setDeliver(String deliver) {
		this.deliver = deliver;
	}



	public String getDelivery_ratio() {
		return delivery_ratio;
	}



	public void setDelivery_ratio(String delivery_ratio) {
		this.delivery_ratio = delivery_ratio;
	}

	private String id;
    private String name;
    private String description;
    private String price;
    private String orders;
    private ArrayList<String> pics;
    private ArrayList<String> tags;
    private String deliver;
    private String delivery_ratio;
    private String currency;
    
	
    public String getCurrency() {
		return currency;
	}



	public void setCurrency(String currency) {
		this.currency = currency;
	}



	public Product(String id, String name, String description, String price,
			String orders, ArrayList<String> pics, ArrayList<String> tags,
			String deliver, String delivery_ratio) {
		super();
		Product.idgen = Product.idgen + 1;
		this.id = Product.idgen + "";
		this.name = name;
		this.description = description;
		this.price = price;
		this.orders = orders;
		this.pics = pics;
		this.tags = tags;
		this.deliver = deliver;
		this.delivery_ratio = delivery_ratio;
	}

    private static int idgen = 12345;
    
    public Product(){
        this.pics = new ArrayList<String>();
        this.tags = new ArrayList<String>();
        Product.idgen = Product.idgen + 1;
        this.id = Product.idgen + "";
        
    }
    
    public void addPic(String url){
        pics.add(url);
    }
    
}
