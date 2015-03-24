package com.example.mc_project.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.mc_project.R;
import com.example.mc_project.addPost.AddPost;
import com.example.mc_project.classes.Constants;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewMessage extends ActionBarActivity implements OnClickListener
{
	List<ParseUser> Friends=new ArrayList<>();
	List<String> FriendsNames=new ArrayList<>();
	List<ParseObject> Locations;
	Spinner spinnerFriends,spinnerLocation;
	EditText message;
	String[] array_spinner = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_message);
		
		spinnerFriends=(Spinner)findViewById(R.id.spinnerFriends);
		spinnerLocation=(Spinner)findViewById(R.id.spinnerlocation);
		message=(EditText)findViewById(R.id.message);
		Button submit=(Button)findViewById(R.id.submit);
		submit.setOnClickListener(this);
		Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
		
		
		toolbar.setTitle("New Message");
		setSupportActionBar(toolbar);
		
		new BgTask().execute();
		new LocationTask().execute();
		
	}
	
	public class BgTask extends AsyncTask<Void, Void, Void>
	 {
		 
		 @Override
		protected void onPreExecute() 
		 {
			 super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) 
		{
			ParseUser user=Constants.user;
			ParseRelation relationFriends = user.getRelation("UserFriends");
			ParseQuery queryFriends = relationFriends.getQuery();
			try 
			{
				Friends=queryFriends.find();
				for(ParseUser single:Friends)
				{
					FriendsNames.add(single.getString("Name"));
				}
			}
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) 
		{
			super.onPostExecute(result);
			Log.d("friendsmsg",FriendsNames.toString());
			String[] array_spinner=FriendsNames.toArray(new String[FriendsNames.size()]);
			for(String s:array_spinner)
			{
				Log.d("friendsmsg1",array_spinner.toString()+" "+s);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewMessage.this,android.R.layout.simple_spinner_dropdown_item, array_spinner);
			spinnerFriends.setAdapter(adapter);
		}
		 
	 }
	
	public class LocationTask extends AsyncTask<Void, Void, Void>
	 {
		 
		 @Override
		protected void onPreExecute() 
		 {
			 super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) 
		{
			ParseUser user=Constants.user;
			ParseRelation relationLocation = user.getRelation("UserLocation");
			ParseQuery queryLocation = relationLocation.getQuery();
			try 
			{
				Locations = queryLocation.find();
				
				array_spinner = new String[Locations.size()+1];
				array_spinner[0]="Current Location";
				for(int i=0;i<Locations.size();i++)
				{
					array_spinner[i+1] =Locations.get(i).getString("Name");
				}
			} 
			catch (ParseException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) 
		{
			super.onPostExecute(result);
			ArrayAdapter<String>adapter = new ArrayAdapter<String>(NewMessage.this,android.R.layout.simple_spinner_dropdown_item, array_spinner);
			spinnerLocation.setAdapter(adapter);
		}
		 
	 }

	@Override
	public void onClick(View v) 
	{
		if(v.getId()==R.id.submit)
		{
			ParseObject testObject = new ParseObject("Message");

			ParseGeoPoint loc=new ParseGeoPoint();
			loc.setLatitude(Constants.latitude);
			loc.setLongitude(Constants.longitude);
			ParseObject deliverer = ParseObject.createWithoutData("_User", Constants.user.getObjectId());
			
			int spinnerFriendsindex=spinnerFriends.getSelectedItemPosition();
			ParseUser recepient=Friends.get(spinnerFriendsindex);
			ParseObject recepientObject = ParseObject.createWithoutData("_User",recepient.getObjectId());
			
			testObject.put("Text",message.getText().toString());
			testObject.put("Deliverer",deliverer);
			testObject.put("Recepient",recepientObject);
			testObject.put("location",loc);
			testObject.put("DelivererName",Constants.user.getString("Name"));
			
			int spinnerLocationindex=spinnerLocation.getSelectedItemPosition();
			if(spinnerLocationindex!=0)
			{
			    testObject.put("location",Locations.get(spinnerLocationindex-1).getParseGeoPoint("LocationGeopoint"));
			}
			else
			{
				ParseGeoPoint point = new ParseGeoPoint(Double.parseDouble(String.valueOf(Constants.latitude)), Double.parseDouble(String.valueOf(Constants.longitude)));
			    testObject.put("location",point);
			}
			
			try 
			{
				testObject.save();
				Toast.makeText(NewMessage.this, "done", 0).show();
			} 
			catch (ParseException e) 
			{
				Toast.makeText(NewMessage.this, "some error occured", 0).show();
				e.printStackTrace();
			}
		}
	}

}
