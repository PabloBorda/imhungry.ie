package com.papitomarket.android;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class SBAnnouncementActivity extends Activity {

	  @Override
	   public void onCreate(Bundle savedInstanceState){
	       super.onCreate(savedInstanceState);
	       
	       
	       getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, 
	                            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
	       
	       setContentView(R.layout.sbannouncement);

	       String body = getIntent().getStringExtra("body");
	       
	       
	       JsonFactory f = new JsonFactory();
	       JsonParser jp;
	       ObjectMapper om = new ObjectMapper();
	     
	       
			 try {
				jp = f.createJsonParser(body);
				JsonNode node = om.readTree(body);	       
		        TextView tv = (TextView)findViewById(R.id.news);
		        String messa = node.get("message").asText(); 
		        tv.setText(messa);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	       
	  }
		
	
	
	
}
