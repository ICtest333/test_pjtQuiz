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
		
		// assets ������ ���� ó��
		AssetManager assetManager = getResources().getAssets();
		
		try{
			// assets > ������ sample.json ���� �б� : assetManager ��ü �̿�
			AssetInputStream ais = (AssetInputStream)assetManager.open("sample.json");
			
			//����Ʈ��Ʈ���� ���ڽ�Ʈ������ ��ȯ
			//BufferedReader br = new BufferedReader(new InputStreamReader(ais));
			BufferedReader br = new BufferedReader(new InputStreamReader(ais,"UTF-8"));
			                                           //�ι�°���ڷ� �ѱ����ڵ���� ��ð���
			                                           //JSON�� �⺻������ UTF-8
			StringBuffer sb = new StringBuffer();
			String result = null;
			while((result=br.readLine())!=null){ // ��ȣ����!!
				sb.append(result);
			}
			
			//JSON ������
			String msg = sb.toString();
			
			//JSONObject ��ü����(�߰�ȣ{})
			JSONObject jsonObject = new JSONObject(msg);
			
			String menu = jsonObject.getString("menu");
			
			text.setText(menu+"\n");
			
			//JSONArray (�߰�ȣ{} �� ���ȣ[] : �迭)��ü ����
			JSONArray jArray = new JSONArray(jsonObject.getString("member"));
			
			for(int i=0;i<jArray.length();i++){
				text.append("============================\n");
				text.append(jArray.getJSONObject(i).getString("id")+"\n");
				text.append(jArray.getJSONObject(i).getString("name")+"\n");
				text.append(jArray.getJSONObject(i).getString("address")+"\n");
				text.append(jArray.getJSONObject(i).getString("job")+"\n");
				          //�迭��ü �� ��ü������ getJSONObject �޼����...
			}
			
		}catch(Exception e){
			Log.e("JSONDemo", e.toString());
		}		
	}

}
