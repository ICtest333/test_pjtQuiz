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
	
	//DatePickerDialog 사용시 발생하는 이벤트를 처리하는 객체 생성 (멤버내부클래스형태로구현)
	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		//이벤트핸들러
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			//DatePickerDialog에서 변경한 년,월,일을 Calendar 객체에 설정 (날짜 변경)
			dateAndTime.set(Calendar.YEAR, year);
			dateAndTime.set(Calendar.MONTH, monthOfYear);
			dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			//설정된 정보가 반영되도록 updateLabel 호출
			updateLabel();			
		}
	};
	
	//TimePickerDialog 사용시 발생하는 이벤트를 처리하는 객체 생성
	TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener(){
											//start of 익명내부클래스
		//이벤트핸들러
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) { // 시, 분, (초는 빠짐)
			// TODO Auto-generated method stub
			dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			dateAndTime.set(Calendar.MINUTE, minute);
			//변경된 시간을 반영하기 위해 updateLabel 메서드 호출
			updateLabel();
		}		
	}; //end of 익명내부클래스
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		dateAndTimeLabel = (TextView)findViewById(R.id.textView1);
		btn1 = (Button)findViewById(R.id.button1);
		btn2 = (Button)findViewById(R.id.button2);

		//이벤트 소스와 이벤트 리스너 연결 (빠트리지말것!!)
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		
		//초기 데이터 설정
		updateLabel();
	}
	
	
	private void updateLabel(){
		dateAndTimeLabel.setText(fmtDateAndTime.format(dateAndTime.getTime()));
	}


	@Override
	public void onClick(View v) {
		// 구현필메서드
		if(v.getId()==R.id.button1){
			//날짜설정
			new DatePickerDialog(this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH),dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
			                     // d : 이벤트처리객체
		}else if(v.getId()==R.id.button2){
			//시간설정
			new TimePickerDialog(this, t, dateAndTime.get(Calendar.HOUR_OF_DAY), dateAndTime.get(Calendar.MINUTE), true).show();
			                     // t : 이벤트처리객체
		}
	}

}
