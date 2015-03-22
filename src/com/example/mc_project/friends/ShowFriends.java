package com.example.mc_project.friends;

import java.util.ArrayList;
import java.util.List;

import com.example.mc_project.R;
import com.example.mc_project.classes.Constants;
import com.example.mc_project.message.MessageActivity;
import com.example.mc_project.message.MessageActivity.BgTask;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

public class ShowFriends extends ActionBarActivity 
{
	List<ParseObject> Users=new ArrayList<>();
	ListView listView;
	CustomList cList;
	ProgressDialog pd;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_show_people);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
		toolbar.setTitle("People");
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
			ParseUser user=Constants.user;
			ParseQuery<ParseObject> queryUser=new ParseQuery<ParseObject>("_User");
			try 
			{
				Users=queryUser.find();
				Log.d("users",Users.toString());
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
			cList = new CustomList(Users,getApplicationContext());
		    listView.setAdapter(cList);
			try{pd.setCancelable(true);}catch(Exception e){}
			try{pd.dismiss();}catch(Exception e){}
			super.onPostExecute(result);
		}
		 
	 }
	
	 public void initPD() 
	 {
			pd=new ProgressDialog(ShowFriends.this);
			pd.setTitle("Please Wait...");
			pd.setMessage("Fetching Data");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
	 
	 }
}
