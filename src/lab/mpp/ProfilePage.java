package lab.mpp;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import ntu.csie.mpp.util.LocalData;
import ntu.csie.mpp.util.RemoteData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProfilePage extends Activity {
	MyAdapter myadapter;
	Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message m) {
			switch (m.what) {
			case 0:
				// myadapter.array.add("Test" + m.arg1);
				// myadapter.notifyDataSetChanged();
				break;
			case 1:
				if (RemoteData.face[m.arg1] != null) {
					ImageView face = (ImageView) findViewById(R.id.imageView1);
					face.setImageBitmap(RemoteData.face[m.arg1]);
				} else {
					Message m2 = new Message();
					m2.what = 1;
					m2.arg1 = m.arg1;
					sendMessageDelayed(m2, 1000);
				}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		ListView listView = (ListView) findViewById(R.id.listView1);

		myadapter = new MyAdapter(this, new ArrayList<Act>());
		listView.setAdapter(myadapter);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getParent(),
						ActiveDetailPage.class);
				
				TabGroupActivity parentActivity = (TabGroupActivity) getParent();
				parentActivity
						.startChildActivity("ActiveDetailPage", intent);
				
			}
			
		});
		for (int i = 0; i < RemoteData.checkins.length(); i++) {
			JSONObject j;
			try {
				j = RemoteData.checkins.getJSONObject(i);

				myadapter.array
						.add(new Act(j.getString("tag"), j
								.getString("location_name"), j
								.getString("create_time")));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		myadapter.notifyDataSetChanged();
		// listView.setOnItemClickListener(OnCreationListViewClickListener);

		// Message m = new Message();
		// m.what = 0;
		// m.arg1 = 1;
		// myHandler.sendMessage(m);
		// m = new Message();
		// m.what = 0;
		// m.arg1 = 2;
		// myHandler.sendMessage(m);
		// m = new Message();
		// m.what = 0;
		// m.arg1 = 3;
		// myHandler.sendMessage(m);
	}

	@Override
	public void onResume() {
		super.onResume();
		TextView name = (TextView) findViewById(R.id.textView1);
		TextView status = (TextView) findViewById(R.id.textView2);
		TextView location_name = (TextView) findViewById(R.id.textView3);
		ImageView face = (ImageView) findViewById(R.id.imageView1);
		try {
			this.getParent().getParent().setTitle("ProfilePage");
			if (Globo.prefid == -1) {
				name.setText("name");
				status.setText("status");
				location_name.setText("location_name");

			} else {

				name.setText(RemoteData.checkins.getJSONObject(Globo.prefid)
						.getString("name"));
				status.setText(RemoteData.checkins.getJSONObject(Globo.prefid)
						.getString("status"));
				location_name.setText(RemoteData.checkins.getJSONObject(
						Globo.prefid).getString("location_name"));
				if (RemoteData.face[Globo.prefid] != null) {
					face.setImageBitmap(RemoteData.face[Globo.prefid]);
				} else {
					Message m = new Message();
					m.what = 1;
					m.arg1 = Globo.prefid;
					myHandler.sendMessage(m);
				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Globo.prefid = -1;

	}
}
