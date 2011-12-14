package ntu.csie.mpp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpPoster {
	// debug tag
	private static final String TAG = "init_data";
	// http post utility
	private HttpClient httpclient;
	private HttpPost httppost;
	ArrayList<NameValuePair> nameValuePairs; 
	private HttpResponse response;
	private HttpEntity entity;
	private InputStream is;
	
	public HttpPoster(){
		httpclient = new DefaultHttpClient();
		nameValuePairs = new ArrayList<NameValuePair>();
	}
	
	public void setInitFbData(String type , String data){
		// http post
		try {
			httppost = new HttpPost("http://140.112.107.209/mpp_final/initFbData.php");
			nameValuePairs.add(new BasicNameValuePair("id","123"));
			nameValuePairs.add(new BasicNameValuePair("name","jacky"));
			nameValuePairs.add(new BasicNameValuePair("type","friends"));
			nameValuePairs.add(new BasicNameValuePair("data",data));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpclient.execute(httppost);
            entity = response.getEntity();
		    is = entity.getContent();
		    
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Log.e(TAG , e.toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e(TAG , e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG , e.toString());
		}
		
        // convert response to string
		try{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF_8"),8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	        }
	        is.close();
	        
	        Log.d(TAG, "result:" + sb.toString());
		}catch(Exception e){
	        Log.e(TAG, "Error converting:" + e.toString());
		}
	}
	
	public void getCheckIn(){
		// http post
		try {
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost("http://140.112.107.209/mpp_final/getCheckIn.php");
			response = httpclient.execute(httppost);
		    entity = response.getEntity();
		    is = entity.getContent();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Log.e(TAG , e.toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e(TAG , e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(TAG , e.toString());
		}
		
		String result = null;
        // convert response to string
		try{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF_8"),8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	        }
	        is.close();
	        result = sb.toString();
	        Log.d(TAG, "result:" + result);
		}catch(Exception e){
	        Log.e(TAG, "Error converting:" + e.toString());
		}
		
		//parse json data
		try{
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                Log.i("log_tag","id: "+json_data.getInt("id")+
		                        ", name: "+json_data.getString("name")+
		                        ", sex: "+json_data.getInt("sex")+
		                        ", birthyear: "+json_data.getInt("birthyear")
		                );
		        }
		}catch(JSONException e){
		        Log.e("log_tag", "Error parsing data "+e.toString());
		}
	}
}
