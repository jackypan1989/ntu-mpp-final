package lab.mpp;

import org.json.JSONException;

import ntu.csie.mpp.util.InitFacebook;
import ntu.csie.mpp.util.MyPreferences;
import ntu.csie.mpp.util.RemoteData;

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
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {
	public static final String TAG = "jacky";
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
        
        
        Button saveFriendBtn = (Button)findViewById(R.id.button1);
        saveFriendBtn.setOnClickListener(new Button.OnClickListener(){
	    	 public void onClick(View v){
	    		 InitFacebook init = new InitFacebook(mAsyncRunner);
	             init.run();
	    	 }
	    });
        
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
				RemoteData.fb_me = Util.parseJson(response);
				
				String id = RemoteData.fb_me.getString("id");
				String name = RemoteData.fb_me.getString("name");
				
				
				SharedPreferences settings = getSharedPreferences(MyPreferences.PREF_FB, 0);
	            settings.edit()
	              .putString(MyPreferences.PREF_FB_ID, id)
	              .putString(MyPreferences.PREF_FB_NAME, name)
	              .commit();
			
        	} catch (JSONException e) {
				Log.e( TAG , e.toString());
			} catch (FacebookError e) {
				Log.e( TAG , e.toString());
			}
			
			
			
        }
    }
}
