package lab.mpp;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

import ntu.csie.mpp.util.RemoteData;

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

public class HomePage extends Activity {
	public static final String TAG = "HomePage";
	// set check list to show
	private ListView checkinList;
	private ArrayList<HashMap<String, Object>> checkinListItem = new ArrayList<HashMap<String, Object>>();
	// private SimpleAdapter checkinListItemAdapter;
	private CheckinListAdapter checkinListItemAdapter;

	// set list content
	private ArrayList<String> idList = new ArrayList<String>();
	private ArrayList<String> nameList = new ArrayList<String>();
	private ArrayList<String> locationNameList = new ArrayList<String>();
	private ArrayList<String> statusList = new ArrayList<String>();
	private ArrayList<String> tagList = new ArrayList<String>();
	private ArrayList<String> dateTimeList = new ArrayList<String>();
	boolean flagHasPic = false;
	ProgressDialog dialog;
	Handler h = new Handler() {
		@Override
		public void handleMessage(Message m) {
			switch (m.what) {
			case 0:
				Log.e("log", "wait");
				if (Globo.flagStringLoad) {
					try {

						for (int i = 0; i < RemoteData.checkins.length(); i++) {
							JSONObject checkin = RemoteData.checkins
									.getJSONObject(i);
							idList.add(checkin.getString("id"));
							nameList.add(checkin.getString("name"));
							locationNameList.add(checkin
									.getString("location_name"));
							statusList.add(checkin.getString("status"));
							tagList.add(checkin.getString("tag"));
							dateTimeList.add(checkin.getString("create_time"));

						}
						updateCheckinList();
						 dialog.dismiss();
					} catch (JSONException e) {
						Log.e(TAG, e.toString());
					}
				} else if (Globo.flagHasInternet) {

					sendEmptyMessageDelayed(0, 1000);
				} else {
					Log.e("log", "nointernet");
				}
				break;
			case 1:
				if (RemoteData.face != null && RemoteData.face[m.arg1] != null) {
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
		dialog = ProgressDialog.show(getParent(), "",
				"Loading. Please wait...", true);
		checkinList = (ListView) findViewById(R.id.checkinListView);
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
		Log.e("log", "homePage");
		// this.getParent().getParent().setTitle("HomePage");
	}

	public void updateCheckinList() {
		checkinListItem.clear();
		for (int i = 0; i < RemoteData.checkins.length(); i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("checkinID", idList.get(i));
			map.put("checkinName", nameList.get(i));
			map.put("checkinLocationName", locationNameList.get(i));
			map.put("checkinStatus", statusList.get(i));
			map.put("checkinTag", tagList.get(i));
			map.put("checkinDateTime", dateTimeList.get(i));

			checkinListItem.add(map);
		}

		Log.e("test", checkinListItem.toString());

		// checkinListItemAdapter =new LazyAdapter(this, mStrings);
		checkinListItemAdapter = new CheckinListAdapter(this, checkinListItem);

		checkinList.setAdapter(checkinListItemAdapter);
	}

	@Override
	public void onDestroy() {
		checkinList.setAdapter(null);
		super.onDestroy();
	}

	public class CheckinListAdapter extends BaseAdapter {
		private Context context;
		ArrayList<HashMap<String, Object>> array;

		private class ViewContainer {
			ImageView imageView;
			TextView nameTV;
			TextView locationNameTV;
			TextView statusTV;
			TextView dateTimeTV;
			TextView tagTV;
		}

		public CheckinListAdapter(Context context,
				ArrayList<HashMap<String, Object>> array) {
			this.context = context;
			this.array = array;
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
			if (position < array.size()) {
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
					if (RemoteData.face == null) {

						h.sendMessageDelayed(m, 1000);
					} else if (RemoteData.face[position] == null) {
						h.sendMessageDelayed(m, 1000);
					} else {
						viewContainer.imageView
								.setImageBitmap(RemoteData.face[position]);
					}
					viewContainer.nameTV.setText(array.get(position)
							.get("checkinName").toString());
					viewContainer.locationNameTV.setText(array.get(position)
							.get("checkinLocationName").toString());
					viewContainer.statusTV.setText(array.get(position)
							.get("checkinStatus").toString());
					viewContainer.tagTV.setText(array.get(position)
							.get("checkinTag").toString());
					viewContainer.dateTimeTV.setText(array.get(position)
							.get("checkinDateTime").toString());

					convertView.setTag(viewContainer);

				} else {
					viewContainer = (ViewContainer) convertView.getTag();
					Message m = new Message();
					m.what = 1;
					m.arg1 = position;
					if (RemoteData.face == null) {

						h.sendMessageDelayed(m, 1000);
					} else if (RemoteData.face[position] == null) {
						h.sendMessageDelayed(m, 1000);
					} else {
						viewContainer.imageView
								.setImageBitmap(RemoteData.face[position]);
					}
					viewContainer.nameTV.setText(array.get(position)
							.get("checkinName").toString());
					viewContainer.locationNameTV.setText(array.get(position)
							.get("checkinLocationName").toString());
					viewContainer.statusTV.setText(array.get(position)
							.get("checkinStatus").toString());
					viewContainer.tagTV.setText(array.get(position)
							.get("checkinTag").toString());
					viewContainer.dateTimeTV.setText(array.get(position)
							.get("checkinDateTime").toString());
					convertView.setTag(viewContainer);
				}

			}
			return convertView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return array.size();// length.intValue();
		}

	}
}
