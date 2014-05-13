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
			// res > xml > ������ words.xml ������ �о�鿩 XmlPullParser ��ü ����
			XmlPullParser xpp = getResources().getXml(R.xml.words); // xml������������
			while(xpp.getEventType()!=XmlPullParser.END_DOCUMENT){//�������������������ȷ�����
				 // xml ������ �±׿� �����Ҷ����� �̺�Ʈ �߻���
				if(xpp.getEventType()==XmlPullParser.START_TAG){ //�����±��ΰ��
					if(xpp.getName().equals("word")){//�����±׸��� word�ΰ��(words�±׳� �����±�)
						                             //������������ word�±׳� value���ΰ��
						items.add(xpp.getAttributeValue(0));//0: �±׳� ù��° �Ӽ���������
					}
				}
				xpp.next();//���� �׸�(element, text)���� �̵�(���ڵ尡�־�߷�����ӵ��Ե�)
			}//while			
			
		}catch(Exception e){
			Log.e("XmlPullParserDemo", e.toString()); //������ �α׷� ���
		}
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		//ListView�� ArrayAdapter ���
		setListAdapter(adapter);
	}

	

}
