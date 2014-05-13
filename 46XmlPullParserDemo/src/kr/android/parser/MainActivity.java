package kr.android.parser;

import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {

	ArrayList<String> items = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try{
			// res > xml > 폴더내 words.xml 파일을 읽어들여 XmlPullParser 객체 생성
			XmlPullParser xpp = getResources().getXml(R.xml.words); // xml문서가져오기
			while(xpp.getEventType()!=XmlPullParser.END_DOCUMENT){//문서가끝나지않은동안루프돎
				 // xml 문서내 태그에 접근할때마다 이벤트 발생함
				if(xpp.getEventType()==XmlPullParser.START_TAG){ //시작태그인경우
					if(xpp.getName().equals("word")){//시작태그명이 word인경우(words태그내 하위태그)
						                             //가져올정보가 word태그내 value값인경우
						items.add(xpp.getAttributeValue(0));//0: 태그내 첫번째 속성값가져옴
					}
				}
				xpp.next();//다음 항목(element, text)으로 이동(이코드가있어야루프계속돌게됨)
			}//while			
			
		}catch(Exception e){
			Log.e("XmlPullParserDemo", e.toString()); //에러를 로그로 찍기
		}
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		//ListView에 ArrayAdapter 등록
		setListAdapter(adapter);
	}

	

}
