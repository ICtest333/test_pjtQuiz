package kr.android.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	ProgressBar bar;
	
	Handler handler = new Handler();
	boolean isRunning = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bar = (ProgressBar)findViewById(R.id.progress);
		
	}
	
	//스레드로 데이터 처리 수행
	@Override
	public void onStart(){
		super.onStart();
		
		//프로그레스바(ProgressBar) 초기화
		bar.setProgress(0);
		
		//화면 인터페이스와 별도의 백그라운드 작업 위해 스레드 생성
/*		Runnable r = new Runnable() {//익명내부클래스
			@Override
			public void run() { ...								
			}
		};//익명내부클래스
*/		//스레스 생성
		//Thread backgroud = new Thread(r);
		// r 변수선언없이 구현 !!!
		Thread background = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					for(int i=0; i<20 && isRunning; i++){
						Thread.sleep(1000);// 1sec 동안
						//웹에서 데이터를 읽어오는 작업수행 가정
						
						// 스레드에서 UI 접근하고자할경우,,, 
						// : Handler 객체를 이용(중계역할)해서 접근함
						handler.post(new Runnable() {//익명내부클래스 : post()메서드 => Runnable 객체 생성							
							@Override
							public void run() {
								// UI에 접근해서 화면 제어 (UI 갱신작업은, 요기...)
								bar.incrementProgressBy(5);	//프로그레스바 5%씩증가, 20번돌면 100%되도록.							
							}
						});//익명내부클래스						
					}
				}catch(Exception e){
					Log.e("HandlerDemo", e.toString());
				}
			}
		});//new Runnable()
		
		isRunning = true; // 백그라운드로전환시 isRunning 이 false반환, 스레드구현한 반복문 빠져나옴!
		background.start();
		
	} //onStart
	
	//스레드 중지 수행
	@Override
	public void onStop(){
		super.onStop();
		isRunning = false;// 백그라운드로전환시 isRunning 이 false반환, 스레드구현한 반복문 빠져나옴!
		                  // 스레드 실행중 인터럽티드 메스드로 강제종료시 오류발생가능(실무적으로 사용안함)
		                  // 스레드구현 반복문을 종료시키는 패턴으로...
	}	
}
