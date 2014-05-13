package kr.adnroid.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		info = (TextView)findViewById(R.id.info);
		
		//��Ʈ��ũ ������ ������ؼ� ConnectivityManager ȣ��
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
		
		for(int i=0;i<networkInfos.length;i++){
			NetworkInfo networkInfo = networkInfos[i];
			info.append("typeName : " + networkInfo.getTypeName()+"\n");
			info.append("available : " + networkInfo.isAvailable()+"\n");
			info.append("connected : " + networkInfo.isConnected()+"\n");
			info.append("=======================\n");
			
		}
		
	}

	

}
