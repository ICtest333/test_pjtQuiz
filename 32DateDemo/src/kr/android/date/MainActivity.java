package kr.android.date;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import java.text.DateFormat; // java api import !

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity implements OnClickListener{

	Calendar dateAndTime = Calendar.getInstance();
	DateFormat fmtDateAndTime = DateFormat.getDateTimeInstance();
	TextView dateAndTimeLabel;
	Button btn1,btn2;
	
	//DatePickerDialog ���� �߻��ϴ� �̺�Ʈ�� ó���ϴ� ��ü ���� (�������Ŭ�������·α���)
	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		//�̺�Ʈ�ڵ鷯
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			//DatePickerDialog���� ������ ��,��,���� Calendar ��ü�� ���� (��¥ ����)
			dateAndTime.set(Calendar.YEAR, year);
			dateAndTime.set(Calendar.MONTH, monthOfYear);
			dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			//������ ������ �ݿ��ǵ��� updateLabel ȣ��
			updateLabel();			
		}
	};
	
	//TimePickerDialog ���� �߻��ϴ� �̺�Ʈ�� ó���ϴ� ��ü ����
	TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener(){
											//start of �͸���Ŭ����
		//�̺�Ʈ�ڵ鷯
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) { // ��, ��, (�ʴ� ����)
			// TODO Auto-generated method stub
			dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			dateAndTime.set(Calendar.MINUTE, minute);
			//����� �ð��� �ݿ��ϱ� ���� updateLabel �޼��� ȣ��
			updateLabel();
		}		
	}; //end of �͸���Ŭ����
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dateAndTimeLabel = (TextView)findViewById(R.id.textView1);
		btn1 = (Button)findViewById(R.id.button1);
		btn2 = (Button)findViewById(R.id.button2);

		//�̺�Ʈ �ҽ��� �̺�Ʈ ������ ���� (��Ʈ��������!!)
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		
		//�ʱ� ������ ����
		updateLabel();
	}
	
	
	private void updateLabel(){
		dateAndTimeLabel.setText(fmtDateAndTime.format(dateAndTime.getTime()));
	}


	@Override
	public void onClick(View v) {
		// �����ʸ޼���
		if(v.getId()==R.id.button1){
			//��¥����
			new DatePickerDialog(this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH),dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
			                     // d : �̺�Ʈó����ü
		}else if(v.getId()==R.id.button2){
			//�ð�����
			new TimePickerDialog(this, t, dateAndTime.get(Calendar.HOUR_OF_DAY), dateAndTime.get(Calendar.MINUTE), true).show();
			                     // t : �̺�Ʈó����ü
		}
	}

}
