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
	
	// ��ġ �̺�Ʈ �߻������� X ��ǥ ����
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
		
		//�̹����� ViewFlipper�� ���
		for(int i : imageItems){
			ImageView image = new ImageView(this);
			image.setImageResource(i);
			flipper.addView(image, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		}//end of for
		
		flipper.setOnTouchListener(this);
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
						// v : ViewFlipper �̺�Ʈ
		// ��ġ �̺�Ʈ�� �Ͼ view�� ViewFlipper�� �ƴϸ� return false
		if(v != flipper)return false;
		
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			//��ġ ���� ���� X��ǥ ����(��ġ���� ��ũ������ ������(down) ����)
			down_x = event.getX();
		}else if(event.getAction()==MotionEvent.ACTION_UP){
			//��ġ�� ���� ���� X��ǥ ����
			up_x = event.getX();
			
			if(up_x < down_x){ // ��ġ�� �� ���� �������� ����
				flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
				flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
				
				flipper.showNext();
				
			}else if(up_x > down_x){// ��ġ�� �� ������ �������� ����
				flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
				flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
				
				flipper.showPrevious();
			}
		}
		
		return true;
	}

}
