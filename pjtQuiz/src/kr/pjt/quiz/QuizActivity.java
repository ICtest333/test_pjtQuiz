package kr.pjt.quiz;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher.ViewFactory;

public class QuizActivity extends Activity implements View.OnTouchListener{

	String category;
	private DatabaseAdapter dbAdapter;
	ArrayList<Quiz> list = new ArrayList<Quiz>();
	ViewFlipper flipper;

	// 터치 이벤트 발생 지점의 x좌표 저장
	float down_x;
	float up_x;
	int[] imageItems;
	ImageView image1,image2;
	int num;
	int cnt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		dbAdapter = new DatabaseAdapter(this);
		//SQLiteDatabase 객체 생성
		dbAdapter.open();	
		   
		Intent i = getIntent();
		Bundle bundle = i.getExtras();
		if(bundle!=null){
			category = bundle.getString("category");
		}
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~"+category);
		
		list= dbAdapter.getQuizbyCategory(category);
		
		//imageItems = new int[]{R.drawable.image01,R.drawable.image02,R.drawable.image03};

		flipper = (ViewFlipper)findViewById(
				R.id.flipper);
		
		image1 = new ImageView(this);
		image1.setImageResource(imageItems[0]);	
		flipper.addView(image1,	
				new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT));

		image2 = new ImageView(this);
		flipper.addView(image2,	
				new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT));

		flipper.setOnTouchListener(this);
		
	}   
	public boolean onTouch(View v, MotionEvent event) {
		// 터치 이벤트가 일어난 뷰가 ViewFlipper가 아니면 return
		if(v != flipper) return false;		

		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			// 터치 시작지점 x좌표 저장			
			down_x = event.getX(); 
		}
		else if(event.getAction() == 
				MotionEvent.ACTION_UP){
			// 터치 끝난지점 x좌표 저장
			up_x = event.getX(); 	

			if( up_x < down_x ) {
				// 터치할 때 왼쪽방향으로  진행
				flipper.setInAnimation(
						AnimationUtils.loadAnimation(this,
								R.anim.push_left_in));

				flipper.setOutAnimation(
						AnimationUtils.loadAnimation(this,
								R.anim.push_left_out));

				if(++num == imageItems.length) num = 0;
				
				if(++cnt%2==1){
					image2.setImageResource( imageItems[num]);
				}else{
					image1.setImageResource( imageItems[num]);
				}
				flipper.showNext();
			}
			else if (up_x > down_x){
				// 터치할 때 오른쪽 방향으로 진행
				flipper.setInAnimation(
						AnimationUtils.loadAnimation(this,
								R.anim.push_right_in));
				flipper.setOutAnimation(
						AnimationUtils.loadAnimation(this,
								R.anim.push_right_out));

				if(--num == -1) num = imageItems.length-1;

				if(++cnt%2==1){
					image2.setImageResource( imageItems[num]);
				}else{
					image1.setImageResource( imageItems[num]);
				}

				flipper.showPrevious();			
			}
		}		
		return true;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbAdapter.close();
	}
}



