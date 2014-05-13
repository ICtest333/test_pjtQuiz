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
			
			text.setText("�̸�: "+name+"\n"); // ù�׸� ���϶�, . setText()!! �����
			text.append("���� : "+korean+"\n"); // �ι�°���ʹ�, .append() !!! �����̱�
			text.append("���� : "+math+"\n");
			text.append("���� : "+english+"\n");
			text.append("���� : "+sum+"\n");
			text.append("��� : "+avg+"\n");
		}
	}	
}
	
