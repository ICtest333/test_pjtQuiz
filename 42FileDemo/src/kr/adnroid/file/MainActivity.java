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
	/*DDMS : FileExplorer : data > data > kr.adnroid.file > files 폴더내에 파일 생성시킴
	 *       루트영역으로 폰에서 실행시에는 내장 루트영역에 접근못하도록 막아놓아서 확인불가
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
			//이벤트 핸들러
			@Override
			public void onClick(View v) {
				/*Activity 종료 : 액티비티생명주기상(onPause->onStop->onDestroy) 
				 *                onPause단계에서 저장구현가능(onResume:디폴트로 먼저호출)
				                  근래에는 별도의 저장버튼 지원됨.*/
				finish();				
			}
		}); // end of new OnClickListener(){} 익명내부클래스	
	} // end of onCreate
	
	@Override
	public void onResume(){
		super.onResume();
		//파일로부터 데이터를 읽어 옴(최초 파일없는상태에서 호출시 최초1회 에러발생하나 무시할것임)
		InputStream in = null;
		InputStreamReader tmp = null;
		BufferedReader reader = null;
		try{
			//파일로부터 데이터를 읽어서 InputStrem으로 반환
			in = openFileInput(NOTES);
			
			if(in!=null){
				//문자스트림으로 변환
				tmp = new InputStreamReader(in);
				reader = new BufferedReader(tmp);
				String str;
				//스트링버퍼에 저장(스트링에 저장시 쓰레기객체 양산됨)
				StringBuffer buf = new StringBuffer();
				while((str=reader.readLine())!=null){
					buf.append(str+'\n');
				}//while				
				editor.setText(buf.toString());
			}// if			
		}catch(FileNotFoundException e){
			//처음호출시는 파일이 존재하지않기때문에 1회 예외가 발생함(이때는아무작업안시킴)
		}catch(Exception e){
			Toast.makeText(this, "예외 : "+ e.toString(), Toast.LENGTH_SHORT).show();
		}finally{
			if(reader!=null)try{reader.close();}catch(IOException e){}
			if(tmp!=null)try{tmp.close();}catch(IOException e){}
			if(in!=null)try{in.close();}catch(IOException e){}
		}
	}
	@Override
	public void onPause(){
		super.onPause();
		//파일에 데이터를 저장함
		OutputStreamWriter out = null;
		
		try{
			//바이트스트림을 문자스트림으로 변환
			out = new OutputStreamWriter(openFileOutput(NOTES, MODE_PRIVATE));
			                           //두번째인자 : 모드지정(덮어쓰기/이어쓰기)
			                           // MODE_PRIVATE : 덮어쓰기(기존데이터를먼저읽어와서추가하는경우임)
			                           // MODE_APPEND : 이어쓰기
			out.write(editor.getText().toString());
			Toast.makeText(this, "데이터를 저장합니다!!", Toast.LENGTH_SHORT).show();
						
		}catch(Exception e){
			Toast.makeText(this, "예외 : " + e.toString(), Toast.LENGTH_SHORT).show();
		}finally{
			if(out!=null)try{out.close();}catch(IOException e){}
		}		
	}//onPause
}
