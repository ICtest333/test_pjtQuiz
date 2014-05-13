package kr.adnroid.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	static final String NOTES = "notes.txt";
	/*DDMS : FileExplorer : data > data > kr.adnroid.file > files �������� ���� ������Ŵ
	 *       ��Ʈ�������� ������ ����ÿ��� ���� ��Ʈ������ ���ٸ��ϵ��� ���Ƴ��Ƽ� Ȯ�κҰ�
	 *       openFileInput() */
	EditText editor;
	Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editor = (EditText)findViewById(R.id.editor);
		btn = (Button)findViewById(R.id.button1);
		
		btn.setOnClickListener(new OnClickListener() {
			//�̺�Ʈ �ڵ鷯
			@Override
			public void onClick(View v) {
				/*Activity ���� : ��Ƽ��Ƽ�����ֱ��(onPause->onStop->onDestroy) 
				 *                onPause�ܰ迡�� ���屸������(onResume:����Ʈ�� ����ȣ��)
				                  �ٷ����� ������ �����ư ������.*/
				finish();				
			}
		}); // end of new OnClickListener(){} �͸���Ŭ����	
	} // end of onCreate
	
	@Override
	public void onResume(){
		super.onResume();
		//���Ϸκ��� �����͸� �о� ��(���� ���Ͼ��»��¿��� ȣ��� ����1ȸ �����߻��ϳ� �����Ұ���)
		InputStream in = null;
		InputStreamReader tmp = null;
		BufferedReader reader = null;
		try{
			//���Ϸκ��� �����͸� �о InputStrem���� ��ȯ
			in = openFileInput(NOTES);
			
			if(in!=null){
				//���ڽ�Ʈ������ ��ȯ
				tmp = new InputStreamReader(in);
				reader = new BufferedReader(tmp);
				String str;
				//��Ʈ�����ۿ� ����(��Ʈ���� ����� �����ⰴü ����)
				StringBuffer buf = new StringBuffer();
				while((str=reader.readLine())!=null){
					buf.append(str+'\n');
				}//while				
				editor.setText(buf.toString());
			}// if			
		}catch(FileNotFoundException e){
			//ó��ȣ��ô� ������ ���������ʱ⶧���� 1ȸ ���ܰ� �߻���(�̶��¾ƹ��۾��Ƚ�Ŵ)
		}catch(Exception e){
			Toast.makeText(this, "���� : "+ e.toString(), Toast.LENGTH_SHORT).show();
		}finally{
			if(reader!=null)try{reader.close();}catch(IOException e){}
			if(tmp!=null)try{tmp.close();}catch(IOException e){}
			if(in!=null)try{in.close();}catch(IOException e){}
		}
	}
	@Override
	public void onPause(){
		super.onPause();
		//���Ͽ� �����͸� ������
		OutputStreamWriter out = null;
		
		try{
			//����Ʈ��Ʈ���� ���ڽ�Ʈ������ ��ȯ
			out = new OutputStreamWriter(openFileOutput(NOTES, MODE_PRIVATE));
			                           //�ι�°���� : �������(�����/�̾��)
			                           // MODE_PRIVATE : �����(���������͸������о�ͼ��߰��ϴ°����)
			                           // MODE_APPEND : �̾��
			out.write(editor.getText().toString());
			Toast.makeText(this, "�����͸� �����մϴ�!!", Toast.LENGTH_SHORT).show();
						
		}catch(Exception e){
			Toast.makeText(this, "���� : " + e.toString(), Toast.LENGTH_SHORT).show();
		}finally{
			if(out!=null)try{out.close();}catch(IOException e){}
		}		
	}//onPause
}
