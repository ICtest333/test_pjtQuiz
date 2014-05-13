package kr.android.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyView1 extends View{
	//Activity에서 커스텀 뷰를 사용하기 위해
	public MyView1(Context context){
		super(context);
	}
	
	//XML에 element로 지정시 반드시 아래 생성자 정의
	public MyView1(Context context, AttributeSet attributeSet){
		super(context, attributeSet);
	}
	
	//그림 그리기
	public void onDraw(Canvas canvas){
		//백그라운드 지정
		canvas.drawColor(Color.BLACK);
		//그리기 옵션 지정
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		//drawLine
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(10);
					//시작x, 시작y, 끝x, 끝y, Paint객체
		canvas.drawLine(50, 0, 50, 100, paint);
		
		
	}

}
