package kr.android.action;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ActionBar는 API 11 부터 사용 가능
		// AndroidManifest.xml 파일내 minSDKversion을 11 이상으로 지정해야 함.
		ActionBar actionBar = getActionBar();
		
		//ActionBar 숨기기
		actionBar.hide();
		
		//ActionBar 보이기
		actionBar.show();
		
		//타이틀 셋팅
		actionBar.setTitle("Action Bar");
		
		//서브타이틀 셋팅
		actionBar.setSubtitle("SubTitle of Action Bar");
		
		//타이틀 영역을 제거하고 아이콘만 보이게 하기
		actionBar.setDisplayShowTitleEnabled(false);
		
		
		
	}

}
