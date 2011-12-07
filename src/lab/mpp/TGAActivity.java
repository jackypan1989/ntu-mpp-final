package lab.mpp;

import android.content.Intent;
import android.os.Bundle;

public class TGAActivity extends TabGroupActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("ActivityPage", new Intent(this,
				ActivityPage.class));
	}

	@Override
	public void onBackPressed() {
		getLocalActivityManager().getCurrentActivity().finish();
	}
}
