package kr.android.video;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends ActionBarActivity {

	VideoView videoView;	
	File sd_path = Environment.getExternalStorageDirectory();
	final String VIDEO_PATH = "http://192.168.0.10:8080/web/be_my_baby5.mp4"; //��pc
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		videoView = (VideoView)findViewById(R.id.videoView);
		videoView.requestFocus();
		videoView.setMediaController(new MediaController(this));
		
		try{
			//raw ������ ������ ���� ������ ����
			rawToFile(this, R.raw.be_my_baby5, "be_my_baby5.mp4"); // raw
			//���� ������ ������ ȣ��
			String path = getFilesDir().getAbsolutePath()+"/be_my_baby5.mp4";
			//videoView.setVideoPath(path);
			
			//SDī�忡�� ������ �б�
			//videoView.setVideoPath(sd_path.getAbsolutePath()+"/be_my_baby5.mp4");
			
			//�������� ������ �о����
			videoView.setVideoURI(Uri.parse(VIDEO_PATH));
			
		}catch(Exception e){
			Log.e("VideoDemo","play error",e);
		}
		
		
	}//onCreate
	
	//raw �������� ������ ȣ���ؼ� InputStream���� ����
	private void rawToFile(Context context, int resId, String filename) throws Exception{
		InputStream in = context.getResources().openRawResource(resId);
		inToFile(context, in, filename);
	}
	
	//InputStream�� ���� ����(Files)�� ���Ϸ� ����
	private void inToFile(Context context, InputStream in, String fileName) throws Exception{
		int size;
		byte[] w = new byte[1024];
		OutputStream out = null;
		try{
			out = openFileOutput(fileName, MODE_WORLD_READABLE);
			while(true){
				size = in.read(w);
				if(size<=0)break;
				out.write(w, 0, size);				
			}
		}catch(Exception e){
			Log.e("VideoDemo","io error",e);
		}finally{
			if(out!=null)try{out.close();}catch(IOException e){}
			if(in!=null)try{in.close();}catch(IOException e){}
		}
		
	}

}
