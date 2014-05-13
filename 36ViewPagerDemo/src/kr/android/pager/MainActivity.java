//ViewPager; �ǰ������ϳ� ��ġ�� �巡���ؼ� �������̵��������
package kr.android.pager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button btn_one, btn_two, btn_three;
	ViewPager mPager;
	int[] btn = {R.id.btn_one, R.id.btn_two, R.id.btn_three}; /*��ư���迭�������о��(��ư��������ڿ�����)*/
	
	//�̺�Ʈ ó��(�������Ŭ�����Ǻ���Ÿ��)
	private View.OnClickListener myListener = new View.OnClickListener(){
		//�̺�Ʈ�ڵ鷯
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btn_one:
				mPager.setCurrentItem(0);
				break;
			case R.id.btn_two:
				mPager.setCurrentItem(1);
				break;
			case R.id.btn_three:
				mPager.setCurrentItem(2);
				break;
			case R.id.btn_yellow:
			case R.id.btn_sky:
			case R.id.btn_pink:
				/*�� ���� ���̽��� ��� �����ϰ� ���������� ��ưŬ���� �ؽ�Ʈ�ѷ��ִ� ������
				  �����ڵ�� ó������*/
				String text = ((Button)v).getText().toString();
				Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
				//�ѹ�ȣ���ϰ� ����������� ��ü�� ������ ������ �ּҸ� ���� �ʴ� ���� �ٶ���!!!
			}
			
		}
		
	}; // end of View.OnClickListener()
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Button ȣ�� �� �̺�Ʈ ����
		for(int id : btn){ //Ȯ��for��
			findViewById(id).setOnClickListener(myListener); /*������ȣ�⸸�ϹǷκ�������������������Ÿ��
			Button�� �θ��� View�κ��� ��ӹ��� setOnClickListener�޼������Ѱ����� ĳ���úҿ�*/
		}
		//ViewPager ȣ��
		mPager = (ViewPager)findViewById(R.id.pager);
		
		//ViewPager�� ����� Ŭ������ ���
		mPager.setAdapter(new MyPagerAdapter(this));
		
	}

	//Ŀ���� ������ ����� ����(�������� �������� ��Ī)
	private class MyPagerAdapter extends PagerAdapter{
		//xml �о�鿩�� xml�� ǥ�õ� Ŭ���� ������ ���� ��ü ����
		LayoutInflater inflater; //��, layout������ ����ڰ� ������ xml ���� �о�鿩 ��ü ����
		
		public MyPagerAdapter(Context c){
			inflater = LayoutInflater.from(c);
		}
		
		//������������ ����� �䰴ü ����/���
		@Override
		public Object instantiateItem(View pager, int position){
			
			View v = null;
			switch(position){
			case 0:
				  // ������ xml������ �о�鿩 ��ü�� �����ϰ�xml������ �ֻ�����ü(RelativeLayout)��ȯ
				v = inflater.inflate(R.layout.page_one, null); // null : �����θ�������������(RelativeLayout�̺θ���)
				  //RelativeLayout�� ������ü�� Button�о�鿩 �̺�Ʈ ����
				v.findViewById(R.id.btn_yellow).setOnClickListener(myListener);
				//�ѹ�ȣ���ϰ� ����������� ��ü�� ������ ������ �ּҸ� ���� �ʴ� ���� �ٶ���!!!
				break;
			case 1:
				v = inflater.inflate(R.layout.page_two, null);
				v.findViewById(R.id.btn_sky).setOnClickListener(myListener);
				break;
			case 2: 
				v = inflater.inflate(R.layout.page_three, null);
				v.findViewById(R.id.btn_pink).setOnClickListener(myListener);
				break;
			}
			//������ �� ��ü ��� (������Ŀ���ȯ�ؾ���)
			((ViewPager)pager).addView(v, 0);
			
			return v; // �� ��ü ��ȯ
		}
		
		// �� ��ü ����
		@Override
		public void destroyItem(View pager, int position, Object view){
			((ViewPager)pager).removeView((View)view);
		}
		
		//���� PagerAdapter���� ������ ������ ����(3��) ��ȯ
		@Override
		public int getCount() {
			// TODO Auto-generated method stub (�߻�Ŭ������ PagerAdapter�� �߻�޼���)
			return btn.length;
		}
		
		// instantiateItem�޼��忡�� ������ ��ü�� �̿��� ���������� ��ȯ(���۵��������Ѱ�����޼���)
		@Override
		public boolean isViewFromObject(View view, Object obj) {
			// TODO Auto-generated method stub (�߻�Ŭ������ PagerAdapter�� �߻�޼���)
			return view == obj;  /*������ü�ΰ��(���簴ü��ȭ�鿡��������ü���������)true��ȯ*/
		}
		
	}

}
