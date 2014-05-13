package kr.android.frame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView img;
	Button btn;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		img = (ImageView)findViewById(R.id.img);
		btn = (Button)findViewById(R.id.btn);
		btn.setOnClickListener( //�͸���Ŭ������ �̺�Ʈ�ҽ��� �̺�Ʈ�ڵ鷯 ����
				                new View.OnClickListener() {// start of �͸���Ŭ����
			
		    //�̺�Ʈ �ڵ鷯
			@Override
			public void onClick(View v) {
				if(img.getVisibility() == View.VISIBLE){// View �������� �ִ� ��� 
					img.setVisibility(View.INVISIBLE); // -> �Ⱥ�������
				}else{// View �Ⱥ������� �ִ� ���
					img.setVisibility(View.VISIBLE); // -> �������� (�̺�Ʈó��: �������)
				}
				
			}
		}); // end of �͸���Ŭ����
		
	}

	

}
