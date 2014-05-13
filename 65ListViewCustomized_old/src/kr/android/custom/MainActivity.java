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

		// 데이터 생성 및 저장
		item = new ArrayList<MyItem>();
		item.add(new MyItem(R.drawable.ic_launcher, "삼성 노트북"));
		item.add(new MyItem(R.drawable.ic_launcher, "LG 에어컨"));
		item.add(new MyItem(R.drawable.ic_launcher, "팬택 휴대폰"));
		
	} // onCreate
	
	//커스텀 어댑터 클래스
	class MyListAdapter extends BaseAdapter{ // BaseAdapter : 추상클래스

		Context context;
		ArrayList<MyItem> list;
		int layout;
		LayoutInflater inflater;
		
		public MyListAdapter(Context context, int layout, ArrayList<MyItem> list){
			this.context = context;
			this.layout = layout;
			this.list = list;
			
			//ListView에서 사용할 View를 정의한 xml을 읽어오기 위해 
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

	//리스트뷰에 출력할 항목
	class MyItem{
		int icon;
		String name;
		
		public MyItem(int icon, String name){
			this.icon = icon;
			this.name = name;
		}
		
	} // MyItem
	
	
}
