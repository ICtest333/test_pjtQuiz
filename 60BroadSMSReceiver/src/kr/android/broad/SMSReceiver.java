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

public class SMSReceiver extends BroadcastReceiver{ // SMSReceiver : 추상클래스

	private static final int NOTIFY_ID = 1234;
	
	//브로드캐스트 메시지 수신시 자동 호출됨	
	@Override
	public void onReceive(Context context, Intent intent) { // 추상메서드 구현 필!
		                 // Context : App. (Activity의 부모)
										// Intent intent : 메시지에 대한 정보 가져옴
		// TODO Auto-generated method stub	
		Bundle bundle = intent.getExtras();
		SmsMessage[] smsMsg = null;
		String address = "";
		String content = "";
		
		if(bundle!=null){
			//PDU : sms 메세지의 산업 포맷 명칭
			Object[] pdus = (Object[])bundle.get("pdus"); // 번들로부터 오브젝트 배열로 빼옴
			
			//가져온 Object(pdu)만큼 SmsMessage 객체 생성
			smsMsg = new SmsMessage[pdus.length];
			
			for(int i=0; i<smsMsg.length; i++){
				//Object 배열(pdu)에 담겨있는 메시지를 byte[]로 캐스팅하여 SmsMessage객체에 담음
				smsMsg[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				//전화번호 추출
				address += smsMsg[i].getOriginatingAddress();
				//메시지 추출
				content += smsMsg[i].getDisplayMessageBody();
				content += "\n";				
			}
			
			Toast.makeText(context, address+":"+content, Toast.LENGTH_SHORT).show();
			
			//Notification status bar에 등록
			addNotificationStatusBar(context, address, content);			
		} // if		
	} // onReceive
	
	//Notification Status Bar에 표시될 Notification 생성 메서드
	private void addNotificationStatusBar(Context context, String address, String message){
		
		// 1. NotificationManager 얻기
		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		// 2. Notofocation.Builder 객체 생성
		//minSDK ver. 11 
		Notification.Builder note = new Notification.Builder(context);
		
		// 알림메시지 클릭시 이동할 Activity 지정 : Intent 객체 이용(매개)
		Intent i = new Intent(context, NotificationMessage.class);
		PendingIntent pi = PendingIntent.getActivity(context,0,i,0); // PendingIntent : 알림메시지영역상 이동시 이용
		/*
		  사용가능한 flag(네번째 인자)
		  FLAG_CANCEL_CURRENT : 이전에 생성한 PendingIntent 는 취소하고 새롭게 하나를 만듬
		  FLAG_NO_CREATE : 현재 생성된 PendingIntent를 반환
		  FLAG_ONE_SHOT : 이 플래그를 이용해 생성된 PendingIntent는 단 한번밖에 사용될 수 없음
		  FLAG_UPDATE_CURRENT : 만일 이미 생성된 PendingIntent가 존재한다면, 해당 intent의 내용을 변경함
		  0 : flag 사용하지 않음 (일반적)
		 */
		
	    // setTicker() : 알림창 최상단에 아이콘 옆에 보여지는 메시지 처리
		note.setTicker(address+ " : "+message);
		// 알림메시지를 클릭해서 Activity를 호출하면 자동으로 알림메시지 제거(true)
		note.setAutoCancel(true);
		// 알림메시지 아이콘 설정
		note.setSmallIcon(R.drawable.ic_launcher);
		
		// 알림메시지 컨텐트 항목 : 주소 표시
		note.setContentText(address);
		// 알림메시지 컨텐트 항목 : 문자내용 표시
		note.setContentText(message);
		// 이동할 Activity 정보를 갖고 있는 PendiingIntent 등록
		note.setContentIntent(pi);
		
		// 생성된 알림메시지에 ID를 부여해서 NotificationManager에 등록
		                        //build() : minSDKVersion 16으로 지정
		nm.notify(NOTIFY_ID,note.build());	
				
	}
	
}
