package lab.mpp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private Context context;

	private class ViewContainer {

		ImageView creationImage;
		TextView creationTitle;
		TextView creationDate;

	}

	public MyAdapter(Context context) {
		this.context = context;
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
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(R.layout.listitem, null);

			// Create and set ViewContainer

			viewContainer.creationImage = (ImageView) convertView
					.findViewById(R.id.item_view_item_img);
			viewContainer.creationTitle = (TextView) convertView
					.findViewById(R.id.title);
			viewContainer.creationDate = (TextView) convertView
					.findViewById(R.id.date);

			convertView.setTag(viewContainer);

		} else {
			viewContainer = (ViewContainer) convertView.getTag();
		}
		// 填入Title

		viewContainer.creationTitle.setText("title");
		viewContainer.creationDate.setText("date");
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

}
