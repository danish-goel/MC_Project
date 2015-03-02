package com.example.mc_project.categoryPost;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import com.example.mc_project.R;
import com.example.mc_project.classes.Constants;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

public class CategoryActivity extends ActionBarActivity 
{
	
	private static final int NUM_PAGES =4;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	private ListView mDrawerList;
	private String[] mTitles;
	private DrawerLayout mDrawerLayout;
	static ActionBarDrawerToggle drawerToggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category_option);
		
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
		toolbar.setTitle("Browse by Category");
        setSupportActionBar(toolbar);
        
        Button compose=(Button)findViewById(R.id.compose);
        compose.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent addPost=new Intent(Constants.addPost);
				startActivity(addPost);
				
			}
		});
        
     // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(2);
        
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() 
        {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
            public void onPageSelected(int position) 
            {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
            }
        });
        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mPager);
        

        
        mTitles = Constants.getDrawerItems();
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        
        drawerToggle= new ActionBarDrawerToggle(this, mDrawerLayout,toolbar, R.string.app_name, R.string.app_name)
        { 

            /** Called when a drawer has settled in a completely closed state. */ 
            public void onDrawerClosed(View view) 
            {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            } 

            /** Called when a drawer has settled in a completely open state. */ 
            public void onDrawerOpened(View drawerView) 
            {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            } 
        };
        mDrawerLayout.setDrawerListener(drawerToggle);
        mDrawerList.setAdapter((ListAdapter) new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, mTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setItemChecked(0, true); 
        drawerToggle.syncState();
	}
	
	
	
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter 
	{
	        public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) 
	        {
	            super(fm);
	        }
	
	        @Override
	        public CharSequence getPageTitle(int position) 
	        {
	            if(position==0)
	            	return "All";
	            else if(position==1)
	            	return "Food";
	            else if(position==2)
	            	return "Event";
	            else if(position==3)
	            	return "I was Here";
	            else
	            	return "Establishment";
	        }
	        
	        @Override
	        public android.support.v4.app.Fragment getItem(int position) 
	        {
	        	 if(position==0)
	        		 return new Category_option_fragment();
	             	
	             else if(position==1)
	            	 return new Category_option_fragment();
	             else
	            	 return new Category_option_fragment();
	        }
	
	        @Override
	        public int getCount() 
	        {
	            return NUM_PAGES;
	        }
	}
	
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
			//Toast.makeText(this, "adas", 0).show();
		}



}
