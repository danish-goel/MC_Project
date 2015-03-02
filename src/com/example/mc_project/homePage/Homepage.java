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
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
	private ListView mDrawerList;
	String[] mTitles;
	private DrawerLayout mDrawerLayout;
	static ActionBarDrawerToggle drawerToggle;
	/*---------------------------*/
	
	ListView listView ;
	ProgressDialog pd;
	
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
			mapbutton=(Button)findViewById(R.id.Mapbutton);
		 	
			mapbutton.setOnClickListener(new View.OnClickListener() 
			{
				
				@Override
				public void onClick(View v) 
				{
					// TODO Auto-generated method stub
					Intent map=new Intent("com.example.mc_project.map.Map");
					startActivity(map);
					
				}
			});
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
		     mDrawerList = (ListView) findViewById(R.id.left_drawer);
		     mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		        
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
		     mDrawerList.setAdapter((ListAdapter) new ArrayAdapter<String>
		     (this,android.R.layout.simple_list_item_activated_1, mTitles));
		     // Set the list's click listener
		     mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		     mDrawerList.setItemChecked(0, true); 
		     drawerToggle.syncState();
		        
		     /*-----------------------------------------------------------------*/
		        

	}
	 
		public void setlayout()
		{
			 CustomList cList = new CustomList(Constants.all_posts,Homepage.this);
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
		
		/*------------------Navigation Drawer Functions -------------------------*/
		private class DrawerItemClickListener implements ListView.OnItemClickListener 
		{
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) 
			{
				Intent intent=Constants.selectItem(position);
				startActivity(intent);
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		}

		public void openDrawer(View v)
		{
			mDrawerLayout.openDrawer(mDrawerList);
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
}