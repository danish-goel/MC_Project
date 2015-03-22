package com.example.mc_project.places;

import java.util.ArrayList;

import com.example.mc_project.classes.Constants;
import com.google.android.gms.maps.model.LatLng;

public class Places 
{
	public Places()
	{
		name="";
		vicinity="";
		rating="";
		opening_hours="";
	}
	@Override
	public String toString() 
	{
		// TODO Auto-generated method stub
		StringBuilder description=new StringBuilder();
		description.append("Name:"+this.getName()+"\n");
		description.append("Vicinity:"+this.getVicinity()+"\n");
		description.append("Lat:"+this.getGeopoint().latitude+"\n");
		description.append("Long:"+this.getGeopoint().longitude+"\n");
		description.append("Types:"+this.getTypes().toString()+"\n");
		return description.toString();
	}
	String name;
	String vicinity;
	ArrayList<String>types;
	LatLng geopoint;
	String rating;
	String  opening_hours;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getOpening_hours() {
		return opening_hours;
	}
	public void setOpening_hours(String opening_hours) {
		this.opening_hours = opening_hours;
	}
	public String getVicinity() {
		return vicinity;
	}
	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public ArrayList<String> getTypes() {
		return types;
	}
	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}
	public LatLng getGeopoint() {
		return geopoint;
	}
	public void setGeopoint(LatLng geopoint) {
		this.geopoint = geopoint;
	}
	
	public String getTypeString()
	{
		String resultType="";
		for(String s:types)
		{
			resultType+=Constants.capitaliseFirstLetter(s)+",";
		}
		return resultType;
	}
}
