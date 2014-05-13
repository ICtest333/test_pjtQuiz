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

		// 이 코드는 현재 액티비티의 액션바를 얻고(getActionBar())
		//상단 왼쪽에 있는 아이콘을 클릭했을 때 어플리케이션 홈으로 이동할 수 있게 해주는 코드
		
		//홈으로 갈 수 있도록 링크할 때, 타이틀이 같이 보여지도록 함
		// API 14 (android:minSdkVersion="14") 부터 사용 가능 : setHomeButtonEnabled() : 꺽쇠표시(<)없음
		//getActionBar().setHomeButtonEnabled(true);
		
		//홈으로 가거나 상위 메뉴로 이동할 경우 타이틀이 기본적으로 보여지지 않도록 할 수 있음
		// API 11 (android:minSdkVersion="11") 부터 사용 가능
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);
		//타이틀이 보여지게 처리
		getActionBar().setDisplayShowTitleEnabled(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		// MenuInflater : xml 파일에 메뉴정보를 읽어들여 메뉴 등록
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
			Toast.makeText(this, "홈으로", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_search:
			Toast.makeText(this, "검색", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.itemChat:
			Toast.makeText(this, "채팅", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.itemEmail:
			Toast.makeText(this, "이메일", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_settings:
			Toast.makeText(this, "셋팅", Toast.LENGTH_SHORT).show();
			return true;
		}
				
		return super.onOptionsItemSelected(item);
		
	}

}
