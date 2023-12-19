package com.papitomarket.model.products.android;

import java.util.ArrayList;
import java.util.Iterator;

public class Products {


	private ArrayList<Product> products;
	
	public Products(ArrayList<Product> products) {
		super();
		this.products = products;
	}

	
	public Products(){
		
	}
	
	
	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public Product getProduct(String id){
		Iterator<Product> it = products.iterator();
		Product prod;
		while (it.hasNext()){
			prod = (Product)it.next();
			if (prod.getId().equals(id)){
				return prod;
			}
		}
		return null;
		
	}

	
	
	
}
