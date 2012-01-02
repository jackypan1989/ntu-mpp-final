package lab.mpp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ntu.csie.mpp.util.HttpPoster;
import ntu.csie.mpp.util.LocalData;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class ActivityPage extends Activity {
	public static final String TAG = "ActivityPage";
	
	private ListView friendsListView;
	private Spinner locationSpinner;
	private Spinner tagSpinner;
	private Spinner statusSpinner;
	private TextView descriptionTextView;
	private Button addButton;
	
	private String[] nameList;
	private String[] idList;
	private Dialog dialog;
	private long[] selectedFriends;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View contentView = LayoutInflater.from(this.getParent()).inflate(
				R.layout.act, null);
		// setContentView(R.layout.search_activity);
		setContentView(contentView);

		// set View
		descriptionTextView = (TextView) findViewById(R.id.descriptionText); 
		
		// set button
		addButton = (Button) findViewById(R.id.recommendButton);
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.show();
			}
		});

		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				createAct();
				Toast.makeText(ActivityPage.this, "活動已建立!", Toast.LENGTH_LONG).show();
				MPPFinalActivity.goTo(2);
			}
		});

		if (LocalData.placeName != null) {
			// 地標的spinner
			ArrayAdapter<String> placeAdapter = new ArrayAdapter<String>(
					getParent().getParent(),
					android.R.layout.simple_spinner_item, LocalData.placeName);
			placeAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			locationSpinner = (Spinner) findViewById(R.id.spinner3);
			locationSpinner.setAdapter(placeAdapter);
			locationSpinner.setPrompt("請選擇地點");
		}

		// set spinner for tag
		tagSpinner = (Spinner) findViewById(R.id.spinnerActivityTag);
		// create array to set adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getParent()
				.getParent(), android.R.layout.simple_spinner_item,
				LocalData.tagList);
		// set dropdown view
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tagSpinner.setAdapter(adapter);
		tagSpinner.setPrompt("請選擇一個標籤");
		// set onclick
		tagSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView adapterView,
							View view, int position, long id) {

					}

					@Override
					public void onNothingSelected(AdapterView arg0) {

					}
				});

		// set spinner for tag
		statusSpinner = (Spinner) findViewById(R.id.spinnerActivityStatus);
		// create array to set adapter
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getParent()
				.getParent(), android.R.layout.simple_spinner_item,
				LocalData.statusList);
		// set dropdown view
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		statusSpinner.setAdapter(adapter2);
		statusSpinner.setPrompt("請選擇目前狀態");
		// set onclick
		statusSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView adapterView,
							View view, int position, long id) {

					}

					@Override
					public void onNothingSelected(AdapterView arg0) {

					}
				});

	}

	@Override
	public void onResume() {
		super.onResume();
		setFriendList();
	}

	void setFriendList() {
		ArrayList<String> nameArrayList = LocalData.getFbFriendNameList();
		ArrayList<String> idArrayList = LocalData.getFbFriendIdList();
		
		nameList = nameArrayList.toArray(new String[nameArrayList
				.size()]);
		idList = idArrayList.toArray(new String[idArrayList
		                            				.size()]);
		
		
		friendsListView = new ListView(this.getParent().getParent());
		dialog = new Dialog(ActivityPage.this.getParent().getParent());
		AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPage.this
				.getParent().getParent());
		builder.setTitle("加入一個朋友");

		ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(
				ActivityPage.this.getParent().getParent(),
				android.R.layout.simple_list_item_multiple_choice,
				android.R.id.text1, nameList);

		friendsListView.setAdapter(modeAdapter);
		friendsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		builder.setView(friendsListView);

		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int i) {
				selectedFriends = friendsListView.getCheckItemIds();
				addButton.setText("目前已有"+selectedFriends.length+"位朋友");
//				Log.e(TAG,selectedFriends[0]+"test");
			}
		});
		builder.setNegativeButton("清除", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int i) {
				setFriendList();
				addButton.setText("目前尚未選擇任何朋友");
			}
		});
		dialog = builder.create();
	}
    
	// create a new activity
	public void createAct(){
		selectedFriends = friendsListView.getCheckItemIds();
		String location = locationSpinner.getSelectedItem().toString();
		String tag = tagSpinner.getSelectedItem().toString();
		String status = statusSpinner.getSelectedItem().toString();		
		String description = descriptionTextView.getText().toString();
		String withFriend = null;
		
		JSONArray friendList = new JSONArray();
		
		Log.e(TAG,selectedFriends.length+"");
		for(int i = 0;i<selectedFriends.length;i++){
			int index = (int)selectedFriends[i];
			try {
				JSONObject json = new JSONObject();
				json.put("id", idList[index]);
				json.put("name", nameList[index]);
				friendList.put(index, json);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		withFriend = friendList.toString();
		Log.e(TAG,friendList.toString());
		
	    HttpPoster hp = new HttpPoster();
	    hp.createCheckin(location,tag,status,description,withFriend);
		
	}
	
}
