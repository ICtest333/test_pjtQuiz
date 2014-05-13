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
	static final LatLng ADDR_GANGNAM_STATION = new LatLng(37.498231, 127.027637); //위도,경도
	static final LatLng ADDR_HANBIT = new LatLng(37.498908,127.028226);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if(map==null){
			map = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
			if(map!=null){
				//기본마커표시
				addBasicMarker();
				
				//화면에 지정한 위도 경도 표시
				CameraPosition cp = new CameraPosition.Builder().target(ADDR_GANGNAM_STATION).zoom(17).build();
				
				map.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
				
				//맵에 이벤트 연결
				map.setMyLocationEnabled(true);
				map.setOnInfoWindowClickListener(this);
				
				// 현재위치정보 가져오기
				map.setMyLocationEnabled(true); 
				
				// 현재위치로 이동할수있는 아이콘 표시 (우상단 아이콘)
				UiSettings set = map.getUiSettings();
				set.setMyLocationButtonEnabled(true);
				
			}			
		} //outer if		
	} //onCreate 
	
	// 마커 표시
	private void addBasicMarker(){
		map.addMarker(new MarkerOptions()
					.position(ADDR_GANGNAM_STATION)
					.title("강남역")
					.snippet("나 지하철 타요~~")
					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))		
					);
		
		map.addMarker(new MarkerOptions()
					.position(ADDR_HANBIT)
					.title("한빛교육센터")
					.snippet("여기서 공부해요~~")
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))		
					);
	} //addBasicMarker 

	@Override
	public boolean onMarkerClick(Marker m) {
		Toast.makeText(this, m.getTitle()+", 마커 클릭", Toast.LENGTH_SHORT).show();
		//이벤트를 여러개 연결해서 사용시 다른 이벤트를 방해하지 않으려면 false로 지정
		// true로 명시하면 마커 클릭시 이벤트만 처리함(즉,말풍선이 방해받는 현상발생)
		return false; 
	}

	@Override
	public void onInfoWindowClick(Marker m) {
		Toast.makeText(this, m.getTitle()+", 말풍선 클릭", Toast.LENGTH_SHORT).show();
	}
	
}
