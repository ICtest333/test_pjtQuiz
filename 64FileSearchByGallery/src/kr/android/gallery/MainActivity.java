/* Media DB로부터 데이터 불러오고자 함 => Content Provider 구성/이용 : Uri로 접근해야함. "content: " 스키마, Last Path segment: primary key
 Intent : Activity(화면) 호출해 Activity(화면)에 데이터 담음/화면 디스플레이
 Cursor : Activity 매개없이 데이터 뽑아오고자 할때, ContentResolver 매개객체활용 
*/
package kr.android.gallery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	ImageView photo;
	Button btn;
	TextView text;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		photo = (ImageView)findViewById(R.id.photo);
		text = (TextView)findViewById(R.id.text);
		btn = (Button)findViewById(R.id.btn);
		btn.setOnClickListener(this); // 이벤트연결							
	}

	@Override
	public void onClick(View v) {
		// 갤러리를 호출해서 휴대폰에 저장된 이미지를 읽어들임
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT); //이미지정보읽어옴(갤러리역할하는App모두호출함)
		intent.setType("image/*"); // image/jpeg, image/png, image/gif등 모두 포함
		startActivityForResult(intent, 0);// 0: requestCode 1개로 명확한경우(결과값있으므로 인자생략안됨)
				
	}
	
	//Activity 호출해서 만들어진 데이터를 전달받은 메서드
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data); // 정보처리위한 필수코드
	
		//이미지디스플레이되도록
		if(data!=null){
			FileOutputStream fileOut = null; // FileOutputStream : 읽어온이미지를 저장(활용)할수있도록
			try{
				//이미지 Uri 가져옴
				Uri uri = data.getData();
				//이미지뷰에 이미지보여주기
				Bitmap image = Images.Media.getBitmap(getContentResolver(), uri);
						//getContentResolver(): ContentResolver객체반환
						//ContentResolver객체: Content Provider구성된 App의 DB접근, Uri정보에 매칭되는 데이터추출/매개역할)
				photo.setImageBitmap(image);
				photo.setVisibility(View.VISIBLE);
				
				//이미지정보 추출 및 표시
				text.setText("====이미지 정보====\n");
				text.append("URI:" + uri.toString()+"\n");
				text.append("Last Path Segment: " + uri.getLastPathSegment()+"\n");
				
				//기타 정보 추출 및 표시 : Cursor 객체/검색 이용
				Cursor c = getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI, 
						null, 
						Images.Media._ID+"=?", 
						new String[]{uri.getLastPathSegment()}, 
						null);
				
				if(c.moveToFirst()){
					String imagePath = c.getString(c.getColumnIndexOrThrow(Images.Media.DATA));
														
					text.append("원본이미지경로:"+imagePath+"\n");
					
					File f = new File(imagePath);
					text.append("이미지용량:"+f.length()+"\n"); // 원본이미지경로 이용
															
				} // if
				
				text.append("크기:"+image.getWidth()+"*"+image.getHeight()); // Bitmap으로부터 바로가져옴
				
				text.setVisibility(View.VISIBLE);
				
				// SDCard에 저장된 파일을 읽어들여 어플리케이션 내부 공간에 저장(테스트)
				fileOut = openFileOutput("test.jpg", MODE_PRIVATE);
				               //포멧방식,     100:퀄리티(원본), fileOut: 스트림
				image.compress(CompressFormat.JPEG, 100, fileOut);
				//image:Bitmap
			}catch(Exception e){
				Log.e("FileSearchByGallery", "IO Error", e);
			}finally{
				if(fileOut!=null)try{fileOut.close();}catch(IOException e){}
			}
		} // outer if		
	} // onActivityResult	
} // MainActivity
