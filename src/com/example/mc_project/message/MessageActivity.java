package com.example.mc_project.message;

import java.util.ArrayList;
import java.util.List;

import com.example.mc_project.R;
import com.example.mc_project.addMessage.AddMessage;
import com.example.mc_project.classes.Constants;
import com.example.mc_project.homePage.NavigationDrawerAdapter;
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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class MessageActivity extends ActionBarActivity 
{
	/*-----------Drawer items---------*/
	RecyclerView mRightNavigationDrawerRecyclerView;
	String[] mTitles;
	private DrawerLayout mDrawerLayout;
	static ActionBarDrawerToggle drawerToggle;
	/*---------------------------*/
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
		/*------------------Navigation Drawer-------------------------*/
		 
		 mTitles = Constants.getDrawerItems();
	     mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	       
	     setRightDrawer();
	     drawerToggle= new ActionBarDrawerToggle(this, mDrawerLayout,toolbar, R.string.app_name, R.string.app_name)
	     { 

		      /** Called when a drawer has settled in a completely closed state. */ 
		      public void onDrawerClosed(View view) 
		     {
		          super.onDrawerClosed(view);
		     } 
	
		     /** Called when a drawer has settled in a completely open state. */ 
		     public void onDrawerOpened(View drawerView) 
		     {
		         super.onDrawerOpened(drawerView);
		     } 
	     };
	     mDrawerLayout.setDrawerListener(drawerToggle);
	     // Set the list's click listener
	     drawerToggle.syncState();
	        
	     /*-----------------------------------------------------------------*/
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
	 
		public void setRightDrawer()
	    {
	        String TITLES[] = Constants.getDrawerItems();
	        int ICONS[] = {R.drawable.ic_home,R.drawable.ic_calender,R.drawable.ic_mail,R.drawable.ic_mail};

	        //Similarly we Create a String Resource for the name and email in the header view
	        //And we also create a int resource for profile picture in the header view

	        String NAME =Constants.user_name;
	        String EMAIL =Constants.user_email;
	        int PROFILE = R.drawable.user;
	        mRightNavigationDrawerRecyclerView = (RecyclerView) findViewById(R.id.RightNavigationDrawerRecyclerView); // Assigning the RecyclerView Object to the xml View
	        mRightNavigationDrawerRecyclerView.setHasFixedSize(true);
	        NavigationDrawerAdapter mRightNavigationDrawerAdapter = new NavigationDrawerAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE);
	        mRightNavigationDrawerRecyclerView.setAdapter(mRightNavigationDrawerAdapter);

	        final GestureDetector mGestureDetector = new GestureDetector(MessageActivity.this, new GestureDetector.SimpleOnGestureListener()
	        {

	            @Override public boolean onSingleTapUp(MotionEvent e)
	            {
	                return true;
	            }

	        });

	        mRightNavigationDrawerRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener()
	        {
	            @Override
	            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent)
	            {
	                View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());



	                if(child!=null && mGestureDetector.onTouchEvent(motionEvent))
	                {
	                    mDrawerLayout.closeDrawer(mRightNavigationDrawerRecyclerView);
	                    int position=recyclerView.getChildPosition(child);
	                    Intent i=Constants.selectItem(position-1);
	                    startActivity(i);
	                    return true;

	                }
	                return false;
	            }

				@Override
				public void onTouchEvent(RecyclerView arg0, MotionEvent arg1) {
					// TODO Auto-generated method stub
					
				}
	        });

//	        mRightNavigationDrawerRecyclerView.setOnClickListener(new RightDrawerItemClickListener());

	        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(this);                 // Creating a layout Manager

	        mRightNavigationDrawerRecyclerView.setLayoutManager(mLayoutManager);
	    }
}
