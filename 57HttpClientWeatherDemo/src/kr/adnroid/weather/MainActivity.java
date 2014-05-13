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
import org.w3c.dom.Element; // 임포트 주의!!!
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

	//기상청 날씨 정보
	static final String WEATHER_URL = "http://www.kma.go.kr/XML/weather/sfc_web_map.xml";
	static final String TAG = "HttpClientWeatherDemo";
	WebView browser;
	ArrayList<ForeCast> list = new ArrayList<ForeCast>();
	// <ForeCast> : JSP에서 자바빈과 같은 역할
	ProgressBar progress;
	
	Handler h = new Handler();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		browser = (WebView)findViewById(R.id.webView1);
		progress = (ProgressBar)findViewById(R.id.progressBar1);
		
		updateForeCast(); // 호출

		
	} // onCreate
	
	//스레드 호출
	public void updateForeCast(){
		//ProgressBar를 보여지게 처리
		progress.setVisibility(View.VISIBLE);
		
		new Thread(){
			@Override
			public void run(){
				//DOM 파서 이용시 구현 코드
				//buildForeCastByDOM(getStreamFromUrl());
				
				
				//XmlPullParser 이용시 구현 코드
				buildForeCastByXmlPullParser(getStreamFromUrl());
				
				h.post(new Runnable(){
					@Override
					public void run(){
						//Toast.makeText(MainActivity.this, "성공!", Toast.LENGTH_LONG).show(); // 테스트코드
						String page = generatePage();
						
						browser.loadDataWithBaseURL(null, page, "text/html", "UTF-8", null);
						
						//ProgressBar 안 보여지게 처리
						progress.setVisibility(View.GONE);
					}
				}); // .post(new Runnable(){
				
			} // run(){
		}.start(); // Thread(){
		
	} // updateForeCast(){
	
	// XML 파일을 읽어등려 XmlPullParser가 파싱함
	public void buildForeCastByXmlPullParser(InputStream input){
		String local = null;
		String desc = null;
		String temp = null;
		
		try{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			
			//파서에 스트림연결
			parser.setInput(input, "UTF-8");
			while(parser.getEventType()!= XmlPullParser.END_DOCUMENT){
				if(parser.getEventType()==XmlPullParser.START_TAG){
					if(parser.getName().equals("local")){
						//날씨 처리
						desc = parser.getAttributeValue(2);
						//온도 처리
						temp = parser.getAttributeValue(3);						
					}
				}else if(parser.getEventType()==XmlPullParser.TEXT){
					//지역정보 처리
					local = parser.getText();
				}else if(parser.getEventType()==XmlPullParser.END_TAG){
					if(parser.getName().equals("local")){
						list.add(new ForeCast(local,desc,temp));
					}
				}
				//XmlPullParser의 커서를 다음 요소(텍스트)로 이동
				parser.next();
			} // while
			
		}catch(Exception e){			
		}		
	}
	
	
	//XML 파일을 DOM 트리를 생성해서 파싱함
	public void buildForeCastByDOM(InputStream input){
		try{
			//DOM 파서 생성
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			//DOM 트리생성
			Document doc = builder.parse(input);
			
			NodeList locals = doc.getElementsByTagName("local");
			
			for(int i=0;i<locals.getLength();i++){
				Element local = (Element)locals.item(i); // 아래 메서드 호출못한다면 Element 임포트 체크할 것!!!
				                                         // getFirstChild(), getAttribute()
				ForeCast foreCast = new ForeCast();
				//지역(도시명)
				foreCast.local = local.getFirstChild().getNodeValue();
				//날씨
				foreCast.desc = local.getAttribute("desc");
				//온도
				foreCast.temp = local.getAttribute("ta");
				
				list.add(foreCast);
			}
			
			
		}catch(Exception e){
			Log.e(TAG,"parsing error", e);
		}
	} // buildForeCastByDOM(){
	
	
	//서버에 접근해서 xml 데이터 요청
	public InputStream getStreamFromUrl(){
		InputStream input = null;
		
		try{
			HttpClient client = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(WEATHER_URL);
			
			//응답을 받을 객체
			HttpResponse response = (HttpResponse)client.execute(getMethod);
			
			//응답 수신 처리 (HttpEntity : 중계 객체)
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity buf = new BufferedHttpEntity(entity);
			input = buf.getContent();
			
		}catch(Exception e){
			Log.e(TAG, "network error", e);
		}
		return input; // 빠트리지말것!!
	}
	
	//UI 작업 (데이터를 표시하기 위한 HTML)
	public String generatePage(){
		StringBuffer result = new StringBuffer("<html><body><table width=100%>");
		result.append("<tr><th width=30%>지역</th>");
		result.append("<th width=50%>날씨</th>");
		result.append("<th width=20%>온도</th></tr>");
		
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
	
	// 지역,날씨,온도를 저장하는 클래스
	// (자바빈 유사 역할, 같은 클래스내에서만 호출하므로 은닉화/캡슐화 불요)
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
