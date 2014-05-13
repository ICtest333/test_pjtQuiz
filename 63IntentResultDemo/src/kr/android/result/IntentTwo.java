package kr.android.result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IntentTwo extends Activity implements OnClickListener{ // AndroidManifestApp Activity�߰�!

	Button okButton, cancelButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Source => Override Implements 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two);
		
		okButton = (Button)findViewById(R.id.ok_button); //��ư����
		cancelButton = (Button)findViewById(R.id.cancel_button);
		okButton.setOnClickListener(this); // �̺�Ʈ����
		cancelButton.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// add unimplemented method
		if(v.getId()==R.id.ok_button){
			Intent result = getIntent();
			result.putExtra("msg", "������");
			// ���� Activity���� ������ �����͸� �� Activity�� ����
			setResult(RESULT_OK, result);
			
			finish(); // Activity����
			
		}else if(v.getId()==R.id.cancel_button){
			
			setResult(RESULT_CANCELED, null);
			
			finish(); // Activity����
		}
		
	}

}
