package kr.android.action;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ActionBar�� API 11 ���� ��� ����
		// AndroidManifest.xml ���ϳ� minSDKversion�� 11 �̻����� �����ؾ� ��.
		ActionBar actionBar = getActionBar();
		
		//ActionBar �����
		actionBar.hide();
		
		//ActionBar ���̱�
		actionBar.show();
		
		//Ÿ��Ʋ ����
		actionBar.setTitle("Action Bar");
		
		//����Ÿ��Ʋ ����
		actionBar.setSubtitle("SubTitle of Action Bar");
		
		//Ÿ��Ʋ ������ �����ϰ� �����ܸ� ���̰� �ϱ�
		actionBar.setDisplayShowTitleEnabled(false);
		
		
		
	}

}
