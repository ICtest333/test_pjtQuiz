package kr.adnroid.weather;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element; // ����Ʈ ����!!!
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

	//���û ���� ����
	static final String WEATHER_URL = "http://www.kma.go.kr/XML/weather/sfc_web_map.xml";
	static final String TAG = "HttpClientWeatherDemo";
	WebView browser;
	ArrayList<ForeCast> list = new ArrayList<ForeCast>();
	// <ForeCast> : JSP���� �ڹٺ�� ���� ����
	ProgressBar progress;
	
	Handler h = new Handler();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		browser = (WebView)findViewById(R.id.webView1);
		progress = (ProgressBar)findViewById(R.id.progressBar1);
		
		updateForeCast(); // ȣ��

		
	} // onCreate
	
	//������ ȣ��
	public void updateForeCast(){
		//ProgressBar�� �������� ó��
		progress.setVisibility(View.VISIBLE);
		
		new Thread(){
			@Override
			public void run(){
				//DOM �ļ� �̿�� ���� �ڵ�
				//buildForeCastByDOM(getStreamFromUrl());
				
				
				//XmlPullParser �̿�� ���� �ڵ�
				buildForeCastByXmlPullParser(getStreamFromUrl());
				
				h.post(new Runnable(){
					@Override
					public void run(){
						//Toast.makeText(MainActivity.this, "����!", Toast.LENGTH_LONG).show(); // �׽�Ʈ�ڵ�
						String page = generatePage();
						
						browser.loadDataWithBaseURL(null, page, "text/html", "UTF-8", null);
						
						//ProgressBar �� �������� ó��
						progress.setVisibility(View.GONE);
					}
				}); // .post(new Runnable(){
				
			} // run(){
		}.start(); // Thread(){
		
	} // updateForeCast(){
	
	// XML ������ �о��� XmlPullParser�� �Ľ���
	public void buildForeCastByXmlPullParser(InputStream input){
		String local = null;
		String desc = null;
		String temp = null;
		
		try{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			
			//�ļ��� ��Ʈ������
			parser.setInput(input, "UTF-8");
			while(parser.getEventType()!= XmlPullParser.END_DOCUMENT){
				if(parser.getEventType()==XmlPullParser.START_TAG){
					if(parser.getName().equals("local")){
						//���� ó��
						desc = parser.getAttributeValue(2);
						//�µ� ó��
						temp = parser.getAttributeValue(3);						
					}
				}else if(parser.getEventType()==XmlPullParser.TEXT){
					//�������� ó��
					local = parser.getText();
				}else if(parser.getEventType()==XmlPullParser.END_TAG){
					if(parser.getName().equals("local")){
						list.add(new ForeCast(local,desc,temp));
					}
				}
				//XmlPullParser�� Ŀ���� ���� ���(�ؽ�Ʈ)�� �̵�
				parser.next();
			} // while
			
		}catch(Exception e){			
		}		
	}
	
	
	//XML ������ DOM Ʈ���� �����ؼ� �Ľ���
	public void buildForeCastByDOM(InputStream input){
		try{
			//DOM �ļ� ����
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			//DOM Ʈ������
			Document doc = builder.parse(input);
			
			NodeList locals = doc.getElementsByTagName("local");
			
			for(int i=0;i<locals.getLength();i++){
				Element local = (Element)locals.item(i); // �Ʒ� �޼��� ȣ����Ѵٸ� Element ����Ʈ üũ�� ��!!!
				                                         // getFirstChild(), getAttribute()
				ForeCast foreCast = new ForeCast();
				//����(���ø�)
				foreCast.local = local.getFirstChild().getNodeValue();
				//����
				foreCast.desc = local.getAttribute("desc");
				//�µ�
				foreCast.temp = local.getAttribute("ta");
				
				list.add(foreCast);
			}
			
			
		}catch(Exception e){
			Log.e(TAG,"parsing error", e);
		}
	} // buildForeCastByDOM(){
	
	
	//������ �����ؼ� xml ������ ��û
	public InputStream getStreamFromUrl(){
		InputStream input = null;
		
		try{
			HttpClient client = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(WEATHER_URL);
			
			//������ ���� ��ü
			HttpResponse response = (HttpResponse)client.execute(getMethod);
			
			//���� ���� ó�� (HttpEntity : �߰� ��ü)
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity buf = new BufferedHttpEntity(entity);
			input = buf.getContent();
			
		}catch(Exception e){
			Log.e(TAG, "network error", e);
		}
		return input; // ��Ʈ��������!!
	}
	
	//UI �۾� (�����͸� ǥ���ϱ� ���� HTML)
	public String generatePage(){
		StringBuffer result = new StringBuffer("<html><body><table width=100%>");
		result.append("<tr><th width=30%>����</th>");
		result.append("<th width=50%>����</th>");
		result.append("<th width=20%>�µ�</th></tr>");
		
		for(ForeCast foreCast : list){
			result.append("<tr><td align=center>");
			result.append(foreCast.local);
			result.append("</td><td align=center>");
			result.append(foreCast.desc);
			result.append("</td><td>");
			result.append(foreCast.temp);
			result.append("</td></tr>");
		}		
		result.append("</table></body></html>");
		
		return result.toString();
	}
	
	// ����,����,�µ��� �����ϴ� Ŭ����
	// (�ڹٺ� ���� ����, ���� Ŭ������������ ȣ���ϹǷ� ����ȭ/ĸ��ȭ �ҿ�)
	class ForeCast{
		String local;
		String desc;
		String temp;
		
		public ForeCast(){}
		
		public ForeCast(String local,String desc,String temp){
			this.local = local;
			this.desc = desc;
			this.temp = temp;
		}		
	} // ForeCast{	
}
