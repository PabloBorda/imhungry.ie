package com.papitomarket.model.notification;

import java.util.ArrayList;



public class Items {
	
	private String category;
	private ArrayList<Item> items;
	
	public Items(){
		items = new ArrayList<Item>();
	}
	
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public void addItem(Item it){
		items.add(it);
	}
	
	public Item[] toArray(){
		Item []items = new Item[this.getItems().size()];
		return this.getItems().toArray(items);
	}
	
	
}