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
	
	Handler handler = new Handler(); // �ڵ鷯 ��ü ����
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageView = (ImageView)findViewById(R.id.imageView1);
		goButton = (Button)findViewById(R.id.go);
		goButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				dialog = ProgressDialog.show(MainActivity.this, "����Ʈ������", "�̹����ε���..");
				
				processData();
			}
		});
	}
	// �����带 �����ؼ� ������ ����
	public void processData(){
		
		new Thread(){
			@Override
			public void run(){
				final Bitmap img = getRemoteImage(getStreamFromUrl()); // final !!
				
				//UI �۾�
				handler.post(new Runnable(){ // handler : ���������� ȣ���ϴ� ���̹Ƿ� �ҹ��ڷ� �����ߴ���...
					@Override
					public void run(){
						//ImageView�� Bitmap ����
						imageView.setImageBitmap(img);
						dialog.dismiss(); //â ����
					}
				});
			}
		}.start();
		
	}

	
	// InputStream ���ڷι޾Ƶ��̸� -> Bitmap ��ȯ 
	// (imageView : InputStream�� �̹����� �ٷ� �����ִ� �޼��尡 ����)
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

	//������ �����ؼ� IMAGE�� ȣ��
	public InputStream getStreamFromUrl(){
		InputStream input = null;
		
		try{
			HttpClient client = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(IMAGE_URL); // HttpGet ��û��ü
			
			//������� ��ü
			HttpResponse response = (HttpResponse)client.execute(getMethod);
			
			//���� ���� ó��
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity buf = new BufferedHttpEntity(entity);
			input = buf.getContent(); // Get���
						
		}catch(Exception e){
			Log.e(TAG, "network error", e);
		}
		
		return input; //  InputStream ��ȯ
	}
	
}
