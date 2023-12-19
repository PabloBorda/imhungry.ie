package com.papitomarket.android.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.papitomarket.android.InviteFriendsActivity;
import com.papitomarket.android.R;
import com.papitomarket.android.SearchActivity;
import com.papitomarket.android.ShowStoreActivity;
import com.papitomarket.model.facebook.android.FbFriend;
import com.papitomarket.util.Util;

public class ActionListAdapter extends ArrayAdapter<FbFriend> {
    private List<FbFriend> listElements;

    public ActionListAdapter(Context context, int resourceId, 
                             List<FbFriend> listElements) {
        super(context, resourceId, listElements);
        this.listElements = listElements;
        // Set up as an observer for list item changes to
        // refresh the view.
        for (int i = 0; i < listElements.size(); i++) {
            listElements.get(i).setAdapter(this);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) InviteFriendsActivity.instance
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.contactlistitem, null);
        }

        FbFriend listElement = listElements.get(position);
        if (listElement != null) {
            view.setOnClickListener(listElement.getOnClickListener());
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            CheckBox text1 = (CheckBox) view.findViewById(R.id.friendname);
           
            if ((text1 != null) && (icon != null)){
            	text1.setChecked(true);
                text1.setText(listElement.getName());
                text1.setId(Integer.valueOf(listElement.getId()));
                icon.setImageBitmap(Util.getBitmapFromURL(listElement.getPicture().getData().getUrl(),SearchActivity.instance));
            }
            
            
        }
        Log.i("SMARTBANDS","Position: " + (new Integer(position)).toString() + " " + listElement.toString());
        return view;
    }

}