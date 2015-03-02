package com.example.mc_project.map;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseGeoPoint;
import com.example.mc_project.R;
import com.example.mc_project.classes.Constants;


import android.app.Activity;
import android.os.Bundle;

public class Map extends Activity
{

	GoogleMap googleMap;
	LatLng MyPoint = new LatLng(Constants.latitude,Constants.longitude);
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);
		int i=0;
	     try 
	     { 
	            if (googleMap == null) 
	            {
	               googleMap = ((MapFragment) getFragmentManager().
	               findFragmentById(R.id.map)).getMap();
	            }
	         googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	         Marker marker = googleMap.addMarker(new MarkerOptions().position(MyPoint).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
	         googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14));
	         Marker othermarker;
	         while(!Constants.nearByPosts.isEmpty())
	 		{
	 			ParseGeoPoint temp=Constants.nearByPosts.get(i).getGeopoint();
	 			String story=Constants.nearByPosts.get(i).getText();
	 			MyPoint = new LatLng(temp.getLatitude(),temp.getLongitude());
	 			othermarker = googleMap.addMarker(new MarkerOptions().position(MyPoint).title(story));
	 			i++;
	 		}
	         googleMap.getUiSettings().setZoomGesturesEnabled(true);
	      } 
	     catch (Exception e) 
	     {
	         e.printStackTrace();
	      }
	}

}
