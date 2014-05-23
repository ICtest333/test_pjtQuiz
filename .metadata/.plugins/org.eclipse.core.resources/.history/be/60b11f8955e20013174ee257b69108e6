package kr.pjt.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SqlActivity  extends Activity{

	TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz); 
		
		text = (TextView)findViewById(R.id.textView1);
		
		Intent i = getIntent();
		Bundle b = i.getExtras();

		if(b==null){
			//데이터 보관하는 Bundle객체가 null이면 전달할 데이터가 존재하지않음을 의미함
			text.append("\n\n저장된 데이터가 없습니다.");
		}else{
			String msg = b.getString("msg");
			text.append("\n\n"+msg);
		}
		
	}
}
