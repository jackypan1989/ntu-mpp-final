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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class LoginActivity extends Activity {
	// setting debug_flag
	public static final String TAG = "LoginActivity";

	// setting facebook login info
	private String APP_ID = "229865623746685";
	static Facebook mFacebook;
	private AsyncFacebookRunner mAsyncRunner;

	// setting preferences
	private SharedPreferences settings;

	// setting view
	private LoginButton mLoginButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// setting no title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);

		// initializing the views
		mLoginButton = (LoginButton) findViewById(R.id.loginButton1);
		Button facebookBtn = (Button)findViewById(R.id.facebookButton);
		Button tryBtn = (Button)findViewById(R.id.tryButton);
		Button supportBtn = (Button)findViewById(R.id.supportButton);

		// retrieving preferences
		settings = getSharedPreferences("PREF_FB", 0);
		LocalData.getPreference(settings);

		// initializing facebook
		mFacebook = new Facebook(APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(mFacebook);

		// initializing facebook session and login
		SessionStore.restore(mFacebook, this);
		SessionEvents.addAuthListener(new SampleAuthListener());
		mLoginButton.init(this, mFacebook , new String[] {"read_friendlists" , "user_photos" , "read_stream" });
		mLoginButton.setVisibility(View.INVISIBLE);

		// initializing login page 3 buttons' click listener
		facebookBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v){
				// call mLoginButton' click
				mLoginButton.performClick();
			}
		});

		tryBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(LoginActivity.this ,MPPFinalActivity.class); 
				startActivity(intent); 
				finish();
			}
		});

		supportBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(LoginActivity.this ,SupportActivity.class); 
				startActivity(intent); 
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		// if session is valid , it will directly go to main page
		if(mFacebook.isSessionValid()){
			Intent intent = new Intent(LoginActivity.this ,MPPFinalActivity.class); 
			startActivity(intent); 
			finish();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mFacebook.authorizeCallback(requestCode, resultCode, data);
	}

	public class SampleAuthListener implements AuthListener {

		@Override
		public void onAuthSucceed() {
			// it's the first time to login and fetch facebook data  
			mAsyncRunner.request("me", new MeRequestListener());
			Log.d(TAG,"Authentication successed!");
		}

		@Override
		public void onAuthFail(String error) {
			Log.e(TAG,"Authentication failed!");
		}
	}

	public class MeRequestListener extends BaseRequestListener {
		@Override
		public void onComplete(final String response, final Object state) {
			try {
				// parsing facebook response to local data
				LocalData.fb_me = Util.parseJson(response);
				LocalData.fb_id = LocalData.fb_me.getString("id");
				LocalData.fb_name = LocalData.fb_me.getString("name");

				// updating local data to preference
				SharedPreferences settings = getSharedPreferences("PREF_FB", 0);
				LocalData.updatePreference(settings, "PREF_FB_ME",
						LocalData.fb_me.toString());
				
				// it's the first time to login and fetch other facebook data  
				mAsyncRunner.request("me/friends", new FriendsRequestListener());
				mAsyncRunner.request("me/photos", new PhotosRequestListener());
				mAsyncRunner.request("me/statuses", new StatusesRequestListener());
				
			} catch (JSONException e) {
				Log.e(TAG, e.toString());
			} catch (FacebookError e) {
				Log.e(TAG, e.toString());
			}
		}
	}

	public class FriendsRequestListener extends BaseRequestListener {

		@Override
		public void onComplete(final String response, final Object state) {
			try {
				// parsing facebook response to local data
				LocalData.fb_friends = Util.parseJson(response);

				// updating local data to preference
				LocalData.updatePreference(settings, "PREF_FB_FRIENDS",
						LocalData.fb_friends.toString());
			} catch (JSONException e) {
				Log.e(TAG, e.toString());
			} catch (FacebookError e) {
				Log.e(TAG, e.toString());
			}
			
			// sending me/friends data to server
			HttpPoster hp = new HttpPoster();
			hp.setInitFbData("friends", response);
		}
	}

	public class PhotosRequestListener extends BaseRequestListener {

		@Override
		public void onComplete(final String response, final Object state) {
			Log.d(TAG, response);
			try {
				// parsing facebook response to local data
				LocalData.fb_photos = Util.parseJson(response);

				// updating local data to preference
				LocalData.updatePreference(settings, "PREF_FB_PHOTOS",
						LocalData.fb_photos.toString());

			} catch (JSONException e) {
				Log.e(TAG, e.toString());
			} catch (FacebookError e) {
				Log.e(TAG, e.toString());
			}
			
			// sending me/photos data to server
			HttpPoster hp = new HttpPoster();
			hp.setInitFbData("photos", response);
		}

	}

	public class StatusesRequestListener extends BaseRequestListener {

		@Override
		public void onComplete(final String response, final Object state) {
			Log.d(TAG, response);
			try {
				// parsing facebook response to local data
				LocalData.fb_statuses = Util.parseJson(response);

				// update local data to preference
				LocalData.updatePreference(settings, "PREF_FB_STATUSES",
						LocalData.fb_statuses.toString());

			} catch (JSONException e) {
				Log.e(TAG, e.toString());
			} catch (FacebookError e) {
				Log.e(TAG, e.toString());
			}
			
			// sending me/statuses data to server
			HttpPoster hp = new HttpPoster();
			hp.setInitFbData("statuses", response);
		}
	}

}
