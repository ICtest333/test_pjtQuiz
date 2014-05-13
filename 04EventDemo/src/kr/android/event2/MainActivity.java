package kr.android.event2;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class MainActivity extends Activity {

	Button btn;
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       btn = (Button)findViewById(R.id.button1);
       
       //이벤트 소스와 이벤트 리스너를 연결 (주의 !!!)
       btn.setOnClickListener(new View.OnClickListener(){
    	   
    	   //이벤트핸들러
    	   @Override
   			public void onClick(View v) {
   			   // 이벤트 발생시 날짜 및 시간 설정
    		   updateTime();
   			
   			}
       });  /*익명내부클래스 (이벤트 처리 객체) 
             : 위에서 클래스에서 implements 시키지 않는 방법 */
		
       updateTime();
        
    }


	private void updateTime(){
    	btn.setText(sf.format(new Date()));
    }
    

}
