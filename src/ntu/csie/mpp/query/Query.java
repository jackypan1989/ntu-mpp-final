package ntu.csie.mpp.query;

import ntu.csie.mpp.util.LocalData;

import org.json.*;

import android.util.Log;

import java.util.*;

public class Query {

	static String host;
	static List<String> friends = new ArrayList();
	static Map<String, List<String>> photos = new HashMap();

	final static int USER = 0;
	final static int PHOTO = 1;
	static BPRQuery bprQ;

	public static void setPhoto(JSONObject fbData) {

		try {
			// Log.e("host","1");
			// host = fbData.getString("id");
			host = LocalData.fb_id;
			// Log.e("host","2 "+host);
			JSONArray jPhotos = fbData.getJSONArray(
					"data");

			for (int i = 0; i < jPhotos.length(); i++) {
				List<String> tags = new ArrayList();
				photos.put(jPhotos.getJSONObject(i).getString("id"), tags);
				JSONArray jTags = jPhotos.getJSONObject(i)
						.getJSONObject("tags").getJSONArray("data");
				for (int j = 0; j < jTags.length(); j++)
					tags.add(jTags.getJSONObject(j).getString("id"));
			}

			BPRTrain bpr = new BPRTrain(10);
			bpr.addEntity(host, USER);
			List<String> host_friends = new ArrayList();
			host_friends.add(host);
			for (String f : friends) {
				bpr.addEntity(f, USER);
				host_friends.add(f);
			}
			bpr.addData(host_friends);

			for (String photo : photos.keySet()) {
				List<String> photo_tags = new ArrayList();
				bpr.addEntity(photo, PHOTO);
				photo_tags.add(photo);
				for (String tag : photos.get(photo)) {
					if (!bpr.containsEntity(tag))
						bpr.addEntity(tag, USER);
					photo_tags.add(tag);
				}
				bpr.addData(photo_tags);
			}

			// System.out.println("# of photos="+photos.size());

			for (int i = 0; i < 10; i++) {
				bpr.trainUniform(1000);
				// System.out.println(bpr.sampleTrainErr());
			}
			bprQ = new BPRQuery(bpr);

		} catch (Exception e) {
			Log.e("query", "error");
			e.printStackTrace();
			// System.exit(0);
		}
	}

	public static List<String> queryWithHost() {
		return bprQ.query(host, USER);
	}
}
