package kr.android.action2;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// �� �ڵ�� ���� ��Ƽ��Ƽ�� �׼ǹٸ� ���(getActionBar())
		//��� ���ʿ� �ִ� �������� Ŭ������ �� ���ø����̼� Ȩ���� �̵��� �� �ְ� ���ִ� �ڵ�
		
		//Ȩ���� �� �� �ֵ��� ��ũ�� ��, Ÿ��Ʋ�� ���� ���������� ��
		// API 14 (android:minSdkVersion="14") ���� ��� ���� : setHomeButtonEnabled() : ����ǥ��(<)����
		//getActionBar().setHomeButtonEnabled(true);
		
		//Ȩ���� ���ų� ���� �޴��� �̵��� ��� Ÿ��Ʋ�� �⺻������ �������� �ʵ��� �� �� ����
		// API 11 (android:minSdkVersion="11") ���� ��� ����
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);
		//Ÿ��Ʋ�� �������� ó��
		getActionBar().setDisplayShowTitleEnabled(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		// MenuInflater : xml ���Ͽ� �޴������� �о�鿩 �޴� ���
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		/*int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}*/
		
		switch(item.getItemId()){
		case android.R.id.home:
			Toast.makeText(this, "Ȩ����", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_search:
			Toast.makeText(this, "�˻�", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.itemChat:
			Toast.makeText(this, "ä��", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.itemEmail:
			Toast.makeText(this, "�̸���", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_settings:
			Toast.makeText(this, "����", Toast.LENGTH_SHORT).show();
			return true;
		}
				
		return super.onOptionsItemSelected(item);
		
	}

}
