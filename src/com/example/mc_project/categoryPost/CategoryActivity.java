package com.example.mc_project.categoryPost;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import com.example.mc_project.R;
import com.example.mc_project.classes.Constants;
import com.example.mc_project.homePage.Homepage;
import com.example.mc_project.homePage.NavigationDrawerAdapter;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
	RecyclerView mRightNavigationDrawerRecyclerView;
	private static final int NUM_PAGES =4;
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
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
        

        setRightDrawer();
        mTitles = Constants.getDrawerItems();
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
				mDrawerLayout.closeDrawer(mRightNavigationDrawerRecyclerView);
			}
		}

		
		public void openDrawer(View v)
		{
			mDrawerLayout.openDrawer(mRightNavigationDrawerRecyclerView);
			//Toast.makeText(this, "adas", 0).show();
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

		        final GestureDetector mGestureDetector = new GestureDetector(CategoryActivity.this, new GestureDetector.SimpleOnGestureListener()
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
