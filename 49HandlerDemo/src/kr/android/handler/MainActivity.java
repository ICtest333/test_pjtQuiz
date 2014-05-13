package kr.android.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	ProgressBar bar;
	
	Handler handler = new Handler();
	boolean isRunning = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bar = (ProgressBar)findViewById(R.id.progress);
		
	}
	
	//������� ������ ó�� ����
	@Override
	public void onStart(){
		super.onStart();
		
		//���α׷�����(ProgressBar) �ʱ�ȭ
		bar.setProgress(0);
		
		//ȭ�� �������̽��� ������ ��׶��� �۾� ���� ������ ����
/*		Runnable r = new Runnable() {//�͸���Ŭ����
			@Override
			public void run() { ...								
			}
		};//�͸���Ŭ����
*/		//������ ����
		//Thread backgroud = new Thread(r);
		// r ����������� ���� !!!
		Thread background = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					for(int i=0; i<20 && isRunning; i++){
						Thread.sleep(1000);// 1sec ����
						//������ �����͸� �о���� �۾����� ����
						
						// �����忡�� UI �����ϰ����Ұ��,,, 
						// : Handler ��ü�� �̿�(�߰迪��)�ؼ� ������
						handler.post(new Runnable() {//�͸���Ŭ���� : post()�޼��� => Runnable ��ü ����							
							@Override
							public void run() {
								// UI�� �����ؼ� ȭ�� ���� (UI �����۾���, ���...)
								bar.incrementProgressBy(5);	//���α׷����� 5%������, 20������ 100%�ǵ���.							
							}
						});//�͸���Ŭ����						
					}
				}catch(Exception e){
					Log.e("HandlerDemo", e.toString());
				}
			}
		});//new Runnable()
		
		isRunning = true; // ��׶������ȯ�� isRunning �� false��ȯ, �����屸���� �ݺ��� ��������!
		background.start();
		
	} //onStart
	
	//������ ���� ����
	@Override
	public void onStop(){
		super.onStop();
		isRunning = false;// ��׶������ȯ�� isRunning �� false��ȯ, �����屸���� �ݺ��� ��������!
		                  // ������ ������ ���ͷ�Ƽ�� �޽���� ��������� �����߻�����(�ǹ������� ������)
		                  // �����屸�� �ݺ����� �����Ű�� ��������...
	}	
}
