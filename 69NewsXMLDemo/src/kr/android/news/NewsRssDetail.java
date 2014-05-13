package kr.android.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class NewsRssDetail extends Activity{

	String title, description, pubDate;
	WebView web;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_detail);
		
		Intent intent = getIntent();
		
		title = intent.getExtras().getString("title");
		description = intent.getExtras().getString("description");
		pubDate = intent.getExtras().getString("pubDate");
		
		//img �±׿��� https: �����Ǿ� �־ �̹����� ȣ������ ����
		//����, img �±׸� �˻��ؼ� https:�� ����������� 
		description = description.replaceAll("img src=\"//", "img src=\"https://");
				
		StringBuffer text = new StringBuffer();
		text.append("<html><body><font size=4>");
		text.append(title);
		text.append("</font><hr size=2 width=100% noshade>");
		text.append(description);
		text.append("<br>�Է� ��¥ : ");
		text.append(pubDate);
		text.append("</body></html>");
		
		web = (WebView)findViewById(R.id.web);
		WebSettings set = web.getSettings();
		set.setJavaScriptEnabled(true);
		set.setBuiltInZoomControls(true);
		
		web.loadDataWithBaseURL(null, text.toString(), "text/html", "UTF-8", null);
		
	}

}
