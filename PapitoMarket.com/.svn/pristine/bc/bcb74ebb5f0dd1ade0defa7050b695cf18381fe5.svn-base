package com.papitomarket.android.adapter;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import com.papitomarket.android.R;
import com.papitomarket.model.stores.android.Store;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AddrArrayAdapter extends ArrayAdapter<String> {

	private final Context context;
	private final String[] values;
	
	public AddrArrayAdapter(Context context,String[] values) {
		super(context,0,values);
		this.context = context;
		this.values = values;
	}
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_addr, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.addrlabel);
		textView.setText(values[position].toString());

		// Change icon based on name
		String s = values[position].toString();

		System.out.println(s);

		return rowView;

		
		
	}

}
