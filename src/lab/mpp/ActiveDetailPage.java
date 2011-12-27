package lab.mpp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class ActiveDetailPage extends Activity {
	boolean flagHasRun=false;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actdetail);
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

}
