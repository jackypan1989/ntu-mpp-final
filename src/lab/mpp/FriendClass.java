package lab.mpp;

import java.util.ArrayList;

import ntu.csie.mpp.util.HttpPoster;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class FriendClass {
	String id;
	String name;
	Bitmap pic;
	double latitude;
	double longitude;
	String location_name;
	String status;
	String tag;
	String description;
	With[] with_friends;
	String create_time;
	String update_time;
	ArrayList<CheakClass> cheak = new ArrayList<CheakClass>();

	FriendClass(JSONObject j) {
		try {
			id = j.getString("id");
			name = j.getString("name");
			cheak.add(new CheakClass(j));
			latitude = j.getDouble("latitude");
			longitude = j.getDouble("longitude");
			location_name = j.getString("location_name");
			status = j.getString("status");
			tag = j.getString("tag");
			description = j.getString("description");
			create_time = j.getString("create_time");
			update_time = j.getString("update_time");
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					pic = HttpPoster.getUserPic(id);
					Log.e("pic",id+" ok");
				}

			}).start();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	FriendClass(String inId){
		id=inId;
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				pic = HttpPoster.getUserPic(id);
				Log.e("pic",id+" ok");
			}

		}).start();
	}

	void addCheak(JSONObject j) {
		cheak.add(new CheakClass(j));
	}

	Bitmap getBitmap() {
		if (pic == null) {
			return null;
		} else {
			return pic;
		}
	}
}
