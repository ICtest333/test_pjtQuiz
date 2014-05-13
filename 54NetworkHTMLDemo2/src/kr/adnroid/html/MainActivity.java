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
				dialog = ProgressDialog.show(MainActivity.this, "����Ʈ������", "��ø� ��ٸ�����!");
				
				//try{
					processData(); //�ҷ������������� ��Ʈ������ �ѷ���
				//}catch(Exception e){
				//	Log.e(TAG,"Thread error", e);
				//}
				
			}
		}); //new OnClickListener()
	}
	
	//�����带 �����ؼ� ������ �����ؼ� HTML ȣ���ϴ� �޼���(���ȭ)
	public void processData(){
		edit.setText("");//�ʱ�ȭ
		
		//���������
		new Thread(){
			@Override
			public void run(){
				//�����������ؼ�HTMLȣ��
				final String str = getStringFromUrl(); // final !!
				
				//������ UI�� �ݿ�
				handler.post(new Runnable(){
					@Override
					public void run(){
						// UI �۾� 
						edit.setText(str); // str : final ����Ǿ��־�� ȣ�Ⱑ��!!
						dialog.dismiss();//â ����
					}
				});
			}
		}.start();
	}
	
	
	//������ �����ؼ� ���ϴ� HTML�� ȣ���� �� ������ (��Ʈ������) ��ȯ�޴� �޼���
	public String getStringFromUrl(){
		String reponseBody = null;
		
		try{
			HttpClient httpClient = new DefaultHttpClient();
			//HttpClient : ��Ʈ�� �Ǵ� InputStream ���� ��ȯ ����
			//DOM parsing�ÿ��� InputStream���� ��ȯ�ؾ�..
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			reponseBody = httpClient.execute(new HttpGet(URL), responseHandler);
			                               //��û����ó����ü,  ����ó����ü
		}catch(Exception e){
			Log.e(TAG, "network error", e);
		}
		
		return reponseBody;
	}

}
