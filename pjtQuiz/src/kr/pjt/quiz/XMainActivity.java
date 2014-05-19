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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*
public class MainActivity extends Activity implements View.OnClickListener{

	EditText mResult;
	Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//mResult = (EditText)findViewById(R.id.result);
		btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		InputStream in = null;

		try{
			in = getResources().openRawResource(R.raw.quiz);

			//DOM generation
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			//DOM Tree
			Document doc = builder.parse(in);

			StringBuffer sb = new StringBuffer();
			sb.append("Quiz Start\n");

			NodeList quizes = doc.getElementsByTagName("quiz");
			for(int i=0; i<quizes.getLength(); i++){
				Quiz quiz = new Quiz();
				NodeList article = quizes.item(i).getChildNodes(); // quizes 

				for(int j=0;j<article.getLength();j++){
					Node n = article.item(j); 
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
			}
			mResult.setText(sb.toString());

		}catch(Exception e){
			Toast.makeText(this, " Exception :"+e.toString(), Toast.LENGTH_LONG).show();
		}finally{
			if(in!=null)try{in.close();}catch(IOException e){}
		}		
	} // onClick	

	class Quiz{
		String category;
		String question;
		String example01;
		String example02;
		String example03;
		String example04;
		String answer01;
		String answer02;
		String answer03;
		String answer04;
		String hint;
	}
}
*/