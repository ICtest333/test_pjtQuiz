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
	//��Ͽ� �ʿ��� ������ ����
	ArrayList<MyNews> myRss = new ArrayList<MyNews>();
	
	TextView mainTitle, mainDescription;
	//Thread ���� ���� �о���Ƿ� 
	Handler handler = new Handler();
	
	MyListAdapter adapter; // Ŀ���Ҿ���� ������� ����
	ProgressBar progress;
	
	//���� ������ ��¥ String ������ -> Date �����ͷ� ����(�Ľ�)
	SimpleDateFormat origin_format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
	SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		progress = (ProgressBar)findViewById(R.id.progressBar);
		
		adapter = new MyListAdapter(this, R.layout.new_list);
		
		//ListView�� MyListAdapter ���
		setListAdapter(adapter);
		
		//������ �б�
		progress.setVisibility(View.VISIBLE);
		
		// ������ ����� : new Thread(){}.start();
		new Thread(){
			@Override
			public void run(){
				final InputStream in = getStreamFromUrl();
				
				//UI ���� : handler.post(new Runnable(){});
				handler.post(new Runnable(){
					@Override
					public void run(){
						parseXml(in);
						//ProgressBar ����
						progress.setVisibility(View.GONE);
					}
				}); // Runnable()
				
			} // run()
			
		}.start(); // Thread()
		
	} // onCreate
	
	//XML �Ľ�
	private void parseXml(InputStream in){
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			//XML���鶧 �±� ������ ������ �����ϴ� ����(JDK6.0�̻���� ���)
			//factory.setIgnoringElementContentWhitespace(true); // �����߻��� ���鶧������ �Ľ��� �������� �׽�Ʈ�غ���
			
			DocumentBuilder builder = factory.newDocumentBuilder(); // DocumentBuilder: �ļ�
			
			//DOM Ʈ�� ����
			Document doc = builder.parse(in);
			
			//��� ����
			NodeList articles = doc.getElementsByTagName("item"); // <item>�±׿� ����
			
			for(int i=0;i<articles.getLength();i++){
				MyNews myNews = new MyNews();
				//item �±��� �ڽ� �±� ȣ��
				NodeList article = articles.item(i).getChildNodes();
				//ȣ��� �ڽ� �±׵��� �ʿ��� �ڽ� �±׸� �̾Ƴ�
				for(int j=0;j<article.getLength();j++){
					Node n = article.item(j); 
					if(n.getNodeName().equals("title")){
						myNews.title = n.getFirstChild().getNodeValue();
						
					}
					if(n.getNodeName().equals("description")){
						myNews.description = n.getFirstChild().getNodeValue();
						
					}
					if(n.getNodeName().equals("pubDate")){
						//��¥ ǥ�������� ���ϴ� �������� �����ϱ� ���� �ڵ�
						Date date = origin_format.parse(n.getFirstChild().getNodeValue());
						                                         // String -> Date   
						myNews.pubDate = new_format.format(date);// Date -> String
						
					}
				}// inner for
				// �Ѱ��� ������ ó��
				myRss.add(myNews);	
				
			} // outer for
			// Adapter�� ���ε� �����͸� �о�鿩 ListView ����
			adapter.notifyDataSetChanged();			
			
		}catch(Exception e){
			Log.e("NewsXMLDemo", "parsing error", e);
		}finally{
			if(in!=null)try{in.close();}catch(IOException e){}
		}
	}
	
	//�������� XML �б�
	private InputStream getStreamFromUrl(){
		InputStream input = null;
		
		try{
			HttpClient client = new DefaultHttpClient();
			HttpGet getMethod = new HttpGet(ARTICLE_URL);
			
			//������ ���� ��ü
			HttpResponse response = (HttpResponse)client.execute(getMethod);
			//���� ���� ó��
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity buf = new BufferedHttpEntity(entity);
			input = buf.getContent();
			
		}catch(Exception e){
			Log.e("NewsXMLDemo", "network error", e);
		}
		
		
		return input;
	}
	
	//�̺�Ʈ �ڵ鷯
	@Override
	protected void onListItemClick(ListView list, View v, int position, long id){
		
		Intent intent = new Intent(this, NewsRssDetail.class);
		//�����ͼ���
		intent.putExtra("title", myRss.get(position).title);
		intent.putExtra("description", myRss.get(position).description);
		intent.putExtra("pubDate", myRss.get(position).pubDate);
		
		startActivity(intent);
	}
	
	//Ŀ���� �����
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
				convertView = inflater.inflate(layout, parent, false); // false : parent������(������Ī�Ұ���)
			}
			TextView text = (TextView)convertView.findViewById(R.id.text);
			text.setText(myRss.get(position).title);
			TextView date = (TextView)convertView.findViewById(R.id.date);
			date.setText(myRss.get(position).pubDate);
			
			return convertView;
		} // getView
		
	} // MyListAdapter
	

	//�����Ѱ��Ѱ��� �׸��� ���� (����)Ŭ���� : �Ľ� => ArrayList�� ���� ����
	class MyNews{
		String title;
		String description;
		String pubDate;
	}
		
}
