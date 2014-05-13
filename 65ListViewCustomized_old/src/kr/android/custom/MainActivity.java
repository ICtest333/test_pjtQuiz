package kr.android.custom;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MainActivity extends ActionBarActivity {

	ArrayList<MyItem> item;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ������ ���� �� ����
		item = new ArrayList<MyItem>();
		item.add(new MyItem(R.drawable.ic_launcher, "�Ｚ ��Ʈ��"));
		item.add(new MyItem(R.drawable.ic_launcher, "LG ������"));
		item.add(new MyItem(R.drawable.ic_launcher, "���� �޴���"));
		
	} // onCreate
	
	//Ŀ���� ����� Ŭ����
	class MyListAdapter extends BaseAdapter{ // BaseAdapter : �߻�Ŭ����

		Context context;
		ArrayList<MyItem> list;
		int layout;
		LayoutInflater inflater;
		
		public MyListAdapter(Context context, int layout, ArrayList<MyItem> list){
			this.context = context;
			this.layout = layout;
			this.list = list;
			
			//ListView���� ����� View�� ������ xml�� �о���� ���� 
			inflater = LayoutInflater.from(context);
		}		
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position).name;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

	//����Ʈ�信 ����� �׸�
	class MyItem{
		int icon;
		String name;
		
		public MyItem(int icon, String name){
			this.icon = icon;
			this.name = name;
		}
		
	} // MyItem
	
	
}
