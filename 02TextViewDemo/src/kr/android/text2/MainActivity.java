package kr.android.text2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //객체생성

        //사전에 등록한 ID를 통해 TextView 객체 호출
        TextView text = (TextView) findViewById(R.id.textView1);
        text.setBackgroundColor(Color.BLUE); //배경색
        text.setTextColor(Color.WHITE);//글자색
        
    }
    

}
