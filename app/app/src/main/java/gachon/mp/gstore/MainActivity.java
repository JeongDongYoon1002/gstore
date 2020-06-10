package gachon.mp.gstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Store> stores = new ArrayList<>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;


        final ListView listview = (ListView)findViewById(R.id.listview);

        GpsTracker gpsTracker = new GpsTracker(MainActivity.this); // GpsTracker 객체 생성
        double latitude = gpsTracker.getLatitude(); // 위도
        double longitude = gpsTracker.getLongitude(); //경도


//        final MapView mapView = new MapView(this);
//        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
//        mapViewContainer.addView(mapView);
//
//        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true); // 현재 위치로 중심점 이동

        new Thread(new Runnable() {
            @Override
            public void run() {

                GetApi parser = new GetApi();
                final String text = String.valueOf(parser.getTotalCount("성남시", 1000, 0, 0, ""));

                stores=parser.getAllXmlData("용인시");




//                for(Store store : stores){

//                    if(store.getLat() != null && store.getLongt() != null){
//                        MapPOIItem marker = new MapPOIItem();
//                        marker.setItemName(store.getName());
//                        marker.setTag(0);
//                        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.valueOf(store.getLat()), Double.valueOf(store.getLongt())));
//                        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//                        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//
//                        mapView.addPOIItem(marker);
//                    }
//                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final StoreAdapter storeAdapter = new StoreAdapter(context, stores, listview);
                        listview.setAdapter(storeAdapter);
                    }
                });
            }
        }).start();


    }
}

