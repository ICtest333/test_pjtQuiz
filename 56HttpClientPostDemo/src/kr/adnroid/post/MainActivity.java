package kr.adnroid.post;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	static final String POST_URL = "http://192.168.0.50:8282/web/postTest.jsp";
	static final String TAG = "HttpClientPostDemo";
	
	EditText edit_name, edit_address;
	Button send;
	ProgressDialog dialog;
	
	Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edit_name = (EditText)findViewById(R.id.edit_name);
		edit_address = (EditText)findViewById(R.id.edit_address);
		send = (Button)findViewById(R.id.send);
		
		send.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				String name = edit_name.getText().toString();
				String address = edit_address.getText().toString();
				
				//데이터유효성체크(빈문자열을 넘기는 것 막으려고)
				if(name.length()<=0){
					Toast.makeText(MainActivity.this, "이름은 필수!", Toast.LENGTH_SHORT).show();
					edit_name.requestFocus();
					return; // onClick() 빠져나가도록함!!
				}
				if(address.length()<=0){
					Toast.makeText(MainActivity.this, "주소는 필수!", Toast.LENGTH_SHORT).show();
					edit_address.requestFocus();
					return; // onClick() 빠져나가도록함!!
				}
				
				dialog = ProgressDialog.show(MainActivity.this, "사이트 접속 중", "데이터 전송 중!");
				
				new Thread(){
					@Override
					public void run(){
						final InputStream input = getStreamFromUrl(POST_URL);// final !!
						
						//UI 작업
						handler.post(new Runnable(){
							@Override
							public void run(){
								parseXml(input); // final !!
							}
						}); // .post(new Runnable(){						
					} // run(){
				}.start(); // Thread(){
			} // onClick(View v){
		}); // .setOnClickListener(new OnClickListener(){
				
	} // onCreate()
	
	//InputStream -> DOM tree 생성 (xml 파싱) 
	public void parseXml(InputStream input){
		//Log.i(TAG, "Seccess"); //테스트코드(전송여부만테스트해보려고함)
		
		try{
			//DOM 파서 생성
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			//DOM 트리(Document객체) 생성
			Document doc = builder.parse(input);
			
			NodeList node = doc.getElementsByTagName("code");
			String result = node.item(0).getFirstChild().getNodeValue(); 
			                 // xml의 <code>태그내 아이템(첫자식)에 접근해서 값가져오기
			dialog.dismiss(); // 창 중지 !
			
			if(result.equals("success")){
				Toast.makeText(this, "전송 성공!", Toast.LENGTH_SHORT).show();
				//EditText초기화
				edit_name.setText("");
				edit_address.setText("");
			}else{
				Toast.makeText(this, "전송 실패!", Toast.LENGTH_SHORT).show();
			}
			
		}catch(Exception e){
			Log.e(TAG,"parsing error",e);
			dialog.dismiss();
			Toast.makeText(this, "오류 발생!", Toast.LENGTH_SHORT).show();
		}
		
		//dialog.dismiss(); // 창 중지 코드 위로(try/catch) 옮김!
	} // parseXml(){
	
	
	//서버에 접속해서 데이터를 전달하고(Post방식)
	//서버에서 응답한 정보 처리(xml ; InputStream)
	public InputStream getStreamFromUrl(String url){
		InputStream input = null;
		
		try{
			
			HttpClient client = new DefaultHttpClient();
			
			//Post방식 전송 데이터 보관용
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			//이름
			params.add(new BasicNameValuePair("name", edit_name.getText().toString()));
			//주소
			params.add(new BasicNameValuePair("address", edit_address.getText().toString()));
			
			HttpPost postMethod = new HttpPost(url);
			postMethod.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
			//응답을 받을 객체 생성
			HttpResponse response = (HttpResponse)client.execute(postMethod);
			
			//응답수신 처리
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity buf = new BufferedHttpEntity(entity);
			input = buf.getContent(); 
			
		}catch(Exception e){
			Log.e(TAG, "network error", e);			
		}
		
		return input;
	} // getStreamFromUrl(){

} // MainActivity
