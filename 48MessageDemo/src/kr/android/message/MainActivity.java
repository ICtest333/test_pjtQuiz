package kr.android.message;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

	Button alert,toast,progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		alert=(Button)findViewById(R.id.alert);
		toast=(Button)findViewById(R.id.toast);
		progress=(Button)findViewById(R.id.progress);
		
		alert.setOnClickListener(this);
		toast.setOnClickListener(this);
		progress.setOnClickListener(this);		
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId()==R.id.alert){
			//경고창 생성 및 호출
			new AlertDialog.Builder(this)
			.setTitle("대화상자")
			.setMessage("안녕하세요!")
			.setCancelable(false)
			.setNeutralButton("닫기", new DialogInterface.OnClickListener(){
				                       // DialogInterface 뒤에 괄호()가 있으면 안됨!
				                       // .OnClickListener뒤에는 괄호()가 있어야 함!
				                       // DialogInterface 하위 OnClickListener 인터페이스를 생성자메서드로 호출하는 패턴
				//이벤트핸들러
				@Override
				public void onClick(DialogInterface dlg, int sumthin){
					                // AlertDialog().Builder // 무슨버튼
					//단순히 창을 닫을 경우는 명시할 코드가 없음
					//창이 닫힐 때 부가적인 작업을 할경우 onClick 메서드 안에 기재(요기!)
				}					
			}).show(); // .show() 빠트리지말것!
			
/*			.setCancelable(false) :  경고창 띄어졌을 때 Back버튼으로 없애기 안할때
 * 			.setNeutralButton() : 닫기버튼시
 *          => Builder 객체를 반환하는 메서드는 변수선언없이 쩜으로 연결해서 사용가능 : 체인(chain)으로 연결 패턴
 * 			.show(); : 경고창은 확실히 보여져야 하는 것이므로
*/			
		}else if(v.getId()==R.id.toast){
			Toast.makeText(this, "토스트메시지", Toast.LENGTH_LONG).show(); // .show() 빠트리지말것!
		}else if(v.getId()==R.id.progress){
			final ProgressDialog dialog = ProgressDialog.show(this, "싸이트 접속 중", "잠시만 기다려주세요!");//포그라운드
			// Thread로 백그라운드작업 시킨뒤 dismiss() 해줌!
			new Thread(){//익명내부클래스 (별도 변수선언없이 활용)
				@Override
				public void run(){
				
					try{
						sleep(5000);	//5초간 백그라운드작업					
					}catch(InterruptedException e){
						Log.e("MessageDemo", e.toString());
					}
					//창 중지
					dialog.dismiss(); // local 내부클래스가 메서드(onClick)내부에 들어간 경우 지역변수 호출못함!
					                  // final ProgressDialog dialog 와같이 final 붙여줘야함!!!
				}				
			}.start(); //익명내부클래스=>참조주소생성=>바로 메서드호출가능			
		}//else if		
	}//onClick
}
