package imhungry.activities;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Session;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.papitomarket.android.adapter.NewsAdapter;
import com.papitomarket.global.GlobalData;
import com.papitomarket.io.android.Search;
import com.papitomarket.model.cart.Order;
import com.papitomarket.model.facebook.android.User;
import com.papitomarket.model.facebook.android.UserDataSource;
import com.papitomarket.model.products.android.Product;
import com.papitomarket.model.shipping.ShippingAddrs;
import com.papitomarket.model.shipping.ShippingAddrsDataSource;
import com.papitomarket.notifications.android.SBNotification;
import com.papitomarket.notifications.android.SBNotificationFactory;
import com.papitomarket.service.android.IUpdatesManager;
import com.papitomarket.service.android.Updates;
import com.papitomarket.util.Util;
import com.papitomarket.widgets.TagAutoCompleteTextView;
import com.smartbands.widgets.slidingmenu.lib.SlidingMenu;







 




public class NewsActivity extends ListActivity{

	private static SlidingMenu cart = null;
	private static SlidingMenu menu = null;
	public static NewsActivity instance=null;
	public static Updates updates = null;
	public static String cache = "";
	public static boolean got=false;	

	static boolean tagSelected;
	public static IUpdatesManager service;
	public static UpdatesServiceConnector usc;
    public static String addr;	
    static String usernameCache;
	public static ArrayList<Order> orders = new ArrayList<Order>();
    private NotificationReceiver notificationReceiver;
    public static boolean binded;
	
	
	public void initService(){
		  NewsActivity.usc = new UpdatesServiceConnector();
		  Intent i = new Intent();
	      i.setClassName(this, com.papitomarket.service.android.Updates.class.getName());
	      NewsActivity.binded = bindService(i, NewsActivity.usc, Context.BIND_AUTO_CREATE);
	      i.putExtra("user",User.getInstance().getUsername());
		
	   // startService(i);
	}
	
	protected void onStop()
	{
	 	if (NewsActivity.binded){
            getApplicationContext().unbindService(NewsActivity.usc);
      	}
	    //unregisterReceiver(notificationReceiver);
	    super.onStop();
	}
	
	
	private class FacebookRetriever extends AsyncTask<Void,String,String>{
	    
	    private Session s;
	    
	    
	    public FacebookRetriever(){
	        s = Session.getActiveSession();
	        
	    }

	    @Override
	    protected String doInBackground(Void... arg0) {
	        while (!s.isClosed() && !s.isOpened()){}
	        if (s.isOpened() && (NewsActivity.cache.equals(""))){
	              Request r = new Request(s,"/me");
	              String ans = r.executeAndWait().toString();
	              NewsActivity.cache = ans;
	              return ans;
	        } else {
	          return "";
	        }
	    }
	    
	    @Override
	    protected void onPostExecute(String result){
	      NewsActivity.got = true;        
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
	                    Logger.getLogger(NewsActivity.class.getName()).log(Level.SEVERE, null, ex);
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
	                Logger.getLogger(NewsActivity.class.getName()).log(Level.SEVERE, null, ex);
	            } catch (IllegalArgumentException ex) {
	                Logger.getLogger(NewsActivity.class.getName()).log(Level.SEVERE, null, ex);
	            } catch (IOException ex) {
	            Logger.getLogger(NewsActivity.class.getName()).log(Level.SEVERE, null, ex);
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
	        //while (NewsActivity.updates==null){}
	        String jabber_username = User.getInstance().getUsername() + "-fb";
	        String response = Util.get_no_encode("http://192.241.140.67:9494/newxmpp?uid=" + jabber_username);
	        Log.i("papitomarket", "XMPP NEW USER: " + response);
	     }

	}
	

	
	
	private class NotificationReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			
			String notification = arg1.getStringExtra("notification");
			 						
			GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(SBNotification.class, new SBNotificationFactory());
            Gson gson = gsonBuilder.create();
              		   
		    SBNotification noti = ((SBNotification) gson.fromJson(notification.toString(),SBNotification.class));
			
		    
		    if (!GlobalData.current_out_order.contains(User.getInstance().getLast_name())){
		    
			  GlobalData.pushNotification(noti); 
			
			  NewsActivity.na.notifyDataSetChanged();
			
			  Toast.makeText(NewsActivity.this,
			    "Triggered by Service!\n"
			    + "Data passed: " + notification,
			    Toast.LENGTH_LONG).show();
		    }
			
			
		}
		
		
		
		
		
	}
	
	
	
	
	private void handleNotification(Context c){
		 notificationReceiver = new NotificationReceiver();
	      IntentFilter intentFilter = new IntentFilter();
	      intentFilter.addAction(Updates.ACTION_FOR_INTENT_FILTER);
	      registerReceiver(notificationReceiver, intentFilter);
	     
	      
	      Intent intent = new Intent(NewsActivity.this,Updates.class);
	      startService(intent);
	}
	
	
	public static NewsAdapter na;
	
	
	@Override
	protected void onResume() {
		NewsActivity.menu = null;
		NewsActivity.cart = null;
		super.onResume();
	};
	
	@Override
	protected void onDestroy() {
		NewsActivity.menu = null;
		NewsActivity.cart = null;
		if (NewsActivity.binded){
		  getApplicationContext().unbindService(NewsActivity.usc);
		}
		super.onDestroy();
		
	};
	
	
	private static boolean DEVELOPER_MODE = true;
	
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
    	/*
    	 if (DEVELOPER_MODE) {
             StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                     .detectDiskReads()
                     .detectDiskWrites()
                     .detectNetwork()   // or .detectAll() for all detectable problems
                     .penaltyLog()
                     .build());
             StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                     .detectLeakedSqlLiteObjects()
                     .detectLeakedClosableObjects()
                     .penaltyLog()
                     .penaltyDeath()
                     .build());
         }
    	
  */  	
    	
        super.onCreate(icicle);
        
        handleNotification(NewsActivity.instance);
        
        NewsActivity.instance = this;
        
        NewsActivity.na = new NewsAdapter(NewsActivity.instance);
        
        setContentView(R.layout.news);
        
        setListAdapter(na);
        
		UserDataSource uds = new UserDataSource(getApplicationContext());
	    List<User> users =  uds.getAllUsers();
	    uds.close();
	    if (users.size()==0){
	      FacebookRetriever fr = new FacebookRetriever();
	      fr.execute();        
	    } else {
	    	User.setInstance((User)users.get(0));
	    	RelativeLayout ll = (RelativeLayout)findViewById(R.id.newsheader);
	    	
	    	
	    	TextView tv = (TextView)findViewById(R.id.wellcome);
	        tv.setWidth(ll.getWidth()-100);
	    	tv.setText("Welcome " + (User.getInstance()).getName());
	        
	    	
	    	
	        User.getInstance().setStatus("ONLINE");           
	        // Start chat service
	        Updates s = new Updates(NewsActivity.this);
	        Intent updatesIntent = new Intent(getApplicationContext(),Updates.class);
	        updatesIntent.putExtra("user",User.getInstance().getUsername());
	        startService(updatesIntent);

	    }

	    RelativeLayout ll = (RelativeLayout)findViewById(R.id.newsheader);

    	TextView tv = (TextView)findViewById(R.id.wellcome);
        tv.setWidth(ll.getWidth()-100);    
        
   // ==================================================== CART ===================================================================================
        
        
        ImageButton cartBtn = (ImageButton)findViewById(R.id.news_cartBtn);
        cartBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (NewsActivity.cart==null){
					NewsActivity.cart = new SlidingMenu(NewsActivity.instance);
					NewsActivity.cart.attachToActivity(NewsActivity.instance,SlidingMenu.SLIDING_WINDOW | SlidingMenu.SLIDING_CONTENT);
					NewsActivity.cart.setMode(SlidingMenu.RIGHT);
					NewsActivity.cart.showContent(true);
					NewsActivity.cart.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					NewsActivity.cart.setShadowWidthRes(R.dimen.shadow_width);
					NewsActivity.cart.setShadowDrawable(R.drawable.shadow);
					NewsActivity.cart.setBehindOffsetRes(R.dimen.slidingmenu_offset);
					NewsActivity.cart.setFadeDegree(0.35f);
					NewsActivity.cart.setMenu(R.layout.shoppingcart);
				
				
				} 
				
				NewsActivity.cart.toggle();

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

				
				Button save_address = (Button)findViewById(R.id.savelocationbtn);
				save_address.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Dialog saveAddrPopup = new Dialog(NewsActivity.instance);
						ShippingAddrsDataSource addrsds = new ShippingAddrsDataSource(saveAddrPopup.getContext());
					    addrsds.open();	
						EditText myaddr = (EditText)findViewById(R.id.addredit);
						EditText myap = (EditText)findViewById(R.id.appartment);
						
						
						addrsds.createShippingAddrs(new ShippingAddrs(myaddr.getText().toString(),myap.getText().toString()));
					    addrsds.close();
						saveAddrPopup.setTitle("Shipping address saved");
						saveAddrPopup.setCancelable(true);				

						saveAddrPopup.show();
					}
				});
				
				Button clear_shipping_addr_btn = (Button)findViewById(R.id.clearshippingbtn);
				clear_shipping_addr_btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						EditText addrEdit = (EditText)(NewsActivity.instance).findViewById(R.id.addredit);
						addrEdit.setText("");
						EditText appEdit = (EditText)(NewsActivity.instance).findViewById(R.id.appartment);
						appEdit.setText("");
					}
				});
				
				Button load_address = (Button)findViewById(R.id.loadaddrbtn);
				load_address.setOnClickListener(new OnClickListener() {			
					@Override
					public void onClick(View v) {			
						  Dialog selectAddrPopup = new Dialog(NewsActivity.instance);
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
								EditText addrEdit = (EditText)(NewsActivity.instance).findViewById(R.id.addredit);
								addrEdit.setText(((ShoppingCartActivity.addrs).get(arg2).getAddress()).toString());
								EditText appEdit = (EditText)(NewsActivity.instance).findViewById(R.id.appartment);
								appEdit.setText(((ShoppingCartActivity.addrs).get(arg2).getAp()).toString());
								Log.i("SMARTBANDS","Item: selected " + arg2);
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub
								
							}
						
						  
						  
						  
						  });
						  
						  
						  ArrayList<String> addrss = new ArrayList<String>();
						  Iterator<ShippingAddrs> my_addrs = ShoppingCartActivity.addrs.iterator();
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
				
				Button ordernowbtn = (Button)findViewById(R.id.orderbtn);		
				ordernowbtn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Button ordernowbtn = (Button)findViewById(R.id.orderbtn);
						ordernowbtn.setClickable(false);
						EditText addr = (EditText)findViewById(R.id.addredit);
						if (addr.getText().toString()!=""){
		  				  boolean sent;
						  ShoppingCartActivity.usernameCache = User.getInstance().getName();
						  String products = "{\"sold\": [";
						  String to = GlobalData.selectedCompany.replace(" ","") + "-fb";
						  for (int i=0;i<GlobalData.orders.size();i++){
							//send order messages to selected stores
							Order currentorder = ((Order)GlobalData.orders.get(i));
							Product current = currentorder.getP();
							products = products + "{ \"id\" : \"" + current.getId() + "\",\"name\":\"" + current.getName() + "\",\"addr\": \"" +  ShoppingCartActivity.addr + "\", \"amount\": \"" +  currentorder.getAmount() + "\",\"total\":\"" + currentorder.getPrice() + "\",\"who\":" + "\"" + ShoppingCartActivity.usernameCache.replace(" ","") + "-fb" + "\"" + "},";	
						  }
						  products = products.substring(0, products.length()-1);				
						  products = products + "]}";
						  try {
							while (service == null) {};
							sent = ShoppingCartActivity.service.sendNotification(to,products);
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewsActivity.instance);
							alertDialogBuilder.setTitle("Thank you!").setMessage("Your order is being confirmed, wait to receive confirmation").setCancelable(true);
							AlertDialog alertDialog = alertDialogBuilder.create();
							alertDialog.show();
							GlobalData.orders = new ArrayList<Order>();
						  } catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						  }
						
						
						// TODO Auto-generated method stub
						
					} else {
					
				  	  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NewsActivity.instance);
			 		// set title
			     	  
			 
						// set dialog message
					  alertDialogBuilder.setMessage(addr.getText().toString()).setPositiveButton("Ok",new DialogInterface.OnClickListener() {
						
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
				});
 			}
		});
    
    // ================================================== ENDCART =================================================================================

        
    // =================================================== MENU ===================================================================================
        ImageButton menuBtn = (ImageButton)findViewById(R.id.news_menubtn);
        menuBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (NewsActivity.menu==null){
					NewsActivity.menu = new SlidingMenu(NewsActivity.instance);
					NewsActivity.menu.attachToActivity(NewsActivity.instance,SlidingMenu.SLIDING_WINDOW | SlidingMenu.SLIDING_CONTENT);
					NewsActivity.menu.setMode(SlidingMenu.LEFT);
					NewsActivity.menu.showContent(true);
					NewsActivity.menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					NewsActivity.menu.setShadowWidthRes(R.dimen.shadow_width);
					NewsActivity.menu.setShadowDrawable(R.drawable.shadow);
					NewsActivity.menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
					NewsActivity.menu.setFadeDegree(0.35f);
					NewsActivity.menu.setMenu(R.layout.menu);
				}
				NewsActivity.menu.toggle();
				
	
				Button search_button = (Button)findViewById(R.id.search_button);
				
	            //while (search_button==null){
	            //	search_button = (Button)findViewById(R.id.search_button);
	            //};

				search_button.setOnClickListener(new View.OnClickListener() {

				  public void onClick(View arg0) {
				    Intent search_screen = new Intent(getApplicationContext(),SearchActivity.class);
				    startActivity(search_screen);
				  }
				});

				
				
				Button previousOrders = (Button)findViewById(R.id.log_button);
				previousOrders.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent startPreviousOrdersActivity = new Intent(getApplicationContext(),PreviousOrdersActivity.class);
                        startActivity(startPreviousOrdersActivity);
						// TODO Auto-generated method stub
						
					}
				});
				
				
				/*        
				Button buycredit_button = (Button)findViewById(R.id.buycredit_button);
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
	
			}
		});
        
   // ================================================== ENDMENU ==================================================================================
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
		    addrs = s.getAddrsFor(p1[0],getApplicationContext());

                    
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
