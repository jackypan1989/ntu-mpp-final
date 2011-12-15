package lab.mpp;

import ntu.csie.mpp.util.LocalData;
import ntu.csie.mpp.util.MyPreferences;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MPPFinalActivity extends TabActivity {
	/** Called when the activity is first created. */
	public static final String TAG = "MPPFinalActivity";
	TabHost tabHost;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences settings = getSharedPreferences(MyPreferences.PREF_FB, 0);
	    LocalData.fb_id = settings.getString(MyPreferences.PREF_FB_ID, "");
	    LocalData.fb_name = settings.getString(MyPreferences.PREF_FB_NAME, "");
		
	    if(LocalData.fb_id.equals("") && LocalData.fb_name.equals("")){
	    	Log.d(TAG , "It's the first time to use this app.");
	    	Intent intent = new Intent(MPPFinalActivity.this , LoginActivity.class);
    		startActivity(intent);
	    }
	    
		// setContentView(R.layout.main);
		tabHost = getTabHost();
		addTab("Home", new Intent(this, TGAHome.class), getResources()
				.getDrawable(R.drawable.icon));
		addTab("Map", new Intent(this, TGAMap.class), getResources()
				.getDrawable(R.drawable.icon));
		addTab("Search", new Intent(this, TGAProfile.class), getResources()
				.getDrawable(R.drawable.icon));
		addTab("Activity", new Intent(this, TGAActivity.class), getResources()
				.getDrawable(R.drawable.icon));

		tabHost.setCurrentTab(0);
	}

	void addTab(String name, Intent intent, Drawable pic) {
		View tabview = LayoutInflater.from(tabHost.getContext()).inflate(
				R.layout.tab, null);

		tabview.setBackgroundDrawable(pic);

		TabSpec setContent = tabHost.newTabSpec(name).setIndicator(tabview)
				.setContent(intent);

		tabHost.addTab(setContent);
	}
}