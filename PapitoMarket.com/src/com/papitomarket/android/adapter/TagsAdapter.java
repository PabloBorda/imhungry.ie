package com.papitomarket.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.papitomarket.android.R;
import com.papitomarket.model.products.android.Product;
import com.papitomarket.model.tags.android.Tag;
import com.papitomarket.model.tags.android.Tags;

public class TagsAdapter extends android.widget.ArrayAdapter<Tag> {

	private Context c;
	private Tag[] tags;
	  
	public TagsAdapter(Context context, Tag[] tags) {
		super(context, R.layout.tagrow,tags);
		this.c = context;
		this.tags = tags;
		// TODO Auto-generated constructor stub
	}
    
	public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View rowView = inflater.inflate(R.layout.tagrow, parent, false);
      TextView tagname = (TextView)rowView.findViewById(R.id.tagname);
      
      tagname.setText(tags[position].getLabel());
      
      TextView tagrank = (TextView)rowView.findViewById(R.id.tagrank);
      tagrank.setText("rank: " + tags[position].get_rank().toString());
      return rowView; 
	}
	
	
}
