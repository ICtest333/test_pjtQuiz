package kr.adnroid.sqlite;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener; // ����Ʈ����!
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
		
		// Button ���
		int buttons[] = {R.id.delete_button, R.id.modify_button, R.id.add_button};
		
		// Button ��ü ȣ�� �� �̺�Ʈ ����
		for(int id : buttons){
			Button button = (Button)findViewById(id);
			button.setOnClickListener(this);
		}
		
		// ����,������ư ��Ȱ��ȭ
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
		//DatabaseAdapter ȣ��
		dbAdapter = new DatabaseAdapter(this);
		//SQLiteDatabase ��ü ����
		dbAdapter.open();
		  
		//��� �����͸� ���� �ִ� Ŀ�� ��ü ȣ��
		c = dbAdapter.fetchAllMemo();
		
		//���̺� �÷��� �� ����
		String[] from = DatabaseAdapter.PROJECTION;
		int[] to = new int[]{R.id._id, R.id.memo_text};
		
		// min SDK version 11 �̻��̾�� ��/ this : Activity, layout, c: Ŀ��, from �÷�, to ��
		mAdapter = new SimpleCursorAdapter(this, R.layout.memo_row, c, from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
																				//������ ��Ī���� ������ ��ü�� ���ȭ(�ǹ�����)
		// ListView�� SimpleCursorAdapter ���
		setListAdapter(mAdapter);
		
	}

	@Override
	public void onClick(View v) {
		// event 
		String content = mEdit.getText().toString();
		
		String id = mCurrentId.getText().toString();
		
		if(v.getId()==R.id.add_button){ //����
			if(content.length()!=0){
				dbAdapter.addMemo(content);
				toastMemo(content);//�˻�
			}
		}else if(v.getId()==R.id.modify_button){ // ����
			if(content.length()!=0){
				dbAdapter.setMemo(id, content);
				toastMemo(content);//�˻�
			}
		}else if(v.getId()==R.id.delete_button){ // ����
			dbAdapter.deleteMemo(id);
		}
				
		mEdit.setText(""); // EditText �ʱ�ȭ		
		mCurrentId.setText("ID"); // TextView �ʱ�ȭ
		//����,������ư ��Ȱ��
		setEnabled(false);
		
		//�߰��� �����͸� �о��
		c= dbAdapter.fetchAllMemo();
		//SimpleCursorAdapter�� Cursor ��ü => ListView ���ŵǴ� ȿ��
		mAdapter.changeCursor(c);
		
	} // onClick
	
	// ListView �̺�Ʈ �ڵ鷯
	@Override                                     
	protected void onListItemClick(ListView parent, View v, int position, long id){
					//View : LinearLayout�� TextView                                                  
					// id : primary key (SimpleCursorAdapter), position�� �ٸ�
		
		// Tap�� ���� ID�� content ������ ���� �ִ� ���̾ƿ� ȣ��
		LinearLayout layout = (LinearLayout)v;
		TextView t = (TextView)layout.findViewById(R.id.memo_text);
		mEdit.setText(t.getText());
		mCurrentId.setText(Long.toString(id));
				
		//����,������ư Ȱ��ȭ
		setEnabled(true);
		//�˻�
		toastMemo(t.getText().toString());
		
	}
	
	//����,������ư ���� ���� (Tap��/�ʿ�ÿ��� Ȱ��ȭ�ǵ���)
	private void setEnabled(boolean enabled){
		int buttons[] = {R.id.delete_button, R.id.modify_button};
		for(int id : buttons){
			Button button = (Button)findViewById(id);
			button.setEnabled(enabled);
		}
	}
	
	//�˻��� ���� ǥ��
	private void toastMemo(String str){
		if(str.length() == 0){
			return;
		}
		String memo = dbAdapter.searchMemo(str);
		Toast.makeText(this, memo, Toast.LENGTH_LONG).show();
	}
	
}
