package com.papitomarket.model.cart;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.papitomarket.model.database.GlobalData;

public class PreviousOrderDataSource {

	// Database fields
	  private SQLiteDatabase database;
	  private GlobalData dbHelper;
	  private String[] allColumns = { GlobalData.COLUMN_PREVIOUS_ORDERS_ID,
	      GlobalData.COLUMN_PREVIOUS_ORDERS_JSON };
	  
	  public PreviousOrderDataSource(Context c){
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

	  public PreviousOrders createPreviousOrders(PreviousOrders u) {
	    ContentValues values = new ContentValues();	    
	    values.put(GlobalData.COLUMN_PREVIOUS_ORDERS_JSON,u.getJson());
	    long insertId = database.insert(GlobalData.TABLE_PREVIOUS_ORDERS, null,values);
	    Cursor cursor = database.query(GlobalData.TABLE_PREVIOUS_ORDERS,allColumns, GlobalData.COLUMN_PREVIOUS_ORDERS_ID + " = " + insertId, null,null, null, null);
	    cursor.moveToFirst();
	    PreviousOrders newPreviousOrders = cursorToPreviousOrders(cursor);
	    cursor.close();
	    return newPreviousOrders;
	  }

	  public void deletePreviousOders(PreviousOrders u) {
	    long id = Long.valueOf(u.getOrder_id());
	    System.out.println("Previous Orders deleted with id: " + id);
	    database.delete(GlobalData.TABLE_PREVIOUS_ORDERS, GlobalData.COLUMN_ID + " = " + id, null);
	  }

	  public List<PreviousOrders> getAllPreviousOrders() {
	    List<PreviousOrders> orders = new ArrayList<PreviousOrders>();

	    Cursor cursor = database.query(GlobalData.TABLE_PREVIOUS_ORDERS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      PreviousOrders u = cursorToPreviousOrders(cursor);
	      orders.add(u);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return orders;
	  }

	  private PreviousOrders cursorToPreviousOrders(Cursor cursor) {
	    PreviousOrders u = new PreviousOrders(cursor.getString(1));
	    return u;
	  }

}
