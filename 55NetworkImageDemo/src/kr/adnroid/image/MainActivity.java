package kr.adnroid.image;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	static final String TAG = "NetworkImageDemo";
	static final String IMAGE_URL = "http://img.naver.net/static/www/u/2013/0731/nmms_224940510.gif";
	ImageView imageView;
	ProgressDialog dialog;
	Button goButton;
	
	Handler handler = new Handler(); // 핸들러 객체 생성
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageView = (ImageView)findViewById(R.id.imageView1);
		goButton = (Button)findViewById(R.id.go);
		goButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				dialog = ProgressDialog.show(MainActivity.this, "사이트접속중", "이미지로드중..");
				
				processData();
			}
		});
	}
	// 스레드를 생성해서 서버에 접속
	public void processData(){
		
		new Thread(){
			@Override
			public void run(){
				final Bitmap img = getRemoteImage(getStreamFromUrl()); // final !!
				
				//UI 작업
				handler.post(new Runnable(){ // handler : 참조변수를 호출하는 것이므로 소문자로 시작했던거...
					@Override
					public void run(){
						//ImageView에 Bitmap 전달
						imageView.setImageBitmap(img);
						dialog.dismiss(); //창 중지
					}
				});
			}
		}.start();
		
	}

	
	// InputStream 인자로받아들이면 -> Bitmap 변환 
	// (imageView : InputStream을 이미지로 바로 보여주는 메서드가 없음)
	public Bitmap getRemoteImage(InputStream input){
		
		Bitmap bitmap = null;
		
		try{
			BufferedInputStream bis = new BufferedInputStream(input);
			bitmap = BitmapFactory.decodeStream(bis);
			bis.close();
			
		}catch(IOException e){
			Log.e(TAG,"parsing error",e);
		}
		
		return bitmap;
	}

	//서버에 접속해서 IMAGE를 호출
	public InputStream getStreamFromUrl(){
		InputStream input = null;
		
		try{
			HttpClient client = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(IMAGE_URL); // HttpGet 요청객체
			
			//응답받을 객체
			HttpResponse response = (HttpResponse)client.execute(getMethod);
			
			//응답 수신 처리
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity buf = new BufferedHttpEntity(entity);
			input = buf.getContent(); // Get방식
						
		}catch(Exception e){
			Log.e(TAG, "network error", e);
		}
		
		return input; //  InputStream 반환
	}
	
}
