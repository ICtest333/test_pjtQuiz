package kr.android.cycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	public static final String TAG = "Lifecycle"; //"Lifecycle"�� ���� �α׸� ��� ���� �±� ����
	
	//Activity�� ������ ���Ŀ� ȣ��
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.i(TAG,"onCreate����");
				
	}
	
	//onCreate�� ȣ��� ���� �Ǵ� onRestart�� ȣ��� ���Ŀ� ���������� ȣ���
	@Override
	protected void onStart(){
		super.onStart(); // �ǹ����� �ڵ�
		Log.i(TAG, "onStart����");
	}
	
	//Activity�� ��׶��� ���¿��� ���׶��� ���°� �Ƿ��� �Ҷ� ���ʿ� ȣ��
	@Override
	protected void onRestart(){
		super.onRestart();Log.i(TAG, "onRestart����");
	}
	
	//Activity�� ���׶��� ���°� �Ǳ� ������ ȣ��
	@Override
	protected void onResume(){
		super.onResume();
		Log.i(TAG, "onResume����");
	}
	
	//Activity�� ��׶��� ���°� �Ǳ����� ȣ��
	@Override
	protected void onPause(){
		super.onPause();
		Log.i(TAG, "onPause����");
	}
	
	//Activity�� ��׶��� ���°� �Ǹ� ȣ��
	@Override
	protected void onStop(){
		super.onStop();
		Log.i(TAG, "onStop����");
	}
	
	//Activity�� ����� �� ȣ��
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.i(TAG, "onDestroy����");
	}



}
