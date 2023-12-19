/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.model.stores.android;

import com.papitomarket.model.products.android.Category;
import com.papitomarket.model.products.android.Product;
import java.util.ArrayList;

/**
 * 
 * @author Pablo Tomas Borda Di Berardino
 */
public class Store {
	
	
	private Context context;
	private ArrayList<Category> categories;
	private Info info;
	
	
	public Store(Context context, ArrayList<Category> categories, Info info) {
		super();
		this.context = context;
		this.categories = categories;
		this.info = info;
	}
	
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public ArrayList<Category> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}


	
	

}
