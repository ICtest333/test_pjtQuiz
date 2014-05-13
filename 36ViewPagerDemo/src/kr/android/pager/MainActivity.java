//ViewPager; 탭과유사하나 터치로 드래그해서 페이지이동기능제공
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
	int[] btn = {R.id.btn_one, R.id.btn_two, R.id.btn_three}; /*버튼을배열식으로읽어옴(버튼많을경우자원절약)*/
	
	//이벤트 처리(멤버내부클래스의변형타입)
	private View.OnClickListener myListener = new View.OnClickListener(){
		//이벤트핸들러
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
				/*위 세개 케이스는 모두 동일하게 뷰페이저내 버튼클릭시 텍스트뿌려주는 것으로
				  동일코드로 처리가능*/
				String text = ((Button)v).getText().toString();
				Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
				//한번호출하고 재사용되지않을 객체는 별도로 변수에 주소를 담지 않는 것이 바람직!!!
			}
			
		}
		
	}; // end of View.OnClickListener()
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Button 호출 및 이벤트 연결
		for(int id : btn){ //확장for문
			findViewById(id).setOnClickListener(myListener); /*페이지호출만하므로별도변수설정하지않은타입
			Button의 부모인 View로부터 상속받은 setOnClickListener메서드사용한것으로 캐스팅불요*/
		}
		//ViewPager 호출
		mPager = (ViewPager)findViewById(R.id.pager);
		
		//ViewPager에 어댑터 클래스를 등록
		mPager.setAdapter(new MyPagerAdapter(this));
		
	}

	//커스텀 페이저 어댑터 정의(페이지와 페이지뷰 매칭)
	private class MyPagerAdapter extends PagerAdapter{
		//xml 읽어들여서 xml에 표시된 클래스 정보를 토대로 객체 생성
		LayoutInflater inflater; //즉, layout폴더내 사용자가 생성한 xml 파일 읽어들여 객체 생성
		
		public MyPagerAdapter(Context c){
			inflater = LayoutInflater.from(c);
		}
		
		//뷰페이저에서 사용할 뷰객체 생성/등록
		@Override
		public Object instantiateItem(View pager, int position){
			
			View v = null;
			switch(position){
			case 0:
				  // 지정된 xml파일을 읽어들여 객체를 생성하고xml파일의 최상위객체(RelativeLayout)반환
				v = inflater.inflate(R.layout.page_one, null); // null : 별도부모지정하지않음(RelativeLayout이부모역할)
				  //RelativeLayout의 하위객체인 Button읽어들여 이벤트 연결
				v.findViewById(R.id.btn_yellow).setOnClickListener(myListener);
				//한번호출하고 재사용되지않을 객체는 별도로 변수에 주소를 담지 않는 것이 바람직!!!
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
			//생성된 뷰 객체 등록 (등록한후에반환해야함)
			((ViewPager)pager).addView(v, 0);
			
			return v; // 뷰 객체 반환
		}
		
		// 뷰 객체 삭제
		@Override
		public void destroyItem(View pager, int position, Object view){
			((ViewPager)pager).removeView((View)view);
		}
		
		//현재 PagerAdapter에서 관리할 페이지 갯수(3개) 반환
		@Override
		public int getCount() {
			// TODO Auto-generated method stub (추상클래스인 PagerAdapter의 추상메서드)
			return btn.length;
		}
		
		// instantiateItem메서드에서 생성한 객체를 이용할 것인지여부 반환(오작동방지위한검증용메서드)
		@Override
		public boolean isViewFromObject(View view, Object obj) {
			// TODO Auto-generated method stub (추상클래스인 PagerAdapter의 추상메서드)
			return view == obj;  /*같은객체인경우(만든객체와화면에보여질객체가같은경우)true반환*/
		}
		
	}

}
