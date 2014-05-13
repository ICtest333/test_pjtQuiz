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
				
				//��������ȿ��üũ(���ڿ��� �ѱ�� �� ��������)
				if(name.length()<=0){
					Toast.makeText(MainActivity.this, "�̸��� �ʼ�!", Toast.LENGTH_SHORT).show();
					edit_name.requestFocus();
					return; // onClick() ��������������!!
				}
				if(address.length()<=0){
					Toast.makeText(MainActivity.this, "�ּҴ� �ʼ�!", Toast.LENGTH_SHORT).show();
					edit_address.requestFocus();
					return; // onClick() ��������������!!
				}
				
				dialog = ProgressDialog.show(MainActivity.this, "����Ʈ ���� ��", "������ ���� ��!");
				
				new Thread(){
					@Override
					public void run(){
						final InputStream input = getStreamFromUrl(POST_URL);// final !!
						
						//UI �۾�
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
	
	//InputStream -> DOM tree ���� (xml �Ľ�) 
	public void parseXml(InputStream input){
		//Log.i(TAG, "Seccess"); //�׽�Ʈ�ڵ�(���ۿ��θ��׽�Ʈ�غ�������)
		
		try{
			//DOM �ļ� ����
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			//DOM Ʈ��(Document��ü) ����
			Document doc = builder.parse(input);
			
			NodeList node = doc.getElementsByTagName("code");
			String result = node.item(0).getFirstChild().getNodeValue(); 
			                 // xml�� <code>�±׳� ������(ù�ڽ�)�� �����ؼ� ����������
			dialog.dismiss(); // â ���� !
			
			if(result.equals("success")){
				Toast.makeText(this, "���� ����!", Toast.LENGTH_SHORT).show();
				//EditText�ʱ�ȭ
				edit_name.setText("");
				edit_address.setText("");
			}else{
				Toast.makeText(this, "���� ����!", Toast.LENGTH_SHORT).show();
			}
			
		}catch(Exception e){
			Log.e(TAG,"parsing error",e);
			dialog.dismiss();
			Toast.makeText(this, "���� �߻�!", Toast.LENGTH_SHORT).show();
		}
		
		//dialog.dismiss(); // â ���� �ڵ� ����(try/catch) �ű�!
	} // parseXml(){
	
	
	//������ �����ؼ� �����͸� �����ϰ�(Post���)
	//�������� ������ ���� ó��(xml ; InputStream)
	public InputStream getStreamFromUrl(String url){
		InputStream input = null;
		
		try{
			
			HttpClient client = new DefaultHttpClient();
			
			//Post��� ���� ������ ������
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			//�̸�
			params.add(new BasicNameValuePair("name", edit_name.getText().toString()));
			//�ּ�
			params.add(new BasicNameValuePair("address", edit_address.getText().toString()));
			
			HttpPost postMethod = new HttpPost(url);
			postMethod.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			
			//������ ���� ��ü ����
			HttpResponse response = (HttpResponse)client.execute(postMethod);
			
			//������� ó��
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity buf = new BufferedHttpEntity(entity);
			input = buf.getContent(); 
			
		}catch(Exception e){
			Log.e(TAG, "network error", e);			
		}
		
		return input;
	} // getStreamFromUrl(){

} // MainActivity
