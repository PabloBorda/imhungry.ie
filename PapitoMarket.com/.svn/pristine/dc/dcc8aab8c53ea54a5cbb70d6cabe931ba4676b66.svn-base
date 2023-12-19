/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;

import com.papitomarket.android.R;
import com.papitomarket.model.cache.Cache;
import com.papitomarket.model.cache.CacheDataSource;
import com.papitomarket.model.facebook.android.User;

/**
 *
 * @author Pablo Tomas Borda Di Berardino
 */
public class Util {

	
	// convert from UTF-8 -> internal Java String format
    public static String convertFromUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
 
    // convert from internal Java String format -> UTF-8
    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
 
	
	
	
	
	
	public static void CopyStream(InputStream is, OutputStream os)
	    {
	        final int buffer_size=1024;
	        try
	        {
	            byte[] bytes=new byte[buffer_size];
	            for(;;)
	            {
	              int count=is.read(bytes, 0, buffer_size);
	              if(count==-1)
	                  break;
	              os.write(bytes, 0, count);
	            }
	        }
	        catch(Exception ex){}
	    }
	
//	public static String filter_shit_from_string(String to_filter){
//		return to_filter;
//		/*
//		
//		Hashtable<String,String> latin_to_utf8 = new Hashtable<String, String>();
//		latin_to_utf8.put("�","a");
//		latin_to_utf8.put("�","e");
//		latin_to_utf8.put("�","i");
//		latin_to_utf8.put("�","o");
//		latin_to_utf8.put("�","u");
//		latin_to_utf8.put("�","A");
//		latin_to_utf8.put("�","E");
//		latin_to_utf8.put("�","I");
//		latin_to_utf8.put("�","O");
//		latin_to_utf8.put("�","U");
//		latin_to_utf8.put("�","n");
//		latin_to_utf8.put("�","N");
//		latin_to_utf8.put("�","A");
//		latin_to_utf8.put("�","A");
//		latin_to_utf8.put("�","A");
//		latin_to_utf8.put("�","A");
//		latin_to_utf8.put("�","A");
//		
//		
//		   
//		latin_to_utf8.put("�","E");
//		latin_to_utf8.put("�","E");
//		latin_to_utf8.put("�","E");
//		latin_to_utf8.put("�","E");
//		
//		   
//		latin_to_utf8.put("�","I");
//		latin_to_utf8.put("�","I");
//		latin_to_utf8.put("�","I");
//		latin_to_utf8.put("�","I");
//		
//		
//		
//
//		latin_to_utf8.put("�","O");
//		latin_to_utf8.put("�","O");
//		latin_to_utf8.put("�","O");
//		latin_to_utf8.put("�","O");
//		latin_to_utf8.put("�","O");
//		latin_to_utf8.put("�","D");
//
//		      
//		latin_to_utf8.put("�","p");
//		latin_to_utf8.put("�","Y");
//		latin_to_utf8.put("�","U");
//		latin_to_utf8.put("�","U");
//		latin_to_utf8.put("�","U");
//		latin_to_utf8.put("�","U");
//		latin_to_utf8.put("�","0");
//		
//		     
//		latin_to_utf8.put("�","o");
//		latin_to_utf8.put("�","o");
//		latin_to_utf8.put("�","o");
//		latin_to_utf8.put("�","o");
//		latin_to_utf8.put("�","o");
//		latin_to_utf8.put("�","o");
//		latin_to_utf8.put("�","c");
//		latin_to_utf8.put("�","ae");
//		latin_to_utf8.put("�","e");
//		latin_to_utf8.put("�","e");
//		
//		latin_to_utf8.put("�","a");
//		latin_to_utf8.put("�","a");
//		latin_to_utf8.put("�","a");
//		latin_to_utf8.put("�","a");
//		latin_to_utf8.put("�","a");
//		latin_to_utf8.put("�","e");
//		latin_to_utf8.put("�","i");
//		latin_to_utf8.put("�","i");
//		latin_to_utf8.put("�","i");
//		latin_to_utf8.put("�","i");
//		
//		latin_to_utf8.put("�","B");
//		latin_to_utf8.put("�","y");
//		latin_to_utf8.put("�","C");
//
//	      
//		latin_to_utf8.put("�","0");
//		latin_to_utf8.put("�","E");
//		latin_to_utf8.put("�","E");
//		latin_to_utf8.put("�","E");
//		latin_to_utf8.put("�","E");
//		latin_to_utf8.put("�","p");
//		
//		
//		latin_to_utf8.put("\u00fa","u");
//		
//		latin_to_utf8.put("�","p");
//		latin_to_utf8.put("�","p");
//		latin_to_utf8.put("�","p");
//		latin_to_utf8.put("�","p");
//		latin_to_utf8.put("�","p");
//		latin_to_utf8.put("�","p");
//		latin_to_utf8.put("�","p");
//		latin_to_utf8.put("�","p");
//		latin_to_utf8.put("�","p");
//		latin_to_utf8.put("�","p");
//		latin_to_utf8.put("�","p");
//		
//		
//		StringBuffer out = new StringBuffer(); 
//		for (int i=0;i<to_filter.length();i++){
//			if (latin_to_utf8.containsKey(to_filter.charAt(i) + "")){
//			  out.append(latin_to_utf8.get(to_filter.charAt(i) + ""));	
//			} else {
//			  out.append(to_filter.charAt(i));				
//			}			
//		}
//		
//		return out.toString();
//		
//		*/
//		
//	}
	
	
	    public static String convertBitmapToString(Bitmap src) 
	    { 
	    ByteArrayOutputStream os=new ByteArrayOutputStream(); 
	    src.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, 
	(OutputStream) os); 
	    return os.toString(); 
	    } 

	    public static Bitmap getBitMapFromString(String src) 
	    { 
	            Log.i("b=",""+src.getBytes().length);//returns 12111 as a length. 
	    return BitmapFactory.decodeByteArray(src.getBytes(),0,src.getBytes 
	().length); 
	    } 

	 
	   
	   
	   
	   
	   
	   
	   
	   
	   
	public static Bitmap getBitmapFromURL(String src,Context ctx) {
	    Cache content = Util.retrieveFromCache(src, ctx);
		if (content==null){
		try {
	    	
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        
	        CacheDataSource cds = new CacheDataSource(ctx);
            cds.createCache(new Cache(0,(src.replace(" ","%20").toString()),convertBitmapToString(myBitmap)));
	        
	        
	        
	        //CacheDataSource cds = new CacheDataSource(ctx);
	       // byte[] buffer;
	       // Cqche u = new Cache(0,src,(new String(input.)));
	       // cds.createCache(u);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	  } else {
		  byte[] bytes = content.getCache().getBytes();		  
	      return getBitMapFromString(bytes.toString());
	  }
	}
	
	
	
	
	
	private static Cache retrieveFromCache(String url,Context ctx){
		CacheDataSource cds = new CacheDataSource(ctx);
		Cache c = cds.findByUrl(url);
		return c;
	}
	
	
	
	
	
	
	public static String get_own_encoder(String url,Context ctx){
        
          Cache c = (Util.retrieveFromCache(url.replace(" ","%20"),ctx));
          if (c==null){
        	try{        	  
        	  HttpClient httpclient = new DefaultHttpClient();          
              HttpResponse response = httpclient.execute(new HttpGet(url.replace(" ","%20")));
          
              StatusLine statusLine = response.getStatusLine();
              if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                 response.getEntity().writeTo(out);
                out.close();
                CacheDataSource cds = new CacheDataSource(ctx);
                cds.createCache(new Cache(0,(url.replace(" ","%20").toString()),out.toString()));                
                Log.i("SMARTBANDS","Create cache element");
                return out.toString();
              } else{
              //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
              }
            
            } catch (IOException ex) {
              Log.i("SMARTBADNS","Failedf to get " + url.replace(" ","%20").toString());
              return "error";
           }
          }  else {
        	  Log.i("SMARTBANDS","I got cache entry for url: " + c.getUrl());
        	  return c.getCache();        	  
          }

    }

	

	
    public static String get_no_encode(String url){
        
        HttpClient httpclient = new DefaultHttpClient();          
        HttpResponse response;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			response = httpclient.execute(new HttpGet(url));
	          
	        StatusLine statusLine = response.getStatusLine();
	        if (statusLine.getStatusCode() == HttpStatus.SC_OK){
	          
	          response.getEntity().writeTo(out);
	          //out.close();                       
	          //response.getEntity().getContent().close();

	        } else {
	        	return "error";
	        }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return (out.toString());
          
          //JsonStringEncoder encoder = new JsonStringEncoder();
          

        }
    
	
	
    public static String get(String url,String params){
        try{
          HttpClient httpclient = new DefaultHttpClient();
          String url_encoded = url + URLEncoder.encode(params);
          HttpResponse response = httpclient.execute(new HttpGet(url_encoded));
          
          StatusLine statusLine = response.getStatusLine();
          if(statusLine.getStatusCode() == HttpStatus.SC_OK){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            response.getEntity().writeTo(out);
            out.close();
            return out.toString();
          } else{
          //Closes the connection.
            response.getEntity().getContent().close();
            throw new IOException(statusLine.getReasonPhrase());
          }
        } catch (IOException ex) {
            
        } 
        return "error";

    }
    
    private String getScreenSize(String prefix, Activity activity){
        int width;
        int height;
        
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        width = display.getWidth();
        height = display.getHeight();

    	return (prefix + width + "x" + height);
    }
    
	////////////////////////////////// Seteo de imagenes en las activities
    
    public static void resizeImageBackground(String prefix, Activity activity, int idResource,ViewGroup vGroup ){
    	vGroup.setBackgroundResource(getResId(calculatePic("l",activity), R.drawable.class));
    	
    }
    
    static int width;
    static int height;
	private static String calculatePic(String prefix, Activity activity){
    	
	     
    	Field[] drawables = com.papitomarket.android.R.drawable.class.getFields();
    	ArrayList<String> files = new ArrayList<String>();
    	for (Field f : drawables) {
    	    try {
               if (f.getName().contains("l") && f.getName().contains("x")){
    	    	  files.add(f.getName());
               }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }
    	}
    	  	
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        width = display.getWidth();
        height = display.getHeight();
        User.scrwidth = width;
        User.scrheight = height;
        
        int minwdiff = 999999;
        int minhdiff = 999999;
        String bestpic = "";
        for (String s : files){
        	
          String without_prefix = s.substring(1);
          String[] halfs= without_prefix.split("x");
          
          
        	
          int w = Integer.parseInt(halfs[0]);
          int h = Integer.parseInt(halfs[1]);
          if ((Math.abs(width - w) <= minwdiff) && (Math.abs(height - h) <= minhdiff)){
        	  minwdiff = Math.abs(width - w);
        	  minhdiff = Math.abs(height - h);
        	  bestpic = s;
          }
        }
        
        
    	return bestpic;
    }

	private static int getResId(String variableName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } 
    }
	
	
	public static String postData(String url,String json) {
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(url);
        String tmp = "";
	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("order",json));
	        
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        tmp = GetText(response.getEntity().getContent());
	        		
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	    return tmp;
	} 
	
	public static String GetText(InputStream in) {
	    String text = "";
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    try {
	      while ((line = reader.readLine()) != null) {
	        sb.append(line + "\n");
	      }
	      text = sb.toString();
	    } catch (Exception ex) {

	    } finally {
	      try {

	        in.close();
	      } catch (Exception ex) {
	      }
	    }
	    return text;
	  }
	
	
}
