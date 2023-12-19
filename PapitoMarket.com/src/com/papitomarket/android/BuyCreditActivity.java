/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.FbDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.facebook.widget.WebDialog.RequestsDialogBuilder;
import com.facebook.FacebookException;
import com.facebook.Session;
import com.facebook.SessionState;

/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class BuyCreditActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, 
                                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        
        // ToDo add your GUI initialization code here 
        setContentView(R.layout.buycredit);
        
        ImageButton paybutton = (ImageButton)findViewById(R.id.paybutton);
        
        paybutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                String dialog_url = "https://www.facebook.com/dialog/pay?app_id=398054573586712&" +
                                    "action=buy_item&" +
                                    "order_info={\"item_id\":\"1a\"}&" +
                                    "dev_purchase_params={\"oscif\":true}\"";
                
                
                Bundle params = new Bundle();   
                	params.putString("message",
                      "Learn how to make your Android apps social");
                	//params.putString("to", "YOUR_FRIEND_UID_HERE");
                	params.putString("URI", dialog_url);
                
                WebDialog dialog = new WebDialog.Builder(arg0.getContext(),  Session.getActiveSession(),"",params).build();
                
                dialog.show();
                
                
                
                
            }
        });
        
    }
}








//WebDialog requestsDialog = (new WebDialog.RequestsDialogBuilder(this, Session.getActiveSession(), params)).setOnCompleteListener(new OnCompleteListener() {
//
//          @Override
//          public void onComplete(Bundle values,
//                  FacebookException error) {
//              // your code here 
//          }
//
//      }).build();

//requestsDialog.setOnCompleteListener(listener);
//requestsDialog.show();


//WebDialog requestsDialog = new RequestsDialogBuilder((Context)getBaseContext(), Session.getActiveSession(), params);

// requestsDialog.setOnCompleteListener(listener);
//WebDialog rDialog = WebDialog.RequestsDialogBuilder() 
//		/(requestsDialog;






//FbDialog pay_dialog = new FbDialog(getBaseContext(),dialog_url,new Facebook.DialogListener() {
//
//
//
//public void onComplete(Bundle bundle) {
//    throw new UnsupportedOperationException("Not supported yet.");
//}
//
//public void onFacebookError(FacebookError fe) {
//    throw new UnsupportedOperationException("Not supported yet.");
//}
//
//public void onError(DialogError de) {
//    throw new UnsupportedOperationException("Not supported yet.");
//}
//
//public void onCancel() {
//    throw new UnsupportedOperationException("Not supported yet.");
//}
//});


//WebDialog pay_dialog = new WebDialog(getBaseContext(), dialog_url);
//
//Bundle params = new Bundle();   
//params.putString("message",
//      "Learn how to make your Android apps social");
//params.putString("to", "YOUR_FRIEND_UID_HERE");
//RequestsDialogBuilder requestsDialog = new RequestsDialogBuilder(getBaseContext(), Session.getActiveSession());
//requestsDialog.build();
//
//OnCompleteListener listener = new OnCompleteListener() {
//	@Override
//	public void onComplete(Bundle values, FacebookException error) {
//
//		values.toString();
//		
//	}
//};
//
//pay_dialog.setOnCompleteListener(listener);
//pay_dialog.show();
