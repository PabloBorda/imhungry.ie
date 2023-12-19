package com.papitomarket.model.stores.android;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.papitomarket.android.adapter.CategoriesAdapter;
import com.papitomarket.model.products.android.Category;
import com.papitomarket.model.products.android.Product;

public class StoresDeserializer implements JsonDeserializer<Stores>{

	private Gson gson;
	
	
	
	
	private Product processProduct(JsonElement e){
	  Product p = gson.fromJson(e.getAsJsonObject().get("product").toString(), Product.class);
	  return p;	
	}
	
	
	
	
	private Category processCategory(JsonElement e){
		String catename = e.getAsJsonObject().get("category").getAsString();
		Category c = null;
		
		Iterator<JsonElement> itproducts = e.getAsJsonObject().get("products").getAsJsonArray().iterator();
		ArrayList<Product> products = new ArrayList<Product>();
		while (itproducts.hasNext()){
			JsonElement currentProductJSON = (JsonElement)itproducts.next();
			Product tmp = processProduct(currentProductJSON);
			products.add(tmp);
			
		}
		if (products.size()>0){
		  c = new Category(catename, products);
		}
		return c;
	}
	
	private ArrayList<Category> processCategories(JsonElement e){
		ArrayList<Category> categories_raw = new ArrayList<Category>();
		JsonArray categories = e.getAsJsonArray();
		for (int i=0;i<categories.size();i++){
			Category tmp = processCategory(categories.get(i));
			if (tmp!=null){
			  categories_raw.add(tmp);
			}
		}
		return categories_raw;
		
		
	}
	
	private Context processContext(JsonElement e){
		Context tmp = null;
		if (!e.isJsonNull()){
		  String t = e.getAsJsonObject().toString();
		  Log.i("SMARTBANDS",t);
         
		  JsonArray zone = e.getAsJsonObject().getAsJsonObject("zone").getAsJsonArray("coordinates");
		  Iterator<JsonElement> it = zone.iterator();
		  
		  JsonObject fromJson = e.getAsJsonObject();
		  String mon = fromJson.get("mon").toString().replace("\"","");
		  String tue = fromJson.get("tue").toString().replace("\"","");
		  String wed = fromJson.get("wed").toString().replace("\"","");
		  String thu = fromJson.get("thu").toString().replace("\"","");
		  String fri = fromJson.get("fri").toString().replace("\"","");
		  String sat = fromJson.get("sat").toString().replace("\"","");
		  String sun = fromJson.get("sun").toString().replace("\"","");
		  String minorder = fromJson.get("minorder").toString().replace("\"","");
		  String estimated = fromJson.get("estimated").toString().replace("\"","");
		  
		  
		  
		  Context newCon = new Context(mon,tue,wed,thu,fri,sat,sun,minorder,estimated);
		  while (it.hasNext()){
			  float[] point = new float[2];
			  JsonArray current_point = (JsonArray)it.next();
			  point[0] = current_point.getAsJsonArray().get(0).getAsFloat();
			  point[1] = current_point.getAsJsonArray().get(1).getAsFloat();
			  newCon.getZone().add(point);
			  
		  }
		  
		  tmp = newCon; 
          
		  
        }
        
        return (tmp);
	}
	
	
	private Location processLocation(JsonElement e){
	  Location tmp = gson.fromJson(e.getAsJsonObject().toString(), Location.class);
	  return tmp;
	}
	
	
	private Info processInfo(JsonElement e){
		Location location = processLocation(e.getAsJsonObject().get("location"));
		Info tmp = gson.fromJson(e.getAsJsonObject().toString(), Info.class);
		tmp.setLocation(location);
		return (tmp);
		
	}
	
	
	private Store processStore(JsonElement e){
		Context c = processContext(e.getAsJsonObject().get("context"));
		Info f = processInfo(e.getAsJsonObject().get("info"));
		ArrayList<Category> cc = processCategories(e.getAsJsonObject().get("categories"));
		
		
		Store s = new Store(c,cc,f);
		
		return s;
	}
	
	
	
	
	@Override
	public Stores deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		this.gson = new Gson();
		Stores stores = new Stores();
		final JsonArray jsonStores = arg0.getAsJsonArray();
		for (int i=0;i<jsonStores.size();i++){
			JsonElement tmp = jsonStores.get(i);
			stores.addStore(processStore(tmp.getAsJsonObject().get("store")));
			
		}
		
		
		// TODO Auto-generated method stub
		return stores;
	}

}
