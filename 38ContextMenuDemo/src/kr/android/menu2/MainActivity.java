//ContextMenu
package kr.android.menu2;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {

	String[] items = {"포도","사과","멜론","배","귤","망고","땅콩","호두","밤","대추","카카오","오렌지","바나나","감"};
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		
		setListAdapter(adapter);
		
		//컨텍스트 메뉴를 연결할 view 객체를 등록 (리스트뷰를 꾹누르고 있으면 메뉴 튀어나오게...)
		registerForContextMenu(getListView());
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
	
		//컨텍스트 메뉴 생성
		menu.setHeaderTitle("Divider 변경");
		menu.setHeaderIcon(R.drawable.ic_launcher);
		menu.add(Menu.NONE, 1, Menu.NONE, "16픽셀");
		menu.add(Menu.NONE, 2, Menu.NONE, "24픽셀");
		menu.add(Menu.NONE, 3, Menu.NONE, "32픽셀");
		menu.add(Menu.NONE, 4, Menu.NONE, "40픽셀");
	}
	
	//컨텍스트 메뉴의 아이템을 클릭시 이벤트 발생에 대한 처리
	@Override
	public boolean onContextItemSelected(MenuItem item){
		
		switch(item.getItemId()){
		case 1:
			getListView().setDividerHeight(16);
			return true;
		case 2:
			getListView().setDividerHeight(24);
			return true;
		case 3:
			getListView().setDividerHeight(32);
			return true;
		case 4:
			getListView().setDividerHeight(40);
			return true;
		}
		
		return false;
	}
}
