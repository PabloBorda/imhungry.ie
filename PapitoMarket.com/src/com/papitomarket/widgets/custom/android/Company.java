/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.widgets.custom.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class Company extends View{
    
    public Company(Context ctx){
        super(ctx);
    }

    @Override
    protected void onDraw (Canvas canvas){
        
        Paint colors = new Paint();
        colors.setColor(Color.YELLOW);
        canvas.drawText("HELLLO WORLD IN CANVAS!", 0, 0,colors);
        canvas.drawLine(0, 0, 20, 20, colors);
        canvas.drawLine(20, 0, 0, 20, colors);
        
        
    }



}





