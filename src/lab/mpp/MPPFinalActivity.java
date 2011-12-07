package lab.mpp;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MPPFinalActivity extends TabActivity {
	/** Called when the activity is first created. */
	TabHost tabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		tabHost = getTabHost();
		addTab("Home", new Intent(this, TGAHome.class), getResources()
				.getDrawable(R.drawable.icon));
		addTab("Map", new Intent(this, TGAMap.class), getResources()
				.getDrawable(R.drawable.icon));
		addTab("Search", new Intent(this, TGASearch.class), getResources()
				.getDrawable(R.drawable.icon));
		addTab("Activity", new Intent(this, TGAActivity.class), getResources()
				.getDrawable(R.drawable.icon));

		tabHost.setCurrentTab(0);
	}

	void addTab(String name, Intent intent, Drawable pic) {
		View tabview = LayoutInflater.from(tabHost.getContext()).inflate(
				R.layout.tab, null);

		tabview.setBackgroundDrawable(pic);

		TabSpec setContent = tabHost.newTabSpec(name).setIndicator(tabview)
				.setContent(intent);

		tabHost.addTab(setContent);
	}
}