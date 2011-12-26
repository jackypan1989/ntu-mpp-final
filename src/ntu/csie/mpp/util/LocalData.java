package ntu.csie.mpp.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.util.Log;

public class LocalData {
    // the facebook data at local device
	public static String fb_id = "";
	public static String fb_name = "";
	public static double latitude;
	public static double longitude;
	
	// app status : demo , login(first used) , session(have used)
	public static String app_status = "";
	
	public static JSONObject fb_me;
	public static JSONObject fb_friends; 
	public static JSONObject fb_photos; 
	public static JSONObject fb_statuses; 

    // load the facebook data from preference(at local device)
	public static void getPreference(SharedPreferences settings){
		try {
			LocalData.fb_me = new JSONObject(settings.getString("PREF_FB_ME", ""));
			LocalData.fb_friends = new JSONObject(settings.getString("PREF_FB_FRIENDS", ""));
			LocalData.fb_photos = new JSONObject(settings.getString("PREF_FB_PHOTOS", ""));
			LocalData.fb_statuses = new JSONObject(settings.getString("PREF_FB_STATUSES", ""));
			LocalData.fb_id = LocalData.fb_me.getString("id");
			LocalData.fb_name = LocalData.fb_me.getString("name");
		} catch (JSONException e) {
			Log.e("LocalData" , e.toString());
		}
	}
    
	// update the facebook data to preference (at local device)
	public static void updatePreference(SharedPreferences settings , String key , String value){
        settings.edit()
          .putString(key, value)
          .commit();
	}
}