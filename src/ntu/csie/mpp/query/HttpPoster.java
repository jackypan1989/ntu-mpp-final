package ntu.csie.mpp.query;

import java.io.*;
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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class HttpPoster {
	//debug tag
	private static final String TAG = "HttpPoster";
	//http post utility
	private HttpClient httpclient;
	private HttpPost httppost;
	ArrayList<NameValuePair> nameValuePairs;
	private HttpResponse response;
	private HttpEntity entity;
	private InputStream is;

	//constructor
	public HttpPoster() {
		httpclient = new DefaultHttpClient();
		nameValuePairs = new ArrayList<NameValuePair>();
	}

	//do http post
	public String doPost() {
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs , HTTP.UTF_8));
			response = httpclient.execute(httppost);
			entity = response.getEntity();
			is = entity.getContent();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//convert response to string
		String result = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
						is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//send fb's graph data to server
	public String setInitFbData(String type, String data) {
		httppost = new HttpPost("http://140.112.107.209/mpp_final/initFbData.php");
		nameValuePairs.add(new BasicNameValuePair("id", "1234"));
		nameValuePairs.add(new BasicNameValuePair("name", "5678"));
		nameValuePairs.add(new BasicNameValuePair("type", type));
		nameValuePairs.add(new BasicNameValuePair("data", data));

		return doPost();
	}

	public String getFbData() {
		httppost = new HttpPost("http://140.112.107.209/mpp_final/getFbData.php");
		return doPost();
	}

	public String getCheckin() {
		httppost = new HttpPost("http://140.112.107.209/mpp_final/getCheckin.php");
		return doPost();
	}

	public String getLastCheckinById(String id) {
		httppost = new HttpPost("http://140.112.107.209/mpp_final/getLastCheckinById.php");
		nameValuePairs.add(new BasicNameValuePair("id", id));
		return doPost();
	}

}
