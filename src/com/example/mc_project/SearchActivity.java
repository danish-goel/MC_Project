package com.example.mc_project;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class SearchActivity extends Activity
{
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

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) 
        {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(SearchActivity.this,query,0).show();
            //use the query to search your data somehow
        }
    }
}
