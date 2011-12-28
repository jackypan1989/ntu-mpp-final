package lab.mpp;

import java.util.ArrayList;

import org.json.JSONException;

import ntu.csie.mpp.util.LocalData;
import ntu.csie.mpp.util.RemoteData;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyOverlay extends ItemizedOverlay {
	boolean flagMove = false;
	int[] c = new int[100];
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
		Log.e("log", "alert touch");
		if (me.getAction() == MotionEvent.ACTION_MOVE) {
			flagMove = true;
		} else if (me.getAction() == MotionEvent.ACTION_UP) {
			Log.e("log", "alert up");
			// Log.e("log", me.getX() + " " + me.getY());
			// Log.e("log", g.getLatitudeE6() + " " + g.getLongitudeE6());

			int cnt = 0;
			if (!flagMove) {

				try {
					if (RemoteData.checkins != null) {
						for (int i = 0; i < RemoteData.checkins.length(); i++) {

							GeoPoint p = new GeoPoint(
									(int) (RemoteData.checkins.getJSONObject(i)
											.getDouble("latitude") * 1000000),
									(int) (RemoteData.checkins.getJSONObject(i)
											.getDouble("longitude") * 1000000));
							Point xy = mp.getProjection().toPixels(p, null);

							if (xy != null) {
								// Log.e("log", xy.x + " " + me.getX());
								if (Math.abs(xy.x - me.getX() + 50) < 50
										&& Math.abs(xy.y - me.getY() + 50) < 50) {

									// Globo.prefid = i;
									// MPPFinalActivity.goTo(2);
									// return true;
									c[cnt] = i;
									cnt++;
								}

							}
						}
					}

					GeoPoint p = new GeoPoint(
							(int) (LocalData.latitude * 1000000),
							(int) (LocalData.longitude * 1000000));
					Point xy = mp.getProjection().toPixels(p, null);

					if (xy != null) {
						// Log.e("log", xy.x + " " + me.getX());
						if (Math.abs(xy.x - me.getX() + 50) < 50
								&& Math.abs(xy.y - me.getY() + 50) < 50) {

							// Globo.prefid = -1;
							// MPPFinalActivity.goTo(2);
							// return true;
							c[cnt] = -1;
							cnt++;
						}

					}

					if (cnt == 1) {
						Globo.prefid = c[0];
						MPPFinalActivity.goTo(2);
						return true;
					} else if (cnt > 1) {
						Log.e("log", "alert cnt");
						String[] nameArray = new String[cnt];
						for (int i = 0; i < cnt; i++) {
							if (c[i] != -1) {
								nameArray[i] = RemoteData.checkins
										.getJSONObject(c[i]).getString("name");
							} else {
								nameArray[i] = LocalData.fb_name;
							}
						}
						Builder nearIcon = new Builder(
								((Activity) (mp.getContext())).getParent());
						nearIcon.setTitle("您點選的人是")
								.setItems(nameArray,
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												Globo.prefid = c[which];
												MPPFinalActivity.goTo(2);
											}
										}).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			flagMove = false;
		}
		return false;
	}
}