package com.ex1603;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class Ex16_03Activity extends Activity{
    
	
	TextSwitcher tSwitcher;
	int num = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        final String [] str = {"A","B","C","D","E","F","G","H","I"};
        
        tSwitcher = (TextSwitcher)findViewById(R.id.textSwitcher1);
        tSwitcher.setFactory(factory);
        tSwitcher.setInAnimation(this, android.R.anim.slide_in_left);
        tSwitcher.setOutAnimation(this, android.R.anim.slide_out_right);
        tSwitcher.setText(str[num]);
         
        Button btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setOnClickListener( new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				num++;
				if( num == str.length) num = 0;
				tSwitcher.setText(str[num]);
			}
		});
    }
    // 동적으로 하위뷰 추가
    private ViewFactory factory = new ViewFactory() {
		
    	@Override
		public View makeView() {
			TextView view = new TextView(Ex16_03Activity.this);
			view.setGravity(Gravity.CENTER);
			view.setWidth(200);
			view.setHeight(200);
			view.setTextSize(TypedValue.COMPLEX_RADIX_23p0, 80);
			return view;
		}
	};

}