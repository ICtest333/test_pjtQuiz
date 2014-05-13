package kr.android.json;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.AssetManager.AssetInputStream;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		text = (TextView)findViewById(R.id.text);
		
		// assets 폴더의 정보 처리
		AssetManager assetManager = getResources().getAssets();
		
		try{
			// assets > 폴더내 sample.json 파일 읽기 : assetManager 객체 이용
			AssetInputStream ais = (AssetInputStream)assetManager.open("sample.json");
			
			//바이트스트림을 문자스트림으로 변환
			//BufferedReader br = new BufferedReader(new InputStreamReader(ais));
			BufferedReader br = new BufferedReader(new InputStreamReader(ais,"UTF-8"));
			                                           //두번째인자로 한글인코딩방식 명시가능
			                                           //JSON은 기본적으로 UTF-8
			StringBuffer sb = new StringBuffer();
			String result = null;
			while((result=br.readLine())!=null){ // 괄호주의!!
				sb.append(result);
			}
			
			//JSON 데이터
			String msg = sb.toString();
			
			//JSONObject 객체생성(중괄호{})
			JSONObject jsonObject = new JSONObject(msg);
			
			String menu = jsonObject.getString("menu");
			
			text.setText(menu+"\n");
			
			//JSONArray (중괄호{} 내 대괄호[] : 배열)객체 생성
			JSONArray jArray = new JSONArray(jsonObject.getString("member"));
			
			for(int i=0;i<jArray.length();i++){
				text.append("============================\n");
				text.append(jArray.getJSONObject(i).getString("id")+"\n");
				text.append(jArray.getJSONObject(i).getString("name")+"\n");
				text.append(jArray.getJSONObject(i).getString("address")+"\n");
				text.append(jArray.getJSONObject(i).getString("job")+"\n");
				          //배열객체 내 객체생성은 getJSONObject 메서드로...
			}
			
		}catch(Exception e){
			Log.e("JSONDemo", e.toString());
		}		
	}

}
