package kr.android.tab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TabHost tabs = (TabHost)findViewById(R.id.tabhost);		
		tabs.setup(); /*탭등록*/
		
		//첫번째 tab작업
		TabHost.TabSpec spec = tabs.newTabSpec("tag1");		
		spec.setContent(R.id.tab1);
		//SDK4.0이상에서는 아이콘 미표시됨(저사양폰등 호환성위해 아래 기본 아이콘 지정)
		spec.setIndicator("시계", getResources().getDrawable(R.drawable.ic_launcher));
		tabs.addTab(spec);
		
		//두번째 tab작업
		spec = tabs.newTabSpec("tag2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("버튼", getResources().getDrawable(R.drawable.ic_launcher));
		tabs.addTab(spec);
		
		//초기에 보여질 탭 지정(default : 0)
		tabs.setCurrentTab(0);
		
	}	
}
