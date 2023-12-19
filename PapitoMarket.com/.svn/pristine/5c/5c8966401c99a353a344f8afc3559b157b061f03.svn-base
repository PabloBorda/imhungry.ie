/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.android;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.papitomarket.android.adapter.CategoriesAdapter;
import com.papitomarket.android.adapter.ProductsAdapter;
import com.papitomarket.global.GlobalData;
import com.papitomarket.model.cart.Order;
import com.papitomarket.model.cart.ShoppingCartAdapter;
import com.papitomarket.model.products.android.Category;
import com.papitomarket.model.products.android.Product;
import com.papitomarket.model.stores.android.Store;
import com.papitomarket.model.stores.android.Stores;
import com.papitomarket.util.ImageLoader;
import com.papitomarket.widgets.custom.android.Company;
import com.smartbands.widgets.slidingmenu.lib.SlidingMenu;

/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class ShowStoreActivity extends ListActivity {

    
    public static Store store;
    private static int cat;
    public static ShowStoreActivity instance;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        GlobalData.name = "ShowStoreActivity";
        
        ShowStoreActivity.instance = this;
        ShowStoreActivity.store = GlobalData.stores.getStores().get(GlobalData.selected_store);
        
        super.onCreate(icicle);
        
        
        
        Company c = new Company(this);
        setContentView(R.layout.showstore);
        
        
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, 
                             WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        

        ImageButton cartBtn = (ImageButton)findViewById(R.id.cartBtn);
        cartBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			        SlidingMenu menu = new SlidingMenu(ShowStoreActivity.instance);
			        menu.setMode(SlidingMenu.RIGHT);
			        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			        menu.setShadowWidthRes(R.dimen.shadow_width);
			        menu.setShadowDrawable(R.drawable.shadow);
			        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
			        menu.setFadeDegree(0.35f);
			        menu.attachToActivity(ShowStoreActivity.instance, SlidingMenu.SLIDING_CONTENT);
			        //menu.setMenu(R.layout.shoppingcart);
			        //menu.setContent(R.layout.shoppingcart);
		
			}
		});
        
        InputStream content = null;
        
        ImageView imageView = (ImageView)findViewById(R.id.logo);
        try {
           String addr = "http://dev.smartbands.com.ar/images/" + store.getInfo().getCompanylogo().replace("/logos/","");
           //String addr = "http://maps.googleapis.com/maps/api/staticmap?center=" + store.lat.toString() + "," + store.lng.toString() + "&markers=-34.603571,-58.417013&zoom=14&size=500x106&markers=color:red%7Ccolor:red%7Clabel:C%7C40.718217,-73.998284&sensor=false";
           ImageLoader il = new ImageLoader(this);
           il.DisplayImage(addr, imageView);
           
         }
         catch(Exception ex)
         {
           imageView.setImageResource(R.drawable.defaultlogo);
           //ex.printStackTrace();
         }
        // ToDo add your GUI initialization code here        
        ImageView mappic = (ImageView)findViewById(R.id.map);
        try {
           //String addr = "http://dev.papitomarket.com/images/" +  store.companyname.replaceAll(" ","") + "/" + store.companylogo;
           String addr = "http://maps.googleapis.com/maps/api/staticmap?center=" + store.getInfo().getLat().toString() + "," + store.getInfo().getLng().toString() + "&markers=" + store.getInfo().getLat().toString() + "," + store.getInfo().getLng().toString() + "&zoom=15&size=500x106&markers=color:red%7Ccolor:red%7Clabel:C%7C40.718217,-73.998284&sensor=false";
           ImageLoader il = new ImageLoader(this);
           il.DisplayImage(addr, mappic);
         }
         catch(Exception ex)
         {
           ex.printStackTrace();
         }
        
         TextView company = (TextView)findViewById(R.id.companyname);
         company.setText(store.getInfo().getCompanyname());
         
         TextView companyDescription = (TextView)findViewById(R.id.companydesciption);
         companyDescription.setText(store.getInfo().getWebpage());
        
         
         
     	 Button addToCartBtn = (Button)findViewById(R.id.addtocartbtn);
     	 addToCartBtn.setVisibility(View.GONE);
     	 addToCartBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Iterator<Spinner> it = ProductsAdapter.spins.iterator();
				int i=0;
				
				while (it.hasNext()){
					
					int amount = Integer.parseInt((String)((Spinner)it.next()).getSelectedItem());
					if (amount>0){
					  Order o = new Order(ProductsAdapter.products[i], amount, true);
					  if (GlobalData.orders==null){
						  GlobalData.orders = new ArrayList<Order>();
					  }
					  GlobalData.orders.add(o);
					}
					i=i+1;
					
				}
				Intent shopCartActIntent = new Intent(getApplicationContext(),ShoppingCartActivity.class);
				startActivity(shopCartActIntent);
		
			}
		});
     	 
     	 
         
         ArrayList<Category> tmp = store.getCategories();//sortAccordingSearchTag();         
         setListAdapter(new CategoriesAdapter(getBaseContext(),tmp.toArray(new Category[tmp.size()])));
        
    }
    
    /*
    private ArrayList<Category> sortAccordingSearchTag(){
    	ArrayList<Category> tmp = store.getCategories();
    	String selected_tag = GlobalData.selected_tag;
    	int source = 0;
    	int target;
    	Category tmpcat=null;
    	int i=0;
    	Stores s = SearchActivity.SEARCH_RESULTS;
    	
    	
    	Iterator<Store> sit = s.getStores().iterator();
    	while (sit.hasNext()){
    	  Store currentStore = (Store)sit.next();
    	  Iterator<Category> icat = currentStore.getCategories().iterator();
    	  while (icat.hasNext()){
    		Category currcat = (Category)icat.next();
    		Iterator<Product> itprods = currcat.getProducts().iterator();
    			  
    				
    	  }
    	}
    		
    		
    	
    	
    	while ((i<tmp.size()) && (tmp.get(i).getName().toLowerCase()!=selected_tag.toLowerCase())){
    		i = i + 1;
    	}
    	
		if ((i<tmp.size())){
			target = i;
			tmpcat = tmp.get(i);
			tmp.set(i,tmp.get(0));
			tmp.set(0,tmpcat);
		}
   
    	return tmp;
    }
    */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

    	
        if ("ShowStoreActivity".equals(GlobalData.name)){
          GlobalData.selected_category = position;
     	  Button addToCartBtn = (Button)findViewById(R.id.addtocartbtn);
     	  addToCartBtn.setVisibility(View.VISIBLE);
          ArrayList<Product> products = store.getCategories().get(GlobalData.selected_category).getProducts();          
          setListAdapter(new ProductsAdapter(this,products.toArray(new Product[products.size()])));
          GlobalData.name = "ShowProductsCategory";
        } else {
            if ("ShowProductsCategory".equals(GlobalData.name)){
              Intent viewProduct = new Intent(getApplicationContext(), ProductActivity.class);
              GlobalData.name = "ShowingProduct";
              GlobalData.selected_product = position;
              startActivity(viewProduct);
             
            }
        }
        
    }

    
    
    
    
}
