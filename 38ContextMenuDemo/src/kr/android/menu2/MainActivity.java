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

	String[] items = {"����","���","���","��","��","����","����","ȣ��","��","����","īī��","������","�ٳ���","��"};
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		
		setListAdapter(adapter);
		
		//���ؽ�Ʈ �޴��� ������ view ��ü�� ��� (����Ʈ�並 �ڴ����� ������ �޴� Ƣ�����...)
		registerForContextMenu(getListView());
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
	
		//���ؽ�Ʈ �޴� ����
		menu.setHeaderTitle("Divider ����");
		menu.setHeaderIcon(R.drawable.ic_launcher);
		menu.add(Menu.NONE, 1, Menu.NONE, "16�ȼ�");
		menu.add(Menu.NONE, 2, Menu.NONE, "24�ȼ�");
		menu.add(Menu.NONE, 3, Menu.NONE, "32�ȼ�");
		menu.add(Menu.NONE, 4, Menu.NONE, "40�ȼ�");
	}
	
	//���ؽ�Ʈ �޴��� �������� Ŭ���� �̺�Ʈ �߻��� ���� ó��
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
