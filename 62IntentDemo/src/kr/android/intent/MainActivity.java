package kr.android.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		int[] btns = {R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6};
		for(int btn : btns){
			Button b = (Button)findViewById(btn);
			b.setOnClickListener(this);
		}
		
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		// Uri �� ���� ����̽��� ŸAPP Activity ȣ���ϴ� ����
		if(v.getId()==R.id.button1){ 
			//�������� ǥ��
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
		}else if(v.getId()==R.id.button2){					//http:������ȣ�� ��Ű��
			//��ȭ ���� (Intent.ACTION_CALL : ��ȭ�ٷΰŴ� ���� -> �����ʿ� => AndroidManifest �����߰�)
			intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-3287-5002"));
		}else if(v.getId()==R.id.button3){					//tel:
			//���̾� ǥ��(��ȭ���� ��� ���̾󷯰� ǥ�õǰ� ������ ��쿣 �������� �ҿ�)
			intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-3287-5002"));			
		}else if(v.getId()==R.id.button4){
			//�޽��� ǥ��
			intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:010-2345-6789"));
		}else if(v.getId()==R.id.button5){
			//����ȭ�� ȣ��
			intent = new Intent("anroid.settings.SETTINGS");
		}else if(v.getId()==R.id.button6){
			//���� ǥ��(����� ���۸� ȣ��)
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Seoul"));
			                                                 //��ǥ���� �ʰ� ���� ǥ�����.
		}
		
		
		startActivity(intent);		
	}

	
}
