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
		Intent intent = new Intent(this, NewsService.class); // 인텐트 : 서비스 매개역할
		
		if(v.getId()==R.id.newsstart){
			//서비스 시작
			startService(intent); //스타트버튼클릭시 서비스시작 메서드 호출
			
		}else if(v.getId()==R.id.newsend){
			// 서비스 끝
			stopService(intent); //엔드버튼클릭시 서비스종료 메서드 호출
			
		}
		
	}

	

}
