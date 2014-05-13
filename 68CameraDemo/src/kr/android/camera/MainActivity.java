package kr.android.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	MyCameraSurface mSurface;
	Button mShutter;
	ImageView rawImage;
	File sd_path = Environment.getExternalStorageDirectory();
	
	SimpleDateFormat dateFormat = 
			new SimpleDateFormat("yyyyMMddHHmmssSS");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSurface = (MyCameraSurface)findViewById(R.id.preview);
		rawImage = (ImageView)findViewById(R.id.rawImage);
		
		mShutter = (Button)findViewById(R.id.shutter);
		mShutter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				/*
				 * 사진 촬영은 Camera객체의 takePicture 메서드
				 * 호출을 통해 이뤄짐
				 * takePicture는 ShutterCallback 구현 하나와
				 * PicutreCallback 구현 두개
				 * (Raw 이미지용 하나와 JPEG으로 인코딩 이미지용)
				 * 하나)를 매개변수로 받음
				 */
				mSurface.mCamera.takePicture(shutterCallback,
						rawCallback,mPicture);
			}
		});
	}
	
	ShutterCallback shutterCallback =
			new ShutterCallback(){
		@Override
		public void onShutter(){
			//셔터가 닫힐 때 필요한 작업을 수행.
		}
	};
	
	PictureCallback rawCallback = 
			new PictureCallback(){
		@Override
		public void onPictureTaken(byte[] data,
				                 Camera camera){
			//Raw 이미지 데이터를 가지고 필요한 작업을 수행
		}
	};
	
	//사진 저장
	PictureCallback mPicture =
			new PictureCallback(){
		@Override
		public void onPictureTaken(byte[] data,
				                   Camera camera){
			if(data!=null){
				//촬영한 이미지 표시
				BitmapFactory.Options opts = 
						new BitmapFactory.Options();
				
				//원본은 건드리지 않고, 이미지 크기만
				//'1/입력숫자(예:16)'로 줄여서 로딩함
				//비트맵이 메모리를 너무 많이 차지하는
				//문제 해결하기 위한 옵션
				opts.inSampleSize = 16; // => 예: 1/16
				
				Bitmap bitmap = 
				BitmapFactory.decodeByteArray(data, 0, 
						data.length,opts);
				rawImage.setImageBitmap(bitmap); // 저해상도이미지=>미리보기구현
						
				//촬영된 영상을 파일에 저장
				
				//파일명 지정
				String filename = 
						dateFormat.format(new Date());
				String path = sd_path.getAbsolutePath()+"/"+filename+".jpg";
				
				
				FileOutputStream fos = null;
				try{
					fos = new FileOutputStream(path);
					fos.write(data); // 파일저장
				}catch(Exception e){
					Toast.makeText(MainActivity.this, 
							"파일 저장 중 에러 발생", 
							Toast.LENGTH_SHORT).show();
					Log.e("CameraDemo","File Error",e);
					
					return; //오류 발생시 메서드 종료 : 중요 !!!
					
				}finally{
					if(fos!=null)try{fos.close();}catch(IOException e){}
				}
				
				//미디어 파일을 추가한 후, MediaScanner를 이용하여
				//해당 파일을 미디어 DB에 추가
				Intent intent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				
				Uri uri = Uri.parse("file://"+path);
				intent.setData(uri);
				
				//브로드캐스트 인텐트를 이용해 해당 미디어 파일이 삽입
				//되어 사용 가능함을 알려야 함
				sendBroadcast(intent);
				
				Toast.makeText(MainActivity.this, 
						"사진 저장 완료", 
						Toast.LENGTH_SHORT).show();
				
				mSurface.mCamera.startPreview(); // 이미지정보갱신
			}
		}
	}; //end of new PictureCallback(){
	
	//메뉴 등록
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuItem item = menu.add(0, 1, 0, "갤러리보기");
		//메뉴 이벤트 처리
		item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				Intent intent = new Intent(Intent.ACTION_VIEW, Media.EXTERNAL_CONTENT_URI);
				startActivity(intent);
				
				return true;
			}
		});
		
		return true;
	}
		
	
	
} // end of MainActivity
/*
 * View에 동영상 또는 카메라 프리뷰와 같이 빠른 화면 변화
 * 또는 그려지는 양이 많을 경우 SurfaceView를 사용해 처리
 * 
 * Surface는 그래픽버퍼
 * SurfaceView에서 화면 제어를 하기 위해서 SurfaceHolder
 * 생성
 * ------------------
 * SurfaceView
 * ------------------
 * Surface
 * ------------------
 * SurfaceHolder
 * 
 * SurfaceHolder를 이용해서 Surface(버퍼)에 그림을 그리면
 * SurfaceView에 반영
 * 
 *[이벤트 처리]
 *Callback Interface는 SurfaceHolder를 통해서 작성한
 *Surface와 SurfaceView를 연결하기 위해서
 *Surface의 생성, 변경, 종료에 대한 이벤트 처리
 */

class MyCameraSurface extends SurfaceView 
                 implements SurfaceHolder.Callback{
	SurfaceHolder mHolder;
	Camera mCamera;
	
	public MyCameraSurface(Context context, 
			                   AttributeSet attrs){
		super(context,attrs);
		
		//표면홀더 초기화
		mHolder = getHolder();
		//SurfaceHolder와 Callback 연결
		mHolder.addCallback(this);
	}
	//표면 생성시 카메라 오픈하고 미리보기 설정
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//카메라 초기화
		mCamera = Camera.open();
		
		//SurfaceView가 만들어질 때 카메라 객체를 참조한
		//후 미리보기 화면으로 홀더 객체 생성
		try{
			//카메라 객체 홀더로 세팅
			mCamera.setPreviewDisplay(mHolder);
		}catch(IOException e){
			mCamera.release();
			mCamera = null;
		}
	}
	//SurfaceView의 화면 크기가 바뀌는 등의 변경 시점에
	//미리보기 시작
	@Override
	public void surfaceChanged(SurfaceHolder holder, 
			    int format, int width, int height) {
		//카메라로 보기 구현하는 이벤트 처리
		mCamera.startPreview();
	}

	//표면 파괴시 카메라도 파괴
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//카메라로 보기구현하는 이벤트 정지 처리
		if(mCamera!=null){
			mCamera.stopPreview();//뷰 정지
			mCamera.release();
			mCamera = null;
		}
	}
}
