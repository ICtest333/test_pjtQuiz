package kr.android.event;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener{

	Button btn;
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       btn = (Button)findViewById(R.id.button1);
       
       //�̺�Ʈ �ҽ��� �̺�Ʈ �����ʸ� ����(JAVA awt�� ����)
       btn.setOnClickListener(this);
       
       updateTime(); 
        
    }
    private void updateTime(){
    	btn.setText(sf.format(new Date()));
    }
    //�̺�Ʈ �ڵ鷯
	@Override
	public void onClick(View v) {//(View v) : �̺�Ʈ�� �߻��� �̺�Ʈ�ҽ�(�θ�(View)Ÿ��)
		                         // �̺�Ʈ ��ü ��ü�� �����°� �ƴ�(JAVA awt���� ������)
		//Ŭ���Ҷ����� ���Ӱ� �ð��� �о Button�� text�� ǥ����
		updateTime();
		
	}


}
