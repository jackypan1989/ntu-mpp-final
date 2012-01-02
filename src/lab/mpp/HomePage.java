package lab.mpp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import ntu.csie.mpp.util.LocalData;
import ntu.csie.mpp.util.RemoteData;
import ntu.csie.mpp.query.Query;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class HomePage extends Activity {
	public static final String TAG = "HomePage";
	// set check list to show
	private ListView checkinList;
	// private ArrayList<HashMap<String, Object>> checkinListItem = new
	// ArrayList<HashMap<String, Object>>();
	// private SimpleAdapter checkinListItemAdapter;
	private CheckinListAdapter checkinListItemAdapter;

	// set list content
	// private ArrayList<String> idList = new ArrayList<String>();
	// private ArrayList<String> nameList = new ArrayList<String>();
	// private ArrayList<String> locationNameList = new ArrayList<String>();
	// private ArrayList<String> statusList = new ArrayList<String>();
	// private ArrayList<String> tagList = new ArrayList<String>();
	// private ArrayList<String> dateTimeList = new ArrayList<String>();
	boolean flagHasPic = false;

	Handler h = new Handler() {
		@Override
		public void handleMessage(Message m) {
			switch (m.what) {
			case 0:
				Log.e("log", "wait");
				if (Globo.flagStringLoad) {
					updateCheckinList();
					query();

				} else if (Globo.flagHasInternet) {

					sendEmptyMessageDelayed(0, 1000);
				} else {
					Log.e("log", "nointernet");
				}
				break;
			case 1:
				// if (RemoteData.face != null && RemoteData.face[m.arg1] !=
				// null) {
				if (RemoteData.friend.get(m.arg1).getBitmap() != null) {
					// updateCheckinList();
					updateCheckinList();
				} else {
					Message m2 = new Message();
					m2.what = 1;
					m2.arg1 = m.arg1;
					sendMessageDelayed(m2, 1000);
				}

				break;
			}
		}

	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		checkinList = (ListView) findViewById(R.id.checkinListView);
		checkinList.setAdapter(new CheckinListAdapter(this, RemoteData.friend));
		checkinList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Globo.prefid = position;
				MPPFinalActivity.goTo(2);
			}
		});
		h.sendEmptyMessage(0);

	}

	@Override
	public void onResume() {
		super.onResume();
		updateCheckinList();
		// Log.e("log", "homePage");
		// this.getParent().getParent().setTitle("HomePage");
	}

	public void updateCheckinList() {
		checkinList.setAdapter(new CheckinListAdapter(this, RemoteData.friend));
		checkinList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Globo.prefid = position;
				MPPFinalActivity.goTo(2);
			}
		});
	}

	void query() {
		if (LocalData.query == null) {
			return;
		}
		ArrayList<FriendClass> f = RemoteData.friend;
		for (FriendClass c : f) {
			Log.e("id", c.id);
		}
		try {
			for (int i = LocalData.query.length() - 1; i >= 0; i--) {
				Log.e("swap", LocalData.query.getJSONObject(i).getString("id"));
				for (int j = 0; j < f.size(); j++) {

					if (f.get(j).id.equals(LocalData.query.getJSONObject(i)
							.getString("id"))) {
						Log.e("swap", f.get(j).name);
						f.add(0, f.remove(j));
					}

				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// f.add(2, f.remove(6));
		updateCheckinList();
		Toast.makeText(this.getParent(), "swapped", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDestroy() {
		checkinList.setAdapter(null);
		super.onDestroy();
	}

	public class CheckinListAdapter extends BaseAdapter {
		private Context context;
		private ArrayList<FriendClass> f;

		private class ViewContainer {
			ImageView imageView;
			TextView nameTV;
			TextView locationNameTV;
			TextView statusTV;
			TextView dateTimeTV;
			TextView tagTV;
		}

		public CheckinListAdapter(Context context, ArrayList<FriendClass> inF) {
			this.context = context;
			f = inF;

		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewContainer viewContainer = new ViewContainer();
			if (position < RemoteData.friend.size()) {
				if (convertView == null) {
					LayoutInflater layoutInflater = (LayoutInflater) context
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = layoutInflater.inflate(
							R.layout.checkin_listitem, null);

					// Create and set ViewContainer

					viewContainer.imageView = (ImageView) convertView
							.findViewById(R.id.checkinUserImage);
					viewContainer.nameTV = (TextView) convertView
							.findViewById(R.id.checkinName);
					viewContainer.locationNameTV = (TextView) convertView
							.findViewById(R.id.checkinLocationName);
					viewContainer.statusTV = (TextView) convertView
							.findViewById(R.id.checkinStatus);
					viewContainer.tagTV = (TextView) convertView
							.findViewById(R.id.checkinTag);
					viewContainer.dateTimeTV = (TextView) convertView
							.findViewById(R.id.checkinDateTime);

					// HttpPoster hp = new HttpPoster();
					// Bitmap bm =
					// hp.getUserPic(array.get(position).get("checkinID").toString());
					Message m = new Message();
					m.what = 1;
					m.arg1 = position;
					// if (RemoteData.face == null) {
					//
					// h.sendMessageDelayed(m, 1000);
					// } else if (RemoteData.face[position] == null) {
					// h.sendMessageDelayed(m, 1000);
					if (f.get(position).getBitmap() == null) {
						h.sendMessage(m);
					} else {
						viewContainer.imageView.setImageBitmap(f.get(position)
								.getBitmap());
					}
					viewContainer.nameTV.setText(f.get(position).name);
					viewContainer.locationNameTV
							.setText(f.get(position).location_name);
					viewContainer.statusTV.setText(f.get(position).status);
					viewContainer.tagTV.setText(f.get(position).tag);
					viewContainer.dateTimeTV
							.setText(f.get(position).update_time);

					convertView.setTag(viewContainer);

				} else {
					viewContainer = (ViewContainer) convertView.getTag();
					Message m = new Message();
					m.what = 1;
					m.arg1 = position;
					if (f.get(position).getBitmap() == null) {
						h.sendMessage(m);
					} else {
						viewContainer.imageView.setImageBitmap(f.get(position)
								.getBitmap());
					}
					viewContainer.nameTV.setText(f.get(position).name);
					viewContainer.locationNameTV
							.setText(f.get(position).location_name);
					viewContainer.statusTV.setText(f.get(position).status);
					viewContainer.tagTV.setText(f.get(position).tag);
					viewContainer.dateTimeTV
							.setText(f.get(position).update_time);
					convertView.setTag(viewContainer);
				}

			}
			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (f == null) {
				return 0;
			}
			return f.size();// length.intValue();
		}

	}
}
