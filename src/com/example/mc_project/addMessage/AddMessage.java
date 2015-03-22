package com.example.mc_project.addMessage;

import com.example.mc_project.R;
import com.example.mc_project.classes.Constants;
import com.example.mc_project.homePage.Homepage;
import com.example.mc_project.homePage.NavigationDrawerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class AddMessage extends ActionBarActivity 
{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newmessage);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
		toolbar.setTitle("New Message");
		setSupportActionBar(toolbar);
		
		
	}

}
