/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.Request;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.papitomarket.model.facebook.android.User;
import com.papitomarket.model.facebook.android.UserDataSource;
import com.papitomarket.service.android.Updates;
import com.papitomarket.util.Util;
import com.smartbands.widgets.slidingmenu.lib.app.SlidingActivity;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jivesoftware.smackx.ServiceDiscoveryManager;

/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class MenuActivity extends SlidingActivity {

    
    public static MenuActivity instance;
    public static Updates updates = null;
    public static String cache = "";
    public static boolean got=false;


    
     private class FacebookRetriever extends AsyncTask<Void,String,String>{
        
        private Session s;
        
        
        public FacebookRetriever(){
            s = Session.getActiveSession();
            
        }

        @Override
        protected String doInBackground(Void... arg0) {
            while (!s.isClosed() && !s.isOpened()){}
            if (s.isOpened() && (MenuActivity.cache.equals(""))){
                  Request r = new Request(s,"/me");
                  String ans = r.executeAndWait().toString();
                  MenuActivity.cache = ans;
                  return ans;
            } else {
              return "";
            }
        }
        
        @Override
        protected void onPostExecute(String result){
          MenuActivity.got = true;        
          publishProgress(result);
        }
        
        @Override           
        protected void onProgressUpdate(String... res){
          super.onProgressUpdate(res);
          if ((res != null) && (!res[0].equals(""))){  
            JsonParser jp;
            JsonFactory f = new JsonFactory();
            ObjectMapper om = new ObjectMapper();
            
            String json = res[0].split("=")[2].split("\\}\\},")[0]  + "}"; 
            
            try {
                JsonNode node = om.readTree(json);
                jp = f.createJsonParser(res[0]);
                Iterator<String> fields = node.fieldNames();
                while (fields.hasNext()){
                  String fieldName = fields.next();
                    try {            
                    	String lastPart = fieldName.substring(1);
                        String methodName = "set" + fieldName.toUpperCase().charAt(0) + lastPart;
                    	(User.class.getDeclaredMethod(methodName, String.class)).invoke(User.getInstance(), (node.get(fieldName)).textValue());
                    } catch (SecurityException ex) {
                        Logger.getLogger(MenuActivity.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            
                }
                UserDataSource uds = new UserDataSource(getApplicationContext());
                User.setInstance(uds.createUser(User.getInstance()));
               
            }   catch (IllegalAccessException ex) {
                    Logger.getLogger(MenuActivity.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(MenuActivity.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                Logger.getLogger(MenuActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            
           
            TextView tv = (TextView)findViewById(R.id.wellcome);
            tv.setText("Welcome " + (User.getInstance()).getName());
            
            (User.getInstance()).setStatus("ONLINE");           
            // Start chat service
            Intent updatesIntent = new Intent(getApplicationContext(),Updates.class);
            updatesIntent.putExtra("user",(User.getInstance()).getUsername());
            create_xmpp_account();           
            startService(updatesIntent);
         
          }
        }
   
    
         private void create_xmpp_account(){
            //while (MenuActivity.updates==null){}
            String jabber_username = User.getInstance().getUsername() + "-fb";
            String response = Util.get_no_encode("http://192.241.140.67:9494/newxmpp?uid=" + jabber_username);
            Log.i("papitomarket", "XMPP NEW USER: " + response);
         }

    }

    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //this.mMenuItems = 
        
        
        setContentView(R.layout.menu);
        
        UserDataSource uds = new UserDataSource(getApplicationContext());
        List<User> users =  uds.getAllUsers();
        uds.close();
        if (users.size()==0){
          FacebookRetriever fr = new FacebookRetriever();
          fr.execute();        
        } else {
        	User.setInstance((User)users.get(0));
        	TextView tv = (TextView)findViewById(R.id.wellcome);
            tv.setText("Welcome " + (User.getInstance()).getName());
             
            User.getInstance().setStatus("ONLINE");           
            // Start chat service
            Updates s = new Updates(MenuActivity.this);
            Intent updatesIntent = new Intent(getApplicationContext(),Updates.class);
            updatesIntent.putExtra("user",User.getInstance().getUsername());
            startService(updatesIntent);

        }

        
        
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, 
                             WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        
        // ToDo add your GUI initialization code here 
        
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        //                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
        


        
        
        
        
        Button search_button = (Button)findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent search_screen = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(search_screen);
            }
        });




      /*  Button buycredit_button = (Button)findViewById(R.id.buycredit_button);
        buycredit_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent buycredit_screen = new Intent(getApplicationContext(),BuyCreditActivity.class);
                startActivity(buycredit_screen);
            }
        });
        */
        
        Button invite_button = (Button)findViewById(R.id.invite_button);
        invite_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent invite_screen = new Intent(getApplicationContext(),InviteFriendsActivity.class);
                startActivity(invite_screen);
            }
        });
        
        Button exit_button = (Button)findViewById(R.id.exit_button);
        exit_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        MenuActivity.instance = this;
    }
}
