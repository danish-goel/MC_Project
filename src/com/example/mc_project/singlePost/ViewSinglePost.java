package com.example.mc_project.singlePost;


import java.util.List;


import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewSinglePost extends Activity
{
	Post singlePost;
	ImageView post_image;
	Bitmap post_bitmap;
	ImageButton likeButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "cIlG71ZlahKyRJv8kaJ0L2y6hDbdvixZyimny8tH", "QhqzYsrDG8GwvzTqvX2LcV6ZgCAhhy2pPW4Corg7");
		setContentView(R.layout.single_post_expanded);
		
		singlePost=Constants.post;

		TextView title,date,poster,timing,details;
		title=(TextView)findViewById(R.id.post_title);
		date=(TextView)findViewById(R.id.post_date);
		poster=(TextView)findViewById(R.id.poster_name);
		timing=(TextView)findViewById(R.id.post_time);
		details=(TextView)findViewById(R.id.post_details);
		post_image=(ImageView)findViewById(R.id.poste_image);
		likeButton=(ImageButton)findViewById(R.id.like);
		
		date.setText(Constants.getDate(singlePost.getCreatedAt().getDate(),singlePost.getCreatedAt().getMonth(),singlePost.getCreatedAt().getYear()+1900));
		timing.setText(Constants.getTime(singlePost.getCreatedAt().getHours(),singlePost.getCreatedAt().getMinutes(),singlePost.getCreatedAt().getSeconds()));
		poster.setText(singlePost.getName());
		details.setText(singlePost.getText());
		
		try {
			singlePost.incrementViews();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		likeButton.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				try 
				{
					singlePost.increaseLikes();
				} 
				catch (ParseException e) 
				{
					e.printStackTrace();
				}
				likeButton.setBackgroundResource(R.drawable.like);
				Animation popup = AnimationUtils.loadAnimation(ViewSinglePost.this, R.anim.popup_effect);
				likeButton.startAnimation(popup);
				likeButton.setEnabled(false);
			}
		});
		new LayoutTask().execute();
		
	}
	
	 class LayoutTask extends AsyncTask<Void, Void, Void>
	 {
		 
		 @Override
		protected void onPreExecute() 
		 {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) 
		{
			try 
			{
				Log.d("image","yes");
				post_bitmap=singlePost.getPhoto();
				
			} 
			catch (ParseException e) 
			{
				post_bitmap=null;
			}

			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) 
		{
			if(post_bitmap==null)
			{
				post_image.setVisibility(View.INVISIBLE);
			}
			else
			{
				post_image.setVisibility(View.VISIBLE);
				post_image.setImageBitmap(post_bitmap);
			}
			super.onPostExecute(result);
		}
		 
	 }


}
