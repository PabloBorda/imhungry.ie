/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.model.products.android;

import java.util.ArrayList;

/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class Category {
	


	private String name;
    private ArrayList<Product> products;
    public boolean collapsed;
    
    
    public Category(String name, ArrayList<Product> products) {
		super();
		this.name = name;
		this.products = products;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<Product> getProducts() {
		return products;
	}


	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

    
    public Category(){
        products = new ArrayList<Product>();
    }
    
    
    public void addProduct(Product p){
        products.add(p);
    }
}
