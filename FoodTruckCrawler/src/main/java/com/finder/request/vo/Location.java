package com.finder.request.vo;

public class Location {
	
    String type; 
    String [] coordinates;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String[] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String[] coordinates) {
		this.coordinates = coordinates;
	}
    
}
