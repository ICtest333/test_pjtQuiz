package kr.android.move2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity{

	TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_main); 
		
		text = (TextView)findViewById(R.id.text1);
		
		Intent i = getIntent();
		Bundle b = i.getExtras();


		if(b!=null){
			String name = b.getString("name");
			String korean = b.getString("korean");
			String math = b.getString("math");
			String english = b.getString("english");
			int sum = Integer.parseInt(korean)+Integer.parseInt(math)+Integer.parseInt(english);
			
			int avg = sum/3;
			
			text.setText("이름: "+name+"\n"); // 첫항목 붙일때, . setText()!! 덮어쓰기
			text.append("국어 : "+korean+"\n"); // 두번째부터는, .append() !!! 덧붙이기
			text.append("수학 : "+math+"\n");
			text.append("영어 : "+english+"\n");
			text.append("총점 : "+sum+"\n");
			text.append("평균 : "+avg+"\n");
		}
	}	
}
	
