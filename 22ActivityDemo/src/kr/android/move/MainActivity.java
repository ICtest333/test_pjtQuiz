package kr.android.move;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener{
	
	Button btn,btn2;
	EditText edit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn = (Button)findViewById(R.id.button1);
		btn2 = (Button)findViewById(R.id.button2);
		
		edit = (EditText)findViewById(R.id.editText1);

		//�̺�Ʈ�ҽ��� �̺�Ʈ������ ����
		btn.setOnClickListener(this);
		btn2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) { // v : �̺�Ʈ�߻��� �䰴ü(��ư)
		// ȭ�� �̵�, ������ ����
		Intent i = null; // Intent : ��Ƽ��Ƽ�� �Ű�����, 
		if(v.getId()==R.id.button1){//ȭ���̵� ��ưŬ���� ���(id��ġ���η��Ǵ�)
			i = new Intent(this,SecondActivity.class);
			             //����(ù��°)��Ƽ��Ƽ,�̵���(�ι�°)��Ƽ��Ƽ(��Ŭ��������)
		}else if(v.getId()==R.id.button2){//���������� ��ưŬ���� ���
			i = new Intent(this,SecondActivity.class);
			//ȭ�� �̵��ÿ� �̵��� ȭ�鿡�� ȣ���� �����͸� ����
			i.putExtra("msg", edit.getText().toString());//Ű,��� ��
		}
		//ȭ���̵�
		startActivity(i);//����Ʈ�������� �ִ� ��Ƽ��Ƽ������ �ι�°ȭ��ȣ��(ȭ���̵�)
		             //��, ��Ƽ��Ƽ=>��������(AndroidManifest.xml)�� ��ϵǾ��־����
		//���ܵ��Ƽ��Ƽ���� �����͹��� �غ��Ű������...
	}

	

}
