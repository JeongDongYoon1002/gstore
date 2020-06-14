package gachon.mp.gstore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class storeViewAcitvity extends AppCompatActivity {

    ImageButton back_btn;
    TextView store_name;
    TextView store_addr;
    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeview);


        Toolbar toolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);

        store_name = (TextView) findViewById(R.id.store_name);
        store_addr = (TextView) findViewById(R.id.store_addr);
        back_btn = (ImageButton) findViewById(R.id.back_btn);

        Intent data = getIntent();
        if(data != null) {
            Bundle bundle = data.getExtras();
            store = bundle.getParcelable("store");
            store_name.setText(store.getName() + "이(가) 선택되엇습니다.");
            store_addr.setText(store.getRoadAddr());
        }

        final MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(store.getName());
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.valueOf(store.getLat()), Double.valueOf(store.getLongt())));
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);

        GpsTracker gpsTracker = new GpsTracker(this); // GpsTracker 객체 생성
        double latitude = gpsTracker.getLatitude(); // 위도
        double longitude = gpsTracker.getLongitude(); //경도

        MapPOIItem here = new MapPOIItem();
        here.setItemName("현 위치");
        here.setTag(0);
        here.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        here.setMarkerType(MapPOIItem.MarkerType.YellowPin); // 기본으로 제공하는 BluePin 마커 모양.
        here.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(here);

        mapView.fitMapViewAreaToShowAllPOIItems();
        mapView.zoomOut(true);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListViewFragment.class);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }
}
