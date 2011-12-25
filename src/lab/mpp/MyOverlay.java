package lab.mpp;

import java.util.ArrayList;

import org.json.JSONException;

import ntu.csie.mpp.util.LocalData;
import ntu.csie.mpp.util.RemoteData;

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

			try {
				for (int i = 0; i < RemoteData.checkins.length(); i++) {

					GeoPoint p = new GeoPoint((int) (RemoteData.checkins
							.getJSONObject(i).getDouble("latitude") * 1000000),
							(int) (RemoteData.checkins.getJSONObject(i)
									.getDouble("longitude") * 1000000));
					Point xy = mp.getProjection().toPixels(p, null);

					if (xy != null) {
						Log.e("log", xy.x + " " + me.getX());
						if (Math.abs(xy.x - me.getX()) < 100
								&& Math.abs(xy.y - me.getY()) < 100) {

							Globo.prefid = i;
							MPPFinalActivity.goTo(2);
							return true;
						}

					}
				}

				GeoPoint p = new GeoPoint((int) (LocalData.latitude * 1000000),
						(int) (LocalData.longitude * 1000000));
				Point xy = mp.getProjection().toPixels(p, null);

				if (xy != null) {
//					Log.e("log", xy.x + " " + me.getX());
					if (Math.abs(xy.x - me.getX()) < 100
							&& Math.abs(xy.y - me.getY()) < 100) {

						Globo.prefid = -1;
						MPPFinalActivity.goTo(2);
						return true;
					}

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
}