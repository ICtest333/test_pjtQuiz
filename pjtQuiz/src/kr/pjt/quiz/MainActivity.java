package kr.pjt.quiz;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

	DatabaseAdapter dbAdapter;
	Button btn_java,btn_sql,btn_android,btn_jsp,btn_js,btn_etc,btn_search;
	EditText edit_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		dbAdapter = new DatabaseAdapter(this);
		//SQLiteDatabase 객체 생성
		dbAdapter.open();		
		
		readQuizFromXML();
		
		btn_java = (Button)findViewById(R.id.btn_java);
		btn_sql = (Button)findViewById(R.id.btn_sql);
		btn_android = (Button)findViewById(R.id.btn_android);
		btn_jsp = (Button)findViewById(R.id.btn_jsp);
		btn_js = (Button)findViewById(R.id.btn_js);
		btn_etc = (Button)findViewById(R.id.btn_etc);
		btn_search = (Button)findViewById(R.id.btn_search);
		
		edit_search = (EditText)findViewById(R.id.edit_search);
		
		//이벤트소스와 이벤트리스너 연결
		btn_java.setOnClickListener(this);
		btn_sql.setOnClickListener(this);
		btn_android.setOnClickListener(this);
		btn_jsp.setOnClickListener(this);
		btn_js.setOnClickListener(this);
		btn_etc.setOnClickListener(this);
		btn_search.setOnClickListener(this);
	}

	public void readQuizFromXML(){
		InputStream in = null;

		try{
			in = getResources().openRawResource(R.raw.quiz);

			//DOM generation
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
  
			//DOM Tree
			Document doc = builder.parse(in);

			NodeList quizes = doc.getElementsByTagName("quiz");
			for(int i=0; i<quizes.getLength(); i++){
				Quiz quiz = new Quiz();
				NodeList Q = quizes.item(i).getChildNodes(); // quizes 

				for(int j=0;j<Q.getLength();j++){
					Node n = Q.item(j); 
					
					if(n.getNodeName().equals("nid")){
						quiz.nid = Integer.parseInt(n.getFirstChild().getNodeValue());
					}
					
					if(n.getNodeName().equals("category")){
						quiz.category = n.getFirstChild().getNodeValue();
					}
					if(n.getNodeName().equals("question")){
						quiz.question = n.getFirstChild().getNodeValue();
					}
					if(n.getNodeName().equals("example")){
						quiz.example01 = ((Element)n).getAttribute("first");
						quiz.example02 = ((Element)n).getAttribute("second");
						quiz.example03 = ((Element)n).getAttribute("third");
						quiz.example04 = ((Element)n).getAttribute("fourth");
					}
					if(n.getNodeName().equals("answer")){
						quiz.answer01 = ((Element)n).getAttribute("first");
						quiz.answer02 = ((Element)n).getAttribute("second");
						quiz.answer03 = ((Element)n).getAttribute("third");
						quiz.answer04 = ((Element)n).getAttribute("fourth");
					}
					if(n.getNodeName().equals("hint")){					
						quiz.hint = n.getFirstChild().getNodeValue();
					}
				}
				if(!dbAdapter.searchQuiz(String.valueOf(quiz.nid))){
					dbAdapter.addQuiz(quiz);
				}
			}
			
		}catch(Exception e){
			Toast.makeText(this, " Exception :"+e.toString(), Toast.LENGTH_LONG).show();
		}finally{
			if(in!=null)try{in.close();}catch(IOException e){}
		}
		
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbAdapter.close();
	}

	@Override
	public void onClick(View v) { // v : 이벤트발생한 뷰객체(버튼)
			// 화면 이동, 데이터 전달
			Intent i = null; // Intent : 액티비티간 매개역할, 
			if(v.getId()==R.id.btn_java){//
				i = new Intent(this,QuizActivity.class);
				i.putExtra("category", btn_java.getText().toString());//키,밸류 쌍
			}else if(v.getId()==R.id.btn_sql){//
				i = new Intent(this,QuizActivity.class);
				i.putExtra("category", btn_sql.getText().toString());//키,밸류 쌍
			}else if(v.getId()==R.id.btn_android){//
				i = new Intent(this,QuizActivity.class);
				i.putExtra("category", btn_android.getText().toString());//키,밸류 쌍
			}else if(v.getId()==R.id.btn_jsp){//
				i = new Intent(this,QuizActivity.class);
				i.putExtra("category", btn_jsp.getText().toString());//키,밸류 쌍
			}else if(v.getId()==R.id.btn_js){//
				i = new Intent(this,QuizActivity.class);
				i.putExtra("category", btn_js.getText().toString());//키,밸류 쌍
			}else if(v.getId()==R.id.btn_etc){//
				i = new Intent(this,QuizActivity.class);
				i.putExtra("category", btn_etc.getText().toString());//키,밸류 쌍
			}
			
			//화면이동
			startActivity(i);//인텐트가가지고 있는 액티비티정보로 두번째화면호출(화면이동)
			             //단, 액티비티=>설정파일(AndroidManifest.xml)에 등록되어있어야함
			//세콘드액티비티에서 데이터받을 준비시키러가자...
		}
	
		
}



