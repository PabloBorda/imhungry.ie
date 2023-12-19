/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.global;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import com.papitomarket.android.NewsActivity;
import com.papitomarket.model.cart.Order;
import com.papitomarket.model.stores.android.Location;
import com.papitomarket.model.stores.android.Store;
import com.papitomarket.model.stores.android.Stores;
import com.papitomarket.notifications.android.SBAnnouncementNotification;
import com.papitomarket.notifications.android.SBNotification;
import com.papitomarket.util.Util;

/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class GlobalData {
    public static String name;
    public static String selectedCompany;
    public static Stores stores;
    public static int selected_store;
    public static int selected_category;
    public static int selected_product;
    public static String selected_tag;
    public static ArrayList<Order> orders = new ArrayList<Order>();
    public static ArrayList<SBNotification> news = new ArrayList<SBNotification>();
    public static Location gps_location;
    public static Location network_location;
    public static int current_repeat_order_clicked_button;
    public static String current_out_order;
    
    /**
     * 
     * This preloadImagesOnBackground should wun when all stores are retrieved, and
     * when network connection is iddle.
     * 
     * **/
    
    
    private static void preloadImagesOnBackground(){
    	// On each load the getBitmapFromUrl creates a cache entry, of if available loads the pic from the database
    	
		String base_url = "http://dev.smartbands.com.ar/images/";
		
		
		Iterator<Store> it = stores.getStores().iterator();
		while (it.hasNext()){
			try {
		    Store current = (Store)it.next();
		    // Getting company logos
		    String logo_url;
		    String to_utf8;
		    logo_url = current.getInfo().getCompanylogo().replace("logos/","");
		    String parts[] = logo_url.split("/");
		  
		    String encoded_url;
		
			encoded_url = base_url + (new String(parts[parts.length-2].getBytes("UTF-8"), "ISO-8859-1")) + "/" + (new String(parts[parts.length-1].getBytes("UTF-8"), "ISO-8859-1"));
		    Util.getBitmapFromURL(encoded_url,NewsActivity.instance);
		  // Getting google maps
		    String addr = "http://maps.googleapis.com/maps/api/staticmap?center=" + current.getInfo().getLocation().getLat() + "," + current.getInfo().getLocation().getLng() + "&markers=" + current.getInfo().getLocation().getLat() + "," + current.getInfo().getLocation().getLng() + "&zoom=15&size=500x106&markers=color:red%7Ccolor:red%7Clabel:C%7C40.718217,-73.998284&sensor=false";
		    Util.getBitmapFromURL(addr,NewsActivity.instance);
	   	  } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }
			
		}

    	   
       
    	
    }
    
    
    
    
    public static void setStores(Stores strs){
    	GlobalData.stores = strs;
    	
    	Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				GlobalData.preloadImagesOnBackground();
				// TODO Auto-generated method stub
				
			}
		});    	
    	t.start();
    	
    }
    
    
    
    
    public static void pushNotification(SBNotification notification){
		if (GlobalData.news==null){
			GlobalData.news = new ArrayList<SBNotification>();
		}
		if (notification != null){
			GlobalData.news.add(notification);
		} else {
			GlobalData.news.add(new SBAnnouncementNotification("{ \"message\": \"Error getting smartbands notification.\"  }","SBAnnouncementNotification"));
		}
	}
    
}
