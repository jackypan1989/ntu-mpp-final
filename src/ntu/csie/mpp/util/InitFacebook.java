package ntu.csie.mpp.util;

import android.util.Log;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.Facebook;

public class InitFacebook {
	private Facebook mFacebook ;
    private AsyncFacebookRunner mAsyncRunner ;
    
    private final String TAG = "init_data";
    
	public InitFacebook(Facebook fb , AsyncFacebookRunner fbar) {
		// TODO Auto-generated constructor stub
		mFacebook = fb ;
		mAsyncRunner = fbar ;
	}
    
	public void run(){
		mAsyncRunner.request("me/friends", new FriendsRequestListener());
		//mAsyncRunner.request("me/photos", new PhotosRequestListener());
		//mAsyncRunner.request("me/statuses", new StatusesRequestListener());
	}
	
	public class FriendsRequestListener extends BaseRequestListener {

        @Override
		public void onComplete(final String response, final Object state) {
        	Log.d(TAG , response);
        	HttpPoster hp = new HttpPoster();
        	hp.setInitFbData("friends" , response);
        }
 
    }
	
	public class PhotosRequestListener extends BaseRequestListener {

        @Override
		public void onComplete(final String response, final Object state) {
        	Log.d(TAG , response.toString());
        }
 
    }
	
	public class StatusesRequestListener extends BaseRequestListener {

        @Override
		public void onComplete(final String response, final Object state) {
        	Log.d(TAG , response.toString());
        }
 
    }
}
