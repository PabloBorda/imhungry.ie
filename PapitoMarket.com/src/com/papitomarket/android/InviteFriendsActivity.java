/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.android;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.papitomarket.android.adapter.ActionListAdapter;
import com.papitomarket.model.facebook.android.FbFriend;
import com.papitomarket.model.facebook.android.FriendDataSource;
import com.smartbands.http.SBFacebookRequest;

/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class InviteFriendsActivity extends Activity {

	public static InviteFriendsActivity instance;
	private ListView listView;
    private List<FbFriend> listElements;
	
    
    private void sendRequestDialog() {
        Bundle params = new Bundle();
        params.putString("message", "Start buying and selling instantly by location using SmartBandS");

        WebDialog requestsDialog = (
            new WebDialog.RequestsDialogBuilder(this,
                Session.getActiveSession(),
                params))
                .setOnCompleteListener(new OnCompleteListener() {

                    @Override
                    public void onComplete(Bundle values,
                        FacebookException error) {
                        if (error != null) {
                            if (error instanceof FacebookOperationCanceledException) {
                                Toast.makeText(InviteFriendsActivity.instance.getApplicationContext(), 
                                    "Request cancelled", 
                                    Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(InviteFriendsActivity.instance.getApplicationContext(), 
                                    "Network Error", 
                                    Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            final String requestId = values.getString("request");
                            if (requestId != null) {
                                Toast.makeText(InviteFriendsActivity.instance.getApplicationContext(), 
                                    "Request sent",  
                                    Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(InviteFriendsActivity.instance.getApplicationContext(), 
                                    "Request cancelled", 
                                    Toast.LENGTH_SHORT).show();
                            }
                        }   
                    }

                })
                .build();
        requestsDialog.show();
    }
    
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, 
                             WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        // ToDo add your GUI initialization code here
       // setContentView(R.layout.invitefriends);
        
      
        
        InviteFriendsActivity.instance = this;
        
           

        // Find the list view
    /*    listView = (ListView) findViewById(R.id.selection_list);

        
        
        FriendDataSource fds = new FriendDataSource(this);
        
        listElements = fds.getAllFbFriends();

        if (listElements.size()==0){
             Set the list view adapter               
            SBFacebookRequest fr = new SBFacebookRequest(this);
            fr.execute("");
        	
        } else {
        
        listView.setAdapter(new ActionListAdapter(this,R.id.selection_list, listElements));
        
        }
        
        
        
        Button selectallbtn = (Button) findViewById(R.id.selectallbtn);

        selectallbtn.setOnClickListener(new OnClickListener() {
			
			@Override			
			public void onClick(View v) {
// TODO Auto-generated method stub
				ListView ll = (ListView)findViewById(R.id.selection_list);
				int chi = ll.getChildCount();
				for(int i = 0; i < chi; i++) {
					int current = Integer.valueOf(((FbFriend)listElements.get(i)).getId());
				    CheckBox cb = ((CheckBox)ll.findViewById(current));
					cb.setChecked(true);
				}
			}
		});
        
        
        Button invertselection = (Button) findViewById(R.id.invertselectionbtn);

        invertselection.setOnClickListener(new OnClickListener() {
			
			@Override			
			public void onClick(View v) {
// TODO Auto-generated method stub
				ListView ll = (ListView)findViewById(R.id.selection_list);
				int chi = ll.getChildCount();
				for(int i = 0; i < chi; i++) {
					int current = Integer.valueOf(((FbFriend)listElements.get(i)).getId());
				    CheckBox currcb =  ((CheckBox)ll.findViewById(current));
				    if (currcb.isChecked()){
				    	currcb.setChecked(false);
				    } else {
				    	currcb.setChecked(true);
				    }
					
				}
			}
		});
        
*/
       // Button continuebtn = (Button) findViewById(R.id.continuebtn);
       // continuebtn.setOnClickListener(new OnClickListener() {
			
		//	@Override
		//	public void onClick(View v) {
				// TODO Auto-generated method stub
				//ListView ll = (ListView)findViewById(R.id.selection_list);
				sendRequestDialog();
			}
		//});

        // Check for an open session
        
        
        
    
    
    
    
    
}
