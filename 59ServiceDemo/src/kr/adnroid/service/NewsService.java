//NewsService : ��׶����۾�(Back��ư���� MainActivity����=>UI������ ��ӱ���(����Toast��)
//              => UI�ٽñ����� �����ų�� ���� (����ڸ𸣰� ��� �����ǰ� �����ϴ� ��쵵...)
//Service => AndroidManifest.xml �� App �߰� ��!!
//�ֱ������� �۾���ų��
package kr.adnroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class NewsService extends Service{ // Service : �߻�Ŭ����(�߻�޼��屸����)

	Handler handler = new Handler();
	boolean mQuit; //����������flag�� Ȱ��(true�϶�����������)
	
	@Override
	public IBinder onBind(Intent arg0) { // �߻�޼��屸���ؾ�../���̵��ִ��۾��� ���(���⼭�� �Ⱦ�)
		// TODO Auto-generated method stub
		return null;
	}

	//���� ����(������ ����)
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){//���ڵ�:�⺻��..(���⼭�� �Ⱦ�)
		
		// ������ ����.����
		NewsThread thread = new NewsThread();
		thread.start();
		
		return super.onStartCommand(intent, flags, startId);		
	}
	
	//���� ����
	@Override
	public void onDestroy(){
		super.onDestroy(); //�ʱ�ȭ
		Toast.makeText(this, "Service End", Toast.LENGTH_SHORT).show();
		mQuit = true; // ���������� => ��������ǰԲ�
	}
	
	//������ ����(���������� (����)�����͸� ���۹޾Ƽ� ǥ���ϴ� ����)
	class NewsThread extends Thread{ // ����Ŭ��������
		String[] arNews = { // �������, ������1��, �����尣 �����Ͱ���/���湮������
				"�Ϻ�, ������ �ѱ������� ����",
				"�ѱ� ������ ��� ����",
				"���б��� ������ Ȯ��",
				"�б� ������ ���� �𸣴� �л���",
				"�����, �� ������� ���� ����",
				"������ ���þƱ��� ���� ��ǰ �Ǹ� ����",
				"�������� ���� ���� �ذ�"
		};
		@Override
		public void run(){
			for(int idx=0;mQuit==false;idx++){ //mQuit==false : ������ ������ 
				final String msg = arNews[idx%arNews.length]; // idx%arNews.length : 0~6������ ���� => loop rotation
				//final
				
				// UI�� ������ ����
				handler.post(new Runnable(){
					@Override
					public void run(){
						Toast.makeText(NewsService.this, msg, Toast.LENGTH_LONG).show(); // msg : final
						              // NewsService.this						
					}					
				}); // .post(new Runnable(){
				try{
					Thread.sleep(7000); // 7sec ������ ������ �о����
				}catch(Exception e){
					
				}
			}
		}
	} // NewsThread
	
	
}
