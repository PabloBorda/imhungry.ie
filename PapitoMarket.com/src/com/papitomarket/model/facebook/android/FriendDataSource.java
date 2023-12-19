package com.papitomarket.model.facebook.android;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.papitomarket.model.database.GlobalData;

public class FriendDataSource {

	// Database fields
	  private SQLiteDatabase database;
	  private GlobalData dbHelper;
	  private String[] allColumns = { GlobalData.COLUMN_FRID,
	      GlobalData.COLUMN_FRUSERNAME, GlobalData.COLUMN_FRPIC};
	  
	  public FriendDataSource(Context c){
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

	  public FbFriend createFbFriend(FbFriend f) {
	    ContentValues values = new ContentValues();
	    values.put(GlobalData.COLUMN_FRID, f.getId());
	    values.put(GlobalData.COLUMN_FRUSERNAME, f.getName());
	    values.put(GlobalData.COLUMN_FRPIC, (f.getPicture()).getData().getUrl());
	    long insertId = database.insert(GlobalData.TABLE_FRIENDS, null,values);
	    Cursor cursor = database.query(GlobalData.TABLE_FRIENDS,allColumns, GlobalData.COLUMN_FRID + " = " + insertId, null,null, null, null);
	    cursor.moveToFirst();
	    FbFriend newFbFriend = cursorToFbFriend(cursor);
	    cursor.close();
	    return newFbFriend;
	  }

	  public void deleteFbFriend(FbFriend f) {
	    long id = new Long(f.getId());
	    System.out.println("FbFriend deleted with id: " + id);
	    database.delete(GlobalData.TABLE_FRIENDS, GlobalData.COLUMN_FRID + " = " + id, null);
	  }

	  public List<FbFriend> getAllFbFriends() {
	    List<FbFriend> friends = new ArrayList<FbFriend>();

	    Cursor cursor = database.query(GlobalData.TABLE_FRIENDS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      FbFriend f = cursorToFbFriend(cursor);
	      friends.add(f);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return friends;
	  }

	  private FbFriend cursorToFbFriend(Cursor cursor) {
	    FbFriend f = new FbFriend();
	    f.setId(cursor.getString(0));
	    f.setName(cursor.getString(1));
	    Picture p = new Picture();
	    Data d = new Data();
	    d.setUrl(cursor.getString(2));
	    d.setIs_silhouette(false);
	    p.setPicture(d);
	    f.setPicture(p);
	    return f;
	  }

	
	
	
	
}
