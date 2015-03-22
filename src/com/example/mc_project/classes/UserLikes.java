package com.example.mc_project.classes;

import com.parse.ParseObject;

public class UserLikes 
{
	ParseObject po;

	public UserLikes()
	{
		this.po=new ParseObject("Like");
//		this.setName("");
//		this.setGeopoint(0.0,0.0);
//		this.setPhoto(null);
	}
	
	public String getName() 
	{
		return this.po.getString("Name");
	}
	
	public void setName(String name) 
	{
		
	}
	public String getCategory() 
	{
		return this.po.getString("Category");
	}
	
	public void setCategory(String category) 
	{
		
	}
	
}
