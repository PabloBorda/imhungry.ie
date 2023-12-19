package com.papitomarket.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.papitomarket.android.R;
import com.papitomarket.android.SearchActivity;
import com.papitomarket.model.stores.android.Store;
import com.papitomarket.model.stores.android.Stores;
import com.papitomarket.util.ImageLoader;

public class MobileArrayAdapter extends ArrayAdapter<Store> {
	private final Context context;
	private final Stores values;
	public ImageLoader imageLoader; 
	
	
	static class MobileArrayAdapterHolder { 
		
		private TextView textView; 
		private ImageView imageView;
		
	}
	
	
	
	
	
	
	
	public MobileArrayAdapter(Context context, Stores values) {
		super(context, R.layout.search,values.getStores());
		this.context = context;
		this.values = values;
		imageLoader=new ImageLoader(SearchActivity.instance);
	} 

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		 View rowView = convertView;
		    if (rowView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			  rowView = inflater.inflate(R.layout.list_mobile, parent, false);
		      MobileArrayAdapterHolder viewHolder = new MobileArrayAdapterHolder();
		      viewHolder.textView = (TextView) rowView.findViewById(R.id.label);
		      viewHolder.imageView = (ImageView) rowView
		          .findViewById(R.id.logo);
		      rowView.setTag(viewHolder);
		    }

		    MobileArrayAdapterHolder holder = (MobileArrayAdapterHolder) rowView.getTag();
		    
		    String s = values.getStores().get(position).getInfo().getCompanyname();
		    
		    
		    holder.textView.setText(s);
			String addr = "http://dev.smartbands.com.ar/images" + values.getStores().get(position).getInfo().getCompanylogo().replace("logos","");

			imageLoader.DisplayImage(addr,holder.imageView);

		    return rowView;
		
	}
        
     
}
