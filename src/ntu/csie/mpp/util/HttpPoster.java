package ntu.csie.mpp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpPoster {
	// debug tag
	private static final String TAG = "HttpPoster";
	// http post utility
	private HttpClient httpclient;
	private HttpPost httppost;
	ArrayList<NameValuePair> nameValuePairs;
	private HttpResponse response;
	private HttpEntity entity;
	private InputStream is;

	// constructor
	public HttpPoster() {
		httpclient = new DefaultHttpClient();
		nameValuePairs = new ArrayList<NameValuePair>();
	}

	// do http post
	public String doPost() {
		try {
			response = httpclient.execute(httppost);
			entity = response.getEntity();
			is = entity.getContent();
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.toString());
		} catch (ClientProtocolException e) {
			Log.e(TAG, e.toString());
		} catch (IOException e) {
			Log.e(TAG, e.toString());
		}

		// convert response to string
		String result = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF_8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.d(TAG, "Result:" + result);
		} catch (Exception e) {
			Log.e(TAG, "Error Converting:" + e.toString());
		}
		return result;
	}

	// send fb's graph data to server
	public String setInitFbData(String type, String data) {
		httppost = new HttpPost(
				"http://140.112.107.209/mpp_final/initFbData.php");
		nameValuePairs.add(new BasicNameValuePair("id", LocalData.fb_id));
		nameValuePairs.add(new BasicNameValuePair("name", LocalData.fb_name));
		nameValuePairs.add(new BasicNameValuePair("type", type));
		nameValuePairs.add(new BasicNameValuePair("data", data));
		Log.d(TAG, data.toString());
		
		return doPost();
	}

	public String getFbData() {
		httppost = new HttpPost(
				"http://140.112.107.209/mpp_final/getFbData.php");
		return doPost();
	}

	public String getCheckin() {
		httppost = new HttpPost(
				"http://140.112.107.209/mpp_final/getCheckin.php");
		return doPost();
	}

	public String getLastCheckinById(String id) {
		httppost = new HttpPost(
				"http://140.112.107.209/mpp_final/getLastCheckinById.php");
		nameValuePairs.add(new BasicNameValuePair("id", id));
		return doPost();
	}

	public Bitmap getUserPic(String userID) {
		String imageURL;
		Bitmap bitmap = null;
		Log.d(TAG, "Loading Picture");
		imageURL = "http://graph.facebook.com/" + userID
				+ "/picture?type=square";
		try {
			bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageURL)
					.getContent());
		} catch (Exception e) {
			Log.d(TAG, "Loading Picture FAILED");
			e.printStackTrace();
		}
		return bitmap;
	}
}