package kr.android.message;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

	Button alert,toast,progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		alert=(Button)findViewById(R.id.alert);
		toast=(Button)findViewById(R.id.toast);
		progress=(Button)findViewById(R.id.progress);
		
		alert.setOnClickListener(this);
		toast.setOnClickListener(this);
		progress.setOnClickListener(this);		
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId()==R.id.alert){
			//���â ���� �� ȣ��
			new AlertDialog.Builder(this)
			.setTitle("��ȭ����")
			.setMessage("�ȳ��ϼ���!")
			.setCancelable(false)
			.setNeutralButton("�ݱ�", new DialogInterface.OnClickListener(){
				                       // DialogInterface �ڿ� ��ȣ()�� ������ �ȵ�!
				                       // .OnClickListener�ڿ��� ��ȣ()�� �־�� ��!
				                       // DialogInterface ���� OnClickListener �������̽��� �����ڸ޼���� ȣ���ϴ� ����
				//�̺�Ʈ�ڵ鷯
				@Override
				public void onClick(DialogInterface dlg, int sumthin){
					                // AlertDialog().Builder // ������ư
					//�ܼ��� â�� ���� ���� ����� �ڵ尡 ����
					//â�� ���� �� �ΰ����� �۾��� �Ұ�� onClick �޼��� �ȿ� ����(���!)
				}					
			}).show(); // .show() ��Ʈ��������!
			
/*			.setCancelable(false) :  ���â ������� �� Back��ư���� ���ֱ� ���Ҷ�
 * 			.setNeutralButton() : �ݱ��ư��
 *          => Builder ��ü�� ��ȯ�ϴ� �޼���� ����������� ������ �����ؼ� ��밡�� : ü��(chain)���� ���� ����
 * 			.show(); : ���â�� Ȯ���� �������� �ϴ� ���̹Ƿ�
*/			
		}else if(v.getId()==R.id.toast){
			Toast.makeText(this, "�佺Ʈ�޽���", Toast.LENGTH_LONG).show(); // .show() ��Ʈ��������!
		}else if(v.getId()==R.id.progress){
			final ProgressDialog dialog = ProgressDialog.show(this, "����Ʈ ���� ��", "��ø� ��ٷ��ּ���!");//���׶���
			// Thread�� ��׶����۾� ��Ų�� dismiss() ����!
			new Thread(){//�͸���Ŭ���� (���� ����������� Ȱ��)
				@Override
				public void run(){
				
					try{
						sleep(5000);	//5�ʰ� ��׶����۾�					
					}catch(InterruptedException e){
						Log.e("MessageDemo", e.toString());
					}
					//â ����
					dialog.dismiss(); // local ����Ŭ������ �޼���(onClick)���ο� �� ��� �������� ȣ�����!
					                  // final ProgressDialog dialog �Ͱ��� final �ٿ������!!!
				}				
			}.start(); //�͸���Ŭ����=>�����ּһ���=>�ٷ� �޼���ȣ�Ⱑ��			
		}//else if		
	}//onClick
}
