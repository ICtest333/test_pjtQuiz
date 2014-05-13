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
		
		// 1. URL ��ũ
		//browser.loadUrl("http://m.naver.com");
		
		// 2. HTML ���� ����
		String msg = "<html><body>Hello WebView!!</body></html>";
		//browser.loadDataWithBaseURL(null, msg, "text/html", "UTF-8", null);
		                           //baseURL    //contextType      //historyURL (���õ������̿��ϹǷ� null)
		// 3. ����Ǿ��ִ� HTML ���� �б� (assets ������)
		browser.loadUrl("file:///android_asset/hello.html"); // android_asset : -s����.
		// �ڹٽ�ũ��Ʈ ��뼳��
		browser.getSettings().setJavaScriptEnabled(true);
		
		// �Ʒ� �ڹٽ�ũ��Ʈ��ü�ڵ� ����Ű���� Ŭ���� ��ü ���
		MyWebChromeClient client = new MyWebChromeClient();
		browser.setWebChromeClient(client);
		
	} // onCreate
	
	// �ڹٽ�ũ��Ʈ�� ���â�� ��ü�ϴ� �ȵ���̵� ���â ���� : onJsAlert()
	// (WebView�� �ڹٽ�ũ��Ʈ�� ���â�� �������������Ƿ�)
	private class MyWebChromeClient extends WebChromeClient{
		@Override
		public boolean onJsAlert(WebView view, String url, String message, JsResult result){
			
			new AlertDialog.Builder(MainActivity.this)
			.setTitle("���")
			.setMessage(message)
			.setCancelable(false)
			.setNegativeButton("Ȯ��", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dlg, int sumthin) {
					// ������̱�����(Ȯ���ϸ������)					
				}
			}).show(); // new DialogInterface.OnClickListener()
			
			result.confirm(); // ��ü�ڵ��۾����� ��������(Ȯ��)			
			return true;
		} // onJsAlert
		
	} // MyWebChromeClient
}
