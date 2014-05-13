package kr.android.flipper2;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import android.view.View; // implements View.OnTouchListener
import android.view.animation.AnimationUtils;

public class MainActivity extends Activity implements View.OnTouchListener{

	ViewFlipper flipper;
	
	// 터치 이벤트 발생지점의 X 좌표 저장
	float down_x;
	float up_x;
	
	int[] imageItems;
	ImageView image1, image2;
	int num;
	int cnt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageItems = new int[]{R.drawable.image01,R.drawable.image02,R.drawable.image03};
		
		flipper = (ViewFlipper)findViewById(R.id.flipper);
		
		//이미지를 ViewFlipper에 등록
		for(int i : imageItems){
			ImageView image = new ImageView(this);
			image.setImageResource(i);
			flipper.addView(image, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		}//end of for
		
		flipper.setOnTouchListener(this);
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
						// v : ViewFlipper 이벤트
		// 터치 이벤트가 일어난 view가 ViewFlipper가 아니면 return false
		if(v != flipper)return false;
		
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			//터치 시작 지점 X좌표 저장(터치위해 스크린으로 내려간(down) 지점)
			down_x = event.getX();
		}else if(event.getAction()==MotionEvent.ACTION_UP){
			//터치가 끝난 지점 X좌표 저장
			up_x = event.getX();
			
			if(up_x < down_x){ // 터치할 때 왼쪽 방향으로 진행
				flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
				flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
				
				flipper.showNext();
				
			}else if(up_x > down_x){// 터치할 때 오른쪽 방향으로 진행
				flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
				flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
				
				flipper.showPrevious();
			}
		}
		
		return true;
	}

}
