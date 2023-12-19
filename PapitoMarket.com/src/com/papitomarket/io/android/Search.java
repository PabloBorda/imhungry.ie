/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.io.android;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.papitomarket.android.SearchActivity;
import com.papitomarket.model.products.android.Category;
import com.papitomarket.model.products.android.Product;
import com.papitomarket.model.stores.android.Store;
import com.papitomarket.model.stores.android.Stores;
import com.papitomarket.model.stores.android.StoresDeserializer;
import com.papitomarket.model.tags.android.Tag;
import com.papitomarket.util.Util;

/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class Search {
    public String tag;

    private String search_url;
    private Context ctx;
    private static Search s;
    
    private Search(Context ctx){
        search_url = "http://soa1.papitomarket.com:9494/search?";  
        this.ctx = ctx;
    }
    
    public static Search getSearch(Context ctx){
        if (s == null){
            s = new Search(ctx);
        }
        return s;
    }
    
    
    
    public String find(String tag){        
       /* Gps gps = new Gps(this.ctx);
        gps.update();
        String url = search_url + "superprod=" + tag + "&lat=" + gps.lat.toString() + "&lng=" + gps.lng.toString();*/
    	String params = "superprod=" + tag + "&lat=-34.603585" + "&lng=-58.417016";
    	return Util.get("http://192.241.140.67:9494/",URLEncoder.encode(params));
    }
    



    public String load_product(String company_id){
        String params= "company/" + company_id;
        return Util.get("http://192.241.140.67:9494/",params);
    }
    
    public Stores load_stores(String tag,String lat,String lng){
        String params = "show_stores_for?term=" + tag + "&lat=" + lat + "&lng=" + lng + "&pnum=0&psize=10";
        Log.i("SMARTBANDS","VISITING STORES: http://192.241.140.67:9494/" + params);
        String response = Util.get_own_encoder("http://192.241.140.67:9494/" + params,SearchActivity.instance);
        if (!response.equals("[]")){
        	GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Stores.class, new StoresDeserializer());
            Gson gson = gsonBuilder.create();
              		   
		    Stores stores = ((Stores) gson.fromJson(response.toString(),Stores.class));
		    return stores;
        }
        	
        return (new Stores());
        
    }
    
    
    public Stores load_stores_close(String lat,String lng,int pnum,int psize){
    	String params = "stores_for_location?&lat=" + lat + "&lng=" + lng + "&pnum=0&psize=50";
        Log.i("SMARTBANDS","VISITING STORES: http://192.241.140.67:9494/" + params);
        String response = Util.get_own_encoder("http://192.241.140.67:9494/" + params,SearchActivity.instance);
        if (!response.equals("[]")){
        	GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Stores.class, new StoresDeserializer());
            Gson gson = gsonBuilder.create();
              		   
		    Stores stores = ((Stores) gson.fromJson(response.toString(),Stores.class));
		    return stores;
        }
        	
        return (new Stores());
    }
    
    
    
    public ArrayList<String> getAddrsFor(String addr,Context ctx){
    	// IE: http://maps.googleapis.com/maps/api/geocode/json?address=Av%20Corrientes%203722&sensor=false
    	// IE: http://soa1.papitomarket.com:9494/geolocation/mobile/addresses?term=Av Corrientes 3722
        // Result example: [{"value":"0","label":"Avenida Corrientes 3722, Buenos Aires, Autonomous City of Buenos Aires, Argentina"},{"value":"1","label":"Avenida Corrientes 3722, Posadas, Misiones Province, Argentina"}]
    	String params = "geolocation/mobile/addresses?term=" + addr.replace(" ","%20");
        String response = Util.get_own_encoder("http://192.241.140.67:9494/" + params,ctx);
        ObjectMapper om = new ObjectMapper();
        JsonFactory f = new JsonFactory();
        JsonParser jp;
        ArrayList<String> arr = new ArrayList<String>();
        
        try
	{
	        jp = f.createJsonParser(response);
	        JsonNode node = om.readTree(response);
            Iterator<JsonNode> all = node.iterator();
            JsonNode current;
            for (int i=0;i<node.size();i++){
            	current = (JsonNode)node.get(i);
            	arr.add(current.get("label").asText());
            }
	} catch (IOException e){
		Log.e("JSON","Error parsing json address " + arr.toString());
	}
        return arr;
        
    }
    
    
}