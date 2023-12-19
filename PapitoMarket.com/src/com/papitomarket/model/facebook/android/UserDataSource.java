package com.papitomarket.model.facebook.android;

import java.util.ArrayList;
import java.util.List;



import com.papitomarket.model.database.GlobalData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserDataSource {

	// Database fields
	  private SQLiteDatabase database;
	  private GlobalData dbHelper;
	  private String[] allColumns = { GlobalData.COLUMN_ID,
	      GlobalData.COLUMN_USERNAME, GlobalData.COLUMN_NAME, GlobalData.COLUMN_FIRST_NAME,
	      GlobalData.COLUMN_LAST_NAME,GlobalData.COLUMN_LOCALE, GlobalData.COLUMN_LINK,
	      GlobalData.COLUMN_EMAIL,GlobalData.COLUMN_GENDER,GlobalData.COLUMN_BIRTHDAY,
	      GlobalData.COLUMN_ADDR_FK, GlobalData.COLUMN_STATUS};
	  
	  public UserDataSource(Context c){
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

	  public User createUser(User u) {
	    ContentValues values = new ContentValues();
	    values.put(GlobalData.COLUMN_ID, u.getId());
	    values.put(GlobalData.COLUMN_USERNAME, u.getUsername());
	    values.put(GlobalData.COLUMN_NAME, u.getName());
	    values.put(GlobalData.COLUMN_FIRST_NAME, u.getFirst_name());
	    values.put(GlobalData.COLUMN_LAST_NAME, u.getLast_name());
	    values.put(GlobalData.COLUMN_LOCALE, u.getLocale());
	    values.put(GlobalData.COLUMN_LINK, u.getLink());
	    values.put(GlobalData.COLUMN_EMAIL, u.getEmail());
	    values.put(GlobalData.COLUMN_GENDER, u.getGender());
	    values.put(GlobalData.COLUMN_BIRTHDAY, u.getBirthday());
	    //values.put(GlobalData.COLUMN_ADDR_FK, u.getAddr());
	    values.put(GlobalData.COLUMN_STATUS, u.getStatus());
	    long insertId = database.insert(GlobalData.TABLE_USER, null,values);
	    Cursor cursor = database.query(GlobalData.TABLE_USER,allColumns, GlobalData.COLUMN_ID + " = " + insertId, null,null, null, null);
	    cursor.moveToFirst();
	    User newUser = cursorToUser(cursor);
	    cursor.close();
	    return newUser;
	  }

	  public void deleteUser(User u) {
	    long id = new Long(u.getId());
	    System.out.println("User deleted with id: " + id);
	    database.delete(GlobalData.TABLE_USER, GlobalData.COLUMN_ID + " = " + id, null);
	  }

	  public List<User> getAllUsers() {
	    List<User> users = new ArrayList<User>();

	    Cursor cursor = database.query(GlobalData.TABLE_USER,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      User u = cursorToUser(cursor);
	      users.add(u);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return users;
	  }

	  private User cursorToUser(Cursor cursor) {
	    User u = User.getInstance();
	    u.setId(cursor.getString(0));
	    u.setUsername(cursor.getString(1));
	    u.setName(cursor.getString(2));
	    u.setFirst_name(cursor.getString(3));
	    u.setLast_name(cursor.getString(4));	    
	    u.setLocale(cursor.getString(5));
	    u.setLink(cursor.getString(6));
	    u.setEmail(cursor.getString(7));
	    u.setGender(cursor.getString(8));
	    u.setBirthday(cursor.getString(9));
	    u.setLat(cursor.getString(10));
	    u.setLng(cursor.getString(11));
	    //u.setAddr(cursor.getString(12));
	    //u.setStatus(cursor.getString(13));
	    
	    return u;
	  }

	
	
	
	
	
	
	
}
