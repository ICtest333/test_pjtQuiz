package kr.android.browser;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MainActivity extends Activity {

	WebView browser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		browser = (WebView)findViewById(R.id.web);
		
		// 1. URL 링크
		//browser.loadUrl("http://m.naver.com");
		
		// 2. HTML 직접 생성
		String msg = "<html><body>Hello WebView!!</body></html>";
		//browser.loadDataWithBaseURL(null, msg, "text/html", "UTF-8", null);
		                           //baseURL    //contextType      //historyURL (로컬데이터이용하므로 null)
		// 3. 내장되어있는 HTML 파일 읽기 (assets 폴더내)
		browser.loadUrl("file:///android_asset/hello.html"); // android_asset : -s없음.
		// 자바스크립트 허용설정
		browser.getSettings().setJavaScriptEnabled(true);
		
		// 아래 자바스크립트대체코드 수행키위한 클래스 객체 등록
		MyWebChromeClient client = new MyWebChromeClient();
		browser.setWebChromeClient(client);
		
	} // onCreate
	
	// 자바스크립트의 경고창을 대체하는 안드로이드 경고창 지정 : onJsAlert()
	// (WebView는 자바스크립트의 경고창을 지원하지않으므로)
	private class MyWebChromeClient extends WebChromeClient{
		@Override
		public boolean onJsAlert(WebView view, String url, String message, JsResult result){
			
			new AlertDialog.Builder(MainActivity.this)
			.setTitle("경고")
			.setMessage(message)
			.setCancelable(false)
			.setNegativeButton("확인", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dlg, int sumthin) {
					// 내용없이구현만(확인하면닫히게)					
				}
			}).show(); // new DialogInterface.OnClickListener()
			
			result.confirm(); // 대체코드작업수행 마감컨펌(확정)			
			return true;
		} // onJsAlert
		
	} // MyWebChromeClient
}
