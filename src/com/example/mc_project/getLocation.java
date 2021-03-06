package com.example.mc_project;

import com.example.mc_project.classes.Constants;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class getLocation extends Activity 
{
	LocationManager manager;
	Criteria criteria;
	ProgressDialog pd;
	boolean gotOnce=false;
	public static final String PREFS_NAME = "login";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
//		setContentView(R.layout.splash);
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "cIlG71ZlahKyRJv8kaJ0L2y6hDbdvixZyimny8tH", "QhqzYsrDG8GwvzTqvX2LcV6ZgCAhhy2pPW4Corg7");
		init();
		initPD();
		pd.show();
		startLocationFetching();
	}

	public void init()
	{
		criteria=new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
		criteria.setVerticalAccuracy(Criteria.NO_REQUIREMENT);
		manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
	}
	
		private void startLocationFetching() 
		{
			
			// TODO Auto-generated method stub
			new Handler().postDelayed(new Runnable() 
			{
				
				@Override
				public void run() 
				{
					// TODO Auto-generated method stub
					manager.requestLocationUpdates(250, 1, criteria, new LocationListener() 
					{
						@Override public void onStatusChanged(String provider, int status, Bundle extras) {}
						@Override public void onProviderEnabled(String provider) {}
						@Override public void onProviderDisabled(String provider) {}
		
						@Override
						public void onLocationChanged(Location location) 
						{
							// TODO Auto-generated method stub
							if(!gotOnce)
							{
								gotOnce=true;
								Constants.latitude=location.getLatitude();
								Constants.longitude=location.getLongitude();
								Log.d("loc",String.valueOf(Constants.latitude));
								Log.d("loc",String.valueOf(Constants.longitude));
								pd.dismiss();
								Constants.user=setUser();
								SharedPreferences sett = getSharedPreferences(PREFS_NAME, 0);
								boolean loginStatus=sett.getBoolean("login",false);
								if(loginStatus)
								{
									Intent fetchHomepage=new Intent(Constants.fetchHomepage);
									startActivity(fetchHomepage);
								}
								else
								{
									Intent login=new Intent("com.example.mc_project.login.Login");
									startActivity(login);
									finish();
									
								}
								
							}
							else
								manager.removeUpdates(this);
						}
					}, null);
				}
			}, 300);
		}
		
		 public void initPD() 
		 {
				pd=new ProgressDialog(getLocation.this);
				pd.setTitle("Please Wait...");
				pd.setMessage("Fetching Location");
				pd.setIndeterminate(true);
				pd.setCancelable(false);
		 }
		 
		 public ParseUser setUser()
		 {
			 SharedPreferences sett = getSharedPreferences(PREFS_NAME, 0);
			 String email=sett.getString("useremail","");
			 String name=sett.getString("username","");
			 Constants.user_email=email;
			 Constants.user_name=name;
			 ParseUser user=ParseUser.getCurrentUser();
			 try 
			 {
					user = ParseUser.logIn(email, "password");
					Log.d("user1",user.getObjectId());
					if(user.getEmail()==null)
					{
						Log.d("user2",user.getObjectId());
						if(user.getEmail()==null && email!=null)
							user.setEmail(email);
						try 
						{
							user.save();
						}
						catch (ParseException e) {	}
					}
					return user;
			 } 
			 catch (ParseException e1) 
			 {
					String debugStr="";
					try{
						user = new ParseUser();
						user.setUsername(email);
						user.put("Name",name);
						user.setEmail(email);
						user.setPassword("password");
						user.signUp(); 
						Log.d("user3",user.getObjectId());
					}
					catch(Exception excep)
					{
						Log.d("jan","x"+excep.getLocalizedMessage()+debugStr);
					}
					return user;
			}
		 }
}
