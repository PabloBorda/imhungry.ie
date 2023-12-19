package com.smartbands.http;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.facebook.Session;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.papitomarket.android.InviteFriendsActivity;
import com.papitomarket.android.LoginActivity;
import com.papitomarket.android.R;
import com.papitomarket.android.adapter.ActionListAdapter;
import com.papitomarket.model.facebook.android.FbFriend;
import com.papitomarket.model.facebook.android.FriendDataSource;
import com.papitomarket.util.Util;

public class SBFacebookRequest extends AsyncTask<String,Void, ArrayList<FbFriend>>{

	
	private Context context;
	
	public SBFacebookRequest(Context c){
		super();
		this.context = c;
		

	}
	
	
	@Override
	protected ArrayList<FbFriend> doInBackground(String... params) {
		//return null;
		// TODO Auto-generated method stub
		
				
		Session session = LoginActivity.session;

        String ans = Util.get_no_encode("https://graph.facebook.com/me/friends?fields=id,name,picture,email&access_token=" + session.getAccessToken());
        Log.i("SMARTBANDS","ACCESSTOKEN: " + session.getAccessToken().toString());
        if (!ans.contains("An active access token must be used to query information about the current user")){
        
          FriendDataSource fds = new FriendDataSource(this.context);
        
   
          Gson gson = new Gson();
     
          JsonObject elem = (JsonObject) new JsonParser().parse(ans);
          
    
	      Type collectionFriends = new TypeToken<Collection<FbFriend>>() {}.getType();
	      String friendstr = elem.get("data").toString();
	      Collection<FbFriend> friendss = gson.fromJson(friendstr,collectionFriends);
          ArrayList<FbFriend> friends = (new ArrayList<FbFriend>(friendss)); 
          publishProgress();
          return friends; 
        
        } else {
        	Log.e("SMARTBANDS","FACEBOOK ERROR: An active access token must be used to query information about the current user");
        	Intent login_screen = new Intent(this.context,LoginActivity.class);
            this.context.startActivity(login_screen);
            return (new ArrayList<FbFriend>());
        }
        
  
	}
	
	
	
	@Override
	protected void onPostExecute(ArrayList<FbFriend> result) {
		super.onPostExecute(result);
		// TODO Auto-generated method stub
		FriendDataSource fds = new FriendDataSource(InviteFriendsActivity.instance);
		ArrayList<FbFriend> friends = (ArrayList<FbFriend>)result;
		for (FbFriend f : friends) {
			fds.createFbFriend(f);
		}
		ListView lview = (ListView)InviteFriendsActivity.instance.findViewById(R.id.selection_list);
        lview.setAdapter(new ActionListAdapter(InviteFriendsActivity.instance,R.id.selection_list, friends));
	}
	
	@Override	
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	
		
	}
	


	
	
	
	
	

}
