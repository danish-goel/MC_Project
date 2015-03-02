package com.example.mc_project.singlePost;

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

public class ViewSinglePlace extends Activity 
{
	Places singlePlace;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_place_expanded);

		TextView title,type,rating,timing,details;
		
		singlePlace=Constants.place;
		
		title=(TextView)findViewById(R.id.place_title);
		type=(TextView)findViewById(R.id.place_type);
		rating=(TextView)findViewById(R.id.place_rating);
		timing=(TextView)findViewById(R.id.place_timing);
		details=(TextView)findViewById(R.id.place_details);
		
		title.setText(singlePlace.getName());
		type.setText(singlePlace.getTypes().toString());
		rating.setText(singlePlace.getRating());
		details.setText(singlePlace.getVicinity());
		
	}


}
