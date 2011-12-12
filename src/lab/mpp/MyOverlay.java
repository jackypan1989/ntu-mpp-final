package lab.mpp;

import java.util.ArrayList;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyOverlay extends ItemizedOverlay {
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	public MyOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub

	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	@Override
	public boolean onTouchEvent(MotionEvent me, MapView mp) {

		if (me.getAction() == MotionEvent.ACTION_UP) {

			// Log.e("log", me.getX() + " " + me.getY());
			// Log.e("log", g.getLatitudeE6() + " " + g.getLongitudeE6());
			Log.e("log", mOverlays.size() + "");
			int i = 0;
			for (OverlayItem oi : mOverlays) {

				Point xy = null;
				xy = mp.getProjection().toPixels(oi.getPoint(), null);
				if (xy != null) {
					if (Math.abs(xy.x - me.getX()) < 100
							&& Math.abs(xy.y - me.getY()) < 100) {
						Toast.makeText(mp.getContext(), "Touch" + i,
								Toast.LENGTH_SHORT).show();

						return true;
					}
				}
				i++;
			}
		}
		return false;
	}
}