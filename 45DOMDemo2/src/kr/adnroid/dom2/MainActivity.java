package kr.adnroid.dom2;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

	EditText mResult;
	Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mResult = (EditText)findViewById(R.id.result);
		btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		
		InputStream in = null;
		
		try{
			in = getResources().openRawResource(R.raw.product);
			
			//DOM 파서 생성
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			//DOM 트리 생성
			Document doc = builder.parse(in);
			
			StringBuffer sb = new StringBuffer();
			sb.append("주문항목\n");
			
			NodeList items = doc.getElementsByTagName("item");
			for(int i=0; i<items.getLength(); i++){
				/*Node n = items.item(i); //태그에 접근
				Node n2 = n.getFirstChild(); //태그 중간 바디text에 접근
				String s = n2.getNodeValue();// text값 빼내기 : 이상 3 line을 아래 1 line으로 대체*/
				String s = items.item(i).getFirstChild().getNodeValue();
				sb.append((i+1)+":"+s+"\n");	// (i+1) : 출력시 일련번호를 1부터 출력시키기 위함			
			}
			mResult.setText(sb.toString());
			
		}catch(Exception e){
			Toast.makeText(this, "예외:"+e.toString(), Toast.LENGTH_LONG).show();
		}finally{
			if(in!=null)try{in.close();}catch(IOException e){}
		}		
	} // onClick	
	
}