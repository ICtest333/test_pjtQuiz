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
       
       //이벤트 소스와 이벤트 리스너를 연결(JAVA awt와 유사)
       btn.setOnClickListener(this);
       
       updateTime(); 
        
    }
    private void updateTime(){
    	btn.setText(sf.format(new Date()));
    }
    //이벤트 핸들러
	@Override
	public void onClick(View v) {//(View v) : 이벤트가 발생한 이벤트소스(부모(View)타입)
		                         // 이벤트 객체 자체가 들어오는게 아님(JAVA awt와의 차이점)
		//클릭할때마다 새롭게 시간을 읽어서 Button에 text로 표시함
		updateTime();
		
	}


}
