package kr.adnroid.dom;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	ArrayList<String> items = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		InputStream in = null;
		
		try{		
			// xml 파일을 읽어들여 InputStream 생성
			in = getResources().openRawResource(R.raw.words);
			
			//DOM 파서 생성 (메모리에 DOM Tree 생성키 위함)
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			                                        // 공장에서 만들어냄
			// InputStream을 읽어들여 DOM Tree 생성
			Document doc = builder.parse(in); // 파싱
			
			NodeList words = doc.getElementsByTagName("word");
			//배열형태로 읽어들임                  // word : words.xml 내 태그명
			
			for(int i=0; i<words.getLength(); i++){
				Element e = (Element)words.item(i);
				items.add(e.getAttribute("value")); //속성값을뽑아 ArrayList에붙임
			}
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
			setListAdapter(adapter);
			
		}catch(Exception e){
			Toast.makeText(this, "예외 : "+ e.toString(), Toast.LENGTH_SHORT).show();
		}finally{
			if(in!=null)try{in.close();}catch(IOException e){}
		}
		
	}
}
