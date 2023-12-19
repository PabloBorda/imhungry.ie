package com.papitomarket.contacts.android;


import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.papitomarket.android.R;
import com.papitomarket.model.facebook.android.FbFriend;

public class ContactAdapter extends ArrayAdapter<FbFriend> {

	private final List<FbFriend> friends;
	private final Context context;
	
	
	public ContactAdapter(Activity context, List<FbFriend> myFriends)
	{
		super(context,R.layout.contactlistitem,myFriends);
		this.friends= myFriends;
		this.context=context;
	}
	
	
	@Override
	public FbFriend getItem(int position)
	{
		return friends.get(position);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view=null;
	
		return view;
	}
}
