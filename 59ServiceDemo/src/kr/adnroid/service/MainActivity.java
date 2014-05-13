package kr.adnroid.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	Button btnstart, btnend;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnstart = (Button)findViewById(R.id.newsstart);
		btnend = (Button)findViewById(R.id.newsend);
		btnstart.setOnClickListener(this);
		btnend.setOnClickListener(this);
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, NewsService.class); // ����Ʈ : ���� �Ű�����
		
		if(v.getId()==R.id.newsstart){
			//���� ����
			startService(intent); //��ŸƮ��ưŬ���� ���񽺽��� �޼��� ȣ��
			
		}else if(v.getId()==R.id.newsend){
			// ���� ��
			stopService(intent); //�����ưŬ���� �������� �޼��� ȣ��
			
		}
		
	}

	

}
