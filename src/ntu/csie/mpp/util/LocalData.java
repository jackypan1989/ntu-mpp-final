package ntu.csie.mpp.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

public class LocalData {
	// the facebook data at local device
	public static String fb_id = "";
	public static String fb_name = "";
	// public static Bitmap myFace;
	public static double latitude;
	public static double longitude;

	// app status : demo , login(first used) , session(have used)
	public static String app_status = "";

	public static JSONObject fb_me;
	public static JSONObject fb_friends;
	public static JSONObject fb_photos;
	public static JSONObject fb_statuses;
	public static JSONArray query;
	public static String[] tagList = { "純打卡", "吃飯", "打球", "睡覺", "念書", "逛街",
			"聊天", "寫作業" };
	public static String[] statusList = { "無聊", "忙碌", "徵人", "覺得很爽", "快樂", "難過" };
	public static String[] statusPost = { "好無聊", "好忙", "要徵人", "覺得很爽", "很快樂", "很難過" };
	public static String[] placeName;
	public static String[] placeId;

	// load the facebook data from preference(at local device)
	public static void getPreference(SharedPreferences settings) {
		try {
			LocalData.fb_me = new JSONObject(settings.getString("PREF_FB_ME",
					""));
			LocalData.fb_friends = new JSONObject(settings.getString(
					"PREF_FB_FRIENDS", ""));
			LocalData.fb_photos = new JSONObject(settings.getString(
					"PREF_FB_PHOTOS", ""));
			LocalData.fb_statuses = new JSONObject(settings.getString(
					"PREF_FB_STATUSES", ""));

			LocalData.fb_id = LocalData.fb_me.getString("id");
			LocalData.fb_name = LocalData.fb_me.getString("name");
			Log.e("query", "!" + settings.getString("PREF_QUERY", ""));
			LocalData.query = new JSONArray(
					settings.getString("PREF_QUERY", ""));

			Log.e("get", "id=" + LocalData.fb_id);
		} catch (JSONException e) {
			Log.e("LocalData", e.toString());
		}
	}

	// update the facebook data to preference (at local device)
	public static void updatePreference(SharedPreferences settings, String key,
			String value) {
		settings.edit().putString(key, value).commit();
	}// get friend name list

	public static ArrayList<String> getFbFriendIdList() {
		ArrayList<String> s = new ArrayList<String>();
		if (fb_friends == null) {
			return s;
		}
		// set fb friend
		try {
			JSONArray json = fb_friends.getJSONArray("data");
			for (int i = 0; i < json.length(); i++) {
				s.add(json.getJSONObject(i).getString("id"));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	// get friend name list
	public static ArrayList<String> getFbFriendNameList() {
		ArrayList<String> s = new ArrayList<String>();
		if (fb_friends == null) {
			return s;
		}
		// set fb friend
		try {
			JSONArray json = fb_friends.getJSONArray("data");
			for (int i = 0; i < json.length(); i++) {
				s.add(json.getJSONObject(i).getString("name"));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
}