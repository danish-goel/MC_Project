package com.example.mc_project.message;

import java.util.List;


import com.example.mc_project.R;
import com.example.mc_project.classes.Constants;
import com.example.mc_project.classes.Post;
import com.example.mc_project.places.Places;
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
		TextView postTitle;
		TextView postContent;
		TextView posterName;
		TextView postTime;
		TextView postDate;
		
		public PostView(TextView postcontent, 
				TextView postername,TextView posttime,TextView postdate)
		{
//			this.postImage = userImage;
//			this.postTitle = post;
			this.postContent = postcontent;
			this.posterName = postername;
			this.postTime = posttime;
			this.postDate=postdate;
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
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		Log.d("customlist","getView");
		ParseObject currentObject = (ParseObject)this.listOfObjects.get(position);
		View view;
		PostView holderPost;
	    ViewGroup viewGroup = (ViewGroup)((LayoutInflater)superContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.homepage_post_layout, null);
	
	    holderPost = new PostView((TextView)viewGroup.findViewById(R.id.post_details),(TextView)viewGroup.findViewById(R.id.poster_name) 
	       			 					,(TextView)viewGroup.findViewById(R.id.post_date),(TextView)viewGroup.findViewById(R.id.post_time));	
			
	    view = viewGroup;
			        
		holderPost.postContent.setText(currentObject.getString("Text"));
		holderPost.posterName.setText(currentObject.getString("DelivererName"));
		holderPost.postDate.setText(Constants.getDate(currentObject.getCreatedAt().getDate(),currentObject.getCreatedAt().getMonth(),currentObject.getCreatedAt().getYear()+1900));
		holderPost.postTime.setText(Constants.getTime(currentObject.getCreatedAt().getHours(),currentObject.getCreatedAt().getMinutes(),currentObject.getCreatedAt().getSeconds()));
		return view;
		
	}
	
	
}
