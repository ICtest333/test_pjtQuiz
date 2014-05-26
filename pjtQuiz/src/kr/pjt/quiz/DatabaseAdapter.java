// DatabaseAdapter : DAO 역할
package kr.pjt.quiz;

import java.util.ArrayList;

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
	static final String DB_NAME = "icD_quiz.db";
	//테이블 이름
	static final String TABLE_NAME = "quiz";
	//각 열(컬럼)의 이름
	static final String QUIZ_ID = "_id";
	static final String QUIZ_NID = "nid";
	static final String QUIZ_CAT = "category";
	static final String QUIZ_Quiz = "quiz";
	static final String QUIZ_Opt1 = "opt1";
	static final String QUIZ_Opt2 = "opt2";
	static final String QUIZ_Opt3 = "opt3";
	static final String QUIZ_Opt4 = "opt4";
	static final String QUIZ_Ans1 = "ans1";
	static final String QUIZ_Ans2 = "ans2";
	static final String QUIZ_Ans3 = "ans3";
	static final String QUIZ_Ans4 = "ans4";	
	static final String QUIZ_Hint = "hint";
	//컬럼 인덱스
	static final int ID_INDEX = 0;
	static final int NID_INDEX = 1;
	static final int CAT_INDEX = 2;
	static final int QUIZ_INDEX = 3;
	static final int OPT1_INDEX = 4;
	static final int OPT2_INDEX = 5;
	static final int OPT3_INDEX = 6;
	static final int OPT4_INDEX = 7;
	static final int ANS1_INDEX = 8;
	static final int ANS2_INDEX = 9;
	static final int ANS3_INDEX = 10;
	static final int ANS4_INDEX = 11;
	static final int HINT_INDEX = 12;
	
	//컬럼 명세
	static final String[] PROJECTION = new String[]{QUIZ_ID,QUIZ_NID,QUIZ_CAT,QUIZ_Quiz,QUIZ_Opt1,QUIZ_Opt2,QUIZ_Opt3,QUIZ_Opt4,QUIZ_Ans1,QUIZ_Ans2,QUIZ_Ans3,QUIZ_Ans4,QUIZ_Hint};
	//테이블 생성 SQL
	static final String CREATE_TABLE = "CREATE table "+ TABLE_NAME + "(" + QUIZ_ID + " integer primary key autoincrement, " + QUIZ_NID + " integer not null, " + QUIZ_CAT + " text," + QUIZ_Quiz + " text not null," + QUIZ_Opt1 + " text not null," + QUIZ_Opt2 + " text not null," + QUIZ_Opt3 + " text," + QUIZ_Opt4 + " text," + QUIZ_Ans1 + " text not null," + QUIZ_Ans2 + " text," + QUIZ_Ans3 + " text," + QUIZ_Ans4 + " text," + QUIZ_Hint + " text)";
	//static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "(" + QUIZ_ID + " integer primary key autoincrement, " + QUIZ_NID + " integer not null, " + QUIZ_CAT + " text," + QUIZ_Quiz + " text not null," + QUIZ_Opt1 + " text not null," + QUIZ_Opt2 + " text not null," + QUIZ_Opt3 + " text," + QUIZ_Opt4 + " text," + QUIZ_Ans1 + " text not null," + QUIZ_Ans2 + " text," + QUIZ_Ans3 + " text," + QUIZ_Ans4 + " text," + QUIZ_Hint + " text)";
	                                  //"CREATE TABLE IF NOT EXISTS "=>이런문법은없음!!!
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
	public Cursor fetchAllQUIZ(){ // Cursor : like ResultSet
		return db.query(//
				        TABLE_NAME,//table : 테이블명
						PROJECTION,//columns : 컬럼명세
						null,//selection : where절
						null,//selectionArgs : where절에 전달될 데이터
						null,//groupBy 절
						null,//having 절
						QUIZ_ID + " DESC" //orderBy  //  " DESC" : D앞에 공백!!! 
						    //limit
						);  //cancellationSignal)		
	} // fetchAllQUIZ()
	
	
	//데이터를 추가하고 추가된 데이터의 primary key를 반환함
	public String addQuiz(Quiz quiz){
		ContentValues values = new ContentValues();
		values.put(QUIZ_NID, quiz.nid);
		values.put(QUIZ_CAT, quiz.category);
		values.put(QUIZ_Quiz, quiz.question);
		values.put(QUIZ_Opt1, quiz.example01);
		values.put(QUIZ_Opt2, quiz.example02);
		values.put(QUIZ_Opt3, quiz.example03);
		values.put(QUIZ_Opt4, quiz.example04);
		values.put(QUIZ_Ans1, quiz.answer01);
		values.put(QUIZ_Ans2, quiz.answer02);
		values.put(QUIZ_Ans3, quiz.answer03);
		values.put(QUIZ_Ans4, quiz.answer04);
		values.put(QUIZ_Hint, quiz.hint);
		//primary key (autoincrement : 자동으로 부여받음)
		long id = db.insert(TABLE_NAME, null, values);
										//null: 빈컬럼은 생기지 않게함 ==> Ans 2, 3, 4, 는 nullable 로 처리해야하는 경우엔??? 사용하지않는인자로 무방함
		if(id < 0){
			return "";
		}
		return Long.toString(id);
	}

	/*	
	//업데이트
	public void setQuiz(Quiz quiz){
		//없데이트값설정
		ContentValues values = new ContentValues();
		values.put(QUIZ_NID, quiz.nid);
		values.put(QUIZ_CAT, quiz.category);
		values.put(QUIZ_Quiz, quiz.question);
		values.put(QUIZ_Opt1, quiz.example01);
		values.put(QUIZ_Opt2, quiz.example02);
		values.put(QUIZ_Opt3, quiz.example03);
		values.put(QUIZ_Opt4, quiz.example04);
		values.put(QUIZ_Ans1, quiz.answer01);
		values.put(QUIZ_Ans2, quiz.answer02);
		values.put(QUIZ_Ans3, quiz.answer03);
		values.put(QUIZ_Ans4, quiz.answer04);
		values.put(QUIZ_Hint, quiz.hint);
		
		//레코드 업데이트
		db.update(TABLE_NAME, // 테이블명
				values, // 수정할 데이터
				QUIZ_ID + "=?", // where절 // 컬럼이 여러개일 경우 물음표? 여러개 사용가능
				new String[]{id} // where절 ?에 전달될 primary key
				);		
	}
		
	//지정된 ID의 행 삭제
	public void deleteQuiz(String id){
		db.delete(TABLE_NAME, // 테이블명
				  QUIZ_ID + "=?", //whereClause, 물음표 필요 !!!
				  new String[]{id} ); //whereArgs : where절 물음표(?)에 전달할 데이터(배열형태로넘김)
	}
*/	
	
// 검색 ********
	//퀴즈 중복 생성 안되도록 체크 메서드
	public boolean searchQuiz(String str){ // int nid ??
		boolean flag = false;
		//읽을 데이터의 조건
		String where = QUIZ_NID + " = ?"; // cf. = (equal:동등비교) ??

		//검색
		Cursor c = db.query(TABLE_NAME,
							PROJECTION,
							where,
							new String[]{str},
							null,
							null,
							null); //limit : 많아도 최대 10개만 가져와라.
		if(c.moveToFirst()){
			flag = true;
		}
		
		c.close(); // Cursor 자원정리
		
		return flag;
	}
	
	//카테고리선택시 카테고리별 문제 검색해서 뿌려주는 메서드 필요(인텐트객체활용=>AndroidManifest.xml에등록필!)
	public ArrayList<Quiz> getQuizbyCategory(String str){
		ArrayList<Quiz> list = null;
		//읽을 데이터의 조건
		String where = QUIZ_CAT+ " = ?"; // cf. = (equal:동등비교) ??

		//검색
		Cursor c = db.query(TABLE_NAME,
							PROJECTION,
							where,
							new String[]{str},
							null,
							null,  
							null); 
		
		if(c.moveToFirst()){
			list = new ArrayList<Quiz>();
			do{				
				Quiz quiz = new Quiz();
				quiz._id = c.getLong(ID_INDEX);
				quiz.nid = c.getInt(NID_INDEX );
				quiz.category = c.getString(CAT_INDEX);
				quiz.question = c.getString(QUIZ_INDEX);
				quiz.example01 = c.getString(OPT1_INDEX);
				quiz.example02 = c.getString(OPT2_INDEX);
				quiz.example03 = c.getString(OPT3_INDEX);
				quiz.example04 = c.getString(OPT4_INDEX);
				quiz.answer01 = c.getString(ANS1_INDEX);
				quiz.answer02 = c.getString(ANS2_INDEX);
				quiz.answer03 = c.getString(ANS3_INDEX);
				quiz.answer04 = c.getString(ANS4_INDEX);
				quiz.hint = c.getString(HINT_INDEX);
				
				
				
				list.add(quiz);
				
			}while(c.moveToNext());
		}
		
		c.close();
		
		return list;
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
