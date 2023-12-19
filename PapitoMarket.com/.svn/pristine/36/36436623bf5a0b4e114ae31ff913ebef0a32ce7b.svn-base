package com.papitomarket.android.adapter;

import com.papitomarket.android.R;
import com.papitomarket.model.notification.Item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SoldAdapter extends ArrayAdapter<Item>{

	private final Context context;
	private final Item[] values;

	public SoldAdapter(Context context, Item[] objects) {
		super(context,R.layout.sbsold, objects);
		this.context = context;
		this.values = objects;
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  		View rowView = inflater.inflate(R.layout.list_sold, parent, false);
  		TextView soldname = (TextView) rowView.findViewById(R.id.soldname);
  		TextView soldamount = (TextView) rowView.findViewById(R.id.soldamount);
  		TextView soldprice = (TextView) rowView.findViewById(R.id.soldprice);
  		ImageView imageView = (ImageView) rowView.findViewById(R.drawable.cat);
  		soldname.setText(values[position].getName());
  		soldamount.setText(values[position].getAmount());
  		soldprice.setText(values[position].getPrice());
        
		
		return rowView;
		
	}
	
	
	
}
