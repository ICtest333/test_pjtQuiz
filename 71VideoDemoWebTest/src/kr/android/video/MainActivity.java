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
	final String VIDEO_PATH = "http://192.168.0.10:8080/web/be_my_baby5.mp4"; //쌤pc
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		videoView = (VideoView)findViewById(R.id.videoView);
		videoView.requestFocus();
		videoView.setMediaController(new MediaController(this));
		
		try{
			//raw 폴더의 파일을 내장 영역에 저장
			rawToFile(this, R.raw.be_my_baby5, "be_my_baby5.mp4"); // raw
			//내장 영역에 동영상 호출
			String path = getFilesDir().getAbsolutePath()+"/be_my_baby5.mp4";
			//videoView.setVideoPath(path);
			
			//SD카드에서 동영상 읽기
			//videoView.setVideoPath(sd_path.getAbsolutePath()+"/be_my_baby5.mp4");
			
			//서버에서 동영상 읽어오기
			videoView.setVideoURI(Uri.parse(VIDEO_PATH));
			
		}catch(Exception e){
			Log.e("VideoDemo","play error",e);
		}
		
		
	}//onCreate
	
	//raw 폴더에서 파일을 호출해서 InputStream으로 가공
	private void rawToFile(Context context, int resId, String filename) throws Exception{
		InputStream in = context.getResources().openRawResource(resId);
		inToFile(context, in, filename);
	}
	
	//InputStream을 내장 영역(Files)에 파일로 저장
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
