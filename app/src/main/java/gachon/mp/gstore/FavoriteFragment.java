package gachon.mp.gstore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";
    //String[] names = {"모바일 프로그래밍", "컴퓨터 프로그래밍", "자료구조", "JEONG", "RHO", "LEE", "CHOI", "KIM", "JEONG", "RHO", "LEE", "CHOI", "KIM", "JEONG", "RHO"};
    ListView listView;
    ScrollView scrollView;
    ArrayList<Store> stores;
    String SIGUN = "";
    String DONG = "";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = (ViewGroup) inflater.inflate(R.layout.fragment_listview, container, false);

        listView = (ListView)rootView.findViewById(R.id.listview);
        scrollView = (ScrollView)rootView.findViewById(R.id.scrollView);

        scrollView.setScrollbarFadingEnabled(true);

        stores=new ArrayList<Store>();
        if(getStoreArrayPref(getActivity(), SETTINGS_PLAYER_JSON) != null){
            stores=getStoreArrayPref(getActivity(),SETTINGS_PLAYER_JSON);
        }

        ArrayAdapter<Store> adapter = new StoreAdapter(getActivity(), stores, listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), PopUpActivity.class);

                Store obj = (Store) listView.getAdapter().getItem(position);

                SIGUN=obj.getSigun();

                Bundle mybundle = new Bundle();
                mybundle.putParcelable("store", obj);
                mybundle.putString("SIGUN", SIGUN);
                mybundle.putString("DONG", DONG);
                intent.putExtras(mybundle);
                startActivity(intent);


            }
        });

        /*ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), R.layout.listviewitem, names);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), PopUpActivity.class);

                Object obj = listView.getAdapter().getItem(position);
                String value = obj.toString();

                Bundle mybundle = new Bundle();
                mybundle.putString("name", value);
                intent.putExtras(mybundle);
                startActivity(intent);


            }
        });*/
        return rootView;
    }

    private ArrayList<Store> getStoreArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        Gson gson = new Gson();
        TypeToken<List<Store>> typeToken = new TypeToken<List<Store>>() {
        };
        ArrayList<Store> data = gson.fromJson(json, typeToken.getType());

        return data;
    }
}


