/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.papitomarket.android.adapter.CategoriesAdapter;
import com.papitomarket.global.GlobalData;
import com.papitomarket.model.products.android.Category;
import com.papitomarket.model.products.android.Product;
import com.papitomarket.model.stores.android.Store;

import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class ProductActivity extends Activity{
 
    public static ProductActivity instance;
    public static Product p;
    public static Store s;
    public static Category c;
    boolean click = true;
    LinearLayout layout;
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ProductActivity.instance = this;
        setContentView(R.layout.product);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, 	
                             WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        
        TextView name = (TextView)findViewById(R.id.productname);
        TextView price = (TextView)findViewById(R.id.price);
        TextView description = (TextView)findViewById(R.id.description);
        ImageView pic = (ImageView)findViewById(R.id.productpic);
        TextView seller = (TextView)findViewById(R.id.productseller);
        ImageView companypic = (ImageView)findViewById(R.id.companylogo);
        TextView stock = (TextView)findViewById(R.id.stock);
        ImageButton order = (ImageButton)findViewById(R.id.orderbutton);
       
        ProductActivity.p = (GlobalData.stores.getStores().get(GlobalData.selected_store)).getCategories().get(GlobalData.selected_category).getProducts().get(GlobalData.selected_product);
        
        
        name.setText(p.getName());
        price.setText(p.getPrice() + " $");
        description.setText(p.getDescription());
        seller.setText(GlobalData.selectedCompany);
        stock.setText("10 of 34 were sold.");
        

        order.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
              AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.instance);
              // Get the layout inflater
              LayoutInflater inflater = ((Activity)getBaseContext()).getLayoutInflater();

              // Inflate and set the layout for the dialog
              // Pass null as the parent view because its going in the dialog layout
              
              builder.setView(inflater.inflate(R.layout.dialog_signin, null))
              // Add action buttons
              .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int id) {
                   // sign in the user ...
                 }
              })
              .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int id) {
                   
                 }
              });
            }
        });

        if (p.getPics().size()>0){
          InputStream content = null;       
          try {
            String addr = "http://dev.papitomarket.com/images/" +  s.getInfo().getCompanyname().replaceAll(" ","") + "/products/" + p.getId().toString() + p.getPics().get(0).toString();
            //String addr = "http://maps.googleapis.com/maps/api/staticmap?center=" + store.lat.toString() + "," + store.lng.toString() + "&markers=-34.603571,-58.417013&zoom=14&size=500x106&markers=color:red%7Ccolor:red%7Clabel:C%7C40.718217,-73.998284&sensor=false";
            Log.i("papitomarket", addr); 
            URL url = new URL(addr);
            content = (InputStream)url.getContent();
            Drawable image = Drawable.createFromStream(content, "src");
            pic.setImageDrawable(image);
          }
          catch(Exception ex)
          {
            ex.printStackTrace();
          }   
       }
    
    
          InputStream content = null;       
          try {
            String addr = "http://dev.papitomarket.com/images/" +  s.getInfo().getCompanyname().replaceAll(" ","") + "/" + s.getInfo().getCompanylogo();
            //String addr = "http://maps.googleapis.com/maps/api/staticmap?center=" + store.lat.toString() + "," + store.lng.toString() + "&markers=-34.603571,-58.417013&zoom=14&size=500x106&markers=color:red%7Ccolor:red%7Clabel:C%7C40.718217,-73.998284&sensor=false";
            Log.i("papitomarket", addr); 
            URL url = new URL(addr);
            content = (InputStream)url.getContent();
            Drawable image = Drawable.createFromStream(content, "src");
            companypic.setImageDrawable(image);
          }
          catch(Exception ex)
          {
            ex.printStackTrace();
          }   
    
    
    
    
    
    
    
    }
    
}
