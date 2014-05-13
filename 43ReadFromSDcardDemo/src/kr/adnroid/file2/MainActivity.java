package kr.adnroid.file2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	//파일명(파일명에 시간정보붙여서 파일명 만들고자 할 경우: 생성시마다 별개 파일 생성함)
	String filename = "test" + System.currentTimeMillis() + ".txt";
	//SDcard 경로
	File sdcard_path = Environment.getExternalStorageDirectory();
	
	TextView readOutput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//TextView 객체 초기화
		readOutput = (TextView)findViewById(R.id.output);
		
		// 파일생성 메서드 호출
		writeFileToSDcard();
		
		// 파일 읽기 메서드 호출
		readFileFromSDcard();
		
	} // onCreate
	
	// 파일 생성 메서드
	private void writeFileToSDcard(){
		readOutput.setText("[파일쓰기]\n");
		//SDcard가 존재하고 파일을 생성(쓰기)할 수 있는지여부 체크
		if(sdcard_path.exists() && sdcard_path.canWrite()){
			//폴더( Storage 하위 폴더로... /filetest)를 생성
			//경로지정
			File uadDir = new File(sdcard_path.getAbsolutePath()+"/filetest");
			//사용하고자하는 폴더(디렉토리)에 파일 생성
			// getAbsoluteFile() 이 아니라 getAbsolutePath()을 사용할 것 !!
			uadDir.mkdir();	 // 폴더생성(동일명폴더존재하지않는경우에만 생성함)		
			FileOutputStream fos = null;
			try{
				fos = new FileOutputStream(uadDir.getAbsolutePath()+"/"+filename); //파일만들기
				
				String msg = "살아라!!!!!!!!"; //파일내에 넣을 내용
				fos.write(msg.getBytes()); // String -> byte[]
				
				readOutput.append("파일이 생성되었습니다.\n");
				
			}catch(IOException e){
				Toast.makeText(this, "예외 : " + e.toString(), Toast.LENGTH_SHORT).show();
				
			}finally{
				if(fos!=null)try{fos.close();}catch(IOException e){}
			}			
		}
	}
	
	// 파일로부터 데이터 읽기 메서드
	private void readFileFromSDcard(){
		readOutput.append("=========================\n");
		readOutput.append("[파일 읽기]\n");
		File rFile = new File(sdcard_path.getAbsoluteFile()+"/filetest/"+filename);
		                                                            // 뒤의 경로슬래쉬(/)도 누락금지!
		//파일 읽어올 수 있는지 여부 조건 체크
		if(rFile.exists() && rFile.canRead()){
			FileInputStream fis = null;
			try{
				fis = new FileInputStream(rFile); // 파일읽어오기
				byte[] reader = new byte[fis.available()];
				fis.read(reader);
				                      // byte[] -> String (생성자를 통해 형변환)
				readOutput.append(new String(reader));
				
			}catch(IOException e){
				Toast.makeText(this, "예외 : " + e.toString(), Toast.LENGTH_SHORT).show();
			}finally{
				if(fis!=null)try{fis.close();}catch(IOException e){}
			}
		} // if		
	} // readFileFromSDcard	
}
