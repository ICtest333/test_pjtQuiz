package kr.android.move;

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
		
		text = (TextView)findViewById(R.id.textView1);
		
		Intent i = getIntent();
		Bundle b = i.getExtras();

		if(b==null){
			//������ �����ϴ� Bundle��ü�� null�̸� ������ �����Ͱ� �������������� �ǹ���
			text.append("\n\n����� �����Ͱ� �����ϴ�.");
		}else{
			String msg = b.getString("msg");
			text.append("\n\n"+msg);
		}
		
	}
	
}
