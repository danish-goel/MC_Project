package com.example.mc_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class Splash extends Activity 
{
	
	boolean internet=false;
	boolean gps=false;
	LocationManager manager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);
		manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
		
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		ConnectivityManager conMgr  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo info = conMgr.getActiveNetworkInfo(); 
		if(info != null && info.isConnected()) 
		{
			internet=true;
			Log.d("net", "1net on");
		}
		else
		{
			Log.d("net", "net off");
			buildAlertMessageNoInternet();
		}
		if(internet==true)
		{
			Log.d("net", "2net on");
			if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) || !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) )
			{
				Log.d("net", "gps off");
    			buildAlertMessageNoGps();
			}
			else
			{
				Log.d("net", "gps on");
				gps=true;
				Intent i;
				i=new Intent(Splash.this,getLocation.class);
				startActivity(i);
			}
		}
		
		
   }
	
	
	private void buildAlertMessageNoInternet() 
	{
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Internet connection seems to be disabled, do you want to enable it?")
               .setCancelable(false)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() 
               {
                   public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                       startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                   }
               })
               .setNegativeButton("No", new DialogInterface.OnClickListener() 
               {
                   public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) 
                   {
                        dialog.cancel();
                        new AlertDialog.Builder(Splash.this)
                        .setTitle("Warning")
                        .setMessage("This App can't be used without Internet.\nClick OK to close.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() 
                        {
							
							@Override
							public void onClick(DialogInterface dialog, int which) 
							{
								dialog.dismiss();
								finish();
							}
						}).show();
                   }
               });
        final AlertDialog alert = builder.create();
        alert.show();
    }
	
	
	
    private void buildAlertMessageNoGps() 
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS or Network Location seems to be disabled, do you want to enable it?\n\nNote: Allowing both GPS and Network Location (high accuracy) is suggested.")
               .setCancelable(false)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() 
               {
                   public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) 
                   {
                       startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                   }
               })
               .setNegativeButton("No", new DialogInterface.OnClickListener() 
               {
                   public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) 
                   {
                        dialog.cancel();
                        new AlertDialog.Builder(Splash.this)
                        .setTitle("Warning")
                        .setMessage("This App can't be used without GPS.\nClick OK to close.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() 
                        {
							
							@Override
							public void onClick(DialogInterface dialog, int which) 
							{
								dialog.dismiss();
								finish();
							}
						}).show();
                   }
               });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}
