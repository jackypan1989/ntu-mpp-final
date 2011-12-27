package lab.mpp;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import ntu.csie.mpp.util.HttpPoster;
import ntu.csie.mpp.util.LocalData;
import ntu.csie.mpp.util.RemoteData;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MPPFinalActivity extends TabActivity implements Runnable,
		LocationListener {
	/** Called when the activity is first created. */
	public static final String TAG = "MPPFinalActivity";
	static TabHost tabHost;
	Handler myHandler = new Handler() {
		public void handleMessage(Message m) {
			switch (m.what) {
			case 0:

				LocationManager lmgr = (LocationManager) getSystemService(LOCATION_SERVICE);
				Criteria criteria = new Criteria();
				String best = lmgr.getBestProvider(criteria, true);
				best = LocationManager.GPS_PROVIDER;
				lmgr.requestLocationUpdates(best, 5000, 1,
						MPPFinalActivity.this);

				Location location = lmgr.getLastKnownLocation(best);
				if (location == null) {
					best = lmgr.getBestProvider(criteria, true);
					lmgr.requestLocationUpdates(best, 5000, 1,
							MPPFinalActivity.this);
					location = lmgr.getLastKnownLocation(best);
				} else if (System.currentTimeMillis() - location.getTime() > 30000) {
					best = lmgr.getBestProvider(criteria, true);
					lmgr.requestLocationUpdates(best, 5000, 1,
							MPPFinalActivity.this);
					location = lmgr.getLastKnownLocation(best);
				}
				if (location != null) {
					if (System.currentTimeMillis() - location.getTime() <= 30000) {
						// TODO Auto-generated method stub
						double x = location.getLatitude();
						double y = location.getLongitude();
						LocalData.latitude = x;
						LocalData.longitude = y;
						// Geocoder c = new Geocoder(MPPFinalActivity.this);
						// try {
						// List<Address> la = c.getFromLocation(x, y, 100);
						// for (Address a : la) {
						// Log.e("location", a + "");
						// }
						// } catch (IOException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
					} else {
						this.sendEmptyMessageDelayed(0, 3000);
					}
				}

				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.d(TAG, "My id is " + LocalData.fb_id + ". My name is "
				+ LocalData.fb_name);

		// get preferences
		SharedPreferences settings = getSharedPreferences("PREF_FB", 0);
		LocalData.getPreference(settings);

		myHandler.sendEmptyMessage(0);
		// check if user is the first time to login
		/*
		 * if(LocalData.fb_id.equals("") && LocalData.fb_name.equals("")){
		 * Log.d(TAG , "It's the first time to use this app."); // go to login
		 * Intent intent = new Intent(MPPFinalActivity.this
		 * ,LoginActivity.class); startActivity(intent); }
		 */
		// send the request to server
		new Thread(this).start();

		tabHost = getTabHost();
		addTab("Home", new Intent(this, TGAHome.class), getResources()
				.getDrawable(R.drawable.tabhome));
		addTab("Map", new Intent(this, TGAMap.class), getResources()
				.getDrawable(R.drawable.tabmap));
		addTab("Search", new Intent(this, TGAProfile.class), getResources()
				.getDrawable(R.drawable.tabprof));
		addTab("Activity", new Intent(this, TGAActivity.class), getResources()
				.getDrawable(R.drawable.tabact));

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

	static void goTo(int i) {
		tabHost.setCurrentTab(i);
		// if (i != 2) {
		// Globo.prefid = -1;
		// }
	}

	@Override
	public void run() {
		Globo.flagStringLoad = false;
		Globo.flagPicLoad = false;
		Globo.flagHasInternet = true;
		// TODO Auto-generated method stub
		Log.e("Log", "thread start");
		HttpPoster hp = new HttpPoster();
		String response = hp.getCheckin();
		if (response == null) {
			Globo.flagHasInternet = false;
			return;
		}

		// Log.d(TAG, response);

		try {
			RemoteData.checkins = new JSONArray(response);
			Globo.flagStringLoad = true;
			// for (int i = 0; i < RemoteData.checkins.length(); i++) {
			// Geocoder c = new Geocoder(MPPFinalActivity.this);
			// try {
			// List<Address> la = c.getFromLocation(
			// RemoteData.checkins.getJSONObject(i).getDouble(
			// "latitude"),
			// RemoteData.checkins.getJSONObject(i).getDouble(
			// "longitude"), 100);
			// Log.e("location", RemoteData.checkins.getJSONObject(i)
			// .getString("name"));
			// for (Address a : la) {
			// Log.e("location", a + "");
			// }
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }

			RemoteData.face = new Bitmap[RemoteData.checkins.length()];
			for (int i = 0; i < RemoteData.face.length; i++) {
				RemoteData.face[i] = hp.getUserPic(RemoteData.checkins
						.getJSONObject(i).getString("id"));
			}
			// Log.e("log", "picok");
			Globo.flagPicLoad = true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
}