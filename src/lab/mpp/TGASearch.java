package lab.mpp;

import android.content.Intent;
import android.os.Bundle;

public class TGASearch extends TabGroupActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("SearchPage", new Intent(this, SearchPage.class));
	}

	@Override
	public void onBackPressed() {
		getLocalActivityManager().getCurrentActivity().finish();
	}
}
