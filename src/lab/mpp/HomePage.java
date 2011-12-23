package lab.mpp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ntu.csie.mpp.util.HttpPoster;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HomePage extends Activity {
	public static final String TAG = "HomePage";
	// set check list to show
	private ListView checkinList;
	private ArrayList<HashMap<String, Object>> checkinListItem = new ArrayList<HashMap<String, Object>>();
	private SimpleAdapter checkinListItemAdapter;

	// set list content
	private ArrayList<String> nameList = new ArrayList<String>();
	private ArrayList<String> locationNameList = new ArrayList<String>();
	private ArrayList<String> statusList = new ArrayList<String>();
	private ArrayList<String> tagList = new ArrayList<String>();
	private ArrayList<String> dateTimeList = new ArrayList<String>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {        		
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.home);  
		checkinList = (ListView)findViewById(R.id.checkinListView); 

		// send the request to server
		HttpPoster hp = new HttpPoster();
		String response = hp.getCheckin();
		Log.d(TAG ,response);

		try {
			JSONArray json = new JSONArray(response);
			for(int i = 0 ; i<json.length();i++){
				JSONObject checkin = json.getJSONObject(i);
				nameList.add(checkin.getString("name"));
				locationNameList.add(checkin.getString("location_name"));
				statusList.add(checkin.getString("status"));
				tagList.add(checkin.getString("tag"));
				dateTimeList.add(checkin.getString("create_time"));
			}
			updateCheckinList();
		} catch (JSONException e) {
			Log.e(TAG , e.toString());
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		this.getParent().getParent().setTitle("HomePage");
	}

	public void updateCheckinList(){
		checkinListItem.clear();
		for(int i = 0 ; i < nameList.size() ; i++)  
		{  
			HashMap<String, Object> map = new HashMap<String, Object>();  
			map.put("checkinName", nameList.get(i));  
			map.put("checkinLocationName", locationNameList.get(i));  
			map.put("checkinStatus", statusList.get(i));  
			map.put("checkinTag", tagList.get(i));  
			map.put("checkinDateTime", dateTimeList.get(i));  

			checkinListItem.add(map);
		}  

		Log.e("test",checkinListItem.toString());


		checkinListItemAdapter = new SimpleAdapter(this,checkinListItem,R.layout.checkin_listitem,  
				new String[] {"checkinName" , "checkinLocationName" , "checkinStatus" , "checkinTag" ,"checkinDateTime"},   
				new int[] {R.id.checkinName , R.id.checkinLocationName , R.id.checkinStatus , R.id.checkinTag , R.id.checkinDateTime}  
		);    

		checkinList.setAdapter(checkinListItemAdapter); 
	}
}
