package com.papitomarket.android;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.papitomarket.global.GlobalData;
import com.papitomarket.io.android.Search;
import com.papitomarket.model.cart.Order;
import com.papitomarket.model.cart.PreviousOrderDataSource;
import com.papitomarket.model.cart.PreviousOrders;
import com.papitomarket.model.cart.ShoppingCartAdapter;
import com.papitomarket.model.facebook.android.User;
import com.papitomarket.model.facebook.android.UserDataSource;
import com.papitomarket.model.products.android.Product;
import com.papitomarket.model.shipping.ShippingAddrs;
import com.papitomarket.model.shipping.ShippingAddrsDataSource;
import com.papitomarket.model.stores.android.Store;
import com.papitomarket.service.android.IUpdatesManager;
import com.papitomarket.service.android.Updates;
import com.papitomarket.util.Util;
import com.papitomarket.widgets.TagAutoCompleteTextView;

public class ShoppingCartActivity extends ListActivity{

	
	static boolean tagSelected;
	public static IUpdatesManager service;
	public static UpdatesServiceConnector usc;
    public static String addr;	
    static String usernameCache;
    public static ShoppingCartActivity instance;
	public static Dialog selectAddrPopup;
	public static ArrayList<ShippingAddrs> addrs;
	public static boolean binded;

	
	
	public void initService(){
		ShoppingCartActivity.usc = new UpdatesServiceConnector();
		Intent i = new Intent();
	    i.setClassName(this, com.papitomarket.service.android.Updates.class.getName());
	    ShoppingCartActivity.binded = bindService(i, ShoppingCartActivity.usc, Context.BIND_AUTO_CREATE);
	    i.putExtra("user",User.getInstance().getUsername());
		
	   // startService(i);
	}
	
	private void resetVariables(){
		GlobalData.orders = new ArrayList<Order>();
		
	}
	
	
	@Override
    protected void onDestroy() {
		//if (ShoppingCartActivity.binded){
		//  getApplicationContext().unbindService(ShoppingCartActivity.usc);		
		//}
	    super.onDestroy();
		
	};
	
	
	protected void onStop()
	{
//		ShoppingCartActivity.usc = null;
//	 	if (ShoppingCartActivity.binded){
//            getApplicationContext().unbindService(ShoppingCartActivity.usc);
//      	}
	    super.onStop();
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stubpablo
		super.onCreate(savedInstanceState);

		ShoppingCartActivity.instance = this;
		
		setContentView(R.layout.shoppingcart);
		
		
		setListAdapter(new ShoppingCartAdapter(getBaseContext(),GlobalData.orders.toArray(new Order[GlobalData.orders.size()])));
		
		TagAutoCompleteTextView  textView = (TagAutoCompleteTextView)findViewById(R.id.addredit);
		
		textView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
				TagAutoCompleteTextView  textView = (TagAutoCompleteTextView)findViewById(R.id.addredit);
				if ((textView.getText().toString().length() % 3)==0){
				  AddrService tg = new AddrService(getBaseContext());
                  tg.execute(textView.getText().toString());
                  tagSelected = false;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			
			}
		});
		
		textView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				tagSelected = true;
				TagAutoCompleteTextView  textView = (TagAutoCompleteTextView)findViewById(R.id.addredit);
				ShoppingCartActivity.addr = textView.getText().toString();
				// TODO Auto-generated method stub
				
			}
		});
	 
		
		float total=0;
		for (int i=0;i<GlobalData.orders.size();i++){
	      int amount = GlobalData.orders.get(i).getAmount();
	      float price = GlobalData.orders.get(i).getPrice();
		  total = total + (amount*price);
		}
		//	Log.i("papitomarket","Cart amount: " + ((Spinner)ProductsAdapter.spins.get(i)).getSelectedItem().toString());
		
		
		TextView tot = (TextView)findViewById(R.id.total);
		tot.setText((new Float(total)).toString());
		
		initService();
		
		
		Button save_address = (Button)findViewById(R.id.savelocationbtn);
		save_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ShoppingCartActivity.instance);
				alertDialogBuilder.setTitle("Address Saved").setMessage("Address saved for future orders").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						arg0.cancel();
						
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
				ShippingAddrsDataSource addrsds = new ShippingAddrsDataSource(alertDialog.getContext());
			    addrsds.open();	
				EditText myaddr = (EditText)findViewById(R.id.addredit);
				EditText myap = (EditText)findViewById(R.id.appartment);
				
				
				addrsds.createShippingAddrs(new ShippingAddrs(myaddr.getText().toString(),myap.getText().toString()));
			    addrsds.close();
			   
			}
		});
		
		  
		
		Button clear_shipping_addr_btn = (Button)findViewById(R.id.clearshippingbtn);
		clear_shipping_addr_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText addrEdit = (EditText)(ShoppingCartActivity.instance).findViewById(R.id.addredit);
				addrEdit.setText("");
				EditText appEdit = (EditText)(ShoppingCartActivity.instance).findViewById(R.id.appartment);
				appEdit.setText("");
			}
		});
		
		
		Button load_address = (Button)findViewById(R.id.loadaddrbtn);
		load_address.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {			
				  Dialog selectAddrPopup = new Dialog(ShoppingCartActivity.instance);
				  ShippingAddrsDataSource addrsds = new ShippingAddrsDataSource(selectAddrPopup.getContext());
				  addrsds.open();
				  ShoppingCartActivity.addrs = (ArrayList<ShippingAddrs>) addrsds.getAllShippingAddrs();
				  
				  addrsds.close();
				  selectAddrPopup.setTitle("Choose your shipping address");
				  selectAddrPopup.setContentView(R.layout.select_shipping_address);
				
				  
				  Spinner choose_shipping = (Spinner)selectAddrPopup.findViewById(R.id.select_addr_spin);

				  choose_shipping.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						EditText addrEdit = (EditText)(ShoppingCartActivity.instance).findViewById(R.id.addredit);
						addrEdit.setText(((ShoppingCartActivity.addrs).get(arg2).getAddress()).toString());
						EditText appEdit = (EditText)(ShoppingCartActivity.instance).findViewById(R.id.appartment);
						appEdit.setText(((ShoppingCartActivity.addrs).get(arg2).getAp()).toString());
						Log.i("SMARTBANDS","Item: selected " + arg2);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
				
				  
				  
				  
				  });
				  
				  
				  ArrayList<String> addrss = new ArrayList<String>();
				  Iterator<ShippingAddrs> my_addrs = addrs.iterator();
				  while (my_addrs.hasNext()){
					  ShippingAddrs current = ((ShippingAddrs)my_addrs.next());
					  String option = "Addr: " + current.getAddress() + "Appartment: " + current.getAp();
					  addrss.add(option);
				  }

				  ArrayAdapter<String> options = new ArrayAdapter<String>(selectAddrPopup.getContext(),android.R.layout.simple_spinner_item,addrss);
				  choose_shipping.setAdapter(options);
				  selectAddrPopup.show();
				  ShoppingCartActivity.selectAddrPopup = selectAddrPopup;
				  Button ok = (Button)selectAddrPopup.findViewById(R.id.okloadaddr);
				  ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
						ShoppingCartActivity.selectAddrPopup.cancel();
					}
				});
				  
			
				
			}
		});
		
		
		
		// SAMPLE JSON: "{ \"type\": \"SBSoldNotification\",\"body\": { \"name\": \"Pablo Borda\",
		//                                                              \"user\": \"Pablo Tomas Borda\",
		//                                                              \"addr\": \"Avenida Corrientes 3722, Buenos Aires, Autonomous City of Buenos Aires, Argentina\",
		//                                                              \"items\": [{ \"category\" : \"Empanadas\", 
		//                                                                              \"products\" : [ {\"name\": \"Carne Suave\","
		//                                                                              		            \"amount\": \"6\","
		//                                                                              		            \"price\": \"4\"},"
		//                                                                              		           {\"name\": \"Carne Picante\","
		//                                                                              		            \"amount\": \"9\","
		//                                                                              		            \"price\": \"5\"},"
		//                                                                              		           {\"name\": \"Carne Saltena\","
		//                                                                              		            \"amount\": \"4\","
		//                                                                              		            \"price\": \"6\"}],"
		//                                                                              \"total\": \"85,5\","
		//                                                                              \"payswith\": \"100\"}]}}" 
		//	 
		
		
		Button ordernowbtn = (Button)findViewById(R.id.orderbtn);		
		ordernowbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			    EditText text_shipping_addr = (EditText)ShoppingCartActivity.instance.findViewById(R.id.addredit);
				if (text_shipping_addr.getText().length()<=0){
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ShoppingCartActivity.instance);
					alertDialogBuilder.setTitle("Enter shipping address").setMessage("Enter Shipping address prior to order").setPositiveButton("Ok", new DialogInterface.OnClickListener() {						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							arg0.cancel();
						}
					});
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();					
					Log.i("smartbands","Shipping address is a must field prior to oder product");
				} else {
			    
			      boolean sent;
				  String to = "";
	              UserDataSource uds = new UserDataSource(getApplicationContext());
	              //User.setInstance(uds.createUser(User.getInstance()));
	              
	              User u = ((User)uds.getAllUsers().get(0));

	            String jsonOrder = "";
	          if (u!=null){

	            String uname = u.getName();
                String fname = u.getFirst_name();
                String lname = u.getLast_name();
                String fid = u.getId();
                  
                String items = "";
                  

  				Button ordernowbtn = (Button)findViewById(R.id.orderbtn);
  				ordernowbtn.setClickable(false);
  				EditText addr = (EditText)findViewById(R.id.addredit);
  				if (addr.getText().toString()!=""){
    			  
  				  ShoppingCartActivity.usernameCache = User.getInstance().getName();
  				  
  				  Store current_store = GlobalData.stores.getStores().get(GlobalData.selected_store);
  				  
  				  to = current_store.getInfo().getCompanyname().replace(" ","") + "_" + current_store.getInfo().getLocation().getLat() + "_" + current_store.getInfo().getLocation().getLng();
  				  float tot = 0;
  				  for (int i=0;i<GlobalData.orders.size();i++){
  					//send order messages to selected stores
  					Order currentorder = ((Order)GlobalData.orders.get(i));
  					Product current = currentorder.getP();
  					tot = tot + Float.parseFloat(current.getPrice());
  					
  					items = items + "{\"name\":\"" + current.getName() + "\",\"amount\": \"" +  currentorder.getAmount() + "\", \"price\": \"" +  currentorder.getPrice() + "\"},";	
  				  }

				  items = items.substring(0, items.length()-1);				
                  
                  
				  TextView ap = (TextView)findViewById(R.id.appartment);
				  
				   
    			  jsonOrder = "{\"type\": \"SBSoldNotification\",\"body\": { \"name\": \"" + uname + "\"," + 
    			                                                                   "\"user\": \"" + fname + " " + lname + "\"," +
    					                                                           "\"fbid\": \"" + fid + "\"," + 
    			                                                                   "\"fbpic\": \"" + "http://graph.facebook.com/" + fid + "/picture?type=square\"," +
    					                                                           "\"store\": \"" + current_store.getInfo().getCompanyname() + "\"," +
    			                                                                   "\"phone\": \"" + current_store.getInfo().getPhone() + "\"," +
    					                                                           "\"orderfrom\": \"androidapp\"," + 
    					                                                           "\"store_jabber_addr\":\"" + to + "\"," + 
    			                                                                   "\"status\": \"neworder\"," + 
    					                                                           "\"time\": \"" + Calendar.getInstance().getTimeInMillis() + "\"," +
    					                                                           "\"addr\": \"" + ShoppingCartActivity.addr + "\"," + 
    					                                                           "\"appartment\": \"" + ap.getText().toString() + "\"," +
    			                                                                   "\"items\": [" + items + "]," + 
    					                                                           "\"total\": \"" + tot + "}\"}}";
                  
                  
                  
                  
	            }
				  
				  
				  try {
					while (service == null) {};
					String utf8_jsonOrder = Util.convertToUTF8(jsonOrder);
					GlobalData.current_out_order = utf8_jsonOrder;
					sent = ShoppingCartActivity.service.sendNotification(to,utf8_jsonOrder);
					String orderres = Util.postData("http://192.241.140.67:9494/order", utf8_jsonOrder);
					
					if ( sent && orderres.contains("OK") ){  
					
					
					  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ShoppingCartActivity.instance);
					  alertDialogBuilder.setTitle("Thank you!").setMessage("Your order is being confirmed, wait to receive confirmation").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							arg0.cancel();
							resetVariables();
							Intent gotoMain = new Intent(getApplicationContext(),NewsActivity.class);
							gotoMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
							startActivity(gotoMain);
						}
					  });
					  AlertDialog alertDialog = alertDialogBuilder.create();
					  alertDialog.show();
					  GlobalData.orders = new ArrayList<Order>();
					
					  PreviousOrderDataSource pods = new PreviousOrderDataSource(ShoppingCartActivity.instance);
					  pods.open();
					  pods.createPreviousOrders(new PreviousOrders(jsonOrder));
					  pods.close();
					  
					  
					} else {
						
						 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ShoppingCartActivity.instance);
						  alertDialogBuilder.setTitle("Order Failed!").setMessage("Su pedido no fue enviado correctamente, intentelo mas tarde.").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								arg0.cancel();
								//resetVariables();
								//Intent gotoMain = new Intent(getApplicationContext(),NewsActivity.class);
								//startActivity(gotoMain);
							}
						  });
						  AlertDialog alertDialog = alertDialogBuilder.create();
						  alertDialog.show();
						  GlobalData.orders = new ArrayList<Order>();
						
						
						
						
						
						
						
						
					}
				    } catch (RemoteException e) {
					  // TODO Auto-generated catch block
					  e.printStackTrace();
				    }
				  
				
				// TODO Auto-generated method stub
				
			} else {
			
		  	  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ShoppingCartActivity.instance);
	 		// set title
	     	  
	 
				// set dialog message
			  alertDialogBuilder.setMessage(ShoppingCartActivity.addr.toString()).setPositiveButton("Ok",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
  			// create alert dialog
	  		  AlertDialog alertDialog = alertDialogBuilder.create();
	  		  alertDialog.show();
			}
			}
		 }
		});
		
	}
	
	
	private class AddrService extends AsyncTask<String,Void,String>
	{

        private Context ctx;
        private boolean first = false;

        public AddrService(Context ctx){
          this.ctx = ctx;
        }

		protected String doInBackground(String[] p1)
		{
            publishProgress();
                    
		   
		    Search s = Search.getSearch(getBaseContext());
		    ArrayList<String> addrs = new ArrayList<String>();
		    addrs = s.getAddrsFor(p1[0],this.ctx);

                    
		    String tmp = "";
            for (int i=0;i<addrs.size();i++){
              tmp = tmp + addrs.get(i).toString() + "_";
            }
                    
            return tmp.substring(0,tmp.length());		
			
		}
       
	        @Override
         protected void onPostExecute(String result){

			TagAutoCompleteTextView  textView = (TagAutoCompleteTextView)findViewById(R.id.addredit);


			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_dropdown_item_1line,result.split("_"));
			textView.setAdapter(adapter);
			textView.setThreshold(3);
			if (!tagSelected){
              textView.showDropDown();
            }
            publishProgress();
		 }

                @Override
         protected void onProgressUpdate(Void... values){  

         }

    }

	
	
	class UpdatesServiceConnector implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder boundService) {
			// TODO Auto-generated method stub
			ShoppingCartActivity.service = IUpdatesManager.Stub.asInterface((IBinder)boundService);
			
		    
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			ShoppingCartActivity.service = null;
			System.gc();
		}
		
	}
	
	
	
	
	
}
