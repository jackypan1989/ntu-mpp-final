package lab.mpp;

import ntu.csie.mpp.util.LocalData;
import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;

public class ActivityPage extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View contentView = LayoutInflater.from(this.getParent()).inflate(R.layout.act, null);
		//setContentView(R.layout.search_activity);
		setContentView(contentView); 
		
		
		// set spinner for tag
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		// create array to set adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getParent().getParent(),android.R.layout.simple_spinner_item,LocalData.tagList);
		// set dropdown view
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setPrompt("選擇活動");
		// set onclick
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			public void onItemSelected(AdapterView adapterView, View view, int position, long id){
				Toast.makeText(getParent().getParent(), "您選擇"+adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
			}
			public void onNothingSelected(AdapterView arg0) {
				Toast.makeText(getParent().getParent(), "您沒有選擇任何項目", Toast.LENGTH_SHORT).show();
			}
		});

	}
	@Override
	public void onResume(){
		super.onResume();
		this.getParent().getParent().setTitle("ActivityPage");
	}
}
