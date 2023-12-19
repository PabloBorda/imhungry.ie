package com.papitomarket.android.adapter;

import java.io.InputStream;
import java.net.URL;

import com.papitomarket.android.R;
import com.papitomarket.model.stores.android.Store;
import com.papitomarket.model.tags.android.Tag;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchTagsAdapter extends ArrayAdapter<Tag>{

	
	
	private Context context;
	private Tag[] values;
	
	
	public SearchTagsAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.context = context;
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_mobile, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		//ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position].getLabel());

		// Change icon based on name
		

        //Drawable image = Drawable.createFromPath(pathName);
        //imageView.setImageDrawable(image);
		return rowView;
	}

	
	
	
	
	

}
