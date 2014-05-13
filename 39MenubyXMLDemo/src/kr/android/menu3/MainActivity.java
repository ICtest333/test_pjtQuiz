package kr.android.menu3;

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
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		//메뉴 생성 및 등록
		
		//MenuInflater : xml 형태로 되어 있는 메뉴를 얽어 들여 객체로 생성하고 메뉴를 등록
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
		case R.id.seoul :
			Toast.makeText(this, "서울", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.busan :
			Toast.makeText(this, "부산", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.suwon :
			Toast.makeText(this, "수원", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.jeju :
			Toast.makeText(this, "제주", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item); //기본값
	}

	

}
