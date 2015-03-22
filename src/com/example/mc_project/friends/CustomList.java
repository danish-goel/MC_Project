package com.example.mc_project.friends;

import java.util.List;


import com.example.mc_project.R;
import com.example.mc_project.classes.Constants;
import com.parse.ParseException;
import com.parse.ParseObject;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomList extends BaseAdapter
{
	
	List<ParseObject> listOfObjects;// = new ArrayList<ObjectDrawer>();
	Context superContext;
	
	public CustomList(List<ParseObject> objDrwList,Context scontext)
	{
		Log.d("customlist","constructor");
		this.listOfObjects = objDrwList;
		this.superContext = scontext;
	}
	
		
	public class PostView
	{
//		ImageView postImage;
		TextView postContent;
		
		public PostView(TextView postcontent)
		{
//			this.postImage = userImage;
//			this.postTitle = post;
			this.postContent = postcontent;
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
		ParseObject currentObject = (ParseObject)this.listOfObjects.get(position);
			View view;
			PostView holderPost;
		    ViewGroup viewGroup = (ViewGroup)((LayoutInflater)superContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.show_people, null);
		
		    holderPost = new PostView((TextView)viewGroup.findViewById(R.id.Name));	
				
		    view = viewGroup;
				        
			holderPost.postContent.setText(currentObject.getString("Name"));
			return view;
		
	}
	
	
}
