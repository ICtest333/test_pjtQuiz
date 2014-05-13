package kr.android.grid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends Activity {

	//이미지 정보를 갖는 배열(스크롤생기게할려구몇번번복)
	Integer[] images ={R.drawable.austria, R.drawable.belgium, R.drawable.bulgaria, R.drawable.cyprus, R.drawable.czech_republic, R.drawable.denmark, R.drawable.estonia, R.drawable.finland, R.drawable.france, R.drawable.germany, R.drawable.greece, R.drawable.hungary, R.drawable.ireland, R.drawable.italy, R.drawable.latvia, R.drawable.lithuania, R.drawable.luxembourg, R.drawable.malta, R.drawable.netherlands, R.drawable.poland, R.drawable.portugal, R.drawable.romania, R.drawable.slovakia, R.drawable.slovenia, R.drawable.spain, R.drawable.sweden,R.drawable.united_kingdom,R.drawable.austria, R.drawable.belgium, R.drawable.bulgaria, R.drawable.cyprus, R.drawable.czech_republic, R.drawable.denmark, R.drawable.estonia, R.drawable.finland, R.drawable.france, R.drawable.germany, R.drawable.greece, R.drawable.hungary, R.drawable.ireland, R.drawable.italy, R.drawable.latvia, R.drawable.lithuania, R.drawable.luxembourg, R.drawable.malta, R.drawable.netherlands, R.drawable.poland, R.drawable.portugal, R.drawable.romania, R.drawable.slovakia, R.drawable.slovenia, R.drawable.spain, R.drawable.sweden,R.drawable.united_kingdom,R.drawable.austria, R.drawable.belgium, R.drawable.bulgaria, R.drawable.cyprus, R.drawable.czech_republic, R.drawable.denmark, R.drawable.estonia, R.drawable.finland, R.drawable.france, R.drawable.germany, R.drawable.greece, R.drawable.hungary, R.drawable.ireland, R.drawable.italy, R.drawable.latvia, R.drawable.lithuania, R.drawable.luxembourg, R.drawable.malta, R.drawable.netherlands, R.drawable.poland, R.drawable.portugal, R.drawable.romania, R.drawable.slovakia, R.drawable.slovenia, R.drawable.spain, R.drawable.sweden,R.drawable.united_kingdom,R.drawable.austria, R.drawable.belgium, R.drawable.bulgaria, R.drawable.cyprus, R.drawable.czech_republic, R.drawable.denmark, R.drawable.estonia, R.drawable.finland, R.drawable.france, R.drawable.germany, R.drawable.greece, R.drawable.hungary, R.drawable.ireland, R.drawable.italy, R.drawable.latvia, R.drawable.lithuania, R.drawable.luxembourg, R.drawable.malta, R.drawable.netherlands, R.drawable.poland, R.drawable.portugal, R.drawable.romania, R.drawable.slovakia, R.drawable.slovenia, R.drawable.spain, R.drawable.sweden,R.drawable.united_kingdom};
	
	GridView gridView;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		gridView = (GridView)findViewById(R.id.gridView1);
		
		//GridView에 이미지를 보여주기 위해 ImageAdapter객체를 등록
		gridView.setAdapter(new ImageAdapter(this));//외부믈래스와 연결시키기 위함
		
	}
	
	//BaseAdapter를 상속하여 GridView에 이미지를 보여줄 수 있는 기능 구현(Customizing)
	public class ImageAdapter extends BaseAdapter{ //내부클래스(추상클래스:구현해야할메서드있음->자동생성기능활용)
		
		private Context context; //Context: Activity의 부모 (App. ; 웹사이트도 하나의 Context(범주)임.)
		
		public ImageAdapter(Context context){ //생성자 -> 외부믈래스와 연결시키기 위함
			this.context = context;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			//전체 이미지 갯수
			return images.length; // 멤버변수(images[])에 접근 
		}
		
		//전달된 position값을 통해 해당 이미지를 구함
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return images[position];
		}
		
		//이벤트발생시 id값 반환 (DB연동시 프라이머리키 인덱스???)
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
		// TODO Auto-generated method stub
		//개별 이미지를 반환(이미지 갯수만큼 반복)
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			               //위치값,    표시될 이미지View,     부모역할View (GridView)
			
			//재사용할 수 있는 ImageView가 있다면 ImageView를 재활용하고 
			//생성된 ImageView가 없으면 ImageView객체 생성작업 수행
			ImageView imageView = (ImageView)convertView; //재활용
			if(convertView == null){//생성되어 재활용할 ImageView가 없으면
				imageView = new ImageView(context);
			}
			//ImageView의 크기와 이미지 크기, 그리고 공백(padding) 설정
			imageView.setLayoutParams(new GridView.LayoutParams(120,120));//이미지View의 넓이,높이 지정
			imageView.setPadding(10, 10, 10, 10);
			//이미지를 인자로 넘어온 position에 맞게 설정
			imageView.setImageResource(images[position]);
			
			return imageView;//개별 이미지뷰 별로 그리드뷰에 반환되어 디스플레이됨
		}
		
	}
	

}
