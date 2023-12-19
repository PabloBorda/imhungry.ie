package com.papitomarket.notifications.android;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.papitomarket.android.R;
import com.papitomarket.android.SBAnnouncementActivity;
import com.papitomarket.android.adapter.NewsAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/*
 * Notification types: each type of notification renders to the attached Activity
 * When a message arrives, the json is parsed, so an instance of the matching notification
 * is created.
 * 
 * Author: Pablo Tomas Borda Di Berardino
 * 
 * */
public class SBAnnouncementNotification extends SBNotification{

	public SBAnnouncementNotification(String body){
		super(body);
	}
	
	public SBAnnouncementNotification(String body,String type){
		super(body,type);
	}
	
	
	@Override
    public Class<? extends Activity> process() {
		
		return SBAnnouncementActivity.class;
	}
	
	public View getView(Context ctx,ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View notiView = inflater.inflate(R.layout.sbannouncement, parent, false);
		
	       
	       JsonFactory f = new JsonFactory();
	       JsonParser jp;
	       ObjectMapper om = new ObjectMapper();
	     
	       
			 try {
				jp = f.createJsonParser(this.body);
				JsonNode node = om.readTree(this.body);	       
		        TextView tv = (TextView)notiView.findViewById(R.id.news);
		        String messa = node.get("message").asText(); 
		        tv.setText(messa);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		
		
        return notiView;
	}
	
	
}
