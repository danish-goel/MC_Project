package com.example.mc_project.singlePost;


import java.util.List;


import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.example.mc_project.R;
import com.example.mc_project.classes.Constants;
import com.example.mc_project.classes.Post;
import com.example.mc_project.homePage.Homepage;
import com.example.mc_project.places.Places;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewSinglePost extends Activity 
{
//	String objectId;
//	int like;
//	String text;
//	ParseQuery<ParseObject> query;
//	ParseObject po;
//	ImageView likeButton;
//	TextView nooflikes;
	Post singlePost;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_post_expanded);
		
		singlePost=Constants.post;
		
		TextView title,date,poster,timing,details;

		title=(TextView)findViewById(R.id.post_title);
		date=(TextView)findViewById(R.id.post_date);
		poster=(TextView)findViewById(R.id.poster_name);
		timing=(TextView)findViewById(R.id.post_time);
		details=(TextView)findViewById(R.id.post_details);
		
		date.setText(Constants.getDate(singlePost.getCreatedAt().getDate(),singlePost.getCreatedAt().getMonth(),singlePost.getCreatedAt().getYear()+1900));
		timing.setText(Constants.getTime(singlePost.getCreatedAt().getHours(),singlePost.getCreatedAt().getMinutes(),singlePost.getCreatedAt().getSeconds()));
		poster.setText(singlePost.getName());
		details.setText(singlePost.getText());
		
		
//		query = ParseQuery.getQuery("Post");
//		query.getInBackground(objectId, new GetCallback<ParseObject>() {
//			
//			@Override
//			public void done(ParseObject parseObject, ParseException arg1) {
//				// TODO Auto-generated method stub
//				ParseFile fileObject = (ParseFile) parseObject.get("photo");
//				//Log.d("objId",fileObject.toString());
//				if(fileObject !=null){
//					fileObject.getDataInBackground(new GetDataCallback() {
//						
//						@Override
//						public void done(byte[] data, ParseException e) {
//							if(e==null){
//								Log.d("test", "We've got data in data.");
//	                            // Decode the Byte[] into Bitmap
//	                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
//
//	                            // Get the ImageView from xml
//	                            ImageView image = (ImageView) findViewById(R.id.flag);
//
//	                            // Set the Bitmap into the ImageView
//	                            image.setImageBitmap(bmp);
//							}
//							
//						}
//					});
//
//				}
//			}
//		});
		

//		likeButton = (ImageView) findViewById(R.id.likeButton);
//		nooflikes=(TextView)findViewById(R.id.nooflikes);
//		likeButton.setOnClickListener(new View.OnClickListener() 
//		{
//			
//			@Override
//			public void onClick(View v) 
//			{
//				
//				// TODO Auto-generated method stub
//				likeButton.setClickable(false);
//				likeButton.setImageResource(R.drawable.liked);
//				query = ParseQuery.getQuery("Post");
//				query.whereEqualTo("Text",text);
//				query.findInBackground(new FindCallback<ParseObject>() 
//				{
//					    public void done(List<ParseObject> scoreList, ParseException e) 
//					    {
//						        if (e == null) 
//						        {
//						        	 for (ParseObject totem :scoreList) 
//							            {
//						        		 	try
//						        		 	{
//						        		 		po=totem;
//						        		 		like=totem.getInt("Like");
//						        		 		like=like+1;
//						        		 		nooflikes.setText(String.valueOf(like));
//						        		 		totem.put("Like",like);
//						        		 		totem.saveInBackground();
//						        		 		objectId=totem.getObjectId();
//						        		 		Log.d("like",objectId);
//						        		 		Log.d("like",String.valueOf(like));
//						        		 		
//						        		 	}
//						        		 	catch(Exception ex)
//						        		 	{
//						        		 		Log.d("err",ex.toString());
//						        		 	}
//							            }
//						        }
//						        else 
//						        {
//						            Log.d("score", "Error: " + e.getMessage());
//						        }
//					    }   
//				});
//			
//		}	
//		});
		
	}
}
