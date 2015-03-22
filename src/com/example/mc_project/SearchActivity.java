package com.example.mc_project;

import java.util.ArrayList;
import java.util.List;

import com.example.mc_project.classes.Constants;
import com.example.mc_project.classes.Post;
import com.example.mc_project.homePage.CustomList;
import com.example.mc_project.homePage.Homepage;
import com.example.mc_project.places.Places;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends Activity
{
	List<Object> searchResults=new ArrayList<Object>();
	ListView listView ;
	CustomList cList;
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) 
    {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) 
    {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) 
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            setContentView(R.layout.search);
            new SearchTask().execute(query);
            //use the query to search your data somehow
        }
    }
    
    private void setLayout()
    {
    	if(searchResults.isEmpty())
    	{
    		Toast.makeText(SearchActivity.this,"No results found",0).show();
    	}
    	listView = (ListView) findViewById(R.id.list);
		cList = new CustomList(searchResults,this);
	    listView.setAdapter(cList);
    }
    
    
    private void SearchData(String query)
    {
    	for(Object o:Constants.all_posts)
    	{
    		if(o instanceof Post)
    		{
    			Post p=(Post)o;
    			if(p.getText().toLowerCase().contains(query.toLowerCase()))
    			{
    				searchResults.add(p);
    			}
    		}
    		else if(o instanceof Places)
    		{
    			Places pl=(Places)o;
    			if(pl.getName().toLowerCase().contains(query.toLowerCase()))
    			{
    				searchResults.add(pl);
    			}
    			else if (pl.getVicinity().toLowerCase().contains(query.toLowerCase())) 
    			{
    				searchResults.add(pl);
				}
    			else if (pl.getTypes().toString().toLowerCase().contains(query.toLowerCase())) 
    			{
    				searchResults.add(pl);
				}
    		}
    	}
    }
    
    
    public class SearchTask extends AsyncTask<String, Void, Void>
	 {
		@Override
		protected Void doInBackground(String... params) 
		{
			// TODO Auto-generated method stub
			SearchData(params[0]);
			return null;
		}
		@Override
		protected void onPostExecute(Void result) 
		{
			setLayout();
			super.onPostExecute(result);
		}

		 
	 }
		
    
    
}
