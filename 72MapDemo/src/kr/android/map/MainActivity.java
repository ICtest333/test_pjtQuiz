package kr.android.map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnMarkerClickListener,OnInfoWindowClickListener{

	GoogleMap map;
	static final LatLng ADDR_GANGNAM_STATION = new LatLng(37.498231, 127.027637); //����,�浵
	static final LatLng ADDR_HANBIT = new LatLng(37.498908,127.028226);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if(map==null){
			map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
			if(map!=null){
				//�⺻��Ŀǥ��
				addBasicMarker();
				
				//ȭ�鿡 ������ ���� �浵 ǥ��
				CameraPosition cp = new CameraPosition.Builder().target(ADDR_GANGNAM_STATION).zoom(17).build();
				
				map.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
				
				//�ʿ� �̺�Ʈ ����
				map.setMyLocationEnabled(true);
				map.setOnInfoWindowClickListener(this);
				
				// ������ġ���� ��������
				map.setMyLocationEnabled(true); 
				
				// ������ġ�� �̵��Ҽ��ִ� ������ ǥ�� (���� ������)
				UiSettings set = map.getUiSettings();
				set.setMyLocationButtonEnabled(true);
				
			}			
		} //outer if		
	} //onCreate 
	
	// ��Ŀ ǥ��
	private void addBasicMarker(){
		map.addMarker(new MarkerOptions()
					.position(ADDR_GANGNAM_STATION)
					.title("������")
					.snippet("�� ����ö Ÿ��~~")
					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))		
					);
		
		map.addMarker(new MarkerOptions()
					.position(ADDR_HANBIT)
					.title("�Ѻ���������")
					.snippet("���⼭ �����ؿ�~~")
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))		
					);
	} //addBasicMarker 

	@Override
	public boolean onMarkerClick(Marker m) {
		Toast.makeText(this, m.getTitle()+", ��Ŀ Ŭ��", Toast.LENGTH_SHORT).show();
		//�̺�Ʈ�� ������ �����ؼ� ���� �ٸ� �̺�Ʈ�� �������� �������� false�� ����
		// true�� ����ϸ� ��Ŀ Ŭ���� �̺�Ʈ�� ó����(��,��ǳ���� ���ع޴� ����߻�)
		return false; 
	}

	@Override
	public void onInfoWindowClick(Marker m) {
		Toast.makeText(this, m.getTitle()+", ��ǳ�� Ŭ��", Toast.LENGTH_SHORT).show();
	}
	
}
