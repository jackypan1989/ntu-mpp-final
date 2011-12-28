package lab.mpp;

import org.json.JSONException;

import ntu.csie.mpp.util.RemoteData;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
		TextView text = (TextView) findViewById(R.id.textView1);
		try {
			text.setText(RemoteData.checkins.getJSONObject(cheakId).getString(
					"name")
					+ "\n"
					+ RemoteData.checkins.getJSONObject(cheakId).getString(
							"tag")
					+ "\n"
					+ RemoteData.checkins.getJSONObject(cheakId).getString(
							"update_time"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
