
package com.example.mc_project.categoryPost;

import java.util.ArrayList;
import java.util.List;

import com.example.mc_project.R;
import com.example.mc_project.classes.Constants;
import com.example.mc_project.classes.Post;
import com.example.mc_project.homePage.CustomList;
import com.example.mc_project.homePage.Homepage;
import com.example.mc_project.places.Places;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Category_option_fragment extends Fragment
{
	   TextView text;
	   String menu;
	   ListView listView ;
	   ProgressDialog progress;
	   View view;
	   
	    @Override 
	    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle args) 
	    {
	        view = inflater.inflate(R.layout.menu_select_option, container, false);
	        return view;
	    }
	    
		@Override
		public void onActivityCreated(@Nullable Bundle savedInstanceState) 
		{
			super.onActivityCreated(savedInstanceState);
//			 	menu = getArguments().getString("Menu");
		        listView = (ListView) view.findViewById(R.id.listView);
		        
//		        progress = new ProgressDialog(getActivity().getApplicationContext());
//				progress.setTitle("Please Wait");
//				progress.setMessage("Fetching Location...");
//				progress.show();
		        
//		        filter_posts();
		        
		        setlayout();
		        
		    	
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
			
		}
		
//		public void filter_posts()
//		{
//			for(Post p:Constants.nearByPosts)
//			{
//				if(p.getTag().equals(menu))
//				{
//					stories.add(p.getText());
//				}
//			}
//				setlayout();
//		}
		
		public void setlayout()
		{
			 CustomList cList = new CustomList(Constants.all_posts,getActivity());
		     listView.setAdapter(cList);
//		     try{pd.setCancelable(true);}catch(Exception e){}
//	    		try{pd.dismiss();}catch(Exception e){}
		}
}
