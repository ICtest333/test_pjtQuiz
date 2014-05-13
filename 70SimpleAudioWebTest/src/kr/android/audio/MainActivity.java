package kr.android.audio;

import java.io.File;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	//final String AUDIO_PATH = "http://192.168.0.50:8282/web/the_boys.mp3"; //��pc
	final String AUDIO_PATH = "http://192.168.0.10:8080/web/the_boys.mp3"; //��pc
	
	MediaPlayer mediaPlayer;
	Button startBtn,restartBtn,pauseBtn;
	int playbackPosition = 0;
	File sd_path = Environment.getExternalStorageDirectory();
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startBtn = (Button)findViewById(R.id.start_btn);
		restartBtn = (Button)findViewById(R.id.restart_btn);
		pauseBtn = (Button)findViewById(R.id.pause_btn);
		
		startBtn.setOnClickListener(this);
		restartBtn.setOnClickListener(this);
		pauseBtn.setOnClickListener(this);
		
	}//onCreate

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.start_btn){
			//�������
			try {
				//playLocalAudio(); // ����� ���������� ȣ��
				//playSdcardAudio(); // sdcard�� ����� ���� ȣ��
				playAudio(AUDIO_PATH);//�������� ������ ȣ��
			} catch (Exception e) {
				Log.e("SimpleAudio","play error",e);
			}
		}else if(v.getId()==R.id.restart_btn){
			//��� �簳
			if(mediaPlayer!=null && !mediaPlayer.isPlaying()){
				mediaPlayer.seekTo(playbackPosition);
				mediaPlayer.start();
			}
		}else if(v.getId()==R.id.pause_btn){
			//��� �Ͻ� ����
			if(mediaPlayer!=null){
				playbackPosition = mediaPlayer.getCurrentPosition();
				mediaPlayer.pause();
			}
		}
		
	}//onClick	
	
	//�������� ������ �ޱ�
	private void playAudio(String url) throws Exception{
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(url);
		mediaPlayer.prepare();
		mediaPlayer.start();
	}
	//����Ǿ� �ִ� ������ ȣ��
	private void playLocalAudio() throws Exception{
		mediaPlayer = MediaPlayer.create(this, R.raw.the_boys);
		mediaPlayer.start();
	}
	//sdcard���� ������ ȣ�� : ���׽�Ʈ��, phone ��Ʈ�ؿ� ����ȣ��
	private void playSdcardAudio() throws Exception{
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(sd_path.getAbsolutePath()+"/twoneone.mp3");
		//mediaPlayer.setDataSource(sd_path.getAbsolutePath()+"/Music/the_boys.mp3");
		mediaPlayer.prepare();
		mediaPlayer.start();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		killMediaPlayer();
	}
	
	//MediaPlayer �ڿ� ����
	private void killMediaPlayer(){
		if(mediaPlayer!=null){
			try{
				mediaPlayer.release();
			}catch(Exception e){
				Log.e("SimpleAudio","release error",e);
			}
		}//if
	}//killMediaPlayer
}
