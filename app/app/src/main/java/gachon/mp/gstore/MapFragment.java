package gachon.mp.gstore;


import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class MapFragment extends Fragment {

    private static final String STORESKEY = "stores_key";
    private ArrayList<Store> listStores = new ArrayList<>();
    String SIGUN = "";
    String DONG = "";

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if(args == null){
            Toast.makeText(getActivity(), "마커를 불러오는 중입니다.", Toast.LENGTH_SHORT).show();
        } else{
            listStores = args.getParcelableArrayList(STORESKEY);
            SIGUN = args.getString("SIGUN");
            DONG = args.getString("DONG");
        }

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        GpsTracker gpsTracker = new GpsTracker(getContext()); // GpsTracker 객체 생성
        double latitude = gpsTracker.getLatitude(); // 위도
        double longitude = gpsTracker.getLongitude(); //경도


        final MapView mapView = new MapView(getContext());
        ViewGroup mapViewContainer = rootView.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true); // 현재 위치로 중심점 이동


        for(Store store : listStores){
            if(store.getLat() != null && store.getLongt() != null){
                MapPOIItem marker = new MapPOIItem();
                marker.setItemName(store.getName());
                marker.setTag(0);
                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.valueOf(store.getLat()), Double.valueOf(store.getLongt())));
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                mapView.addPOIItem(marker);
            }
        }

        return rootView;
    }


}




















