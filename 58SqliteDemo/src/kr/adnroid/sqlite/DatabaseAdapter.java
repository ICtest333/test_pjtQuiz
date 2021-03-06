// DatabaseAdapter : DAO 역할
package kr.adnroid.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter {
	//-------- 상수 정의 시작----------------//
	//Log 처리를 위한 TAG 지정
	static final String TAG = "DatabaseAdapter";
	//데이터베이스 파일 이름
	static final String DB_NAME = "daily_memo.db";
	//테이블 이름
	static final String TABLE_NAME = "memo";
	//각 열(컬럼)의 이름
	static final String MEMO_ID = "_id";
	static final String MEMO_CONTENT = "content";
	//컬럼 인덱스
	static final int ID_INDEX = 0;
	static final int CONTENT_INDEX = 1;
	//컬럼 명세
	static final String[] PROJECTION = new String[]{MEMO_ID,MEMO_CONTENT};
	//테이블 생성 SQL
	static final String CREATE_TABLE = "CREATE table "+ TABLE_NAME + "(" + MEMO_ID + " integer primary key autoincrement, " + MEMO_CONTENT + " text not null)";
	//테이블 삭제 SQL
	static final String DROP_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME; // EXISTS : -S 빠지면 에러남!
	
	//-------- 상수 정의 끝----------------//
	
	//데이터베이스 연동 객체(일종의 커넥션객체)
	private SQLiteDatabase db;
	
	//데이터베이스를 이용한 어플리케이션의 컨텍스트 타입 보관
	private Context context;
	
	public DatabaseAdapter(Context context){
		this.context = context;		
	}
	
	//SQLiteDatabase 생성 (커넥션같은 역할)
	public void open() throws SQLException{
		try{
			db = (new DatabaseHelper(context)).getWritableDatabase();
		}catch(SQLException e){
			db = (new DatabaseHelper(context)).getReadableDatabase(); // 쓸수없으면읽을수있게라도..
		}
	} // open()
	
	//SQLiteDatabase 자원정리
	public void close(){
		if(db!=null){
			db.close(); // try/catch 의무아님
		}
	}
	
	// 목록(List) 작업 ( _id 내림차순 )
	public Cursor fetchAllMemo(){ // Cursor : like ResultSet
		return db.query(//
				        TABLE_NAME,//table : 테이블명
						PROJECTION,//columns : 컬럼명세
						null,//selection : where절
						null,//selectionArgs : where절에 전달될 데이터
						null,//groupBy 절
						null,//having 절
						MEMO_ID + " DESC" //orderBy 
						      //  " DESC" : D앞에 공백!!! (공백없으면달라붙어_idDESC 컬럼없다고 에러발생)
						    //limit
						);  //cancellationSignal)		
	} // fetchAllMemo()
	
	
	//데이터를 추가하고 추가된 데이터의 primary key를 반환함
	public String addMemo(String content){
		ContentValues values = new ContentValues();
		values.put(MEMO_CONTENT, content);
		//primary key (autoincrement : 자동으로 부여받음)
		long id = db.insert(TABLE_NAME, null, values);
										//null: 빈컬럼은 생기지 않게함
		if(id < 0){
			return "";
		}
		return Long.toString(id);
	}
	
	//업데이트
	public void setMemo(String id, String content){
		//없데이트값설정
		ContentValues values = new ContentValues();
		values.put(MEMO_CONTENT, content);
		
		//레코드 업데이트
		db.update(TABLE_NAME, // 테이블명
				values, // 수정할 데이터
				MEMO_ID + "=?", // where절
				         // 컬럼이 여러개일 경우 물음표? 여러개 사용가능
				new String[]{id} // where절 ?에 전달될 primary key
				);
		
	}
		
	//지정된 ID의 행 삭제
	public void deleteMemo(String id){
		db.delete(TABLE_NAME, // 테이블명
				  MEMO_ID + "=?", //whereClause, 물음표 필요 !!!
				  new String[]{id} ); //whereArgs : where절 물음표(?)에 전달할 데이터(배열형태로넘김)
	}
	
	// 검색 ********
	public String searchMemo(String str){
		//읽을 데이터의 조건
		String where = MEMO_CONTENT + " like ?";
		//where절의 ?를 대체할 매개 변수
		String param = str.substring(0, 1) + "%";
		// 특정문자포함된거검색시 : %문자%
		
		//검색
		Cursor c = db.query(TABLE_NAME,
							PROJECTION,
							where,
							new String[]{param},
							null,
							null,
							MEMO_ID+" DESC", // order by, " DESC" : D앞에 공백필요!!
							"10"); //limit : 많아도 최대 10개만 가져와라.
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
		
		c.close(); // Cursor 자원정리
		
		return buff.toString();
	}
	
	
	
	//SQLiteOpenHelper를 상속받아 DB생성 및 테이블 생성
	//테이블 수정, 삭제 클래스
	public class DatabaseHelper extends SQLiteOpenHelper{
		public DatabaseHelper(Context context){
			super(context, DB_NAME,null,1);
			// context : Activity
			// DB_NAME : 데이터베이스명
			// null : 커서 팩토리(일반적으로 null)
			// 1 : 스카마버전 (1=start)
			//     스키마버전 교체할 경우 : DB 업데이트시 (=> onUpgrade() 호출)
		}

		// 최초에 한번만 실행됨
		@Override
		public void onCreate(SQLiteDatabase db) {
			// 테이블 생성
			db.execSQL(CREATE_TABLE);			
		}

		// 스키마 버전이 교체된 경우에만 실행됨
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// 기본 가이드임, 원하는 코드로 변경 가능
			db.execSQL(DROP_TABLE); //기존 테이블 없앤후(기존 데이터(사용자입력데이터) 삭제됨)
			onCreate(db);			// 새 테이블로 교체 (백업정책에 따라 코드수정 필요)
		}
	} // DatabaseHelper
	
}
