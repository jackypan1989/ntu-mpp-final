package lab.mpp;

import android.content.Intent;
import android.os.Bundle;

public class TGAHome extends TabGroupActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("HomePage", new Intent(this,
				HomePage.class));
	}

	@Override
	public void onBackPressed() {
		getLocalActivityManager().getCurrentActivity().finish();
	}
}
