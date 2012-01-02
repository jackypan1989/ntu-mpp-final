package lab.mpp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private Context context;
	ArrayList<CheakClass> array;

	private class ViewContainer {

		// ImageView activityImage;
		TextView time;
		TextView place;
		TextView name;

	}

	public MyAdapter(Context context, ArrayList<CheakClass> a) {
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

				// viewContainer.activityImage = (ImageView) convertView
				// .findViewById(R.id.item_view_item_img);

				viewContainer.time = (TextView) convertView
						.findViewById(R.id.textView1);
				viewContainer.place = (TextView) convertView
						.findViewById(R.id.textView2);
				viewContainer.name = (TextView) convertView
						.findViewById(R.id.textView3);
				convertView.setTag(viewContainer);

			} else {
				viewContainer = (ViewContainer) convertView.getTag();
			}
			viewContainer.name.setText(array.get(position).name);
			viewContainer.time.setText(array.get(position).update_time);
			viewContainer.place.setText(array.get(position).location_name);
		}
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.size();// length.intValue();
	}

}
