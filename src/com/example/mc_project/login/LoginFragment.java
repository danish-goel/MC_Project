package com.example.mc_project.login;

import java.util.Arrays;
import com.example.mc_project.R;
import com.example.mc_project.getLocation;
import com.example.mc_project.classes.Constants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class LoginFragment extends Fragment 
{

	private UiLifecycleHelper uiHelper;
	private static final String TAG = "LoginFragment";
	String useremail="";
	String username="";
	String userLikes="";
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		Log.d("fb","1");
	    super.onCreate(savedInstanceState);
	    Log.d("fb","11");
	    LoginButton authButton = (LoginButton) getActivity().findViewById(R.id.authButton);
	    Log.d("fb","12");
	    authButton.setFragment(this);
	    Log.d("fb","13");
	    authButton.setReadPermissions(Arrays.asList("email","user_likes"));
	    Log.d("fb","14");
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    Log.d("fb","15");
	    uiHelper.onCreate(savedInstanceState);
	    Log.d("fb","16");
	}
	
	@Override
	public void onResume() 
	{
		Log.d("fb","2");
	    super.onResume();
	 // For scenarios where the main activity is launched and user
	    // session is not null, the session state change notification
	    // may not be triggered. Trigger it if it's open/closed.
	    Session session = Session.getActiveSession();
	    if(session==null)
	    {
	    	
	    }
	    if (session != null &&(session.isOpened() || session.isClosed()) ) 
	    {
	        onSessionStateChange(session, session.getState(), null);
	    }
	    uiHelper.onResume();
	}

	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("fb","3");
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		Log.d("fb","4");
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		Log.d("fb","5");
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		Log.d("fb","6");
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	
	static boolean FBnext=false;
	static boolean FBOnce=false;
	String[] backUp=new String[]{};
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) 
	{
		Log.d("fb","7");
	    if (state.isOpened()) 
	    {
	        Request.newMeRequest(session, new Request.GraphUserCallback() 
	        {

				@Override
				public void onCompleted(GraphUser user, Response response) 
				{
					if (user != null) 
					{
						username=user.getName();
						useremail=user.getProperty("email").toString();
						Log.d("fbval","name:"+username);
						Log.d("fbval","email:"+useremail);
						try
						{
							Log.d("fbval","likes:"+userLikes);
						}
						catch(Exception e)
						{
							
						}
						
						SharedPreferences sett = getActivity().getSharedPreferences(getLocation.PREFS_NAME, 0);
						SharedPreferences.Editor editor = sett.edit();
					    editor.putBoolean("login",true);
					    editor.putString("login_type","facebook");
					    editor.commit();
					    
						Intent fetchHomepage=new Intent("com.example.mc_project.homePage.FetchHomepage");
						fetchHomepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(fetchHomepage);
//						getActivity().finish();
	                }
				}
	        }).executeAsync();
	    } 
	    else if (state.isClosed()) 
	    {
	    }
	}

	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	    	Log.d("fb","8");
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	
}
