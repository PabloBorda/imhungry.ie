package com.papitomarket.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.papitomarket.android.adapter.SoldAdapter;
import com.papitomarket.model.notification.Item;
import com.papitomarket.model.notification.Items;


/*
 
  
  
{ "type": "SBSoldNotification",
  "body": { "name": "Pablo Borda",
            "user": "pablo.borda-fb",
            "addr": "Avenida Corrientes 3722, Buenos Aires, Autonomous City of Buenos Aires, Argentina",
            "items": [
              { "category" : "Empanadas",
                "products" : [ {"name": "Carne Suave",
                                "amount": "6",
                                "price": "4"},
                               {"name": "Carne Picante",
                                "amount": "9",
                                "price": "5"},
                               {"name": "Carne Saltena",
                                "amount": "4",
                                "price": "6"} ],
                "total": "85,5",
                "payswith": "100"
               
                 }
            ]                             
           }
}
 
 
 */

public class SBSoldActivity extends ListActivity{
	

	
   public void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, 
                            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
       
       android.os.Debug.waitForDebugger();
       setContentView(R.layout.sbsold);
       
       
       String body = getIntent().getStringExtra("body");
       
       
       JsonFactory f = new JsonFactory();
       JsonParser jp;
       ObjectMapper om = new ObjectMapper();
    
      	
		  try {
			jp = f.createJsonParser(body);
			JsonNode node = om.readTree(body);
	          String usr = node.get("name").asText();
	          String name = node.get("user").asText();
	          String addr = node.get("addr").asText();
	       
	       
	          Iterator<JsonNode> nodes = node.get("items").iterator();
	          while (nodes.hasNext()){	
		        JsonNode current = (JsonNode)nodes.next();
		        Items items = new Items();
	    	    items.setCategory(current.get("category").asText());
	    	  
	    	    Iterator<JsonNode> products = current.get("products").iterator();
	    	    while (products.hasNext()){
	    	       JsonNode prod = ((JsonNode)products.next());
	    	       Item toItem = new Item();
	    	       toItem.setName(prod.get("name").asText());
	    	       toItem.setAmount(prod.get("amount").asText());
		    	   toItem.setPrice(prod.get("price").asText());
	    	       items.addItem(toItem);	    	    	
	    	    }
	    	    
	    	   
	    	    setListAdapter(new SoldAdapter(this,items.toArray()));    
	          }
	          
	          
	      
	          
	          
		  } catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		
		  
		    
       
      
   }
   
}
   
	

