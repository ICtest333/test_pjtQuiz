package kr.android.text2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //��ü����

        //������ ����� ID�� ���� TextView ��ü ȣ��
        TextView text = (TextView) findViewById(R.id.textView1);
        text.setBackgroundColor(Color.BLUE); //����
        text.setTextColor(Color.WHITE);//���ڻ�
        
    }
    

}
