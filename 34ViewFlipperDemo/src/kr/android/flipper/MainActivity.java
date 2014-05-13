package kr.android.flipper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnClickListener{

	ViewFlipper flipper;
	Button btnPrev,btnNext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		flipper = (ViewFlipper)findViewById(R.id.details);
		btnPrev = (Button)findViewById(R.id.prev);
		btnNext = (Button)findViewById(R.id.next);
		
		//이벤트소스와 이벤트리스너 연결
		btnPrev.setOnClickListener(this);
		btnNext.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v){
		if(v.getId()==R.id.next){
			//다음  View 호출(계속 롤링)
			flipper.showNext();
		}else if(v.getId()==R.id.prev){
			//이전  View 호출(계속 롤링)
			flipper.showPrevious();
		}
	}
	
}
