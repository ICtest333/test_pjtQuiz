//엡데이트 후 라고 가정...
package kr.android.text;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
// 우클릭 -> 소스 -> 오거나이즈임포트 
//public class MainActivity extends ActionBarActivity {
public class MainActivity extends Activity { // Activity : 하나의 화면(UI)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //글자를 표시할 수 있는 TextView 객체 생성
        TextView tv = new TextView(this); // this => MainActivity 객체 전달하는 ㅇ니자
        //문자열 입력
        tv.setText("텍스트 직접 입력");
        
        //Activity에 View 등록(옛날방식)
        setContentView(tv);
        
    //이하 메뉴구성관련 기본 코드 삭제
    }

}
