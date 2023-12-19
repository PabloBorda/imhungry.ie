/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.papitomarket.android.R;
import com.papitomarket.model.products.android.Category;

/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class CategoriesAdapter extends ArrayAdapter<Category> {
    
    
    private final Context context;
    private final Category[] values;
    
    
    public CategoriesAdapter(Context context, Category[] values){
        super(context,R.layout.showstore,values);
        this.context = context;
        this.values = values;
    }
            
    	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_categories, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.categoryname);
		ImageView imageView = (ImageView) rowView.findViewById(R.drawable.cat);
		textView.setText(values[position].getName());
        
                return rowView;
                
                
        }
    
    
}
