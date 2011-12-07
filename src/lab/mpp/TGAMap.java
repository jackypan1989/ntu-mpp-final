package lab.mpp;

import android.content.Intent;
import android.os.Bundle;

public class TGAMap extends TabGroupActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("MapPage", new Intent(this,
				MapPage.class));
	}

	@Override
	public void onBackPressed() {
		getLocalActivityManager().getCurrentActivity().finish();
	}
}
