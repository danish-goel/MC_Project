package com.example.mc_project.classes;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mc_project.places.GooglePlaces;
import com.example.mc_project.places.Places;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class Constants 
{
	public static int nearby_distance=3;
	public static Double latitude,longitude;
	public static String user_name;
	public static String user_email;
	public static Post post;
	public static Places place;
	
	public static List<Post> nearByPosts=new ArrayList<Post>();
	public static List<Places> nearByPlaces=new ArrayList<Places>();
	public static List<Object> all_posts=new ArrayList<Object>();
	public static ParseUser user;
	
	public static String viewSinglePost="com.example.mc_project.singlePost.ViewSinglePost";
	public static String viewSinglePlace="com.example.mc_project.singlePost.ViewSinglePlace";
	public static String addPost="com.example.mc_project.addPost.AddPost";
	public static String map="com.example.mc_project.map.Map";
	public static String homePage="com.example.mc_project.homePage.Homepage";
	public static String fetchHomepage="com.example.mc_project.homePage.FetchHomepage";
	public static String CategoryActivity="com.example.mc_project.categoryPost.CategoryActivity";
	public static String MessageActivity="com.example.mc_project.message.MessageActivity";
	
	
	
	public static Bitmap getBitmap(ParseFile parseFile) throws ParseException
	{
		if(parseFile == null)
			return null;
		byte[] byteArray = parseFile.getData();
		Bitmap bitpic = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitpic.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return bitpic;
	}
	public static String getTime(int hour,int min,int sec)
	  {
		String time;
		String am_pm;
			if(hour>12)
			{
				hour=hour-12;
				am_pm="AM";
			}
			else
			{
				am_pm="PM";
			}
			time=String.valueOf(hour)+":";
			if(min==0)
			{
				time+=String.valueOf(min)+"0"+" ";
			}
			else
			{
				time+=String.valueOf(min)+" ";
			}
			
			time+=am_pm;
			return time;
			
	  }
		public static String getDate(int date,int month,int year)
		{
		String datestring;
		datestring=String.valueOf(date)+" ";
		datestring+=getMonthName(month)+" ";
		datestring+=String.valueOf(year);
		return datestring;
					  
		}
		
		public static String getMonthName(int month)
		  {
			  if(month==0)
			  {
				  return "Jan";
			  }
			  else if(month==1)
				  return "Feb";
			  else if (month==2)
				  return "Mar";
			  else if (month==3)
				  return "Apr";
			  else if (month==4)
				  return "May";
			  else if(month==5)
				  return "Jun";
			  else if (month==6)
				  return "Jul";
			  else if (month==7)
				  return "Aug";
			  else if (month==8)
				  return "Sep";
			  else if(month==9)
				  return "Oct";
			  else if(month==10)
				  return "Nov";
			  else
				  return "Dec";
		  }
		
		public static Intent selectItem(int position) 
		{

			//Toast.makeText(getApplicationContext(), mTitles[position], 0).show();

		    // Highlight the selected item, update the title, and close the drawer
		    //mDrawerList.setItemChecked(position, true);
			if(position ==0)
			{
				Intent askquestion=new Intent(homePage);
				return askquestion;
			}
			if(position==1)
			{
				Intent askquestion=new Intent(CategoryActivity);
				return askquestion;
			}
			if(position==2)
			{
				Intent askquestion=new Intent(MessageActivity);
				return askquestion;
			}

			return null;
			
		}
		
		public static String[] getDrawerItems()
		{
			String mTitles[];
	        mTitles = new String[]{"Home","Browse By Category","Message"};
			return mTitles;
		}
		
		public static void fetchGooglePlaces()
		{
				GooglePlaces x=new GooglePlaces();
				try
				{
					String placesSearchStr ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
			        +URLEncoder.encode(String.valueOf(Constants.latitude), "UTF-8")
			        +","
			        +URLEncoder.encode(String.valueOf(Constants.longitude), "UTF-8")
			        +"&radius="
					+URLEncoder.encode("2000", "UTF-8")
					+"&sensor="
					+URLEncoder.encode("true", "UTF-8")
					+"&types="
					+URLEncoder.encode("food|movie_theater|amusement_park|restaurant", "UTF-8")
					+"&key="
					+URLEncoder.encode(GooglePlaces.api_key, "UTF-8");
										
					x.runAsyntask(placesSearchStr);
					Constants.nearByPlaces=x.getGoogle_places_Array();
				}
				catch (UnsupportedEncodingException e)
				{
				        // TODO Auto-generated catch block
				        e.printStackTrace();
				}
			

		 }
		 

			public static void fetch_nearby_posts()
			{
					List<Post> posts_list=new ArrayList<Post>();
					Post x=new Post();
					try 
					{
						posts_list=x.getNearbyPosts();
					} 
					catch (ParseException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Constants.nearByPosts=posts_list;
					
			}
			
			public static void populateAllPosts()
			{
				all_posts.clear();
				for(Post post:nearByPosts)
				{
					Constants.all_posts.add(post);
				}
				Log.d("googleplaces",Constants.nearByPlaces.toString());
				for(Places place:Constants.nearByPlaces)
				{
					Constants.all_posts.add(place);
					Log.d("googleplaces",place.toString());
				}
			}
			
			public static String capitaliseFirstLetter(String string)
			{
				StringBuilder sb = new StringBuilder(string);
				sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));  
				return sb.toString();
			}
			
	
}
