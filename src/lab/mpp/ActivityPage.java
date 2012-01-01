package lab.mpp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ntu.csie.mpp.util.LocalData;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class ActivityPage extends Activity {
	private String[] friendNameList = { "jacky", "wang" };
	private long[] selectId;
	private long[] friendCheak;
	ListView modeList;
	String[] nameList;
	Dialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View contentView = LayoutInflater.from(this.getParent()).inflate(
				R.layout.act, null);
		// setContentView(R.layout.search_activity);
		setContentView(contentView);

		// set button
		Button button = (Button) findViewById(R.id.recommendButton);
		button.setOnClickListener(new View.OnClickListener() {
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
			Spinner spinnerPlcae = (Spinner) findViewById(R.id.spinner3);
			spinnerPlcae.setAdapter(placeAdapter);
			spinnerPlcae.setPrompt("請選擇地點");
		}

		// set spinner for tag
		Spinner spinnerActivityTag = (Spinner) findViewById(R.id.spinnerActivityTag);
		// create array to set adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getParent()
				.getParent(), android.R.layout.simple_spinner_item,
				LocalData.tagList);
		// set dropdown view
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerActivityTag.setAdapter(adapter);
		spinnerActivityTag.setPrompt("請選擇一個標籤");
		// set onclick
		spinnerActivityTag
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView adapterView,
							View view, int position, long id) {

					}

					public void onNothingSelected(AdapterView arg0) {

					}
				});

		// set spinner for tag
		Spinner spinnerActivityStatus = (Spinner) findViewById(R.id.spinnerActivityStatus);
		// create array to set adapter
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getParent()
				.getParent(), android.R.layout.simple_spinner_item,
				LocalData.statusList);
		// set dropdown view
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerActivityStatus.setAdapter(adapter2);
		spinnerActivityStatus.setPrompt("請選擇目前狀態");
		// set onclick
		spinnerActivityStatus
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
					public void onItemSelected(AdapterView adapterView,
							View view, int position, long id) {

					}

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
		nameList = (String[]) nameArrayList.toArray(new String[nameArrayList
				.size()]);

		modeList = new ListView(this.getParent().getParent());
		dialog = new Dialog(ActivityPage.this.getParent().getParent());
		AlertDialog.Builder builder = new AlertDialog.Builder(ActivityPage.this
				.getParent().getParent());
		builder.setTitle("加入一個朋友");

		ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(
				ActivityPage.this.getParent().getParent(),
				android.R.layout.simple_list_item_multiple_choice,
				android.R.id.text1, nameList);

		modeList.setAdapter(modeAdapter);
		modeList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		builder.setView(modeList);

		builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface d, int i) {

				selectId = modeList.getCheckItemIds();
				// if(selectId.is)
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface d, int i) {
				setFriendList();
			}
		});
		dialog = builder.create();
	}

}
