package kr.pjt.quiz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

public class QuizActivity extends Activity {

	String category;
	private DatabaseAdapter dbAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		dbAdapter = new DatabaseAdapter(this);
		//SQLiteDatabase °´Ã¼ »ý¼º
		dbAdapter.open();	
		
		Intent i = getIntent();
		Bundle bundle = i.getExtras();
		if(bundle!=null){
			category = bundle.getString("category");
		}
		
		Cursor c = dbAdapter.getQuizbyCategory(category);
		
	}
		
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbAdapter.close();
	}
}



