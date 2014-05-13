package kr.android.browser2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

	WebView web;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		web = (WebView)findViewById(R.id.webView1);
		
		web.setWebViewClient(new MyWebClient());
	
		//�ڹٽ�ũ��Ʈ ���
		WebSettings set = web.getSettings();
		set.setJavaScriptEnabled(true);
		//ZOOM��� ���
		set.setBuiltInZoomControls(true);
		
		//�⺻ URL ����
		web.loadUrl("http://m.naver.com");
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		//ȭ�� �����丮�� �о�鿩 ���� �������� �����ϴ��� üũ
											//������ �ִ���:���������� �����ϴ���
		if(keyCode==KeyEvent.KEYCODE_BACK && web.canGoBack()){
			web.goBack();
		}else if(keyCode==KeyEvent.KEYCODE_BACK && !web.canGoBack()){
			//���� �������� ���� ���
			new AlertDialog.Builder(this)
			.setTitle("����Ȯ��")
			.setMessage("�����Ͻðڽ��ϱ�")
			.setPositiveButton("��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish(); //Activity ����
				}
			})
			.setNegativeButton("�ƴϿ�",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					
				}
			}).show();
		}
		
		
		return true;
	}
	
	
	//Ŭ���� ���� �̺�Ʈ�� ó���ϴ� ��ü
	private class MyWebClient extends WebViewClient{
		@Override								//�߻� ����	//�̵� url
		public boolean shouldOverrideUrlLoading(WebView view, String url){
			
			//Ŭ���� �ּҸ�  WebView�� ����
			view.loadUrl(url);
			return true;
		}
	}


}
