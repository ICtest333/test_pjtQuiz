package kr.android.broad;

import java.security.acl.NotOwnerException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout.PanelSlideListener;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver{ // SMSReceiver : �߻�Ŭ����

	private static final int NOTIFY_ID = 1234;
	
	//��ε�ĳ��Ʈ �޽��� ���Ž� �ڵ� ȣ���	
	@Override
	public void onReceive(Context context, Intent intent) { // �߻�޼��� ���� ��!
		                 // Context : App. (Activity�� �θ�)
										// Intent intent : �޽����� ���� ���� ������
		// TODO Auto-generated method stub	
		Bundle bundle = intent.getExtras();
		SmsMessage[] smsMsg = null;
		String address = "";
		String content = "";
		
		if(bundle!=null){
			//PDU : sms �޼����� ��� ���� ��Ī
			Object[] pdus = (Object[])bundle.get("pdus"); // ����κ��� ������Ʈ �迭�� ����
			
			//������ Object(pdu)��ŭ SmsMessage ��ü ����
			smsMsg = new SmsMessage[pdus.length];
			
			for(int i=0; i<smsMsg.length; i++){
				//Object �迭(pdu)�� ����ִ� �޽����� byte[]�� ĳ�����Ͽ� SmsMessage��ü�� ����
				smsMsg[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				//��ȭ��ȣ ����
				address += smsMsg[i].getOriginatingAddress();
				//�޽��� ����
				content += smsMsg[i].getDisplayMessageBody();
				content += "\n";				
			}
			
			Toast.makeText(context, address+":"+content, Toast.LENGTH_SHORT).show();
			
			//Notification status bar�� ���
			addNotificationStatusBar(context, address, content);			
		} // if		
	} // onReceive
	
	//Notification Status Bar�� ǥ�õ� Notification ���� �޼���
	private void addNotificationStatusBar(Context context, String address, String message){
		
		// 1. NotificationManager ���
		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		// 2. Notofocation.Builder ��ü ����
		//minSDK ver. 11 
		Notification.Builder note = new Notification.Builder(context);
		
		// �˸��޽��� Ŭ���� �̵��� Activity ���� : Intent ��ü �̿�(�Ű�)
		Intent i = new Intent(context, NotificationMessage.class);
		PendingIntent pi = PendingIntent.getActivity(context,0,i,0); // PendingIntent : �˸��޽��������� �̵��� �̿�
		/*
		  ��밡���� flag(�׹�° ����)
		  FLAG_CANCEL_CURRENT : ������ ������ PendingIntent �� ����ϰ� ���Ӱ� �ϳ��� ����
		  FLAG_NO_CREATE : ���� ������ PendingIntent�� ��ȯ
		  FLAG_ONE_SHOT : �� �÷��׸� �̿��� ������ PendingIntent�� �� �ѹ��ۿ� ���� �� ����
		  FLAG_UPDATE_CURRENT : ���� �̹� ������ PendingIntent�� �����Ѵٸ�, �ش� intent�� ������ ������
		  0 : flag ������� ���� (�Ϲ���)
		 */
		
	    // setTicker() : �˸�â �ֻ�ܿ� ������ ���� �������� �޽��� ó��
		note.setTicker(address+ " : "+message);
		// �˸��޽����� Ŭ���ؼ� Activity�� ȣ���ϸ� �ڵ����� �˸��޽��� ����(true)
		note.setAutoCancel(true);
		// �˸��޽��� ������ ����
		note.setSmallIcon(R.drawable.ic_launcher);
		
		// �˸��޽��� ����Ʈ �׸� : �ּ� ǥ��
		note.setContentText(address);
		// �˸��޽��� ����Ʈ �׸� : ���ڳ��� ǥ��
		note.setContentText(message);
		// �̵��� Activity ������ ���� �ִ� PendiingIntent ���
		note.setContentIntent(pi);
		
		// ������ �˸��޽����� ID�� �ο��ؼ� NotificationManager�� ���
		                        //build() : minSDKVersion 16���� ����
		nm.notify(NOTIFY_ID,note.build());	
				
	}
	
}
