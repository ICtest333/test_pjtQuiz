package kr.android.radio;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener{

	RadioGroup group;
	TextView tv;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RadioGroup 읽어옴 (Radio button은 group으로 묶여있으므로 그룹에서 이벤트발생함
        group = (RadioGroup)findViewById(R.id.radioGroup1);
        //
        tv = (TextView)findViewById(R.id.textView1);
        
        //초기 선택 라디오버튼 지정(activity_main.xml 파일에서 태그내 속성값으로 지정할 수 도 있음)
        group.check(R.id.radio2); // 바위
        
        //이벤트 소스와 이벤트 리스너를 연결
        group.setOnCheckedChangeListener(this);
        
        //초기 정보 설정
        RadioButton rb = (RadioButton)findViewById(group.getCheckedRadioButtonId());
        tv.setText("초기 값 :" + rb.getText());
        
    }

    //이벤트 핸들러
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// 인자 : 이벤트소스(RadioGroup), 체크된버튼ID(checkedId) <- 시스템이 전달해줌
		RadioButton rb = (RadioButton)findViewById(checkedId);
		tv.setText("당신의 선택 : " + rb.getText());
	}


    

}
