package kr.android.result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IntentTwo extends Activity implements OnClickListener{ // AndroidManifestApp Activity추가!

	Button okButton, cancelButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Source => Override Implements 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two);
		
		okButton = (Button)findViewById(R.id.ok_button); //버튼연결
		cancelButton = (Button)findViewById(R.id.cancel_button);
		okButton.setOnClickListener(this); // 이벤트연결
		cancelButton.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// add unimplemented method
		if(v.getId()==R.id.ok_button){
			Intent result = getIntent();
			result.putExtra("msg", "데이터");
			// 현재 Activity에서 생성한 데이터를 전 Activity에 전달
			setResult(RESULT_OK, result);
			
			finish(); // Activity종료
			
		}else if(v.getId()==R.id.cancel_button){
			
			setResult(RESULT_CANCELED, null);
			
			finish(); // Activity종료
		}
		
	}

}
