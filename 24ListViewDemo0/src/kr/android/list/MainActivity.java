package kr.android.list;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	String[] items = {"서울","부산","광주","인천","여수","목포","제주","포항","대구","통영"};
	
	TextView text;
	ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		text = (TextView)findViewById(R.id.textView1);
		list = (ListView)findViewById(R.id.listView1);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
		list.setAdapter(adapter);
	}
	
}
