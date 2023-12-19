package com.papitomarket.model.shipping;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.papitomarket.model.database.GlobalData;

public class ShippingAddrsDataSource {
	// Database fields
		  private SQLiteDatabase database;
		  private GlobalData dbHelper;
		  private String[] allColumns = { GlobalData.COLUMN_SHIPPING_ADDRS_ID, GlobalData.COLUMN_SHIPPING_ADDRESS,GlobalData.COLUMN_SHIPPING_ADDRESS_AP};
		  
		  public ShippingAddrsDataSource(Context c){
			  dbHelper = GlobalData.getInstance(c);
			 // if (!database.isOpen()){
			  database = dbHelper.getWritableDatabase();
			 // }
		  }
		  

		  public void open() throws SQLException {
		    
		  }

		  public void close() {
		    dbHelper.close();
		  }

		  public ShippingAddrs createShippingAddrs(ShippingAddrs u) {
			    ContentValues values = new ContentValues();		    
			    
			    values.put(GlobalData.COLUMN_SHIPPING_ADDRESS, u.getAddress());
			    values.put(GlobalData.COLUMN_SHIPPING_ADDRESS_AP,u.getAp());
			    long insertId = database.insert(GlobalData.TABLE_SHIPPING_ADDRS, null,values);
			    Cursor cursor = database.query(GlobalData.TABLE_SHIPPING_ADDRS,allColumns, GlobalData.COLUMN_SHIPPING_ADDRESS + " = " + insertId, null,null, null, null);
			    
			    cursor.moveToFirst();
			    ShippingAddrs newShippingAddrs = cursorToShippingAddrs(cursor);
			    cursor.close();
			    return newShippingAddrs;
		    }

		  public void deleteShippingAddress(ShippingAddrs u) {
		    long id = new Long(u.getId());
		    Log.i("SMARTBANDS","Shipping Address deleted with id: " + id);
		    database.delete(GlobalData.TABLE_SHIPPING_ADDRS, GlobalData.COLUMN_SHIPPING_ADDRS_ID + " = " + id, null);
		  }

		  public List<ShippingAddrs> getAllShippingAddrs() {
		    List<ShippingAddrs> addrs = new ArrayList<ShippingAddrs>();

		    Cursor cursor = database.rawQuery("SELECT * FROM shippingaddrs",null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      ShippingAddrs u = cursorToShippingAddrs(cursor);
		      addrs.add(u);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return addrs;
		  }

		  private ShippingAddrs cursorToShippingAddrs(Cursor cursor) {
			if (cursor.getCount()>0){
		       ShippingAddrs u = new ShippingAddrs(cursor.getString(1),cursor.getString(2));
		      return u;
			} else {
				return null;
			}
		  }

		  public ShippingAddrs findByUrl(String url){
			  String[] strs = {url.replace(" ", "%20").toString()};
			  Cursor cu = database.rawQuery("select id,address,appartment from shippingaddrs where (url = ?)", strs);		  
			  if (cu.getCount()>0){
				  cu.moveToFirst();
				  return (new ShippingAddrs(cu.getString(1),cu.getString(2)));  
			  } else {
				  return null;
			  }
			  
		  }
		
}
