package kr.adnroid.network2;

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
		
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		//WiFI정보호출
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiAvail = ni.isAvailable();
		boolean isWifiConn = ni.isConnected();	
		info.append("WIFI 연결 가능 : " + isWifiAvail + "\n");
		info.append("WIFI 연결 : " + isWifiConn + "\n");
		
		//모바일 정보 호출
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileAvail = ni.isAvailable();
		boolean isMobileConn = ni.isConnected();
		info.append("모바일 연결 가능 : " + isMobileAvail + "\n");
		info.append("모바일 연결 : " + isMobileConn + "\n");
	
		
	}

}
