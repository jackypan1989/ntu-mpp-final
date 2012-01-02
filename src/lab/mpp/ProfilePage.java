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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ProfilePage extends Activity {
	MyAdapter myadapter;
	int myId = -1;
	Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message m) {
			switch (m.what) {
			// case 0:
			// if (LocalData.myFace != null) {
			// ImageView face = (ImageView) findViewById(R.id.imageView1);
			// face.setImageBitmap((LocalData.myFace));
			// } else {
			// myHandler.sendEmptyMessageDelayed(0, 1000);
			// }
			// break;
			case 1:
				// if (RemoteData.face[m.arg1] != null) {
				if (RemoteData.friend.get(m.arg1).getBitmap() != null) {
					ImageView face = (ImageView) findViewById(R.id.imageView1);
					face.setImageBitmap(RemoteData.friend.get(m.arg1)
							.getBitmap());
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
		myId = Globo.prefid;
		TextView name = (TextView) findViewById(R.id.textView1);
		TextView status = (TextView) findViewById(R.id.textView2);
		TextView location_name = (TextView) findViewById(R.id.textView3);
		ImageView face = (ImageView) findViewById(R.id.imageView1);

		if (myId == -1) {
			for (int i = 0; i < RemoteData.friend.size(); i++) {
				if (RemoteData.friend.get(i).id.equals(LocalData.fb_id)) {
					myId = i;
					break;
				}
			}
		}

		FriendClass f = RemoteData.friend.get(myId);
		name.setText(f.name);
		if (f.getBitmap() != null) {
			face.setImageBitmap(f.getBitmap());
		} else {

		}
		if (f.cheak.size() > 0) {
			status.setText(f.cheak.get(0).status);
			location_name.setText(f.cheak.get(0).location_name);
		}
		// try {
		// this.getParent().getParent().setTitle("ProfilePage");
		// if (Globo.prefid ==-1) {
		// myId = LocalData.fb_id;
		// // for (int i = 0; i < RemoteData.checkins.length(); i++) {
		// for(FriendClass f:RemoteData.friend){
		// if (f.equals(LocalData.fb_id)) {
		// name.setText(f.name);
		// status.setText(f.cheak.get(0).status);
		// location_name.setText(f.cheak.get(0).location_name);
		// face.setImageBitmap(f.getBitmap());
		// break;
		// }
		// // name.setText(LocalData.fb_name);
		// // status.setText("");
		// // location_name.setText("");
		// // if (LocalData.myFace != null) {
		// // face.setImageBitmap((LocalData.myFace));
		// // } else {
		// // myHandler.sendEmptyMessage(0);
		// // }
		// }
		//
		// } else {
		// myId = RemoteData.friend.get(Globo.prefid).id;
		// for(int i=0;i<RemoteData.checkins.length();i++){
		// if(myId.equals(RemoteData.checkins.getJSONObject(i).getString("id"))){
		// Globo.prefid=i;
		// break;
		// }
		// }
		// name.setText(RemoteData.checkins.getJSONObject(Globo.prefid)
		// .getString("name"));
		// status.setText(RemoteData.checkins.getJSONObject(Globo.prefid)
		// .getString("status"));
		// location_name.setText(RemoteData.checkins.getJSONObject(
		// Globo.prefid).getString("location_name"));
		// if (RemoteData.face[Globo.prefid] != null) {
		// face.setImageBitmap(RemoteData.face[Globo.prefid]);
		// } else {
		// Message m = new Message();
		// m.what = 1;
		// m.arg1 = Globo.prefid;
		// myHandler.sendMessage(m);
		// }
		//
		// }
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		ListView listView = (ListView) findViewById(R.id.listView1);
		myadapter = new MyAdapter(this, f.cheak);
		listView.setAdapter(myadapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getParent(), ActiveDetailPage.class);
				// Log.e("press", arg2 + "");
				intent.putExtra("cheak", arg2);
				intent.putExtra("id", myId);
				TabGroupActivity parentActivity = (TabGroupActivity) getParent();
				parentActivity.startChildActivity("ActiveDetailPage", intent);

			}

		});
		// for (int i = 0; i < RemoteData.checkins.length(); i++) {
		// JSONObject j;
		// try {
		//
		// j = RemoteData.checkins.getJSONObject(i);
		// if (j.getString("id").equals(myId)) {
		// myadapter.array.add(new Act(j.getString("tag"), j
		// .getString("location_name"), j
		// .getString("create_time"), i));
		// }
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		myadapter.notifyDataSetChanged();

		Globo.prefid = -1;

	}
}
