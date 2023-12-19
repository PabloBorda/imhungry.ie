package com.papitomarket.model.stores.android;

import java.util.List;

public class Location {

	public String type;
	public List<Float> coordinates;
	

	public Location(String type, List<Float> coordinates) {
		super();
		this.type = type;
		this.coordinates = coordinates;
	}
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Float> getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(List<Float> coordinates) {
		this.coordinates = coordinates;
	}

	public String getLat(){
		return this.coordinates.get(1).toString();
	}
	
	public String getLng(){
		return this.coordinates.get(0).toString();
	}
	
}
