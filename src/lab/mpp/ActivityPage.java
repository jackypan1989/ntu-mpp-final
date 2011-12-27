package lab.mpp;

import ntu.csie.mpp.util.LocalData;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;

public class ActivityPage extends Activity {
	private String[] friendNameList = {"jacky","wang"};
	private long[] selectId ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View contentView = LayoutInflater.from(this.getParent()).inflate(R.layout.act, null);
		//setContentView(R.layout.search_activity);
		setContentView(contentView); 
		
		final Activity mppFinal = this.getParent().getParent();
		
		// set button
		Button button = (Button)findViewById(R.id.recommendButton);
		button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog dialog = new Dialog(mppFinal);
				AlertDialog.Builder builder = new AlertDialog.Builder(mppFinal);
				builder.setTitle("加入朋友");
				
				final ListView modeList = new ListView(mppFinal);
				ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(mppFinal, 
						android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, friendNameList);
				modeList.setAdapter(modeAdapter);
				modeList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
				
				//
				/*modeList.setAdapter(new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_multiple_choice, lv_items));
				modeList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
				*/		
				builder.setView(modeList);
				builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface d, int i) {
			            selectId = modeList.getCheckItemIds();
			            //if(selectId.is)
			        }
			    });        
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface d, int i) {

			        }
			    });
			    
				dialog = builder.create();
				dialog.show();
			}
		});
		
		// set spinner for tag
		Spinner spinner = (Spinner)findViewById(R.id.spinner1);
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
