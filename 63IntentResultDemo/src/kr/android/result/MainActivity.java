package kr.android.result;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	Button btn1, btn2;
	// ȣ���� Activity �ĺ��� ���� �ĺ���
	private final int SHOW_ACTIVITY_ONE = 1; 
	private final int SHOW_ACTIVITY_TWO = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn1 =(Button)findViewById(R.id.button1);
		btn2 =(Button)findViewById(R.id.button2);
		
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
				
	}

	@Override
	public void onClick(View v) {
		// add unimplemented method
		Intent i = null;
		if(v.getId()==R.id.button1){
			//IntentTwo ȣ��
			i = new Intent(this, IntentTwo.class);
			startActivityForResult(i, SHOW_ACTIVITY_ONE);
			//��������������޼��� // SHOW_ACTIVITY_ONE : requestCode
		}else if(v.getId()==R.id.button2){
			//�ּҷ� ȣ��
			i = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
			startActivityForResult(i, SHOW_ACTIVITY_TWO);			
		}
		
	}
	
	// Activity ȣ��� ������� �޴� �޼���
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data); // resultCode : OK or Cancel
		switch(requestCode){
		case SHOW_ACTIVITY_ONE:
			//IntentTwo ��Ƽ��Ƽ�� ����� ó��
			if(resultCode==Activity.RESULT_OK){
				String result = data.getStringExtra("msg");
				Toast.makeText(this, "����:"+result, Toast.LENGTH_SHORT).show();
			}else if(resultCode==Activity.RESULT_CANCELED){
				Toast.makeText(this, "���", Toast.LENGTH_SHORT).show();
			}
			break;
		case SHOW_ACTIVITY_TWO:
			//�ּҷ� ��Ƽ��Ƽ���� ������� ����� ó��
			if(resultCode==Activity.RESULT_OK){
				Toast.makeText(this, "����:"+data.getData().toString(), Toast.LENGTH_SHORT).show();
				                            // getData() : Uri ���·� ��ȯ
											// "����: content://contacts/people/1",2,3 �� ��ȯ
			}else if(resultCode==Activity.RESULT_CANCELED){
				Toast.makeText(this, "���", Toast.LENGTH_SHORT).show();
			}
			break; //������case�� break; ��������
		}//switch
		
	} // onActivityResult
	
}
