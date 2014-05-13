package kr.android.contact;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	Button pickBtn, viewBtn;
	//Uri contact;
	Intent i;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		pickBtn = (Button)findViewById(R.id.pick);
		viewBtn = (Button)findViewById(R.id.view);
		pickBtn.setOnClickListener(this);
		viewBtn.setOnClickListener(this);
		
	}


	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.pick){
			//contact = Uri.parse("content://contacts/people");
			//i = new Intent(Intent.ACTION_PICK, contact); // 아래처럼 변수절감!!
			i = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
														//"content:스키마
		}else if(v.getId()==R.id.view){
			i = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/1")); 
																		// 1 : primary key
		}
		startActivity(i); // i : intent
	} // onClick
	
}
