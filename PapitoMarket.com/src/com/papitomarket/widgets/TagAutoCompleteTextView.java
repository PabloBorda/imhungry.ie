package com.papitomarket.widgets;

import android.widget.AutoCompleteTextView;
import android.content.*;
import android.util.*;



public class TagAutoCompleteTextView extends AutoCompleteTextView
{
    public TagAutoCompleteTextView(Context context) {  
        super(context);  
        // TODO Auto-generated constructor stub  
    }  

    public TagAutoCompleteTextView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        // TODO Auto-generated constructor stub  
    }  

    public TagAutoCompleteTextView(Context context, AttributeSet attrs,  
								  int defStyle) {  
        super(context, attrs, defStyle);  
        // TODO Auto-generated constructor stub  
    }  

    @Override  
    protected void performFiltering(final CharSequence text, final int keyCode) {  
        String filterText = "";  
        super.performFiltering(filterText, keyCode);  
    }  
    /** 
	 * After a selection, capture the new value and append to the existing 
	 * text 
	 */  
    @Override  
    protected void replaceText(final CharSequence text) {  
        super.replaceText(text);  
    }
}
