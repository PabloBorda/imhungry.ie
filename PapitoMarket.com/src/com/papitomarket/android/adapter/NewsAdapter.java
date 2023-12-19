package com.papitomarket.android.adapter;

import java.util.ArrayList;
import java.util.Stack;

import com.papitomarket.android.R;
import com.papitomarket.global.GlobalData;
import com.papitomarket.notifications.android.SBAnnouncementNotification;
import com.papitomarket.notifications.android.SBNotification;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

public class NewsAdapter extends BaseAdapter {
	
	
	
	
	public NewsAdapter(Context ctx){
		NewsAdapter.ctx = ctx;
	}
	


	
	private static Context ctx;	
	
	
	

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		if (position < GlobalData.news.size()){
		  SBNotification current = GlobalData.news.get(position); 
		  return current.getView(ctx, parent);
		} else {
		  return (new View(ctx));
		}			
    }


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return GlobalData.news.size();
	}


	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return GlobalData.news.get(arg0);
	}


	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return ((SBNotification)this.getItem(arg0)).getId();
	}
	
	
}
