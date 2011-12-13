package lab.mpp;

import android.content.Intent;
import android.os.Bundle;

public class TGAProfile extends TabGroupActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startChildActivity("ProfilePage", new Intent(this, ProfilePage.class));
	}

	@Override
	public void onBackPressed() {
		getLocalActivityManager().getCurrentActivity().finish();
	}
}
