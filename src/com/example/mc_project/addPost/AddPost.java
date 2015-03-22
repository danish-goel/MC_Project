package com.example.mc_project.addPost;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.example.mc_project.R;
import com.example.mc_project.getLocation;
import com.example.mc_project.classes.Constants;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class AddPost extends ActionBarActivity
{
	EditText story;
	Button submit,captureButton,videoButton;
	LocationManager manager;
	Criteria criteria;
	Double lat,lng;
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	static final int REQUEST_VIDEO_CAPTURE = 200;
	public static final int MEDIA_TYPE_IMAGE = 1;
	String picturepath="";
	
	ParseObject testObject = new ParseObject("Post");
	private Uri fileUri;
	Spinner s,sploc;
	public static final String PREFS_NAME = "login";
	List<ParseObject> Locations;
	
	Context context=AddPost.this;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
	{
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addpost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.save_location) 
        {
        	saveLocation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    private void saveLocation()
    {
	    	AlertDialog.Builder alert = new AlertDialog.Builder(context);
	    	alert.setTitle("Enter the name of the Location\n"); //Set Alert dialog title here
	
	        final EditText input = new EditText(context);
	        alert.setView(input);
	
	    	alert.setPositiveButton("OK", new DialogInterface.OnClickListener() 
	    	{
		    	public void onClick(DialogInterface dialog, int whichButton) 
		    	{
			    	 String srt = input.getEditableText().toString();
			    	 
			    	 ParseGeoPoint lo=new ParseGeoPoint();
			    	 lo.setLatitude(Constants.latitude);
			    	 lo.setLongitude(Constants.longitude);
			    	 
			    	 ParseObject poLoc=new ParseObject("Location");
			    	 poLoc.put("Name",srt);
			    	 poLoc.put("LocationGeopoint",lo);
			    	 try 
			    	 {
						poLoc.save();
						ParseUser user=Constants.user;
				    	 Log.d("user11",user.getObjectId());
				    	 ParseRelation relationLocation = user.getRelation("UserLocation");
				    	 relationLocation.add(poLoc);
				    	 user.save();
					} 
			    	 catch (ParseException e1) 
			    	 {
			    		 Log.d("user11",e1.toString());
						e1.printStackTrace();
					}
			 
			    	 Toast.makeText(context,srt,Toast.LENGTH_LONG).show();        		
			    }
	    	});
	    	
	    	alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() 
	    	{
		    	  public void onClick(DialogInterface dialog, int whichButton) 
		    	  {
		    		  dialog.cancel();
		    	  }
	    	});
	    	
	    	AlertDialog alertDialog = alert.create();
	    	alertDialog.show();
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_story);
		Parse.initialize(this, "cIlG71ZlahKyRJv8kaJ0L2y6hDbdvixZyimny8tH", "QhqzYsrDG8GwvzTqvX2LcV6ZgCAhhy2pPW4Corg7");
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
		toolbar.setTitle("New Post");
		setSupportActionBar(toolbar);
		
		story=(EditText)findViewById(R.id.editText1);
		captureButton=(Button)findViewById(R.id.capture);
		videoButton=(Button)findViewById(R.id.video);
		submit=(Button)findViewById(R.id.button1);
		s = (Spinner) findViewById(R.id.spin);
		sploc=(Spinner) findViewById(R.id.spinnerlocation);
		
		String[] array_spinner = new String[6];
		array_spinner[0] = "Entertainment";
		array_spinner[1] = "Foods";
		array_spinner[2] = "Love";
		array_spinner[3] = "Events";
		array_spinner[4] = "Games";
		array_spinner[5] = "I was here";
		ArrayAdapter<String>adapter = new ArrayAdapter<String>(AddPost.this,android.R.layout.simple_spinner_dropdown_item, array_spinner);
		s.setAdapter(adapter);
		
		setLocationSpinner();
		
		submit.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				
				String text=story.getText().toString();
				
				SharedPreferences sett = getSharedPreferences(PREFS_NAME, 0);
				String useremail=sett.getString("useremail","");
				String username=sett.getString("username","");
				
				Double lat = Constants.latitude;
				Double lng = Constants.longitude;
				String tag=s.getSelectedItem().toString();
				int spinnerLocationindex=sploc.getSelectedItemPosition();
				if(spinnerLocationindex!=0)
				{
				    testObject.put("location",Locations.get(spinnerLocationindex-1).getParseGeoPoint("LocationGeopoint"));
				}
				else
				{
					ParseGeoPoint point = new ParseGeoPoint(Double.parseDouble(String.valueOf(lat)), Double.parseDouble(String.valueOf(lng)));
				    testObject.put("location",point);
				}
				Log.d("tag",tag);
				
				testObject.put("Email",useremail);	
				testObject.put("Name",username);
				testObject.put("Text",text);
				testObject.put("Tag",tag);
				testObject.put("Like",0);
				testObject.put("Views",0);
//				try 
//				{
//					testObject.save();
//					ParseUser user=Constants.user;
//			    	 Log.d("user11",user.getObjectId());
//			    	 ParseRelation relationPost = user.getRelation("UserPost");
//			    	 relationPost.add(testObject);
//			    	 user.save();
//				} 
//				catch (ParseException e2) 
//				{
//					e2.printStackTrace();
//				}
		
				byte[] bite;
				if(picturepath!="")
				{
						
						if(picturepath.endsWith(".mp4"))
						{
							String nm;
							if(picturepath.endsWith(".mp4"))
							{
								String name="video.mp4";
								InputStream inputStream = null;
								try {
									inputStream = getContentResolver().openInputStream(Uri.fromFile(new File(picturepath)));
								} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						          ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

						          // this is storage overwritten on each iteration with bytes
						          int bufferSize = 1024;
						          byte[] buffer = new byte[bufferSize];

						          // we need to know how may bytes were read to write them to the byteBuffer
						          int len = 0;
						          try {
									while ((len = inputStream.read(buffer)) != -1) {
									    byteBuffer.write(buffer, 0, len);
									  }
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

						          // and then we can return your byte array.
						         byte[] bb=byteBuffer.toByteArray();
						         bite=bb;
						         final ParseFile file=new ParseFile(name,bite);
						    		file.saveInBackground(new SaveCallback() 
						    		{
										@Override
										public void done(ParseException e) 
										{
											Log.d("upload","uploaded");
											testObject.put("photo", file);
											testObject.saveInBackground();
											try 
											{
												testObject.save();
												ParseUser user=Constants.user;
										    	 Log.d("user11",user.getObjectId());
										    	 ParseRelation relationPost = user.getRelation("UserPost");
										    	 relationPost.add(testObject);
										    	 user.save();
											} 
											catch (ParseException e2) 
											{
												e2.printStackTrace();
											}
										}
									});
						    		Intent i=new Intent(Constants.homePage);
									startActivity(i);
							}
						}	
						else
						{
							String name="picture.png";
							//Log.d("complaint_pic",qrPath);
					        BitmapFactory.Options options = new BitmapFactory.Options();
					        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
					        Bitmap mustOpen = null;
					        mustOpen = BitmapFactory.decodeFile(picturepath, options);
					        
					        // Convert it to byte
				            ByteArrayOutputStream stream = new ByteArrayOutputStream();
				            byte[] image;
				            
			//	             Compress image to lower quality scale 1 - 100
				            File f=new File(picturepath);
				            long l=f.length();
				            l=l/1024;
				            int q=(int) (l/500);
				            if (q<1)
				            {
				            	q=1;
				            }
				            Log.d("some","mid");
				            mustOpen.compress(Bitmap.CompressFormat.JPEG, 100/q, stream);
				            image = stream.toByteArray();
				            
				            bite=image;
				    		final ParseFile file=new ParseFile(name,bite);
				    		file.saveInBackground(new SaveCallback() 
				    		{
								@Override
								public void done(ParseException e) 
								{
									Log.d("upload","uploaded");
									testObject.put("photo", file);
									try 
									{
										testObject.save();
										ParseUser user=Constants.user;
								    	 Log.d("user11",user.getObjectId());
								    	 ParseRelation relationPost = user.getRelation("UserPost");
								    	 relationPost.add(testObject);
								    	 user.save();
									} 
									catch (ParseException e2) 
									{
										e2.printStackTrace();
									}
								}
							});
				    		Intent i=new Intent(Constants.homePage);
							startActivity(i);
						}
				}	
				else
				{
					
					try 
					{
						testObject.save();
						ParseUser user=Constants.user;
				    	 Log.d("user11",user.getObjectId());
				    	 ParseRelation relationPost = user.getRelation("UserPost");
				    	 relationPost.add(testObject);
				    	 user.save();
					} 
					catch (ParseException e2) 
					{
						e2.printStackTrace();
					}
					
					Intent i=new Intent(Constants.homePage);
					startActivity(i);
				}
	
			}
		});
		videoButton.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			    String filename=new String();
			    
			    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),"Project");
			    mediaStorageDir.mkdir();
			    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			    timeStamp = "MOV_"+ timeStamp;
			   	//Log.d("path", mediaStorageDir.toString()+"/"+timeStamp);
			   	String t=mediaStorageDir.toString()+"//"+timeStamp;
			    filename=t+".mp4";
			    picturepath=filename;
		        File newfile = new File(filename);
		        try 
		        {
		            newfile.createNewFile();
		        } catch (IOException e) {}       

		        Uri outputFileUri = Uri.fromFile(newfile);
		        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE); 
			       takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
				
			}
		});
		captureButton.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				    String filename=new String();
				    
				    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Project");
				    mediaStorageDir.mkdir();
				    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
				    timeStamp = "PIC_"+ timeStamp;
				   	//Log.d("path", mediaStorageDir.toString()+"/"+timeStamp);
				   	String t=mediaStorageDir.toString()+"//"+timeStamp;
				    filename=t+".jpg";
				    picturepath=filename;
			        File newfile = new File(filename);
			        try 
			        {
			            newfile.createNewFile();
			        } catch (IOException e) {}       

			        Uri outputFileUri = Uri.fromFile(newfile);

			        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
			        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			        startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				
			}
		});

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		//Log.d("asd", "1");
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) 
	    {
	    	Log.d("asd", "2");
	        if (resultCode == RESULT_OK) 
	        {
	            Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show();
	            
	        } else if (resultCode == RESULT_CANCELED) 
	        {
	            // User cancelled the image capture
	        	Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
	        } else {
	            // Image capture failed, advise user
	        	Toast.makeText(this, "Capture failed", Toast.LENGTH_SHORT).show();
	        }
	    }
	    if (requestCode == REQUEST_VIDEO_CAPTURE) 
	    {
	    	Log.d("asd", "2");
	        if (resultCode == RESULT_OK) 
	        {
	            Toast.makeText(this, "Video Selected", Toast.LENGTH_SHORT).show();
	            
	        } else if (resultCode == RESULT_CANCELED) 
	        {
	            // User cancelled the image capture
	        	Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
	        } else {
	            // Image capture failed, advise user
	        	Toast.makeText(this, "Capture failed", Toast.LENGTH_SHORT).show();
	        }
	    }

	}

	public void setLocationSpinner()
	{
		//get all the objects
		String[] array_spinner = null;
		ParseUser user=Constants.user;
		ParseRelation relationLocation = user.getRelation("UserLocation");
		ParseQuery queryLocation = relationLocation.getQuery();
		try 
		{
			Locations = queryLocation.find();
			
			array_spinner = new String[Locations.size()+1];
			array_spinner[0]="Current Location";
			for(int i=0;i<Locations.size();i++)
			{
				array_spinner[i+1] =Locations.get(i).getString("Name");
			}
		} 
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//now use queryLike like normal query
		ArrayAdapter<String>adapter = new ArrayAdapter<String>(AddPost.this,android.R.layout.simple_spinner_dropdown_item, array_spinner);
		sploc.setAdapter(adapter);
	}


}
