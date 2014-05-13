package kr.adnroid.sqlite;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener; // 임포트주의!
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements OnClickListener{

	DatabaseAdapter dbAdapter;
	static final String TAG = "SqliteDemo";
	TextView mCurrentId;
	EditText mEdit;
	SimpleCursorAdapter mAdapter;
	Cursor c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mCurrentId = (TextView)findViewById(R.id.view_id);
		mEdit = (EditText)findViewById(R.id.edit_memo);
		
		// Button 등록
		int buttons[] = {R.id.delete_button, R.id.modify_button, R.id.add_button};
		
		// Button 객체 호출 및 이벤트 연결
		for(int id : buttons){
			Button button = (Button)findViewById(id);
			button.setOnClickListener(this);
		}
		
		// 삭제,수정버튼 비활성화
		setEnabled(false);
				
	} // onCreate()
	
	@Override
	protected void onPause(){
		super.onPause();
		c.close(); // Cursor close
		dbAdapter.close(); // SQLiteDatabase close
	}	
	
	@Override
	protected void onResume(){
		super.onResume();
		//DatabaseAdapter 호출
		dbAdapter = new DatabaseAdapter(this);
		//SQLiteDatabase 객체 생성
		dbAdapter.open();
		  
		//목록 데이터를 갖고 있는 커서 객체 호출
		c = dbAdapter.fetchAllMemo();
		
		//테이블 컬럼과 뷰 연결
		String[] from = DatabaseAdapter.PROJECTION;
		int[] to = new int[]{R.id._id, R.id.memo_text};
		
		// min SDK version 11 이상이어야 함/ this : Activity, layout, c: 커서, from 컬럼, to 뷰
		mAdapter = new SimpleCursorAdapter(this, R.layout.memo_row, c, from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
																				//데이터 매칭위한 내부적 객체를 상수화(의무사항)
		// ListView에 SimpleCursorAdapter 등록
		setListAdapter(mAdapter);
		
	}

	@Override
	public void onClick(View v) {
		// event 
		String content = mEdit.getText().toString();
		
		String id = mCurrentId.getText().toString();
		
		if(v.getId()==R.id.add_button){ //저장
			if(content.length()!=0){
				dbAdapter.addMemo(content);
				toastMemo(content);//검색
			}
		}else if(v.getId()==R.id.modify_button){ // 수정
			if(content.length()!=0){
				dbAdapter.setMemo(id, content);
				toastMemo(content);//검색
			}
		}else if(v.getId()==R.id.delete_button){ // 삭제
			dbAdapter.deleteMemo(id);
		}
				
		mEdit.setText(""); // EditText 초기화		
		mCurrentId.setText("ID"); // TextView 초기화
		//삭제,수정버튼 비활성
		setEnabled(false);
		
		//추가된 데이터를 읽어옴
		c= dbAdapter.fetchAllMemo();
		//SimpleCursorAdapter의 Cursor 교체 => ListView 갱신되는 효과
		mAdapter.changeCursor(c);
		
	} // onClick
	
	// ListView 이벤트 핸들러
	@Override                                     
	protected void onListItemClick(ListView parent, View v, int position, long id){
					//View : LinearLayout의 TextView                                                  
					// id : primary key (SimpleCursorAdapter), position과 다름
		
		// Tap된 행의 ID와 content 정보를 갖고 있는 레이아웃 호출
		LinearLayout layout = (LinearLayout)v;
		TextView t = (TextView)layout.findViewById(R.id.memo_text);
		mEdit.setText(t.getText());
		mCurrentId.setText(Long.toString(id));
				
		//삭제,수정버튼 활성화
		setEnabled(true);
		//검색
		toastMemo(t.getText().toString());
		
	}
	
	//삭제,수정버튼 상태 변경 (Tap시/필요시에만 활성화되도록)
	private void setEnabled(boolean enabled){
		int buttons[] = {R.id.delete_button, R.id.modify_button};
		for(int id : buttons){
			Button button = (Button)findViewById(id);
			button.setEnabled(enabled);
		}
	}
	
	//검색된 내용 표시
	private void toastMemo(String str){
		if(str.length() == 0){
			return;
		}
		String memo = dbAdapter.searchMemo(str);
		Toast.makeText(this, memo, Toast.LENGTH_LONG).show();
	}
	
}
