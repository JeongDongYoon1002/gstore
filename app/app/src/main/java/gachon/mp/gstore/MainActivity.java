package gachon.mp.gstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    EditText edit;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        edit = findViewById(R.id.edit);
        button = findViewById(R.id.button);

        final ListView listview = (ListView)findViewById(R.id.listview);

        GpsTracker gpsTracker = new GpsTracker(MainActivity.this); // GpsTracker 객체 생성
        double latitude = gpsTracker.getLatitude(); // 위도
        double longitude = gpsTracker.getLongitude(); //경도


//        final MapView mapView = new MapView(this);
//        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
//        mapViewContainer.addView(mapView);
//
//        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true); // 현재 위치로 중심점 이동


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String searchQuery = edit.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        GetApi parser = new GetApi();
                        stores=parser.getAllXmlData(searchQuery);




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
        });


    }
}

