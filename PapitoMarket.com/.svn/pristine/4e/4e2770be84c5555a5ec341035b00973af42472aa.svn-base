package com.papitomarket.android;

import java.util.List;

import com.papitomarket.android.adapter.PrevOrdersAdapter;
import com.papitomarket.model.cart.PreviousOrderDataSource;
import com.papitomarket.model.cart.PreviousOrders;

import android.app.ListActivity;
import android.os.Bundle;

public class PreviousOrdersActivity extends ListActivity {
	
	public static PreviousOrdersActivity instance;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
				
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		PreviousOrdersActivity.instance = this;
		setContentView(R.layout.previousorders);
		
		PreviousOrderDataSource pods = new PreviousOrderDataSource(this);
		List<PreviousOrders> orders  = pods.getAllPreviousOrders();
		
		setListAdapter(new PrevOrdersAdapter(orders,this));
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
