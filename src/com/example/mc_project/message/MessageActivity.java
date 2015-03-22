package com.example.mc_project.message;

import java.util.ArrayList;
import java.util.List;

import com.example.mc_project.R;
import com.example.mc_project.classes.Constants;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

public class MessageActivity extends ActionBarActivity 
{
	ProgressDialog pd;
	List<ParseObject> MessageList=new ArrayList<ParseObject>();
	ListView listView;
	CustomList cList;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_messages);
		Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
		toolbar.setTitle("Messages");
		setSupportActionBar(toolbar);
		listView = (ListView) findViewById(R.id.list);
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
			ParseGeoPoint point=new ParseGeoPoint(Constants.latitude,Constants.longitude);
			
			ParseUser user=Constants.user;
			ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("Message");
			ParseQuery<ParseObject> queryUser=new ParseQuery<ParseObject>("_User");
			queryUser.whereEqualTo("objectId",user.getObjectId());
			query.include("Recepient");
			query.whereWithinKilometers("location", point,Constants.nearby_distance);
			query.whereMatchesQuery("Recepient",queryUser);

			try 
			{
				MessageList=query.find();
				Log.d("message",MessageList.toString());
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
			cList = new CustomList(MessageList,getApplicationContext());
		    listView.setAdapter(cList);
			try{pd.setCancelable(true);}catch(Exception e){}
			try{pd.dismiss();}catch(Exception e){}
			super.onPostExecute(result);
		}
		 
	 }
	
	
	 public void initPD() 
	 {
			pd=new ProgressDialog(MessageActivity.this);
			pd.setTitle("Please Wait...");
			pd.setMessage("Fetching Data");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
	 }
}
