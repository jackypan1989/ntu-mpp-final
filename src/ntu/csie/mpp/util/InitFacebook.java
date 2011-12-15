package ntu.csie.mpp.util;

import org.json.JSONException;

import android.util.Log;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

public class InitFacebook {
    private AsyncFacebookRunner mAsyncRunner ;
    
    private final String TAG = "init_data";
    
	public InitFacebook(AsyncFacebookRunner fbar) {
		mAsyncRunner = fbar ;
	}
    
	public void run(){
		mAsyncRunner.request("me/friends", new FriendsRequestListener());
		mAsyncRunner.request("me/photos", new PhotosRequestListener());
		mAsyncRunner.request("me/statuses", new StatusesRequestListener());
	}
	
	public class FriendsRequestListener extends BaseRequestListener {

        @Override
		public void onComplete(final String response, final Object state) {
        	Log.d(TAG , response);
        	try {
				RemoteData.fb_friends = Util.parseJson(response);
			} catch (JSONException e) {
				Log.e( TAG , e.toString());
			} catch (FacebookError e) {
				Log.e( TAG , e.toString());
			}
        	HttpPoster hp = new HttpPoster();
        	hp.setInitFbData("friends" , response);
        }
 
    }
	
	public class PhotosRequestListener extends BaseRequestListener {

        @Override
		public void onComplete(final String response, final Object state) {
        	Log.d(TAG , response);
        	try {
				RemoteData.fb_photos = Util.parseJson(response);
			} catch (JSONException e) {
				Log.e( TAG , e.toString());
			} catch (FacebookError e) {
				Log.e( TAG , e.toString());
			}
        	HttpPoster hp = new HttpPoster();
        	hp.setInitFbData("photos" , response);
        }
 
    }
	
	public class StatusesRequestListener extends BaseRequestListener {

        @Override
		public void onComplete(final String response, final Object state) {
        	Log.d(TAG , response);
        	try {
				RemoteData.fb_statuses = Util.parseJson(response);
			} catch (JSONException e) {
				Log.e( TAG , e.toString());
			} catch (FacebookError e) {
				Log.e( TAG , e.toString());
			}
        	HttpPoster hp = new HttpPoster();
        	hp.setInitFbData("statuses" , response);
         }
    }
}