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

        //RadioGroup �о�� (Radio button�� group���� ���������Ƿ� �׷쿡�� �̺�Ʈ�߻���
        group = (RadioGroup)findViewById(R.id.radioGroup1);
        //
        tv = (TextView)findViewById(R.id.textView1);
        
        //�ʱ� ���� ������ư ����(activity_main.xml ���Ͽ��� �±׳� �Ӽ������� ������ �� �� ����)
        group.check(R.id.radio2); // ����
        
        //�̺�Ʈ �ҽ��� �̺�Ʈ �����ʸ� ����
        group.setOnCheckedChangeListener(this);
        
        //�ʱ� ���� ����
        RadioButton rb = (RadioButton)findViewById(group.getCheckedRadioButtonId());
        tv.setText("�ʱ� �� :" + rb.getText());
        
    }

    //�̺�Ʈ �ڵ鷯
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// ���� : �̺�Ʈ�ҽ�(RadioGroup), üũ�ȹ�ưID(checkedId) <- �ý����� ��������
		RadioButton rb = (RadioButton)findViewById(checkedId);
		tv.setText("����� ���� : " + rb.getText());
	}


    

}
