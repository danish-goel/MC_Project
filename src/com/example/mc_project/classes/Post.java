package com.example.mc_project.classes;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Post 
{
	ParseObject po;

	public Post()
	{
		this.po=new ParseObject("Post");
//		this.setName("");
//		this.setGeopoint(0.0,0.0);
//		this.setPhoto(null);
	}
	
	public String getTag() 
	{
		return this.po.getString("Tag");
	}
	
	public void setTag(String tag) 
	{
		
	}
	
	public Bitmap getPhoto() throws ParseException 
	{
		ParseFile parseFile=(ParseFile)this.po.getParseFile("file");
		return Constants.getBitmap(parseFile);
	}
	
	public void setPhoto(Bitmap photo) 
	{
		
	}
	
	public ParseGeoPoint getGeopoint() 
	{
		return this.po.getParseGeoPoint("location");
	}
	
	public void setGeopoint(Double lat,Double lng) 
	{
		ParseGeoPoint geo=new ParseGeoPoint();
		geo.setLatitude(lat);
		geo.setLongitude(lng);
		this.po.put("location",geo);
	}
	
	public String getEmail() 
	{
		return this.po.getString("Email");
	}
	
	public void setEmail(String email) 
	{
		
	}
	
	public String getName() 
	{
		return this.po.getString("Name");
	}
	
	public void setName(String name) 
	{
		
	}
	
	public String getText() 
	{
		return this.po.getString("Text");
	}
	
	public void setText(String text) 
	{
		
	}
	
	public Date getCreatedAt()
	{
		return this.po.getCreatedAt();
	}
	
	public List<Post> getNearbyPosts() throws ParseException
	{
		List<Post> nearby_posts=new ArrayList<Post>();
		List<ParseObject> lParse;
		
	    ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("Post");
		ParseGeoPoint point=new ParseGeoPoint(Constants.latitude,Constants.longitude);
		Log.d("nearby","point"+point.getLatitude()+point.getLongitude());
		query.whereWithinKilometers("location", point,Constants.nearby_distance);
		lParse=query.find();
		Log.d("nearby", lParse.toString());
		for(ParseObject single_post:lParse)
		{
			Post post=new Post();
			post.po=single_post;
			Log.d("nearby","s"+post.toString());
			nearby_posts.add(post);
		}
		return nearby_posts;
	}

}
