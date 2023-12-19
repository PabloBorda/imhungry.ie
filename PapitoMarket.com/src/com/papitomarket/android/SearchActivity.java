/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.android;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;

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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.papitomarket.android.adapter.MobileArrayAdapter;
import com.papitomarket.android.adapter.TagsAdapter;
import com.papitomarket.global.GlobalData;
import com.papitomarket.io.android.Search;
import com.papitomarket.model.shipping.ShippingAddrs;
import com.papitomarket.model.shipping.ShippingAddrsDataSource;
import com.papitomarket.model.stores.android.Store;
import com.papitomarket.model.stores.android.Stores;
import com.papitomarket.model.tags.android.Tag;
import com.papitomarket.service.android.IUpdatesManager;
import com.papitomarket.util.Util;
import com.papitomarket.widgets.TagAutoCompleteTextView;
import com.smartbands.http.SBSearchTagRequest;
import com.smartbands.widgets.slidingmenu.lib.SlidingMenu;
/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class SearchActivity extends ListActivity {
    
    private static UpdatesServiceConnector usc;
    private static IUpdatesManager service;
    public static Hashtable<String,TagsAdapter> adapters;
    private static SlidingMenu menu = null;
    public static boolean binded;
    
    
    
    public static SearchActivity instance;
    
	static Stores SEARCH_RESULTS;
	
    static boolean tagSelected;
    public ProgressBar pb;
    static int changeCount;
    static int width;
    static int height;
    public static int dd = 0;
    public long interkey_time = 0;
    public long begin_typing;
    private static Stack<SBSearchTagRequest> requests;
    private static Bundle bundlestate;
     
    
    
        @Override
        public void onDestroy()
        {
        	//if (SearchActivity.binded){
            //  getApplicationContext().unbindService(SearchActivity.usc);
        	//}
            super.onDestroy();
        }
    

       @Override
       protected void onStart() {
    	   onCreate(SearchActivity.bundlestate);
       };
        
        @Override
        protected void onResume() {
            onCreate(SearchActivity.bundlestate);
        	super.onResume();
        };
    
        
        @Override
        protected void onStop() {
         	//if (SearchActivity.binded){
            //    getApplicationContext().unbindService(SearchActivity.usc);
          	//}
        	super.onStop();
        };
    
        @Override
        public void onCreate(Bundle savedInstanceState){
      
            super.onCreate(savedInstanceState);
            SearchActivity.bundlestate = savedInstanceState;
            this.interkey_time = System.currentTimeMillis();
            this.begin_typing = System.currentTimeMillis();
            //GlobalData.name = "ShowingTags";
            
            setContentView(R.layout.search);
            SearchActivity.changeCount = 0;
            
            RelativeLayout rl = (RelativeLayout)findViewById(R.id.rellay);
//            String pic = "@drawable/" + calculateBackgroundPic("l");
//            Log.i("papitomarket","LOADING BACKGROUND: " + pic);
            //Util.resizeImageBackground("l", this, R.id.rellay,rl);
            //rl.setBackgroundResource(getResId(calculatePic("l"), R.drawable.class));
            //rl.setBackgroundDrawable(Drawable.createFromPath("@drawable/l960x540"));
            
            		
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, 
                                 WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            
            adapters = new Hashtable<String, TagsAdapter>();
            requests = new Stack<SBSearchTagRequest>();
            tagSelected = false;
            //requestWindowFeature(Window.FEATURE_NO_TITLE);
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
            //                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            
        	
        	ImageButton bylocationbtn = (ImageButton)findViewById(R.id.locationbtn);
        	bylocationbtn.setOnClickListener(new OnClickListener() {
				
        		private void hidekeyboard(){
            		InputMethodManager imm = (InputMethodManager)getSystemService(
            			      Context.INPUT_METHOD_SERVICE);
            		EditText textView = (EditText)findViewById(R.id.search_text_edit);
            			imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
            	}
        		
				@Override
				public void onClick(View v) {
                      hidekeyboard();
					  StoreCloseFinder sf = new StoreCloseFinder();
	                  sf.execute("");
	                  tagSelected = true;
	                  GlobalData.name = "ShowStoreActivity";
				       
				}
			});

            pb = (ProgressBar)findViewById(R.id.progresscircle);

            pb.setVisibility(View.GONE);
            
    		TagAutoCompleteTextView  textView = (TagAutoCompleteTextView)findViewById(R.id.search_text_edit);
    		
    		textView.addTextChangedListener(new TextWatcher() {
    			
    			@Override
    			public void onTextChanged(CharSequence s, int start, int before, int count) {
    				// TODO Auto-generated method stub
    				
    				TagAutoCompleteTextView  textView = (TagAutoCompleteTextView)findViewById(R.id.search_text_edit);
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

    			
    			private void hidekeyboard(){
            		InputMethodManager imm = (InputMethodManager)getSystemService(
            			      Context.INPUT_METHOD_SERVICE);
            		EditText textView = (EditText)findViewById(R.id.search_text_edit);
            			imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
            	}
        		
				
    			
    			@Override
    			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    					long arg3) {
    				tagSelected = true;
    				TagAutoCompleteTextView  textView = (TagAutoCompleteTextView)findViewById(R.id.search_text_edit);
    				ShoppingCartActivity.addr = textView.getText().toString();
    				
    				hidekeyboard();
					StoreCloseFinder sf = new StoreCloseFinder();
	                sf.execute("");
	                tagSelected = true;
	                GlobalData.name = "ShowStoreActivity";
    				
    				// TODO Auto-generated method stub
    				
    			}
    		});
    	 

            
            ListView lv = (ListView)findViewById(android.R.id.list);
            OnItemClickListener ocl = new OnItemClickListener() {

            	private void hidekeyboard(){
            		InputMethodManager imm = (InputMethodManager)getSystemService(
            			      Context.INPUT_METHOD_SERVICE);
            		EditText textView = (EditText)findViewById(R.id.search_text_edit);
            			imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
            	}
            	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
	                  String selectedTag = (String) SearchActivity.instance.getListAdapter().getItem(arg2);
	                  
					  StoreFinder sf = new StoreFinder(selectedTag);
	                  sf.execute("");
	                  tagSelected = true;
	                  hidekeyboard();
	                  GlobalData.name = "ShowingStores";
				}
			};
			
                        
            ImageButton cartBtn = (ImageButton)findViewById(R.id.cartBtn);
            cartBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (SearchActivity.menu==null){
					   SearchActivity.menu = new SlidingMenu(SearchActivity.instance);
					    //menu.attachToActivity(SearchActivity.instance,SlidingMenu.SLIDING_WINDOW | SlidingMenu.SLIDING_CONTENT);
					   SearchActivity.menu.setMode(SlidingMenu.RIGHT);
					   SearchActivity.menu.showContent(true);
					   SearchActivity.menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					   SearchActivity.menu.setShadowWidthRes(R.dimen.shadow_width);
					   SearchActivity.menu.setShadowDrawable(R.drawable.shadow);
					   SearchActivity.menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
					   SearchActivity.menu.setFadeDegree(0.35f);
					   SearchActivity.menu.attachToActivity(SearchActivity.instance, SlidingMenu.SLIDING_CONTENT);
					   SearchActivity.menu.setMenu(R.layout.shoppingcart);
					
					
					} 
					
						SearchActivity.menu.toggle();
										
	
					
				}
			});
            
            SearchActivity.usc = new UpdatesServiceConnector();
            SearchActivity.instance = this;
    		Intent i = new Intent();
    		i.setClassName(getApplicationContext(), "com.papitomarket.service.android.Updates");
    	    SearchActivity.binded = bindService(i, SearchActivity.usc, Context.BIND_AUTO_CREATE);
            
    
    
    } 

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {                   
                   InputMethodManager imm = (InputMethodManager)getSystemService(
                   Context.INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(((EditText)findViewById(R.id.search_text_edit)).getWindowToken(), 0);
                   if (GlobalData.name=="ShowingTags"){
                	 String selectedTag = ((Tag) SearchActivity.instance.getListAdapter().getItem(position)).getLabel();
                	 GlobalData.selected_tag = selectedTag;
                     StoreFinder sf = new StoreFinder(selectedTag);
	                 sf.execute("");
	                 GlobalData.name = "ShowStoreActivity";
                   } else {
                	   if ((GlobalData.name=="ShowStoreActivity") && (SEARCH_RESULTS != null) && (SEARCH_RESULTS.getStores().size()>0)){
                         GlobalData.setStores(SEARCH_RESULTS);                		 
                         GlobalData.selected_store = position;
                         GlobalData.selectedCompany = GlobalData.stores.getStores().get(position).getInfo().getCompanyname();
                         ShowStoreActivity.store = GlobalData.stores.getStores().get(GlobalData.selected_store);
                         Intent showStore = new Intent(getApplicationContext(), ShowStoreActivity.class);
                         startActivity(showStore);
                	   }
                   }
                   
                  // ShowStoreActivity.store = SEARCH_RESULTS[position];
                  // GlobalData.selectedCompany = SEARCH_RESULTS[position].getCompanyname();
                  // Intent showStore = new Intent(getApplicationContext(), ShowStoreActivity.class);
                  // startActivity(showStore);
              		//String selectedValue = (String) getListAdapter().getItem(position);
		//Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

	}
   	
        

    class UpdatesServiceConnector implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder boundService) {
			// TODO Auto-generated method stub
			SearchActivity.service = IUpdatesManager.Stub.asInterface((IBinder)boundService);
			
		    
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			SearchActivity.service = null;
			System.gc();
		}
		
	  }
    
    
    
    
    private class AllStoresLoader extends AsyncTask<String, Void, Store[]>{

		@Override
		protected Store[] doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}
    	
    	
    	
    	
    	
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

			TagAutoCompleteTextView  textView = (TagAutoCompleteTextView)findViewById(R.id.search_text_edit);


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

	private class StoreCloseFinder extends AsyncTask<String,Void,Stores>{

        private boolean first = false;  
        private String tag;  
        
        
        public StoreCloseFinder() {
			// TODO Auto-generated constructor stub
		    
        }

        
        private void select_or_input_addr(){
			  Dialog selectAddrPopup = new Dialog(SearchActivity.instance);
			  ShippingAddrsDataSource addrsds = new ShippingAddrsDataSource(selectAddrPopup.getContext());
			  addrsds.open();
			  ShoppingCartActivity.addrs = (ArrayList<ShippingAddrs>) addrsds.getAllShippingAddrs();
			  
			  addrsds.close();
			  selectAddrPopup.setTitle("No podemos determinar tu ubicacion, selecciona tu direccion");
			  selectAddrPopup.setContentView(R.layout.select_shipping_address);
			
			  
			  Spinner choose_shipping = (Spinner)selectAddrPopup.findViewById(R.id.select_addr_spin);

			  choose_shipping.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					EditText addrEdit = (EditText)(SearchActivity.instance).findViewById(R.id.search_text_edit);
					addrEdit.setText(((ShoppingCartActivity.addrs).get(arg2).getAddress()).toString());
					EditText appEdit = (EditText)(SearchActivity.instance).findViewById(R.id.appartment);
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
        
        
		@Override
        protected Stores doInBackground(String... arg0) {
              publishProgress();
              String addr_search = ((EditText)findViewById(R.id.search_text_edit)).getText().toString();
              String[] gps_locations = new String[3];
              String[] network_locations = new String[3];
              String[] search_addr_locations = new String[3];
              
              Search s = Search.getSearch(getBaseContext());
              
              // choose the most accurate position, or gps, or network, by
              // the amount of decimals.. the larger the number is the more accurate the position
              // is.
              Stores stores = null;
              if (addr_search.length() > 0){
            	  
				String addr_str;
				String url_addr = "http://192.241.140.67:9494/geolocate?addr=" + URLEncoder.encode(Util.convertToUTF8(addr_search.replace(" ","%20")));
				addr_str = Util.get_no_encode(url_addr);
				Log.i("SMARTBANDS", "GEOLOCATE: " + url_addr);
        	    stores = s.load_stores_close(addr_str.split(",")[0],addr_str.split(",")[1],0,10);    
              } else {
              
              try {
              	    while (SearchActivity.service == null) {};					
              	    String tmp = SearchActivity.service.getGPSLocation();
              	    if (tmp!=null) {
              	      gps_locations = tmp.split(",");
              	    } 
					/*if (gps_locations!=null){
					  Log.i("GPSPOSITION",gps_locations[0] + "," + gps_locations[1]);
					}*/
              	    while (SearchActivity.service == null) {};
					String tmp1 = SearchActivity.service.getNetworkLocation();
	                
	                if (tmp1!=null){
	                  network_locations = tmp1.split(",");
	                } 
	                
	                if ((gps_locations==null) && (network_locations==null)){
                      select_or_input_addr();	                	
	                }
	                /*if (network_locations!=null){
	                  Log.i("GPSPOSITION",network_locations[0] + "," + network_locations[1]);
	                }*/
              } catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              
              
              
              
              if (gps_locations[0].length() > network_locations[0].length() ){
                  stores = s.load_stores_close(gps_locations[0],gps_locations[1],0,10);
              	
              } else {
                  stores = s.load_stores_close(network_locations[0],network_locations[1],0,10);                	
              }
              
              }

              Log.i("SMARTBANDS","SEARCHING " + tag);

                

              return stores;
        }
          
        @Override
        protected void onPostExecute(Stores result){
          SearchActivity.SEARCH_RESULTS = result;
          setListAdapter(new MobileArrayAdapter(getBaseContext(),SearchActivity.SEARCH_RESULTS));
          publishProgress();
          this.cancel(true);
        }

        @Override
        protected void onProgressUpdate(Void... values){  
          super.onProgressUpdate(values);
         

          if (pb.getVisibility()==View.GONE){
            pb.setVisibility(View.VISIBLE);
          } else {
            pb.setVisibility(View.GONE);
          }
          
          
          
        }
          
          
          
      }

    
    
    
    
	private class StoreFinder extends AsyncTask<String,Void,Stores>{

          private boolean first = false;  
          private String tag;  
          
          
          public StoreFinder(String selectedTag) {
			// TODO Auto-generated constructor stub
		    this.tag = selectedTag;
          }

		@Override
          protected Stores doInBackground(String... arg0) {
                publishProgress();
                String search_tag = ((EditText)findViewById(R.id.search_text_edit)).getText().toString();
                String[] gps_locations = new String[3];
                String tmpgps;
                String tmpnet;
                String[] network_locations = new String[3];
                try {
                	while (service == null) {};
                	tmpgps = SearchActivity.service.getGPSLocation();
                	if (tmpgps!="nogps"){
					  gps_locations = tmpgps.split(",");
					  Log.i("GPSPOSITION",gps_locations[0] + "," + gps_locations[1]);
                	} else {
                		  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SearchActivity.instance);
              	 		// set title
              	     	  
              	 
              				// set dialog message
              			  alertDialogBuilder.setMessage("GPS Not working, go to settings, or input location by yourself").setPositiveButton("Ok",new DialogInterface.OnClickListener() {
              				
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
                	tmpnet = SearchActivity.service.getNetworkLocation();
                	if (tmpnet!="nonet"){
  	                  network_locations = tmpnet.split(",");
		              Log.i("GPSPOSITION",network_locations[0] + "," + network_locations[1]);						
					} else {
						  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SearchActivity.instance);
	              	 		// set title
	              	     	  
	              	 
	              				// set dialog message
	              			  alertDialogBuilder.setMessage("Net Location Not working, go to settings, or input location by yourself").setPositiveButton("Ok",new DialogInterface.OnClickListener() {
	              				
	              				@Override
	              				public void onClick(DialogInterface dialog, int which) {
	              					// TODO Auto-generated method stub
	              					dialog.cancel();
	              				}
	              			});
	                			// create alert dialog
	              	  		  AlertDialog alertDialog = alertDialogBuilder.create();
	              	  		  alertDialog.show();					}
					
					
                } catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                

                Search s = Search.getSearch(getBaseContext());
                
                // choose the most accurate position, or gps, or network, by
                // the amount of decimals.. the larger the number is the more accurate the position
                // is.
                Stores stores = null;
                
                if (gps_locations[0].length() > network_locations[0].length() ){
                    stores = s.load_stores(tag.replace(" ","%20"),gps_locations[0],gps_locations[1]);
                	
                } else {
                    stores = s.load_stores(tag.replace(" ","%20"),network_locations[0],network_locations[1]);                	
                }
                
                
 
                Log.i("SMARTBANDS","SEARCHING " + tag);

                  

                return stores;
          }
            
          @Override
          protected void onPostExecute(Stores result){
            SearchActivity.SEARCH_RESULTS = result;
            setListAdapter(new MobileArrayAdapter(getBaseContext(),SearchActivity.SEARCH_RESULTS));

            publishProgress();
            this.cancel(true);
          }

          @Override
          protected void onProgressUpdate(Void... values){  
            super.onProgressUpdate(values);
            
            
            
          }
            
            
            
        }
        
	
}
    
    

