package com.example.mc_project.homePage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import com.example.mc_project.R;
import com.example.mc_project.classes.Constants;
import com.example.mc_project.classes.Post;
import com.example.mc_project.places.GooglePlaces;
import com.example.mc_project.places.Places;



import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FetchHomepage extends ActionBarActivity
{
	List<Post> posts_list=new ArrayList<Post>();
	ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.blank);

			Parse.initialize(this, "cIlG71ZlahKyRJv8kaJ0L2y6hDbdvixZyimny8tH", "QhqzYsrDG8GwvzTqvX2LcV6ZgCAhhy2pPW4Corg7");
			Log.d("home","fetching homepage");
			/*------Toolbar----------*/
//			Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
//			toolbar.setTitle("Blabber");
//			setSupportActionBar(toolbar);
			/*-------------------------*/
				new BgTask().execute();
	}
	
	 public class BgTask extends AsyncTask<Void, Void, Void>
	 {
		 
		 @Override
		protected void onPreExecute() 
		 {
			 initPD();
			 pd.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) 
		{
			fetchGooglePlaces();
			fetch_nearby_posts();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) 
		{
			try{pd.setCancelable(true);}catch(Exception e){}
			try{pd.dismiss();}catch(Exception e){}
			populateList();
			Intent homepage=new Intent("com.example.mc_project.homePage.Homepage");
			startActivity(homepage);
			finish();
			super.onPostExecute(result);
		}
		 
	 }
		
	 
		void populateList()
		{
			for(Post post:Constants.nearByPosts)
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
		
	public void fetchGooglePlaces()
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
				+URLEncoder.encode("food|bar|church|museum|art_gallery", "UTF-8")
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
	 

		public void fetch_nearby_posts()
		{
				
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
		
		
		 public void initPD() 
		 {
				pd=new ProgressDialog(FetchHomepage.this);
				pd.setTitle("Please Wait...");
				pd.setMessage("Fetching Data");
				pd.setIndeterminate(true);
				pd.setCancelable(false);
		 }
		
}