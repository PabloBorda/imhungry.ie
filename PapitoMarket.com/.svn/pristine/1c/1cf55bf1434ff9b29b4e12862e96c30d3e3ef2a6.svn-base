package com.smartbands.http;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.papitomarket.android.R;
import com.papitomarket.android.SearchActivity;
import com.papitomarket.android.adapter.TagsAdapter;
import com.papitomarket.global.GlobalData;
import com.papitomarket.model.tags.android.Tag;

public class SBSearchTagRequest extends SBRequest {

	private boolean first = false;
    private static int retry = 0;
	private Tag[] tags_arr = null;

    
    protected static String getBase_url(){
    	return "/searchtags?term=";
    }
    
    
    
    public SBSearchTagRequest(Context c, String name, String parameters) {
		super(c, name,getBase_url() + parameters);
		// TODO Auto-generated constructor stub
	}

	protected ArrayList<String> doInBackground(String[] p1) {
		super.doInBackground(p1);
	    publishProgress();
		ArrayList<String> al = new ArrayList<String>();
		
		if (responses.size() > 0){
			if (!p1.equals("[]")){
		      Gson gson = new Gson();
		      Type collectionTags = new TypeToken<Collection<Tag>>() {}.getType();
		      Collection<Tag> tags = gson.fromJson(responses.get(0).toString(),collectionTags);
              if (tags!=null){
		        Tag[] tags_array = tags.toArray(new Tag[tags.size()]);
		        String tmp = "";
		        for (int i = 0; i < tags_array.length; i++) {
		    	  tmp = tmp + tags_array[i].getLabel() + ",";
		        }
		        al.add(tmp.substring(0, tmp.length()));
		        this.tags_arr = tags_array;
              } else {
            	  return (new ArrayList<String>());
              }
			}
		}
		return al;
	}

	@Override
	protected void onPostExecute(ArrayList<String> result) {
		if (result != null){
			super.onPostExecute(result);
        	EditText textView = (EditText) ((Activity) this.ctx).findViewById(R.id.search_text_edit);

			
              if ((result.size()>0)){

            	TagsAdapter ad = new TagsAdapter(this.ctx, this.tags_arr);
		        
		        
		        if (SearchActivity.adapters.containsKey(textView.getText().toString())){
		          SearchActivity.adapters.remove(textView.getText().toString());
		        }
		        SearchActivity.adapters.put(textView.getText().toString(),ad);
		        ad = SearchActivity.adapters.get(textView.getText().toString());
	            if (ad!=null){
	            	//if (SearchActivity.dd==0){
		            //  textView.setAdapter(ad);		            
		            //  textView.showDropDown();
		   		    //ad.notifyDataSetChanged();
          		  if (GlobalData.name!="ShowStoreActivity"){
	                ((SearchActivity) this.ctx).setListAdapter(SearchActivity.adapters.get(textView.getText().toString()));
		      	    Log.i("smartbands","Show dropdown"); 
          		  }
	            	//}
		        }
              } else {
            	  if (retry <= 5){
                    SBSearchTagRequest tr = new SBSearchTagRequest(SearchActivity.instance,"tag",textView.getText().toString());
                    tr.execute("");  
                    retry = retry + 1;
            	  }

              }
			
		}
        publishProgress();
		//this.cancel(true);
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
		ProgressBar pb = (ProgressBar)((Activity) this.ctx).findViewById(R.id.progresscircle);
		if (pb.getVisibility()==View.GONE) {
			pb.setVisibility(View.VISIBLE);
		} else {
			pb.setVisibility(View.GONE);
	    }
		
		// Refresh list adapter
		EditText textView = (EditText) ((Activity) this.ctx).findViewById(R.id.search_text_edit);
		if (SearchActivity.adapters.containsKey(textView.getText().toString())){
		  SearchActivity.instance.setListAdapter(SearchActivity.adapters.get(textView.getText().toString()));
		}
	}

}
