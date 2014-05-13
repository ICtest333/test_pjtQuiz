package kr.android.move2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity{ // implements View.OnClickListener{
	                                        // �͸���Ŭ���� Ȱ�� ���
	Button btn;
	EditText edit1,edit2,edit3,edit4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		
		edit1 = (EditText)findViewById(R.id.editText1);
		edit2 = (EditText)findViewById(R.id.editText2);
		edit3 = (EditText)findViewById(R.id.editText3);
		edit4 = (EditText)findViewById(R.id.editText4);

		//�̺�Ʈ�ҽ��� �̺�Ʈ������ ����(implements View.OnClickListener ����϶�)
		//btn.setOnClickListener(this);
		
		btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {// start of �͸���Ŭ����
			
			//�̺�Ʈ�ڵ鷯
			@Override
			public void onClick(View v) {
				Intent i = new  Intent(MainActivity.this,SecondActivity.class);
				
				i.putExtra("name", edit1.getText().toString());
				i.putExtra("korean", edit2.getText().toString());
				i.putExtra("math", edit3.getText().toString());
				i.putExtra("english", edit4.getText().toString());
				
				startActivity(i);
			}
		}); // end of �͸���Ŭ����
		
	}


}
