package com.smartbands.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.papitomarket.android.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class SBPictureLoader extends SBRequest {

	protected ImageView target;
	protected InputStream content;
	public SBPictureLoader(Context c, String name, String parameters, ImageView target) {
		super(c, name, parameters);
		root_url = "http://dev.smartbands.com.ar/images";
		this.target = target;
        
		// TODO Auto-generated constructor stub
	}
	
	
	protected ArrayList<String> doInBackground(String[] p1) {
		  
		URL url;
		ArrayList<String> al = new ArrayList<String>();
		try {
			url = new URL(root_url + parameters.replace("/logos",""));
			content = (InputStream)url.getContent();
			
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			target.setImageResource(R.drawable.defaultlogo);
		}
        return al;
        
	
	
	}

	
	
	@Override
	protected void onPostExecute(ArrayList<String> result) {

		Drawable image = Drawable.createFromStream(content, "src");
        
        
        target.setImageDrawable(image);
		
	}
	
}
