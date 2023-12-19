package com.papitomarket.model.facebook.android;

import java.net.URL;

import android.view.View;
import android.widget.BaseAdapter;

public class FbFriend {
	
	private String id;
	private String name;
	private String email;
	private Picture picture;
	private BaseAdapter adapter;
	/*private int requestCode;
	
	
	
	
	public BaseAdapter getAdapter() {
		return adapter;
	}



	public int getRequestCode() {
		return requestCode;
	}


	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}*/

	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}




	public void setAdapter(BaseAdapter adapter) {
		this.adapter = adapter;
	}


	
	public String getId() {
		return id;
	}


	public FbFriend(String uid, String name, Picture pic) {
		super();
		this.id = uid;
		this.name = name;
		this.picture = pic;
		//this.requestCode = requestCode;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
		if (adapter != null) {
		    adapter.notifyDataSetChanged();
		}
	}


	public Picture getPicture() {
		return picture;
	}


	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	
	
	public FbFriend(){
		
	}
	
	
    public View.OnClickListener getOnClickListener(){
    	return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do nothing for now
            }
        };
    }
    
    @Override
    public String toString(){
    	return "FbFriends ID: " + this.getId() + " Name: " + this.getName() +  " Pic: " + this.getPicture().getData().getUrl().toString(); 
    }
	
}