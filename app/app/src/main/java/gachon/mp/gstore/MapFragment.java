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



public class MapFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View rootView = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);

//        final MapView mapView = new MapView(getContext());
//        ViewGroup mapViewContainer = (ViewGroup) rootView.findViewById(R.id.map_view);
//        mapViewContainer.addView(mapView);
        return rootView;
    }


}




















