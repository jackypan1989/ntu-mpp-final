package lab.mpp;

import org.json.JSONException;

import ntu.csie.mpp.util.HttpPoster;
import ntu.csie.mpp.util.LocalData;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.LoginButton;
import com.facebook.android.SessionEvents;
import com.facebook.android.SessionStore;
import com.facebook.android.Util;
import com.facebook.android.SessionEvents.AuthListener;
import com.facebook.android.SessionEvents.LogoutListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class LoginActivity extends Activity {
	public static final String TAG = "Login";
	// set facebook info
	private String APP_ID = "229865623746685" ;
	private Facebook mFacebook ;
    private AsyncFacebookRunner mAsyncRunner ;
	
    // set view
    private LoginButton mLoginButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (APP_ID == null) {
            Util.showAlert(this, "Warning", "Facebook Applicaton ID must be " +
                    "specified before running this example: see Example.java");
        }
        setContentView(R.layout.login_page);
        
        mLoginButton = (LoginButton) findViewById(R.id.loginButton1);
        mFacebook = new Facebook(APP_ID);
       	mAsyncRunner = new AsyncFacebookRunner(mFacebook);
       	
        SessionStore.restore(mFacebook, this);
        SessionEvents.addAuthListener(new SampleAuthListener());
        SessionEvents.addLogoutListener(new SampleLogoutListener());
        mLoginButton.init(this, mFacebook , new String[] {"read_friendlists" , "user_photos" , "read_stream" });
        
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mFacebook.authorizeCallback(requestCode, resultCode, data);
	}
    
	public class SampleAuthListener implements AuthListener {

        @Override
		public void onAuthSucceed() {
        	mAsyncRunner.request("me", new MeRequestListener());
        }

        @Override
		public void onAuthFail(String error) {
          
        }
    }

    public class SampleLogoutListener implements LogoutListener {
        @Override
		public void onLogoutBegin() {
        	
        }

        @Override
		public void onLogoutFinish() {
          
        }
    }
    
    public class MeRequestListener extends BaseRequestListener {
        @Override
		public void onComplete(final String response, final Object state) {
        	Log.d( TAG , response);
        	try {
        		// parse facebook response to local data
				LocalData.fb_me = Util.parseJson(response);
				LocalData.fb_id = LocalData.fb_me.getString("id");
				LocalData.fb_name = LocalData.fb_me.getString("name");
				
				// update local data to preference 
				SharedPreferences settings = getSharedPreferences("PREF_FB", 0);
				LocalData.updatePreference(settings , "PREF_FB_ME" , LocalData.fb_me.toString());
	            
				// get more facebook data
		        InitFacebook init = new InitFacebook(mAsyncRunner);
			    init.run();
			    
        	} catch (JSONException e) {
				Log.e( TAG , e.toString());
			} catch (FacebookError e) {
				Log.e( TAG , e.toString());
			}
        }
    }
    
    public class InitFacebook {
        private AsyncFacebookRunner mAsyncRunner ;
        
        private final String TAG = "InitFacebook";
        
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
    				LocalData.fb_friends = Util.parseJson(response);
    				
    				// update local data to preference 
    				SharedPreferences settings = getSharedPreferences("PREF_FB", 0);
    				LocalData.updatePreference(settings , "PREF_FB_FRIENDS" , LocalData.fb_friends.toString());
    
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
            		LocalData.fb_photos = Util.parseJson(response);
            		
            		// update local data to preference 
    				SharedPreferences settings = getSharedPreferences("PREF_FB", 0);
    				LocalData.updatePreference(settings , "PREF_FB_PHOTOS" , LocalData.fb_photos.toString());
    			
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
            		LocalData.fb_statuses = Util.parseJson(response);
            		
            		// update local data to preference 
    				SharedPreferences settings = getSharedPreferences("PREF_FB", 0);
    				LocalData.updatePreference(settings , "PREF_FB_STATUSES" , LocalData.fb_statuses.toString());
            		
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
}
