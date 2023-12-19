package com.papitomarket.notifications.android;


import java.lang.reflect.Array;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.papitomarket.android.R;
import com.papitomarket.android.SBSoldActivity;
import com.papitomarket.android.adapter.MobileArrayAdapter;
import com.papitomarket.model.cart.Order;
import com.papitomarket.model.notification.Item;
import com.papitomarket.model.notification.SBSoldNotificationModel;
import com.papitomarket.model.products.android.Product;
import com.papitomarket.util.ImageLoader;


/*
 * Notification types: each type of notification renders to the attached Activity
 * When a message arrives, the json is parsed, so an instance of the matching notification
 * is created.
 * 
 * Aut hor: Pablo Tomas Borda Di Berardino
 * 
 * */
public class SBSoldNotification extends SBNotification {

	public SBSoldNotification(String body){
		super(body);
	}
	
	

	public SBSoldNotification(String body,String type){
		super(body,type);
	}
	
	
	
	
	public Class<? extends Activity> process(){
		
		return SBSoldActivity.class;
		 
	}
	
	
	private class ItemsOrderedAdapter extends ArrayAdapter<Item>{
		 
		private Context ctx;
		private Item[] results;
		
		
		public ItemsOrderedAdapter(Context context, Item[] orders) {
			super(context,R.layout.shoppingcart_item,orders);
			this.ctx = context;
			this.results = orders;
			// TODO Auto-generated constructor stub
		}
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
	  	      LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			  View rowView = inflater.inflate(R.layout.shoppingcart_item, parent, false);
			  TextView prodnam = (TextView)rowView.findViewById(R.id.prname);
			  prodnam.setText(results[position].getName());
			  TextView pri = (TextView)rowView.findViewById(R.id.pri);
			  pri.setText("Price: " + results[position].getPrice());
			  TextView amounttext = (TextView)rowView.findViewById(R.id.amounttext);
			  amounttext.setText("Amount: " + results[position].getAmount());
			  return rowView;
		}
		
	}
	

	
	
	
	

	public View getView(Context ctx,ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View notiView = inflater.inflate(R.layout.sbsoldnotification_news_item, parent, false);
	       
  	    Gson parser = new Gson();
	    SBSoldNotificationModel noti = parser.fromJson(this.body,SBSoldNotificationModel.class);
	       
	       
	       
        TextView fbuname_wd = (TextView)notiView.findViewById(R.id.fbuname);
	    ImageView pic_wd = (ImageView)notiView.findViewById(R.id.fbpic);
	    TextView addr_wd = (TextView)notiView.findViewById(R.id.addr_text);

	    fbuname_wd.setText(noti.getName());
	    addr_wd.setText(noti.getAddr());
	    ImageLoader il = new ImageLoader(notiView.getContext());
	    il.DisplayImage(noti.getFbpic(), pic_wd);
	    
	    
	    ListView items = (ListView)notiView.findViewById(R.id.item_list);
	    Item[] ordered_items = noti.getItems().toArray(new Item[noti.getItems().size()]);
	    items.setAdapter(new ItemsOrderedAdapter(notiView.getContext(),ordered_items));
	    
	    
	    
        return notiView;
	}

	
	
	
}
