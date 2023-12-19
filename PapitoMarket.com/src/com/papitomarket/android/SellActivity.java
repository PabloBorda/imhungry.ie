/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.papitomarket.android;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.papitomarket.model.products.android.Product;
import com.papitomarket.model.stores.android.Info;
import com.papitomarket.model.stores.android.Store;
import com.papitomarket.util.Util;

/**
 * 
 * @author Pablo Tomas Borda Di Berardino
 */
public class SellActivity extends Activity {

	private Intent fromGallery;
	private ImageView pic;
	private Bitmap bmp;
	private ArrayList<String> pics;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			if (resultCode == Activity.RESULT_OK) {
				takePicture(data);
			} else if (resultCode == Activity.RESULT_CANCELED) {

			}
		}

	}

	private void takePicture(Intent data) {

		Bundle b = data.getExtras();
		pic = (ImageView) b.get("data");
		if (pic != null) {
			ImageView im = (ImageView) findViewById(R.id.current_picture);

			BitmapDrawable drawable = (BitmapDrawable) pic.getDrawable();
			Bitmap bitmap = drawable.getBitmap();
			im.setImageBitmap(bitmap);

		}

	}

	private String mkdir_for_pictures() {
		File sdDir = Environment.getExternalStorageDirectory();

		File data = new File(sdDir.getAbsolutePath() + "/PapitoMarket");
		data.mkdir();
		try {
			createImageFile();
		} catch (IOException ex) {
			Logger.getLogger(SellActivity.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return data.getAbsolutePath();
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		String imageFileName = timeStamp + "_pm.jpg";
		String fname = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/PapitoMarket/" + imageFileName;
		pics.add(fname);
		File image = new File(fname);
		return image;
	}

	private void openGallery() {
		fromGallery = new Intent(Intent.ACTION_GET_CONTENT);
		fromGallery.setType("image/*");
		startActivityForResult(fromGallery, 2);
	}

	private void show_dialog(String m) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Reset...");

		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions
			}
		});
		// Search s = new Search(getBaseContext());
		alertDialog.setMessage(m);
		alertDialog.show();
	}

	private boolean validate_form() {

		EditText product_title = (EditText) findViewById(R.id.title_edit);
		EditText category_edit = (EditText) findViewById(R.id.category_edit);
		EditText price_edit = (EditText) findViewById(R.id.price_edit);
		EditText tags_edit = (EditText) findViewById(R.id.tags_edit);
		EditText description_edit = (EditText) findViewById(R.id.description_edit);
		CheckBox deliver_checkbox = (CheckBox) findViewById(R.id.deliver_checkbox);
		EditText ratio_edit = (EditText) findViewById(R.id.ratio_edit);

		if (product_title.getText().toString().length() < 3) {
			show_dialog("Product title is required");
		} else if (category_edit.getText().toString().length() < 3) {
			show_dialog("Category is required");
		} else {
			try {
				Float price = (Float
						.parseFloat(price_edit.getText().toString()));
				if (tags_edit.getText().toString().length() < 3) {
					show_dialog("Input space separated search tags");
				} else {
					if (description_edit.getText().toString().length() < 30) {
						show_dialog("Description is required, larger than 30 characters");
					} else {
						try {
							if (deliver_checkbox.isChecked()) {
								Float ratio = (Float.parseFloat(ratio_edit
										.getText().toString()));
							}
						} catch (NumberFormatException ne) {
							show_dialog("Ratio, float number expected");
						}

						return true;
					}

				}
			} catch (NumberFormatException ne) {
				show_dialog("Wrong price, float expected");
			}
		}
		return false;

	}

	private boolean publish_product() {

		EditText product_title = (EditText) findViewById(R.id.title_edit);
		EditText category_edit = (EditText) findViewById(R.id.category_edit);
		EditText price_edit = (EditText) findViewById(R.id.price_edit);
		EditText tags_edit = (EditText) findViewById(R.id.tags_edit);
		EditText description_edit = (EditText) findViewById(R.id.description_edit);
		CheckBox deliver_checkbox = (CheckBox) findViewById(R.id.deliver_checkbox);
		EditText ratio_edit = (EditText) findViewById(R.id.ratio_edit);

		if (validate_form()) {
			Product p = new Product();
			p.setName(product_title.getText().toString());
			p.setPrice(price_edit.getText().toString());
			p.setDescription(description_edit.getText().toString());
			p.setTags(new ArrayList<String>(Arrays.asList(tags_edit.getText()
					.toString().split(" "))));
			p.setDeliver(deliver_checkbox.toString());
			p.setDelivery_ratio(ratio_edit.getText().toString());
			p.setPics(pics);
			//Store s = new Store();
			//Info i = new Info(storeid, companyname, companylogo, productname, prodorstore, sumallprods, webpage, email, address, lat, lng, distance, usr_fb)
			//s.getCategories().getProducts().add(p);
			ObjectMapper mapper = new ObjectMapper();

			HttpParams params = new BasicHttpParams();
			params.setParameter("companyname", "DockeSoft"); // deberia sacarse
																// de
																// s.getCompaniName()
																// ???
			params.setParameter("companylogo", "DockeSoftLogo");
			params.setParameter("webpage", "DockeSoft.com");
			params.setParameter("password", "pass");
			params.setParameter("email", "papito@market.com");
			params.setParameter("address", "Av Debenedetti 2100");
			params.setParameter("lat", "2100");
			params.setParameter("lng", "300");
			params.setParameter("superproduct", "Alimento");
			params.setParameter("name", p.getName());
			params.setParameter("price", p.getPrice());
			params.setParameter("description", p.getDescription());
			params.setParameter("time", new Date());
			//try {
				String jout = "please remove this";//mapper.writeValueAsString(s);
				String url = "http://soa1.papitomarket.com:9494/new_product_from_social";
				params.setParameter("json", jout);
				readJSONFeed(url, params, p);
				Log.i("JSONOUT", jout);

			//} catch (IOException ex) {
			//	Logger.getLogger(SellActivity.class.getName()).log(
			//			Level.SEVERE, null, ex);
			//}
		}

		return true;
	}

	public String readJSONFeed(String URL, HttpParams params, Product p) {
		StringBuilder stringBuilder = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpGet = new HttpPost(URL);

		httpGet.setParams(params);

		try {
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream inputStream = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				String line;
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
				}
				inputStream.close();
			} else {
				Log.d("JSON",
						"Failed to download file: " + response.getStatusLine());
			}
		} catch (Exception e) {
			Log.d("readJSONFeed", e.getLocalizedMessage());
		}
		return stringBuilder.toString();
	}

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// ToDo add your GUI initialization code here
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

		setContentView(R.layout.sell);
		// setContentView(R.id.rellaySell);

		ScrollView rl = (ScrollView) findViewById(R.id.rellaySell);
		Util.resizeImageBackground("l", this, R.id.rellaySell, rl);

		pics = new ArrayList<String>();
		pic = (ImageView) findViewById(R.id.current_picture);

		Button cam_button = (Button) findViewById(R.id.camera_button);
		cam_button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				mkdir_for_pictures(); // falta redireccionar el guardado de las
										// fotos a la carpeta elegida
				Intent nextActivity = new Intent(getBaseContext(),
						CameraActivity.class);
				startActivity(nextActivity);
			}
		});

		Button gal_button = (Button) findViewById(R.id.gal_button);
		gal_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				mkdir_for_pictures();
				openGallery();
			}
		});

		ImageButton publish_button = (ImageButton) findViewById(R.id.publish_button);
		publish_button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				publish_product();
				// Intent menu_screen = new
				// Intent(getApplicationContext(),MenuActivity.class);
				// startActivity(menu_screen);
				// acá iria un cartel de Publicación OK
			}
		});

		WebView captcha = (WebView) findViewById(R.id.captcha);
		captcha.getSettings().setJavaScriptEnabled(true);
		captcha.loadUrl("http://www.papitomarket.com:81/captcha");

		TextView a = (TextView) findViewById(R.id.title_edit);
		a.setTextColor(Color.BLACK);

		a.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				EditText product_title = (EditText) findViewById(R.id.title_edit);
				if (hasFocus) {
					product_title.setBackgroundColor(Color.YELLOW);
					product_title.setTextColor(Color.BLACK);
				} else {

					product_title.setBackgroundColor(Color.BLACK);
					product_title.setTextColor(Color.YELLOW);
				}
			}
		});

		TextView b = (TextView) findViewById(R.id.category_edit);
		b.setTextColor(Color.BLACK);

		b.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				EditText product_title = (EditText) findViewById(R.id.category_edit);
				if (hasFocus) {
					product_title.setBackgroundColor(Color.YELLOW);
					product_title.setTextColor(Color.BLACK);
				} else {

					product_title.setBackgroundColor(Color.BLACK);
					product_title.setTextColor(Color.YELLOW);
				}
			}
		});

		TextView c = (TextView) findViewById(R.id.price_edit);
		c.setTextColor(Color.BLACK);

		c.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				EditText product_title = (EditText) findViewById(R.id.price_edit);
				if (hasFocus) {
					product_title.setBackgroundColor(Color.YELLOW);
					product_title.setTextColor(Color.BLACK);
				} else {

					product_title.setBackgroundColor(Color.BLACK);
					product_title.setTextColor(Color.YELLOW);
				}
			}
		});

		TextView d = (TextView) findViewById(R.id.tags_edit);
		d.setTextColor(Color.BLACK);
		d.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				EditText product_title = (EditText) findViewById(R.id.tags_edit);
				if (hasFocus) {
					product_title.setBackgroundColor(Color.YELLOW);
					product_title.setTextColor(Color.BLACK);
				} else {

					product_title.setBackgroundColor(Color.BLACK);
					product_title.setTextColor(Color.YELLOW);
				}
			}
		});

		TextView e = (TextView) findViewById(R.id.description_edit);
		e.setTextColor(Color.BLACK);
		e.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				EditText product_title = (EditText) findViewById(R.id.description_edit);
				if (hasFocus) {
					product_title.setBackgroundColor(Color.YELLOW);
					product_title.setTextColor(Color.BLACK);
				} else {

					product_title.setBackgroundColor(Color.BLACK);
					product_title.setTextColor(Color.YELLOW);
				}
			}
		});

		TextView f = (TextView) findViewById(R.id.ratio_edit);
		f.setTextColor(Color.BLACK);
		f.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				EditText product_title = (EditText) findViewById(R.id.ratio_edit);
				if (hasFocus) {
					product_title.setBackgroundColor(Color.YELLOW);
					product_title.setTextColor(Color.BLACK);
				} else {

					product_title.setBackgroundColor(Color.BLACK);
					product_title.setTextColor(Color.YELLOW);
				}
			}
		});
	}

}

/**
 * //Intent cameraIntent = new
 * Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
 * //startActivityForResult(cameraIntent, R.string.cameraRequestCode);
 * 
 * //startActivityForResult(takePictureIntent, 1337);
 */
