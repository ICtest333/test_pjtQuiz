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
		tabs.setup(); /*�ǵ��*/
		
		//ù��° tab�۾�
		TabHost.TabSpec spec = tabs.newTabSpec("tag1");		
		spec.setContent(R.id.tab1);
		//SDK4.0�̻󿡼��� ������ ��ǥ�õ�(��������� ȣȯ������ �Ʒ� �⺻ ������ ����)
		spec.setIndicator("�ð�", getResources().getDrawable(R.drawable.ic_launcher));
		tabs.addTab(spec);
		
		//�ι�° tab�۾�
		spec = tabs.newTabSpec("tag2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("��ư", getResources().getDrawable(R.drawable.ic_launcher));
		tabs.addTab(spec);
		
		//�ʱ⿡ ������ �� ����(default : 0)
		tabs.setCurrentTab(0);
		
	}	
}
