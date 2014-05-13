package kr.android.frame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	ImageView img;
	Button btn;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		img = (ImageView)findViewById(R.id.img);
		btn = (Button)findViewById(R.id.btn);
		btn.setOnClickListener( //익명내부클래스로 이벤트소스와 이벤트핸들러 연결
				                new View.OnClickListener() {// start of 익명내부클래스
			
		    //이벤트 핸들러
			@Override
			public void onClick(View v) {
				if(img.getVisibility() == View.VISIBLE){// View 보여지고 있는 경우 
					img.setVisibility(View.INVISIBLE); // -> 안보여지게
				}else{// View 안보여지고 있는 경우
					img.setVisibility(View.VISIBLE); // -> 보여지게 (이벤트처리: 토글형태)
				}
				
			}
		}); // end of 익명내부클래스
		
	}

	

}
