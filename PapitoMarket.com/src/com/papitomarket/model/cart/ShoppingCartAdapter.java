package com.papitomarket.model.cart;

import java.sql.Array;
import java.util.ArrayList;

import com.papitomarket.android.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ShoppingCartAdapter extends ArrayAdapter<Order>{
 
	private Context ctx;
	private Order[] results;
	public String address = "";
	
	public ShoppingCartAdapter(Context context, Order[] orders) {
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
		  prodnam.setText(results[position].getP().getName());
		  TextView pri = (TextView)rowView.findViewById(R.id.pri);
		  pri.setText("Price: " + results[position].getP().getPrice());
		  TextView amounttext = (TextView)rowView.findViewById(R.id.amounttext);
		  amounttext.setText("Amount: " + results[position].getAmount());
		  return rowView;
	}
	
}
