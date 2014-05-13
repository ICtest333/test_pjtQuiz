package kr.android.news;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	public static final String ARTICLE_URL = "https://news.google.co.kr/news/feeds?pz=1&cf=all&ned=kr&h1=ko&topic=e&output=rss";
	//목록에 필요한 데이터 저장
	ArrayList<MyNews> myRss = new ArrayList<MyNews>();
	
	TextView mainTitle, mainDescription;
	//Thread 통해 뉴스 읽어오므로 
	Handler handler = new Handler();
	
	MyListAdapter adapter; // 커스텀어댑터 사용위해 선언
	ProgressBar progress;
	
	//영문 형식의 날짜 String 데이터 -> Date 데이터로 변형(파싱)
	SimpleDateFormat origin_format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
	SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		progress = (ProgressBar)findViewById(R.id.progressBar);
		
		adapter = new MyListAdapter(this, R.layout.new_list);
		
		//ListView에 MyListAdapter 등록
		setListAdapter(adapter);
		
		//데이터 읽기
		progress.setVisibility(View.VISIBLE);
		
		// 스레드 만들기 : new Thread(){}.start();
		new Thread(){
			@Override
			public void run(){
				final InputStream in = getStreamFromUrl();
				
				//UI 설정 : handler.post(new Runnable(){});
				handler.post(new Runnable(){
					@Override
					public void run(){
						parseXml(in);
						//ProgressBar 중지
						progress.setVisibility(View.GONE);
					}
				}); // Runnable()
				
			} // run()
			
		}.start(); // Thread()
		
	} // onCreate
	
	//XML 파싱
	private void parseXml(InputStream in){
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			//XML만들때 태그 사이의 공백을 제거하는 역할(JDK6.0이상부터 사용)
			//factory.setIgnoringElementContentWhitespace(true); // 에러발생시 공백때문인지 파싱이 문제인지 테스트해볼것
			
			DocumentBuilder builder = factory.newDocumentBuilder(); // DocumentBuilder: 파서
			
			//DOM 트리 생성
			Document doc = builder.parse(in);
			
			//기사 추출
			NodeList articles = doc.getElementsByTagName("item"); // <item>태그에 접근
			
			for(int i=0;i<articles.getLength();i++){
				MyNews myNews = new MyNews();
				//item 태그의 자식 태그 호출
				NodeList article = articles.item(i).getChildNodes();
				//호출된 자식 태그들중 필요한 자식 태그만 뽑아냄
				for(int j=0;j<article.getLength();j++){
					Node n = article.item(j); 
					if(n.getNodeName().equals("title")){
						myNews.title = n.getFirstChild().getNodeValue();
						
					}
					if(n.getNodeName().equals("description")){
						myNews.description = n.getFirstChild().getNodeValue();
						
					}
					if(n.getNodeName().equals("pubDate")){
						//날짜 표기형식을 원하는 형식으로 변형하기 위한 코드
						Date date = origin_format.parse(n.getFirstChild().getNodeValue());
						                                         // String -> Date   
						myNews.pubDate = new_format.format(date);// Date -> String
						
					}
				}// inner for
				// 한건의 데이터 처리
				myRss.add(myNews);	
				
			} // outer for
			// Adapter에 매핑된 데이터를 읽어들여 ListView 갱신
			adapter.notifyDataSetChanged();			
			
		}catch(Exception e){
			Log.e("NewsXMLDemo", "parsing error", e);
		}finally{
			if(in!=null)try{in.close();}catch(IOException e){}
		}
	}
	
	//서버에서 XML 읽기
	private InputStream getStreamFromUrl(){
		InputStream input = null;
		
		try{
			HttpClient client = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(ARTICLE_URL);
			
			//응답을 받을 객체
			HttpResponse response = (HttpResponse)client.execute(getMethod);
			//응답 수신 처리
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity buf = new BufferedHttpEntity(entity);
			input = buf.getContent();
			
		}catch(Exception e){
			Log.e("NewsXMLDemo", "network error", e);
		}
		
		
		return input;
	}
	
	//이벤트 핸들러
	@Override
	protected void onListItemClick(ListView list, View v, int position, long id){
		
		Intent intent = new Intent(this, NewsRssDetail.class);
		//데이터설정
		intent.putExtra("title", myRss.get(position).title);
		intent.putExtra("description", myRss.get(position).description);
		intent.putExtra("pubDate", myRss.get(position).pubDate);
		
		startActivity(intent);
	}
	
	//커스텀 어댑터
	class MyListAdapter extends BaseAdapter{

		Context context;
		LayoutInflater inflater;
		int layout;
		
		public MyListAdapter(Context context, int layout){
			this.context = context;
			this.layout = layout;
			
			inflater = LayoutInflater.from(context);
			
					
		}
		
		@Override
		public int getCount() {
			// 
			return myRss.size();
		}

		@Override
		public Object getItem(int position) {
			//int position = 0; //
			// 
			return myRss.get(position).title;
		}

		@Override
		public long getItemId(int position) {
			// 
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 
			
			if(convertView == null){
				convertView = inflater.inflate(layout, parent, false); // false : parent사용안함(직접매칭할것임)
			}
			TextView text = (TextView)convertView.findViewById(R.id.text);
			text.setText(myRss.get(position).title);
			TextView date = (TextView)convertView.findViewById(R.id.date);
			date.setText(myRss.get(position).pubDate);
			
			return convertView;
		} // getView
		
	} // MyListAdapter
	

	//뉴스한건한건의 항목을 담을 (내부)클래스 : 파싱 => ArrayList에 담을 것임
	class MyNews{
		String title;
		String description;
		String pubDate;
	}
		
}
