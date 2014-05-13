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
	
		//자바스크립트 허용
		WebSettings set = web.getSettings();
		set.setJavaScriptEnabled(true);
		//ZOOM기능 사용
		set.setBuiltInZoomControls(true);
		
		//기본 URL 설정
		web.loadUrl("http://m.naver.com");
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		//화면 히스토리를 읽어들여 이전 페이지가 존재하는지 체크
											//갈곳이 있느냐:이전페이지 존재하느냐
		if(keyCode==KeyEvent.KEYCODE_BACK && web.canGoBack()){
			web.goBack();
		}else if(keyCode==KeyEvent.KEYCODE_BACK && !web.canGoBack()){
			//이전 페이지가 없는 경우
			new AlertDialog.Builder(this)
			.setTitle("종료확인")
			.setMessage("종료하시겠습니까")
			.setPositiveButton("예", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish(); //Activity 종료
				}
			})
			.setNegativeButton("아니오",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					
				}
			}).show();
		}
		
		
		return true;
	}
	
	
	//클릭에 대한 이벤트를 처리하는 객체
	private class MyWebClient extends WebViewClient{
		@Override								//발생 웹뷰	//이동 url
		public boolean shouldOverrideUrlLoading(WebView view, String url){
			
			//클릭한 주소를  WebView에 설정
			view.loadUrl(url);
			return true;
		}
	}


}
