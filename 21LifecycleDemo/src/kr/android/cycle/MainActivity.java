package kr.android.cycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	public static final String TAG = "Lifecycle"; //"Lifecycle"에 대한 로그를 찍기 위한 태그 설정
	
	//Activity가 생성된 이후에 호출
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.i(TAG,"onCreate실행");
				
	}
	
	//onCreate가 호출된 이후 또는 onRestart가 호출된 이후에 연속적으로 호출됨
	@Override
	protected void onStart(){
		super.onStart(); // 의무적인 코드
		Log.i(TAG, "onStart실행");
	}
	
	//Activity가 백그라운드 상태에서 포그라운드 상태가 되려고 할때 최초에 호출
	@Override
	protected void onRestart(){
		super.onRestart();Log.i(TAG, "onRestart실행");
	}
	
	//Activity가 포그라운드 상태가 되기 직전에 호출
	@Override
	protected void onResume(){
		super.onResume();
		Log.i(TAG, "onResume실행");
	}
	
	//Activity가 백그라운드 상태가 되기전에 호출
	@Override
	protected void onPause(){
		super.onPause();
		Log.i(TAG, "onPause실행");
	}
	
	//Activity가 백그라운드 상태가 되면 호출
	@Override
	protected void onStop(){
		super.onStop();
		Log.i(TAG, "onStop실행");
	}
	
	//Activity가 종료될 때 호출
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.i(TAG, "onDestroy실행");
	}



}
