//옵션스 메뉴 (Options Menu)
package kr.android.menu;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity { // 정해져있는 id 사용시 ListActivity 상속 필수 !!!

	String[] items = {"포도","사과","멜론","배","귤","망고","땅콩","호두","밤","대추","카카오","오렌지","바나나","감"};
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		
		//ListView에 어댑터 객체 등록
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		
		/*메뉴 생성
		 groupid : 메뉴 아이템 그룹 지정, 미지정시 Menu.NONE=0 
		 itemid : 메뉴 아이템에 부여된 ID
		 order : 메뉴 아이템이 표시될 순서, 미지정시 Menu.NONE=0
		 title : 메뉴 아이템에 표시될 text
		 * */
		menu.add(Menu.NONE, 1, Menu.NONE, "16픽셀");
		menu.add(Menu.NONE, 2, Menu.NONE, "24픽셀");
		menu.add(Menu.NONE, 3, Menu.NONE, "32픽셀");
		menu.add(Menu.NONE, 4, Menu.NONE, "40픽셀");
		
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
		}
		return super.onOptionsItemSelected(item);*/
		
		//메뉴 클릭시 이벤트 처리
		switch(item.getItemId()){
		case 1:
			getListView().setDividerHeight(16); // 리스트아이템간 구분선(Divider)의 높이(두께) 지정
			break;
		case 2:
			getListView().setDividerHeight(24);
			break;
		case 3:
			getListView().setDividerHeight(32);
			break;
		case 4:
			getListView().setDividerHeight(40);
			break;
		}
		return true;
	}

	

}
