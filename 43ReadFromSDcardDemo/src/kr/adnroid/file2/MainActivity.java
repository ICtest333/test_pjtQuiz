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

	//���ϸ�(���ϸ� �ð������ٿ��� ���ϸ� ������� �� ���: �����ø��� ���� ���� ������)
	String filename = "test" + System.currentTimeMillis() + ".txt";
	//SDcard ���
	File sdcard_path = Environment.getExternalStorageDirectory();
	
	TextView readOutput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//TextView ��ü �ʱ�ȭ
		readOutput = (TextView)findViewById(R.id.output);
		
		// ���ϻ��� �޼��� ȣ��
		writeFileToSDcard();
		
		// ���� �б� �޼��� ȣ��
		readFileFromSDcard();
		
	} // onCreate
	
	// ���� ���� �޼���
	private void writeFileToSDcard(){
		readOutput.setText("[���Ͼ���]\n");
		//SDcard�� �����ϰ� ������ ����(����)�� �� �ִ������� üũ
		if(sdcard_path.exists() && sdcard_path.canWrite()){
			//����( Storage ���� ������... /filetest)�� ����
			//�������
			File uadDir = new File(sdcard_path.getAbsolutePath()+"/filetest");
			//����ϰ����ϴ� ����(���丮)�� ���� ����
			// getAbsoluteFile() �� �ƴ϶� getAbsolutePath()�� ����� �� !!
			uadDir.mkdir();	 // ��������(���ϸ��������������ʴ°�쿡�� ������)		
			FileOutputStream fos = null;
			try{
				fos = new FileOutputStream(uadDir.getAbsolutePath()+"/"+filename); //���ϸ����
				
				String msg = "��ƶ�!!!!!!!!"; //���ϳ��� ���� ����
				fos.write(msg.getBytes()); // String -> byte[]
				
				readOutput.append("������ �����Ǿ����ϴ�.\n");
				
			}catch(IOException e){
				Toast.makeText(this, "���� : " + e.toString(), Toast.LENGTH_SHORT).show();
				
			}finally{
				if(fos!=null)try{fos.close();}catch(IOException e){}
			}			
		}
	}
	
	// ���Ϸκ��� ������ �б� �޼���
	private void readFileFromSDcard(){
		readOutput.append("=========================\n");
		readOutput.append("[���� �б�]\n");
		File rFile = new File(sdcard_path.getAbsoluteFile()+"/filetest/"+filename);
		                                                            // ���� ��ν�����(/)�� ��������!
		//���� �о�� �� �ִ��� ���� ���� üũ
		if(rFile.exists() && rFile.canRead()){
			FileInputStream fis = null;
			try{
				fis = new FileInputStream(rFile); // �����о����
				byte[] reader = new byte[fis.available()];
				fis.read(reader);
				                      // byte[] -> String (�����ڸ� ���� ����ȯ)
				readOutput.append(new String(reader));
				
			}catch(IOException e){
				Toast.makeText(this, "���� : " + e.toString(), Toast.LENGTH_SHORT).show();
			}finally{
				if(fis!=null)try{fis.close();}catch(IOException e){}
			}
		} // if		
	} // readFileFromSDcard	
}
