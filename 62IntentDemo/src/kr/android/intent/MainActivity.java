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
		// Uri 를 통해 디바이스내 타APP Activity 호출하는 패턴
		if(v.getId()==R.id.button1){ 
			//웹페이지 표시
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
		}else if(v.getId()==R.id.button2){					//http:브라우저호출 스키마
			//통화 시작 (Intent.ACTION_CALL : 전화바로거는 구현 -> 권한필요 => AndroidManifest 권한추가)
			intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-3287-5002"));
		}else if(v.getId()==R.id.button3){					//tel:
			//다이얼러 표시(통화시작 대신 다이얼러가 표시되게 구현할 경우엔 별도권한 불요)
			intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-3287-5002"));			
		}else if(v.getId()==R.id.button4){
			//메시지 표시
			intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:010-2345-6789"));
		}else if(v.getId()==R.id.button5){
			//설정화면 호출
			intent = new Intent("anroid.settings.SETTINGS");
		}else if(v.getId()==R.id.button6){
			//지도 표시(내장된 구글맵 호출)
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Seoul"));
			                                                 //좌표지정 않고 서울 표시토록.
		}
		
		
		startActivity(intent);		
	}

	
}
