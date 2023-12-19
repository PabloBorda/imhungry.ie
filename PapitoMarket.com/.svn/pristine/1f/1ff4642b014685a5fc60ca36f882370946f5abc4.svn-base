package com.papitomarket.android;

import com.papitomarket.android.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.android.Facebook;
import com.papitomarket.model.facebook.android.User;
import com.papitomarket.service.android.Updates;
import com.papitomarket.util.Util;

public class LoginActivity extends Activity
{
    static final String URL_PREFIX_FRIENDS = "https://graph.facebook.com/me/friends?access_token=";
    private static final String apiKey = "398054573586712";
    private static final String apiSecret = "3ea604fbee11732ca763d265fcda08c2";
    public static Session session = null;
    ImageButton buttonLogin;
    com.facebook.Session.StatusCallback statusCallback = new SessionStatusCallback();
    
    Facebook client;
    
    static Context c;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
               
        super.onCreate(savedInstanceState);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, 
                             WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        //                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
     
        buttonLogin = (ImageButton)findViewById(R.id.loginbtn);

        
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
             onClickLogin();  
                
             // AlertDialog alertDialog = new AlertDialog.Builder(c).create();
             // alertDialog.setTitle("Reset...");
              
             // alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
             //   public void onClick(DialogInterface dialog, int which) {
                  // here you can add functions
             //   }
             // });
             // Search s = new Search(getBaseContext());
             // alertDialog.setMessage(s.tags("em"));
             // alertDialog.show();  
             
            }
           });
        
        
        ImageButton search_close_btn = (ImageButton)findViewById(R.id.location_login_btn);
        search_close_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent search_screen = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(search_screen);
			}
		});
        	
        
        //Settings.addLoggingBehavior(LoggingBehaviors.INCLUDE_ACCESS_TOKENS);

        Session session = Session.getActiveSession();
        
        
        if (session == null) {
            if (savedInstanceState != null) {
                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
            }
            if (session == null) {
                //session = new Session(this);
                session = new Session.Builder(this).setApplicationId("398054573586712").build();
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
            }
            
        }

        c = this;
        session.addCallback(statusCallback);
        
        
        
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }

    
   
    
    private void updateView() {

         Intent news = new Intent(getApplicationContext(), NewsActivity.class);
         startActivity(news);
         
         
         
         
        //buttonLoginLogout.setText(R.string.logout);
    }
    
    private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
        } else {
            Session.openActiveSession(this, true, statusCallback);
        }
    }

    
    
    
   
    
    private class SessionStatusCallback implements Session.StatusCallback {
    @Override
      public void call(Session session, SessionState state, Exception exception) {
        if (session.isOpened()){
          LoginActivity.session = session;
          Log.i("SMARTBANDS","ACCESSTOKEN: " + session.getAccessToken().toString());
          User.getInstance().setStatus("ONLINE");  
          updateView();
        } else {
            TextView connectstatus = (TextView)findViewById(R.id.connectstatus);
            connectstatus.setText("Failed to connect to FaceBook.");
        }
      }
    }
    
    
    
    
    
    
}
