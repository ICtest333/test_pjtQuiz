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
				 * ���� �Կ��� Camera��ü�� takePicture �޼���
				 * ȣ���� ���� �̷���
				 * takePicture�� ShutterCallback ���� �ϳ���
				 * PicutreCallback ���� �ΰ�
				 * (Raw �̹����� �ϳ��� JPEG���� ���ڵ� �̹�����)
				 * �ϳ�)�� �Ű������� ����
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
			//���Ͱ� ���� �� �ʿ��� �۾��� ����.
		}
	};
	
	PictureCallback rawCallback = 
			new PictureCallback(){
		@Override
		public void onPictureTaken(byte[] data,
				                 Camera camera){
			//Raw �̹��� �����͸� ������ �ʿ��� �۾��� ����
		}
	};
	
	//���� ����
	PictureCallback mPicture =
			new PictureCallback(){
		@Override
		public void onPictureTaken(byte[] data,
				                   Camera camera){
			if(data!=null){
				//�Կ��� �̹��� ǥ��
				BitmapFactory.Options opts = 
						new BitmapFactory.Options();
				
				//������ �ǵ帮�� �ʰ�, �̹��� ũ�⸸
				//'1/�Է¼���(��:16)'�� �ٿ��� �ε���
				//��Ʈ���� �޸𸮸� �ʹ� ���� �����ϴ�
				//���� �ذ��ϱ� ���� �ɼ�
				opts.inSampleSize = 16; // => ��: 1/16
				
				Bitmap bitmap = 
				BitmapFactory.decodeByteArray(data, 0, 
						data.length,opts);
				rawImage.setImageBitmap(bitmap); // ���ػ��̹���=>�̸����ⱸ��
						
				//�Կ��� ������ ���Ͽ� ����
				
				//���ϸ� ����
				String filename = 
						dateFormat.format(new Date());
				String path = sd_path.getAbsolutePath()+"/"+filename+".jpg";
				
				
				FileOutputStream fos = null;
				try{
					fos = new FileOutputStream(path);
					fos.write(data); // ��������
				}catch(Exception e){
					Toast.makeText(MainActivity.this, 
							"���� ���� �� ���� �߻�", 
							Toast.LENGTH_SHORT).show();
					Log.e("CameraDemo","File Error",e);
					
					return; //���� �߻��� �޼��� ���� : �߿� !!!
					
				}finally{
					if(fos!=null)try{fos.close();}catch(IOException e){}
				}
				
				//�̵�� ������ �߰��� ��, MediaScanner�� �̿��Ͽ�
				//�ش� ������ �̵�� DB�� �߰�
				Intent intent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				
				Uri uri = Uri.parse("file://"+path);
				intent.setData(uri);
				
				//��ε�ĳ��Ʈ ����Ʈ�� �̿��� �ش� �̵�� ������ ����
				//�Ǿ� ��� �������� �˷��� ��
				sendBroadcast(intent);
				
				Toast.makeText(MainActivity.this, 
						"���� ���� �Ϸ�", 
						Toast.LENGTH_SHORT).show();
				
				mSurface.mCamera.startPreview(); // �̹�����������
			}
		}
	}; //end of new PictureCallback(){
	
	//�޴� ���
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuItem item = menu.add(0, 1, 0, "����������");
		//�޴� �̺�Ʈ ó��
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
 * View�� ������ �Ǵ� ī�޶� ������� ���� ���� ȭ�� ��ȭ
 * �Ǵ� �׷����� ���� ���� ��� SurfaceView�� ����� ó��
 * 
 * Surface�� �׷��ȹ���
 * SurfaceView���� ȭ�� ��� �ϱ� ���ؼ� SurfaceHolder
 * ����
 * ------------------
 * SurfaceView
 * ------------------
 * Surface
 * ------------------
 * SurfaceHolder
 * 
 * SurfaceHolder�� �̿��ؼ� Surface(����)�� �׸��� �׸���
 * SurfaceView�� �ݿ�
 * 
 *[�̺�Ʈ ó��]
 *Callback Interface�� SurfaceHolder�� ���ؼ� �ۼ���
 *Surface�� SurfaceView�� �����ϱ� ���ؼ�
 *Surface�� ����, ����, ���ῡ ���� �̺�Ʈ ó��
 */

class MyCameraSurface extends SurfaceView 
                 implements SurfaceHolder.Callback{
	SurfaceHolder mHolder;
	Camera mCamera;
	
	public MyCameraSurface(Context context, 
			                   AttributeSet attrs){
		super(context,attrs);
		
		//ǥ��Ȧ�� �ʱ�ȭ
		mHolder = getHolder();
		//SurfaceHolder�� Callback ����
		mHolder.addCallback(this);
	}
	//ǥ�� ������ ī�޶� �����ϰ� �̸����� ����
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//ī�޶� �ʱ�ȭ
		mCamera = Camera.open();
		
		//SurfaceView�� ������� �� ī�޶� ��ü�� ������
		//�� �̸����� ȭ������ Ȧ�� ��ü ����
		try{
			//ī�޶� ��ü Ȧ���� ����
			mCamera.setPreviewDisplay(mHolder);
		}catch(IOException e){
			mCamera.release();
			mCamera = null;
		}
	}
	//SurfaceView�� ȭ�� ũ�Ⱑ �ٲ�� ���� ���� ������
	//�̸����� ����
	@Override
	public void surfaceChanged(SurfaceHolder holder, 
			    int format, int width, int height) {
		//ī�޶�� ���� �����ϴ� �̺�Ʈ ó��
		mCamera.startPreview();
	}

	//ǥ�� �ı��� ī�޶� �ı�
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//ī�޶�� ���ⱸ���ϴ� �̺�Ʈ ���� ó��
		if(mCamera!=null){
			mCamera.stopPreview();//�� ����
			mCamera.release();
			mCamera = null;
		}
	}
}
