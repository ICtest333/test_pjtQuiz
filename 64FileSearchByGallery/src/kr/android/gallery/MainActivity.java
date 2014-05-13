/* Media DB�κ��� ������ �ҷ������� �� => Content Provider ����/�̿� : Uri�� �����ؾ���. "content: " ��Ű��, Last Path segment: primary key
 Intent : Activity(ȭ��) ȣ���� Activity(ȭ��)�� ������ ����/ȭ�� ���÷���
 Cursor : Activity �Ű����� ������ �̾ƿ����� �Ҷ�, ContentResolver �Ű���üȰ�� 
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
		btn.setOnClickListener(this); // �̺�Ʈ����							
	}

	@Override
	public void onClick(View v) {
		// �������� ȣ���ؼ� �޴����� ����� �̹����� �о����
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT); //�̹��������о��(�����������ϴ�App���ȣ����)
		intent.setType("image/*"); // image/jpeg, image/png, image/gif�� ��� ����
		startActivityForResult(intent, 0);// 0: requestCode 1���� ��Ȯ�Ѱ��(����������Ƿ� ���ڻ����ȵ�)
				
	}
	
	//Activity ȣ���ؼ� ������� �����͸� ���޹��� �޼���
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data); // ����ó������ �ʼ��ڵ�
	
		//�̹������÷��̵ǵ���
		if(data!=null){
			FileOutputStream fileOut = null; // FileOutputStream : �о���̹����� ����(Ȱ��)�Ҽ��ֵ���
			try{
				//�̹��� Uri ������
				Uri uri = data.getData();
				//�̹����信 �̹��������ֱ�
				Bitmap image = Images.Media.getBitmap(getContentResolver(), uri);
						//getContentResolver(): ContentResolver��ü��ȯ
						//ContentResolver��ü: Content Provider������ App�� DB����, Uri������ ��Ī�Ǵ� ����������/�Ű�����)
				photo.setImageBitmap(image);
				photo.setVisibility(View.VISIBLE);
				
				//�̹������� ���� �� ǥ��
				text.setText("====�̹��� ����====\n");
				text.append("URI:" + uri.toString()+"\n");
				text.append("Last Path Segment: " + uri.getLastPathSegment()+"\n");
				
				//��Ÿ ���� ���� �� ǥ�� : Cursor ��ü/�˻� �̿�
				Cursor c = getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI, 
						null, 
						Images.Media._ID+"=?", 
						new String[]{uri.getLastPathSegment()}, 
						null);
				
				if(c.moveToFirst()){
					String imagePath = c.getString(c.getColumnIndexOrThrow(Images.Media.DATA));
														
					text.append("�����̹������:"+imagePath+"\n");
					
					File f = new File(imagePath);
					text.append("�̹����뷮:"+f.length()+"\n"); // �����̹������ �̿�
															
				} // if
				
				text.append("ũ��:"+image.getWidth()+"*"+image.getHeight()); // Bitmap���κ��� �ٷΰ�����
				
				text.setVisibility(View.VISIBLE);
				
				// SDCard�� ����� ������ �о�鿩 ���ø����̼� ���� ������ ����(�׽�Ʈ)
				fileOut = openFileOutput("test.jpg", MODE_PRIVATE);
				               //������,     100:����Ƽ(����), fileOut: ��Ʈ��
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
