//NewsService : 백그라운드작업(Back버튼으로 MainActivity종료=>UI없더라도 계속구동(뉴스Toast등)
//              => UI다시구동후 종료시킬수 있음 (사용자모르게 계속 구동되게 구현하는 경우도...)
//Service => AndroidManifest.xml 에 App 추가 필!!
//주기적으로 작업시킬때
package kr.adnroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class NewsService extends Service{ // Service : 추상클래스(추상메서드구현필)

	Handler handler = new Handler();
	boolean mQuit; //스레드중지flag로 활용(true일때빠져나오게)
	
	@Override
	public IBinder onBind(Intent arg0) { // 추상메서드구현해야../난이도있는작업시 사용(여기서는 안씀)
		// TODO Auto-generated method stub
		return null;
	}

	//서비스 실행(스레드 생성)
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){//인자들:기본값..(여기서는 안씀)
		
		// 스레드 생성.구동
		NewsThread thread = new NewsThread();
		thread.start();
		
		return super.onStartCommand(intent, flags, startId);		
	}
	
	//서비스 중지
	@Override
	public void onDestroy(){
		super.onDestroy(); //초기화
		Toast.makeText(this, "Service End", Toast.LENGTH_SHORT).show();
		mQuit = true; // 스레드종료 => 서비스종료되게끔
	}
	
	//스레드 정의(원격지에서 (뉴스)데이터를 전송받아서 표시하는 역할)
	class NewsThread extends Thread{ // 내부클래스형태
		String[] arNews = { // 멤버변수, 스레드1개, 스레드간 데이터공유/변경문제없음
				"일본, 독도는 한국땅으로 인정",
				"한국 월드컵 결승 진출",
				"대학까지 무상교육 확정",
				"학교 폭력을 전혀 모르는 학생들",
				"비행기, 배 안전사고 제로 구현",
				"기차로 러시아까지 여행 상품 판매 시작",
				"원전없이 전력 문제 해결"
		};
		@Override
		public void run(){
			for(int idx=0;mQuit==false;idx++){ //mQuit==false : 스레드 가동중 
				final String msg = arNews[idx%arNews.length]; // idx%arNews.length : 0~6까지만 만듬 => loop rotation
				//final
				
				// UI에 데이터 설정
				handler.post(new Runnable(){
					@Override
					public void run(){
						Toast.makeText(NewsService.this, msg, Toast.LENGTH_LONG).show(); // msg : final
						              // NewsService.this						
					}					
				}); // .post(new Runnable(){
				try{
					Thread.sleep(7000); // 7sec 단위로 데이터 읽어오게
				}catch(Exception e){
					
				}
			}
		}
	} // NewsThread
	
	
}
