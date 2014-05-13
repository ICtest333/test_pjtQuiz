package kr.android.move;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener{
	
	Button btn,btn2;
	EditText edit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn = (Button)findViewById(R.id.button1);
		btn2 = (Button)findViewById(R.id.button2);
		
		edit = (EditText)findViewById(R.id.editText1);

		//이벤트소스와 이벤트리스너 연결
		btn.setOnClickListener(this);
		btn2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) { // v : 이벤트발생한 뷰객체(버튼)
		// 화면 이동, 데이터 전달
		Intent i = null; // Intent : 액티비티간 매개역할, 
		if(v.getId()==R.id.button1){//화면이동 버튼클릭한 경우(id일치여부로판단)
			i = new Intent(this,SecondActivity.class);
			             //현재(첫번째)액티비티,이동할(두번째)액티비티(쩜클래스형태)
		}else if(v.getId()==R.id.button2){//데이터전달 버튼클릭한 경우
			i = new Intent(this,SecondActivity.class);
			//화면 이동시에 이동된 화면에서 호출할 데이터를 저장
			i.putExtra("msg", edit.getText().toString());//키,밸류 쌍
		}
		//화면이동
		startActivity(i);//인텐트가가지고 있는 액티비티정보로 두번째화면호출(화면이동)
		             //단, 액티비티=>설정파일(AndroidManifest.xml)에 등록되어있어야함
		//세콘드액티비티에서 데이터받을 준비시키러가자...
	}

	

}
