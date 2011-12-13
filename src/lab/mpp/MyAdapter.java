package lab.mpp;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private Context context;
	ArrayList<String> array;

	private class ViewContainer {

		ImageView activityImage;
		TextView activityName;

	}

	public MyAdapter(Context context, ArrayList<String> a) {
		this.context = context;
		array = a;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewContainer viewContainer = new ViewContainer();
		if (position < array.size()) {
			if (convertView == null) {
				LayoutInflater layoutInflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = layoutInflater.inflate(R.layout.listitem, null);

				// Create and set ViewContainer

				viewContainer.activityImage = (ImageView) convertView
						.findViewById(R.id.item_view_item_img);

				viewContainer.activityName = (TextView) convertView
						.findViewById(R.id.name);

				convertView.setTag(viewContainer);

			} else {
				viewContainer = (ViewContainer) convertView.getTag();
			}
			viewContainer.activityName.setText(array.get(position));
		}
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();// length.intValue();
	}

}
