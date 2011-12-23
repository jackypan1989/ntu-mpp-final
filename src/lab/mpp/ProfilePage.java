package lab.mpp;

import java.util.ArrayList;

import org.json.JSONException;

import ntu.csie.mpp.util.MyPreferences;
import ntu.csie.mpp.util.RemoteData;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class ProfilePage extends Activity {
	MyAdapter myadapter;
	Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message m) {
			if (m.what == 0) {
				myadapter.array.add("Test" + m.arg1);
				myadapter.notifyDataSetChanged();
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		ListView listView = (ListView) findViewById(R.id.listView1);

		myadapter = new MyAdapter(this, new ArrayList<String>());
		listView.setAdapter(myadapter);
		// listView.setOnItemClickListener(OnCreationListViewClickListener);

		Message m = new Message();
		m.what = 0;
		m.arg1 = 1;
		myHandler.sendMessage(m);
		m = new Message();
		m.what = 0;
		m.arg1 = 2;
		myHandler.sendMessage(m);
		m = new Message();
		m.what = 0;
		m.arg1 = 3;
		myHandler.sendMessage(m);
	}

	@Override
	public void onResume() {
		super.onResume();
		TextView t = (TextView) findViewById(R.id.textView1);
		try {
			this.getParent().getParent().setTitle("ProfilePage");
			if (Globo.prefid == -1) {
				t.setText(MyPreferences.PREF_FB_NAME);
			} else {

				t.setText(RemoteData.checkins.getJSONObject(Globo.prefid)
						.getString("name"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Globo.prefid = -1;

	}
}
