package com.smartbands.http;

import java.util.ArrayList;

import com.papitomarket.util.Util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SBRequest extends AsyncTask<String, Void, ArrayList<String>>{
    protected String name;
	protected String parameters;
	protected ArrayList<SBRequest> dependencies;
	protected ArrayList<String> responses;
	protected String type;
	protected static String root_url;
	protected boolean finished;
	protected Context ctx;
	
		
	
	protected static String getBase_url(){
    	return "";
    }

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SBRequest(Context c,String name,String parameters){
	  super();
	  root_url = "http://192.241.140.67:9494";
	  this.parameters = parameters;
	  this.responses = new ArrayList<String>();
	  finished = false;
	  this.ctx = c;
	  this.dependencies = new ArrayList<SBRequest>();
	}
	
	@Override
	protected ArrayList<String> doInBackground(String... params) {
		// TODO Auto-generated method stub
		Log.i("smartbands", "Visiting URL " + root_url + parameters);
		String op = Util.get_own_encoder((root_url + parameters.replace(" ","%20")),this.ctx);
		if (!op.equals("error")){
		  responses.add(op);
		
		  for (int i=0;i<dependencies.size();i++){
		    SBRequest current = (SBRequest)dependencies.get(i);
		    current.execute(current.getParameters());
	  	  }
		  publishProgress();
		}
		
		return responses;
		
	}
	
	
	@Override	
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}


    public boolean finished(){
      return finished;	
    }
    
	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	
	@Override
	protected void onPostExecute(ArrayList<String> result) {
		super.onPostExecute(result);
		// TODO Auto-generated method stub
		
	}
	
	public void addDependency(SBRequest r){
		this.dependencies.add(r);
	}
    
    
	 
	
	
}
