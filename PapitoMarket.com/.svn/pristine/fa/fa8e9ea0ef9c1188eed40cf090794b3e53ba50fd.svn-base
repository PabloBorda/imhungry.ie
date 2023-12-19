package com.papitomarket.android;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class CameraActivity extends Activity {

    private static final String LOG_TAG = "CameraActivity.java";
    private static final int IMAGE_CAPTURE = 0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        try {

            // intent to start device camera
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

            // you can also create your own filename or path

    		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
			.format(new Date());
    		
            // where do you want to save the images
            String path = Environment.getExternalStorageDirectory() + "/"
                    + timeStamp + "_pm.jpg";

            File file = new File(path);

            // if the file name already exists, append __x on the file name
            int x = 2;
            while (file.exists()) {

                path = Environment.getExternalStorageDirectory() + "/"
                        + timeStamp + "__" + x + "_pm.jpg";
                file = new File(path);

                x++;
            }

            Uri outputFileUri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

            // 0, this code will be returned in onActivityResult() when the
            // activity exits.
            startActivityForResult(intent, 0);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // activity exits
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == IMAGE_CAPTURE) {
                if (resultCode == RESULT_OK) {

                    Log.v(LOG_TAG, "Picture taken.");

                    // delete last image from dcim
                    if(!deleteLastSavedDcimImage()){
                        Log.v(LOG_TAG,"Unable to delete last saved image in /Camera/DCIM/");
                    }

                    // ask if the user want's to take another picture
                    takeAnother();

                } else {
                    // you can specify any message here
                    // or just remove it
                    Toast.makeText(getBaseContext(),
                            "Error: Result code is not RESULT_OK.",
                            Toast.LENGTH_SHORT).show();

                }
            } else {
                // you can specify any message here
                // or just remove it
                Toast.makeText(getBaseContext(),
                        "Error: Request code is not IMAGE_CAPTURE.",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        CameraActivity.this.finish();
    }

    public void takeAnother() {
        try {

            new AlertDialog.Builder(this).setTitle("SmartBands")
                    .setMessage("Do you want to take another picture?")
                    .setPositiveButton("YES", new OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();

                            Intent nextActivity = new Intent(
                                    CameraActivity.this, CameraActivity.class);
                            CameraActivity.this.finish();
                            startActivity(nextActivity);

                        }
                    })

                    .setNegativeButton("NO", new OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                            arg0.dismiss();

                            Toast.makeText(CameraActivity.this,
                                    "Done taking picture.", Toast.LENGTH_LONG)
                                    .show();

                            // go to main activity
                            CameraActivity.this.finish();

                        }
                    }).show();

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // since our code also saves the taken images to DCIM/Camera folder of the
    // device, we have to delete it there so that the image will be saved
    // only to the directory we have specified.
    private boolean deleteLastSavedDcimImage() {

        Log.v(LOG_TAG, "Deleting late image from DCIM.");

        boolean success = false;

        try {

            // list the images in the device /DCIM/Camera directory
            File[] images = new File(Environment.getExternalStorageDirectory()
                    + "/DCIM/Camera").listFiles();
            File lastSavedImage = images[0];
            int imagesLen =  images.length;
            
            //loop and check for the last modified image to get the last save image
            for (int i = 1; i < imagesLen; ++i) {
                if (images[i].lastModified() > lastSavedImage.lastModified()) {
                    lastSavedImage = images[i];
                }
            }

            //then delete the last saved image
            success = new File(lastSavedImage.toString()).delete();

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }

}
