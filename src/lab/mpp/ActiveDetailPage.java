package lab.mpp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ntu.csie.mpp.util.LocalData;
import ntu.csie.mpp.util.RemoteData;
import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActiveDetailPage extends Activity {
	boolean flagHasRun = false;
	int cheakId = -1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actdetail);
		cheakId = this.getIntent().getIntExtra("id", -1);
		TextView name = (TextView) findViewById(R.id.textView1);
		TextView status = (TextView) findViewById(R.id.textView2);
		TextView location_name = (TextView) findViewById(R.id.textView3);
		TextView actname = (TextView) findViewById(R.id.textView4);
		TextView friname = (TextView) findViewById(R.id.textView6);
		ImageView face = (ImageView) findViewById(R.id.imageView1);
		try {
			JSONObject j = RemoteData.checkins.getJSONObject(cheakId);
			if (cheakId != -1) {
				name.setText(j.getString("name"));
				status.setText(j.getString("status"));
				location_name.setText(j.getString("location_name"));
				actname.setText("活動內容:" + j.getString("tag"));
				if (RemoteData.face[cheakId] != null) {
					face.setImageBitmap(RemoteData.face[cheakId]);
				}

				JSONArray a = j.getJSONArray("with_friends");
				String s = "";
				for (int i = 0; i < a.length(); i++) {

					s += a.getJSONObject(0).getString("name") + "\n";
				}
				friname.setText(s);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("fri", "err");
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		if (flagHasRun) {
			finish();
		} else {
			flagHasRun = true;
		}
	}

	// @Override
	// public void onBackPressed() {
	// Globo.prefid = cheakId;
	// Log.e("back",Globo.prefid+"");
	// super.onBackPressed();
	//
	// }
	@Override
	public void onPause() {
		Globo.prefid = cheakId;
		// Log.e("back",Globo.prefid+"");
		super.onPause();

	}

}
