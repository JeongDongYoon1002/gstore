package gachon.mp.gstore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ListViewFragment extends Fragment {
    private static final String STORESKEY = "stores_key";
    private ArrayList<Store> listStores = new ArrayList<>();

    ListView listView;
    ScrollView scrollView;
    String location = "";

    public static ListViewFragment newInstance(List<Store> stores) {
        ListViewFragment fragment = new ListViewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STORESKEY, (ArrayList<? extends Parcelable>) stores);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        listStores = getArguments().getParcelableArrayList(STORESKEY);
        Toast.makeText(getContext(), String.valueOf(listStores.size()), Toast.LENGTH_SHORT).show();
        location = getArguments().getString("location");
        View rootView = (ViewGroup) inflater.inflate(R.layout.fragment_listview, container, false);

        listView = (ListView)rootView.findViewById(R.id.listview);
        scrollView = (ScrollView)rootView.findViewById(R.id.scrollView);

        scrollView.setScrollbarFadingEnabled(true);

        StoreAdapter storeAdapter = new StoreAdapter(getActivity(), listStores, listView);
        listView.setAdapter(storeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), PopUpActivity.class);

                Store obj = (Store) listView.getAdapter().getItem(position);

                Bundle mybundle = new Bundle();
                mybundle.putParcelable("store", obj);
                mybundle.putString("location", location);
                mybundle.putString("location", location);
                intent.putExtras(mybundle);
                startActivity(intent);


            }
        });
        return rootView;
    }
}

