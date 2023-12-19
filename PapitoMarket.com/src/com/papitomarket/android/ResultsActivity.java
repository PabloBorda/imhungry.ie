/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class ResultsActivity extends Activity {
        @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, 
                             WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        
        setContentView(R.layout.results);
    }
}
