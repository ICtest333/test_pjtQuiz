//�ɼǽ� �޴� (Options Menu)
package kr.android.menu;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity { // �������ִ� id ���� ListActivity ��� �ʼ� !!!

	String[] items = {"����","���","���","��","��","����","����","ȣ��","��","����","īī��","������","�ٳ���","��"};
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		
		//ListView�� ����� ��ü ���
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		
		/*�޴� ����
		 groupid : �޴� ������ �׷� ����, �������� Menu.NONE=0 
		 itemid : �޴� �����ۿ� �ο��� ID
		 order : �޴� �������� ǥ�õ� ����, �������� Menu.NONE=0
		 title : �޴� �����ۿ� ǥ�õ� text
		 * */
		menu.add(Menu.NONE, 1, Menu.NONE, "16�ȼ�");
		menu.add(Menu.NONE, 2, Menu.NONE, "24�ȼ�");
		menu.add(Menu.NONE, 3, Menu.NONE, "32�ȼ�");
		menu.add(Menu.NONE, 4, Menu.NONE, "40�ȼ�");
		
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
		
		//�޴� Ŭ���� �̺�Ʈ ó��
		switch(item.getItemId()){
		case 1:
			getListView().setDividerHeight(16); // ����Ʈ�����۰� ���м�(Divider)�� ����(�β�) ����
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
