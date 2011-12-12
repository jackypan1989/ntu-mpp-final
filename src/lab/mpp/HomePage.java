package lab.mpp;

import android.app.Activity;
import android.os.Bundle;

public class HomePage extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
	}

	@Override
	public void onResume() {
		super.onResume();
		this.getParent().getParent().setTitle("HomePage");
	}
}
