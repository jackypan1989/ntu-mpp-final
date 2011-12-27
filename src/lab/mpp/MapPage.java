package lab.mpp;

import java.util.List;

import org.json.JSONException;

import ntu.csie.mpp.util.LocalData;
import ntu.csie.mpp.util.RemoteData;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MapPage extends MapActivity implements LocationListener {
	MapView map;
	MapController mc;
	boolean flagHasDraw = false;
	Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message m) {
			switch (m.what) {
			case 0:
				Log.e("log", "waitgps");
				if (LocalData.latitude != 0 && LocalData.longitude != 0) {

					GeoPoint p = new GeoPoint(
							(int) (LocalData.latitude * 1000000),
							(int) (LocalData.longitude * 1000000));
					mc.setCenter(p);
					pin.addOverlay(new OverlayItem(p, "", ""));

					mc.setCenter(p);
					flagHasDraw = true;

				} else {
					this.sendEmptyMessageDelayed(0, 3000);

				}

				break;
			case 1:
				Log.e("log", "waitpic");
				if (Globo.flagPicLoad) {
					try {

						for (int i = 0; i < RemoteData.checkins.length(); i++) {
							Log.e("log", "add");
							GeoPoint p = new GeoPoint(
									(int) (RemoteData.checkins.getJSONObject(i)
											.getDouble("latitude") * 1000000),
									(int) (RemoteData.checkins.getJSONObject(i)
											.getDouble("longitude") * 1000000));
							// mc.setCenter(p);
							OverlayItem o = new OverlayItem(p, "", "");
							Matrix ma = new Matrix();
							ma.postScale(2, 2);
							Bitmap b = BitmapFactory.decodeResource(
									MapPage.this.getResources(),
									R.drawable.icon);
							if (RemoteData.face[i] != null) {
								b = Bitmap.createBitmap(RemoteData.face[i], 0,
										0, 50, 50, ma, true);
							}
							BitmapDrawable bd = new BitmapDrawable(b);
							bd.setBounds(0, 0, bd.getIntrinsicWidth(),
									bd.getIntrinsicHeight());
							o.setMarker(bd);
							pin.addOverlay(o);

						}
						sendEmptyMessage(0);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (Globo.flagHasInternet) {
					sendEmptyMessageDelayed(1, 1000);
				}
				break;
			}
		}
	};
	MyOverlay pin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		map = (MapView) findViewById(R.id.mapview);
		map.setBuiltInZoomControls(true);
		mc = map.getController();
		mc.setZoom(18);
		pin = new MyOverlay(MapPage.this.getResources().getDrawable(
				R.drawable.map_pin));
		map.getOverlays().add(pin);
		myHandler.sendEmptyMessage(1);
	}

	@Override
	public void onResume() {
		super.onResume();
		// this.getParent().getParent().setTitle("MapPage");
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}