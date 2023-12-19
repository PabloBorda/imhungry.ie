package imhungry.imhungry;
 
import ie.imhungry.imhungry.R;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.VideoView;
 
//Implement SurfaceHolder interface to Play video
//Implement this interface to receive information about changes to the surface
public class IMHSplash extends Activity implements SurfaceHolder.Callback{
 
    MediaPlayer mediaPlayer;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean pausing = false;;
     
     
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imhsplash);
         
        ImageButton imh_image_button = (ImageButton)findViewById(R.id.imh_start_button);
        imh_image_button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        
        
        
        
        getWindow().setFormat(PixelFormat.UNKNOWN);
        
        //Displays a video file.   
        VideoView mVideoView = (VideoView)findViewById(R.id.videoview);
        
         
        String uriPath = "android.resource://com.android.AndroidVideoPlayer/"+R.raw.imh_background_video;
        Uri uri = Uri.parse(uriPath);
        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();
        mVideoView.start();
 
     }
     
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        // TODO Auto-generated method stub
         
    }
 
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
         
    }
 
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
         
    }
}