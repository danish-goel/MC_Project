package com.example.mc_project.homePage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import com.example.mc_project.R;
import com.example.mc_project.categoryPost.Category_option_fragment;
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
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Homepage extends ActionBarActivity
{
	/*-----------Drawer items---------*/
	RecyclerView mRightNavigationDrawerRecyclerView;
	String[] mTitles;
	private DrawerLayout mDrawerLayout;
	static ActionBarDrawerToggle drawerToggle;
	/*---------------------------*/
	
	ListView listView ;
	ProgressDialog pd;
	SwipeRefreshLayout swipeLayout;
	CustomList cList;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.layout_homepage);
			
			initPD();
			pd.show();
			
			Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
			toolbar.setTitle("Blabber");
			setSupportActionBar(toolbar);
			
			Parse.initialize(this, "cIlG71ZlahKyRJv8kaJ0L2y6hDbdvixZyimny8tH", "QhqzYsrDG8GwvzTqvX2LcV6ZgCAhhy2pPW4Corg7");
	
			listView = (ListView) findViewById(R.id.list);
			setlayout();
			
			
			Button newstory,mapbutton;
			newstory=(Button)findViewById(R.id.newstorybutton);
		 	
			newstory.setOnClickListener(new View.OnClickListener() 
			{
				
				@Override
				public void onClick(View arg0) 
				{
					// TODO Auto-generated method stub
					Intent addPost=new Intent("com.example.mc_project.addPost.AddPost");
					startActivity(addPost);
				}
			});
			 listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
			 {
		            @Override
		            public void onItemClick(AdapterView<?> parent, View view,int position,long id) 
		            {
		            	if(Constants.all_posts.get(position) instanceof Places)
		            	{
		            		Log.d("click","click1");
		            		Constants.place=(Places)Constants.all_posts.get(position);
		            		Intent askquestion=new Intent(Constants.viewSinglePlace);
		    				startActivity(askquestion);	
		            	}
		            	else if(Constants.all_posts.get(position) instanceof Post)
		            	{
		            		Log.d("click","click2");
		            		Constants.post=(Post)Constants.all_posts.get(position);
		            		Intent askquestion=new Intent(Constants.viewSinglePost);
		    				startActivity(askquestion);
		            	}
		            }
		        });
			 
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
		        
		     
		     swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		        swipeLayout.setOnRefreshListener(new OnRefreshListener() 
		        {
					@Override
					public void onRefresh() 
					{
						new RefreshList().execute();
					}
				});
		        swipeLayout.setColorScheme(android.R.color.holo_blue_bright, 
		                android.R.color.holo_green_light, 
		                android.R.color.holo_orange_light, 
		                android.R.color.holo_red_light);

	}
	 
		public void setlayout()
		{
			 cList = new CustomList(Constants.all_posts,Homepage.this);
		     listView.setAdapter(cList);
		     try{pd.setCancelable(true);}catch(Exception e){}
	    		try{pd.dismiss();}catch(Exception e){}
		}
		

		@Override
		public boolean onCreateOptionsMenu(Menu menu) 
		{
			// Inflate the menu; this adds items to the action bar if it is present.
			MenuInflater inflater = getMenuInflater();
			getMenuInflater().inflate(R.menu.main, menu);
			
			SearchManager searchManager =
			           (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			    SearchView searchView =
			            (SearchView) menu.findItem(R.id.search).getActionView();
			    searchView.setSearchableInfo(
			            searchManager.getSearchableInfo(getComponentName()));
			return true;
		}
		
		   @Override
		    public boolean onOptionsItemSelected(MenuItem item) 
		   {
		        int id = item.getItemId();
		        if (id == R.id.select_map) 
		        {
		        	Intent map=new Intent("com.example.mc_project.map.Map");
					startActivity(map);
		            return true;
		        }
		        return super.onOptionsItemSelected(item);
		    }
		
		/*------------------Navigation Drawer Functions -------------------------*/
		private class DrawerItemClickListener implements ListView.OnItemClickListener 
		{
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) 
			{
				Intent intent=Constants.selectItem(position);
				startActivity(intent);
				mDrawerLayout.closeDrawer(mRightNavigationDrawerRecyclerView);
			}
		}

		public void openDrawer(View v)
		{
			mDrawerLayout.openDrawer(mRightNavigationDrawerRecyclerView);
		}
		
		 /*-----------------------------------------------------------------*/
		 public void initPD() 
		 {
				pd=new ProgressDialog(Homepage.this);
				pd.setTitle("Please Wait...");
				pd.setMessage("Displaying Posts");
				pd.setIndeterminate(true);
				pd.setCancelable(false);
		 }
		 
		 public class RefreshList extends AsyncTask<Void, Void, Void>
		 {
			 
			@Override
			protected Void doInBackground(Void... params) 
			{
				Constants.fetch_nearby_posts();
				Log.d("newpost",Constants.nearByPosts.toString());
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) 
			{
				Constants.populateAllPosts();
				Log.d("newpost","1\t"+Constants.nearByPosts.toString());
				cList.notifyDataSetChanged();
				swipeLayout.setRefreshing(false);
				super.onPostExecute(result);
			}
			 
		 }
		 
		    public void setRightDrawer()
		    {
		        String TITLES[] = Constants.getDrawerItems();
		        int ICONS[] = {R.drawable.ic_home,R.drawable.ic_calender,R.drawable.ic_mail};

		        //Similarly we Create a String Resource for the name and email in the header view
		        //And we also create a int resource for profile picture in the header view

		        String NAME =Constants.user_name;
		        String EMAIL =Constants.user_email;
		        int PROFILE = R.drawable.user;
		        mRightNavigationDrawerRecyclerView = (RecyclerView) findViewById(R.id.RightNavigationDrawerRecyclerView); // Assigning the RecyclerView Object to the xml View
		        mRightNavigationDrawerRecyclerView.setHasFixedSize(true);
		        NavigationDrawerAdapter mRightNavigationDrawerAdapter = new NavigationDrawerAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE);
		        mRightNavigationDrawerRecyclerView.setAdapter(mRightNavigationDrawerAdapter);

		        final GestureDetector mGestureDetector = new GestureDetector(Homepage.this, new GestureDetector.SimpleOnGestureListener()
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

//		        mRightNavigationDrawerRecyclerView.setOnClickListener(new RightDrawerItemClickListener());

		        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(this);                 // Creating a layout Manager

		        mRightNavigationDrawerRecyclerView.setLayoutManager(mLayoutManager);
		    }
		  

		 
}