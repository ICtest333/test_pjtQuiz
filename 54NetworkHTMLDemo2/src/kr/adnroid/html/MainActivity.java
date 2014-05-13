package kr.adnroid.html;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	static final String TAG = "NetworkHTMLDemo";
	static final String URL = "http://www.naver.com/index.html";
	EditText edit;
	Button goButton;
	ProgressDialog dialog;
	
	Handler handler = new Handler();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edit = (EditText)findViewById(R.id.edit);
		goButton = (Button)findViewById(R.id.go);
		goButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				dialog = ProgressDialog.show(MainActivity.this, "사이트접속중", "잠시만 기다리세요!");
				
				//try{
					processData(); //불러온웹페이지를 스트링으로 뿌려줌
				//}catch(Exception e){
				//	Log.e(TAG,"Thread error", e);
				//}
				
			}
		}); //new OnClickListener()
	}
	
	//스레드를 구성해서 서버에 접근해서 HTML 호출하는 메서드(모듈화)
	public void processData(){
		edit.setText("");//초기화
		
		//스레드생성
		new Thread(){
			@Override
			public void run(){
				//서버에접근해서HTML호출
				final String str = getStringFromUrl(); // final !!
				
				//정보를 UI에 반영
				handler.post(new Runnable(){
					@Override
					public void run(){
						// UI 작업 
						edit.setText(str); // str : final 선언되어있어야 호출가능!!
						dialog.dismiss();//창 중지
					}
				});
			}
		}.start();
	}
	
	
	//서버에 접근해서 원하는 HTML을 호출한 후 파일을 (스트링으로) 반환받는 메서드
	public String getStringFromUrl(){
		String reponseBody = null;
		
		try{
			HttpClient httpClient = new DefaultHttpClient();
			//HttpClient : 스트링 또는 InputStream 으로 반환 지원
			//DOM parsing시에는 InputStream으로 반환해야..
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			reponseBody = httpClient.execute(new HttpGet(URL), responseHandler);
			                               //요청정보처리객체,  응답처리객체
		}catch(Exception e){
			Log.e(TAG, "network error", e);
		}
		
		return reponseBody;
	}

}
