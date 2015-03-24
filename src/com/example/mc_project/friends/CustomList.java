package com.example.mc_project.friends;

import java.util.List;


import com.example.mc_project.R;
import com.example.mc_project.classes.Constants;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomList extends BaseAdapter
{
	
	List<ParseUser> listOfObjects;// = new ArrayList<ObjectDrawer>();
	List<String> listOfFriends;
	Context superContext;
	
	public CustomList(List<ParseUser> objDrwList,List<String> friends,Context scontext)
	{
		Log.d("customlist","constructor");
		this.listOfObjects = objDrwList;
		this.listOfFriends = friends;
		this.superContext = scontext;
	}
	
		
	public class PostView
	{
		TextView postContent;
		Button Add;
		
		public PostView(TextView postcontent,Button add)
		{
			this.postContent = postcontent;
			this.Add=add;
		}
	}

	@Override
	public int getCount() 
	{
		Log.d("customlist","getCount");
		return this.listOfObjects.size();
	}

	@Override
	public Object getItem(int position) 
	{
		
			return this.listOfObjects.get(position);
		
		
	}

	@Override
	public long getItemId(int position) 
	{
		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		Log.d("customlist","getView");
		ParseUser currentObject = (ParseUser)this.listOfObjects.get(position);
			View view;
			PostView holderPost;
		    ViewGroup viewGroup = (ViewGroup)((LayoutInflater)superContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.show_people, null);
		
		    holderPost = new PostView((TextView)viewGroup.findViewById(R.id.Name),(Button)viewGroup.findViewById(R.id.add));	
				
		    view = viewGroup;
				        
			holderPost.postContent.setText(currentObject.getString("Name"));
			if(currentObject.getObjectId().equals(Constants.user.getObjectId()))
			{
				Log.d("User","Exists");
				holderPost.Add.setVisibility(View.INVISIBLE);
			}
			if(listOfFriends.contains(currentObject.getUsername()))
			{
				Log.d("User1","Exists");
				holderPost.Add.setText("Added");
			}
			else
			{
				holderPost.Add.setOnClickListener(new AddUserListener((ParseObject)this.listOfObjects.get(position)));
			}
			return view;
		
	}
	
	public class AddUserListener implements View.OnClickListener
	{
		ParseObject SelectFriend;
		public AddUserListener(ParseObject selectFriend)
		{
			SelectFriend=selectFriend;
		}

		@Override
		public void onClick(View v) 
		{
			try
			{
				Button x=(Button)v;
				Log.d("like",x.getText().toString());
				ParseUser user=Constants.user;
				ParseRelation relationLocation = user.getRelation("UserFriends");
		    	relationLocation.add(SelectFriend);
		    	user.save();
		    	x.setText("Added");
			}
			catch (IllegalArgumentException | ParseException e) 
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
		
	}
	
	
}
