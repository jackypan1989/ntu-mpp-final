package lab.mpp;

import ntu.csie.mpp.util.InitFacebook;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.Facebook;
import com.facebook.android.LoginButton;
import com.facebook.android.SessionEvents;
import com.facebook.android.SessionStore;
import com.facebook.android.Util;
import com.facebook.android.SessionEvents.AuthListener;
import com.facebook.android.SessionEvents.LogoutListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {
	// set facebook info
	private String APP_ID = "229865623746685" ;
	private Facebook mFacebook ;
    private AsyncFacebookRunner mAsyncRunner ;
    private String mFacebookID ;
    private String mFacebookName ;
	
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
	    		 InitFacebook init = new InitFacebook(mFacebook ,mAsyncRunner);
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
    
    public class SampleRequestListener extends BaseRequestListener {

        @Override
		public void onComplete(final String response, final Object state) {
        }
 
    }
}
