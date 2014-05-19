// DatabaseAdapter : DAO ����
package kr.pjt.quiz;
/*
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter {
	//-------- ��� ���� ����----------------//
	//Log ó���� ���� TAG ����
	static final String TAG = "DatabaseAdapter";
	//�����ͺ��̽� ���� �̸�
	static final String DB_NAME = "daily_memo.db";
	//���̺� �̸�
	static final String TABLE_NAME = "memo";
	//�� ��(�÷�)�� �̸�
	static final String MEMO_ID = "_id";
	static final String MEMO_CONTENT = "content";
	//�÷� �ε���
	static final int ID_INDEX = 0;
	static final int CONTENT_INDEX = 1;
	//�÷� ����
	static final String[] PROJECTION = new String[]{MEMO_ID,MEMO_CONTENT};
	//���̺� ���� SQL
	static final String CREATE_TABLE = "CREATE table "+ TABLE_NAME + "(" + MEMO_ID + " integer primary key autoincrement, " + MEMO_CONTENT + " text not null)";
	//���̺� ���� SQL
	static final String DROP_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME; // EXISTS : -S ������ ������!
	
	//-------- ��� ���� ��----------------//
	
	//�����ͺ��̽� ���� ��ü(������ Ŀ�ؼǰ�ü)
	private SQLiteDatabase db;
	
	//�����ͺ��̽��� �̿��� ���ø����̼��� ���ؽ�Ʈ Ÿ�� ����
	private Context context;
	
	public DatabaseAdapter(Context context){
		this.context = context;		
	}
	
	//SQLiteDatabase ���� (Ŀ�ؼǰ��� ����)
	public void open() throws SQLException{
		try{
			db = (new DatabaseHelper(context)).getWritableDatabase();
		}catch(SQLException e){
			db = (new DatabaseHelper(context)).getReadableDatabase(); // �����������������ְԶ�..
		}
	} // open()
	
	//SQLiteDatabase �ڿ�����
	public void close(){
		if(db!=null){
			db.close(); // try/catch �ǹ��ƴ�
		}
	}
	
	// ���(List) �۾� ( _id �������� )
	public Cursor fetchAllMemo(){ // Cursor : like ResultSet
		return db.query(//
				        TABLE_NAME,//table : ���̺���
						PROJECTION,//columns : �÷�����
						null,//selection : where��
						null,//selectionArgs : where���� ���޵� ������
						null,//groupBy ��
						null,//having ��
						MEMO_ID + " DESC" //orderBy 
						      //  " DESC" : D�տ� ����!!! (���������޶�پ�_idDESC �÷����ٰ� �����߻�)
						    //limit
						);  //cancellationSignal)		
	} // fetchAllMemo()
	
	
	//�����͸� �߰��ϰ� �߰��� �������� primary key�� ��ȯ��
	public String addMemo(String content){
		ContentValues values = new ContentValues();
		values.put(MEMO_CONTENT, content);
		//primary key (autoincrement : �ڵ����� �ο�����)
		long id = db.insert(TABLE_NAME, null, values);
										//null: ���÷��� ������ �ʰ���
		if(id < 0){
			return "";
		}
		return Long.toString(id);
	}
	
	//������Ʈ
	public void setMemo(String id, String content){
		//������Ʈ������
		ContentValues values = new ContentValues();
		values.put(MEMO_CONTENT, content);
		
		//���ڵ� ������Ʈ
		db.update(TABLE_NAME, // ���̺���
				values, // ������ ������
				MEMO_ID + "=?", // where��
				         // �÷��� �������� ��� ����ǥ? ������ ��밡��
				new String[]{id} // where�� ?�� ���޵� primary key
				);
		
	}
		
	//������ ID�� �� ����
	public void deleteMemo(String id){
		db.delete(TABLE_NAME, // ���̺���
				  MEMO_ID + "=?", //whereClause, ����ǥ �ʿ� !!!
				  new String[]{id} ); //whereArgs : where�� ����ǥ(?)�� ������ ������(�迭���·γѱ�)
	}
	
	// �˻� ********
	public String searchMemo(String str){
		//���� �������� ����
		String where = MEMO_CONTENT + " like ?";
		//where���� ?�� ��ü�� �Ű� ����
		String param = str.substring(0, 1) + "%";
		// Ư���������ԵȰŰ˻��� : %����%
		
		//�˻�
		Cursor c = db.query(TABLE_NAME,
							PROJECTION,
							where,
							new String[]{param},
							null,
							null,
							MEMO_ID+" DESC", // order by, " DESC" : D�տ� �����ʿ�!!
							"10"); //limit : ���Ƶ� �ִ� 10���� �����Ͷ�.
		StringBuffer buff = new StringBuffer();
		if(c.moveToFirst()){
			do{
				long id = c.getLong(ID_INDEX);
				String memo = c.getString(CONTENT_INDEX);
				buff.append("id(");
				buff.append(id);
				buff.append(")");
				buff.append(memo);
				buff.append("\n");
				
			}while(c.moveToNext());
		}
		
		c.close(); // Cursor �ڿ�����
		
		return buff.toString();
	}
	
	
	
	//SQLiteOpenHelper�� ��ӹ޾� DB���� �� ���̺� ����
	//���̺� ����, ���� Ŭ����
	public class DatabaseHelper extends SQLiteOpenHelper{
		public DatabaseHelper(Context context){
			super(context, DB_NAME,null,1);
			// context : Activity
			// DB_NAME : �����ͺ��̽���
			// null : Ŀ�� ���丮(�Ϲ������� null)
			// 1 : ��ī������ (1=start)
			//     ��Ű������ ��ü�� ��� : DB ������Ʈ�� (=> onUpgrade() ȣ��)
		}

		// ���ʿ� �ѹ��� �����
		@Override
		public void onCreate(SQLiteDatabase db) {
			// ���̺� ����
			db.execSQL(CREATE_TABLE);			
		}

		// ��Ű�� ������ ��ü�� ��쿡�� �����
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// �⺻ ���̵���, ���ϴ� �ڵ�� ���� ����
			db.execSQL(DROP_TABLE); //���� ���̺� ������(���� ������(������Էµ�����) ������)
			onCreate(db);			// �� ���̺��� ��ü (�����å�� ���� �ڵ���� �ʿ�)
		}
	} // DatabaseHelper
	
}
*/