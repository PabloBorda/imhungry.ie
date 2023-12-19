/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.service.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.papitomarket.android.MenuActivity;
import com.papitomarket.android.NewsActivity;
import com.papitomarket.android.R;
import com.papitomarket.global.GlobalData;
import com.papitomarket.model.facebook.android.User;
import com.papitomarket.model.facebook.android.UserDataSource;

/**
 * This service retrieves products orders from xmpp and updates
 * from subscribed tags
 * @author Pablo Tomas Borda Di Berardino
 * 
 * Smack API: http://www.igniterealtime.org/builds/smack/docs/latest/javadoc/
 * 
 * 
 */
public class Updates extends Service implements LocationListener{
	
	/////////////////////////////// LOCATION RELATED STUFF /////////////////////////////////////////
	
	
	private static final String TAG = "SMARTBANDS";
	public static final String ACTION_FOR_INTENT_FILTER = "ACTION_FOR_INTENT_FILTER";
	public static boolean running = false;
	

	
		
	
	/////////////////////////////// LOCATION RELATED STUFF END /////////////////////////////////////////
	
	
   
    private NotificationManager nm;
    private int count=0;
    private static final int NOTIFY_ME_ID=1987;
    public XMPPConnection jabberConnection;
    private static Updates inst;
    public static Context appContext;
    public static Intent currentNotificationActivity;
    private static ArrayList<String> stack; //Notifications stack, when one is clicked, another appears.. no user doesnt miss any
    private static boolean usingTaskBar;
   
    public Updates(){
    	
    }
    
    
    public Updates(Context ctx){
    	this.mContext = ctx;
    }
    
    @Override
    public boolean onUnbind(Intent intent) {
    	
    	Updates.running = false;
    	return Updates.running;
    };
    
    
    
    public boolean isOnline() {
        /*ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;*/
    	return true;
    }
    
    
    
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      if (!Updates.running){
    	android.os.Debug.waitForDebugger();
    	Updates.usingTaskBar = false;
    	Updates.stack = new ArrayList<String>();
    	Log.i("SERVICE","com.papitomarket.android: Service Start command");
        
        Updates.inst = this;
        MenuActivity.updates = this;
    	

    	 //Bundle extras = intent.getExtras();
    	 
        UserDataSource uds = new UserDataSource(this);
        ArrayList<User> usrs = (ArrayList<User>)uds.getAllUsers();
        while (usrs.size()==0){};
        if (usrs.size()>0){
          User u = (User)usrs.get(0);
          String user = u.getUsername().replace(" ","");
        
          boolean showed = false;
          while (!this.isOnline()){
        	if (!showed){
        		this.online_notify("No internet access...connecting..",MenuActivity.class);
        		showed = true;
        	}
        	
          }
        
        
           //String user = intent.getStringExtra("user");
         Log.i("SERVICE","com.papitomarket.android: Connecting to Jabber");
 
         try {
            SmackConfiguration.setPacketReplyTimeout(20000);
           
            ConnectionConfiguration cc = new ConnectionConfiguration("imhungry.p1.im",5222);
            
            jabberConnection = new XMPPConnection(cc);
            jabberConnection.connect();
            jabberConnection.login("pablotomasborda","pablotomasborda");

            Presence presence = new Presence(Presence.Type.available);

            jabberConnection.sendPacket(presence);
            
            ChatManager cm = jabberConnection.getChatManager();
            
            
            jabberConnection.getChatManager().addChatListener(new ChatManagerListenerImpl());

            this.online_notify("SmartBandS is Online",NewsActivity.class);
            mContext = getApplicationContext();
			Updates.inst.getLocation();
          } catch (XMPPException ex) {
            Log.e("SERVICE","com.papitomarket.android: jabber connection failed");
            Logger.getLogger(Updates.class.getName()).log(Level.SEVERE, null, ex);
          }
      
        }
        Updates.running = true;
      }

          return super.onStartCommand(intent, flags, startId);
    }


    
    private class ChatManagerListenerImpl implements ChatManagerListener {

        /** {@inheritDoc} */
        @Override
        public void chatCreated(final Chat chat, final boolean createdLocally) {
        	android.os.Debug.waitForDebugger();
        	chat.addMessageListener(new MessageListener() {
				@Override
				public void processMessage(Chat arg0, Message arg1) {
					// TODO Auto-generated method stub
					Log.i("papitomarket","I got message " + arg1.getBody());
					if (Updates.usingTaskBar==false){
					  
					  Updates.inst.online_notify_jabber(arg1.getBody(),NewsActivity.class);
					  Updates.appContext = getApplicationContext();
					  Updates.usingTaskBar=true;										   
					  

					} 
					Updates.stack.add(arg1.getBody());
					Intent intent = new Intent();
				    intent.setAction(Updates.ACTION_FOR_INTENT_FILTER);				      
				    intent.putExtra("notification",arg1.getBody());				      
				    sendBroadcast(intent);
				    //This process notification and opens the apropiate activity
				}
			});
        }
    }
    
    @Override
    public void onCreate() {
    	Log.i("SERVICE","com.papitomarket.android: Service created");
    	
    	
    }

    @Override
    public void onDestroy() {
//      unbindService(conn);
     Updates.running = false;
     Log.i("SERVICE","com.papitomarket.android: Service destroyed");
    }

    
    public void online_notify(String msgbody,Class<? extends Activity> notificationActivity){
        Log.i("SERVICE","com.papitomarket.android: Running status bar notification");
        nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification note=new Notification(R.drawable.stat_buy,"SmartBandS alert!",System.currentTimeMillis());
        Intent act = new Intent(this.getApplicationContext(), notificationActivity);
        PendingIntent i=PendingIntent.getActivity(this.getApplicationContext(), 0,act,0);
	    note.contentIntent = i;
	    note.setLatestEventInfo(this, "SmartBandS",msgbody, i);
	    note.number=++count;
	    note.vibrate=new long[] {500L, 200L, 200L, 500L};
	    note.flags|=Notification.FLAG_ONGOING_EVENT;
	    
	    nm.notify(NOTIFY_ME_ID, note);
           	
    }

    public void online_notify_jabber(String msgbody,Class<? extends Activity> notificationActivity){
    	
        Log.i("SERVICE","com.papitomarket.android: Running status bar notification");
        nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification note=new Notification(R.drawable.stat_buy,"SmartBandS alert!",System.currentTimeMillis());
        Intent act = new Intent(this.getApplicationContext(), notificationActivity);
        
        
        JsonFactory f = new JsonFactory();
        JsonParser jp;
        ObjectMapper om = new ObjectMapper();
     
       	try {
		  jp = f.createJsonParser(msgbody);
		  JsonNode node = om.readTree(msgbody);
		  String passData = node.get("body").toString();
          act.putExtra("body",passData);
	      PendingIntent i=PendingIntent.getActivity(this.getApplicationContext(), 0,act,0);
	      note.contentIntent = i;
	      note.setLatestEventInfo(this, "SmartBandS",msgbody, i);
	      note.number=++count;
	      note.vibrate=new long[] {500L, 200L, 200L, 500L};
	      note.flags|=Notification.FLAG_AUTO_CANCEL;
	    
	      nm.notify(NOTIFY_ME_ID, note);
	      
       	
       	} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       	
       	
       	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return new IUpdatesManager.Stub() {
			
			@Override
			public boolean sendNotification(String to, String message)
					throws RemoteException {
				
		        ChatManager cm = jabberConnection.getChatManager();
	            Chat chat = cm.createChat(to + "@192.241.140.67", new MessageListener() {

	              @Override
	              public void processMessage(Chat chat, Message message) {
                    Log.i("papitomarket", "order confirmed to message: " + message.getBody());
	              }
	            });
                try {
					chat.sendMessage(message);
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            return true;
			}
			
			@Override
			public boolean onReceivedNotification(String from, String message)
					throws RemoteException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getNetworkLocation() throws RemoteException {
				// check if GPS enabled		
		        if(Updates.inst.canGetLocation()){
		        	
		        	double latitude = Updates.inst.getLatitude();
		        	double longitude = Updates.inst.getLongitude();
		        	
		        	// \n is for new line
		        	Log.i("papitomarket","Your Location is - \nLat: " + latitude + "\nLong: " + longitude);	
		        }else{
		        	// can't get location
		        	// GPS or Network is not enabled
		        	// Ask user to enable GPS/network in settings
		        	Updates.inst.showSettingsAlert();
		        	return "nonet";
		        }
				
				
		        return ((new Double(latitude)).toString() + "," + (new Double(longitude)).toString());                  
			}

			@Override
			public String getGPSLocation() throws RemoteException {
				while (Updates.inst==null){};
				if(Updates.inst.canGetLocation()){
		        	
		        	double latitude = Updates.inst.getLatitude();
		        	double longitude = Updates.inst.getLongitude();
		        	
		        	// \n is for new line
		        	Log.i("papitomarket","Your Location is - \nLat: " + latitude + "\nLong: " + longitude);	
		        }else{
		        	// can't get location
		        	// GPS or Network is not enabled
		        	// Ask user to enable GPS/network in settings

		        	return "nogps";
		        }
				
				
				
		        return ((new Double(latitude)).toString() + "," + (new Double(longitude)).toString()); 			}
		};
		
	}


	
	  private Context mContext;
	  
      // flag for GPS status
	  boolean isGPSEnabled = false;
	 
	  // flag for network status
	  boolean isNetworkEnabled = false;
	 
	  boolean canGetLocation = false;
	 
	  Location location; // location
	  double latitude; // latitude
	  double longitude; // longitude
	
      // The minimum distance to change Updates in meters
	  private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	 
	  // The minimum time between updates in milliseconds
	  private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	 
	  // Declaring a Location Manager
	  protected LocationManager locationManager;


	  public Location getLocation() {
	        try {
	            locationManager = (LocationManager) mContext
	                    .getSystemService(LOCATION_SERVICE);
	 
	            // getting GPS status
	            isGPSEnabled = locationManager
	                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
	 
	            // getting network status
	            isNetworkEnabled = locationManager
	                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	 
	            if (!isGPSEnabled && !isNetworkEnabled) {
	                // no network provider is enabled
	            } else {
	                this.canGetLocation = true;
	                // First get location from Network Provider
	                if (isNetworkEnabled) {
	                    locationManager.requestLocationUpdates(
	                            LocationManager.NETWORK_PROVIDER,
	                            MIN_TIME_BW_UPDATES,
	                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                    Log.d("Network", "Network");
	                    if (locationManager != null) {
	                        location = locationManager
	                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	                        if (location != null) {
	                            latitude = location.getLatitude();
	                            longitude = location.getLongitude();
	                        }
	                    }
	                }
	                // if GPS Enabled get lat/long using GPS Services
	                if (isGPSEnabled) {
	                    if (location == null) {
	                        locationManager.requestLocationUpdates(
	                                LocationManager.GPS_PROVIDER,
	                                MIN_TIME_BW_UPDATES,
	                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
	                        Log.d("GPS Enabled", "GPS Enabled");
	                        if (locationManager != null) {
	                            location = locationManager
	                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
	                            if (location != null) {
	                                latitude = location.getLatitude();
	                                longitude = location.getLongitude();
	                            }
	                        }
	                    }
	                }
	            }
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	        return location;
	    } 
	  
	  
	  
	  /**
		 * Stop using GPS listener
		 * Calling this function will stop using GPS in your app
		 * */
		public void stopUsingGPS(){
			if(locationManager != null){
				locationManager.removeUpdates(Updates.this);
			}		
		}
		
		/**
		 * Function to get latitude
		 * */
		public double getLatitude(){
			if(location != null){
				latitude = location.getLatitude();
			}
			
			// return latitude
			return latitude;
		}
		
		/**
		 * Function to get longitude
		 * */
		public double getLongitude(){
			if(location != null){
				longitude = location.getLongitude();
			}
			
			// return longitude
			return longitude;
		}
		
		/**
		 * Function to check GPS/wifi enabled
		 * @return boolean
		 * */
		public boolean canGetLocation() {
			return this.canGetLocation;
		}
		
		/**
		 * Function to show settings alert dialog
		 * On pressing Settings button will lauch Settings Options
		 * */
		public void showSettingsAlert(){
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
	   	 
	        // Setting Dialog Title
	        alertDialog.setTitle("GPS is settings");
	 
	        // Setting Dialog Message
	        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
	 
	        // On pressing Settings button
	        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog,int which) {
	            	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	            	mContext.startActivity(intent);
	            }
	        });
	 
	        // on pressing cancel button
	        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            dialog.cancel();
	            }
	        });
	 
	        // Showing Alert Message
	        alertDialog.show();
		}

		@Override
		public void onLocationChanged(Location location) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

}
