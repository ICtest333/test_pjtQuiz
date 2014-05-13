package kr.android.result;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	Button btn1, btn2;
	// 호출한 Activity 식별을 위한 식별자
	private final int SHOW_ACTIVITY_ONE = 1; 
	private final int SHOW_ACTIVITY_TWO = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn1 =(Button)findViewById(R.id.button1);
		btn2 =(Button)findViewById(R.id.button2);
		
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
				
	}

	@Override
	public void onClick(View v) {
		// add unimplemented method
		Intent i = null;
		if(v.getId()==R.id.button1){
			//IntentTwo 호출
			i = new Intent(this, IntentTwo.class);
			startActivityForResult(i, SHOW_ACTIVITY_ONE);
			//결과값받을경우사용메서드 // SHOW_ACTIVITY_ONE : requestCode
		}else if(v.getId()==R.id.button2){
			//주소록 호출
			i = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts/people"));
			startActivityForResult(i, SHOW_ACTIVITY_TWO);			
		}
		
	}
	
	// Activity 호출시 결과값을 받는 메서드
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data); // resultCode : OK or Cancel
		switch(requestCode){
		case SHOW_ACTIVITY_ONE:
			//IntentTwo 액티비티의 결과값 처리
			if(resultCode==Activity.RESULT_OK){
				String result = data.getStringExtra("msg");
				Toast.makeText(this, "성공:"+result, Toast.LENGTH_SHORT).show();
			}else if(resultCode==Activity.RESULT_CANCELED){
				Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show();
			}
			break;
		case SHOW_ACTIVITY_TWO:
			//주소록 액티비티에서 만들어진 결과값 처리
			if(resultCode==Activity.RESULT_OK){
				Toast.makeText(this, "성공:"+data.getData().toString(), Toast.LENGTH_SHORT).show();
				                            // getData() : Uri 형태로 반환
											// "성공: content://contacts/people/1",2,3 등 반환
			}else if(resultCode==Activity.RESULT_CANCELED){
				Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show();
			}
			break; //마지막case는 break; 생략가능
		}//switch
		
	} // onActivityResult
	
}
