package gachon.mp.gstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getSystemService;

public class SearchFragment extends Fragment {

    ArrayList<Store> searchStores = new ArrayList<>();
    ListView listView;
    ScrollView scrollView;
    SearchView searchView;
    String SIGUN = "";
    String DONG = "";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        if(args == null){
            Toast.makeText(getContext(), "정보를 불러오는 중입니다.", Toast.LENGTH_SHORT).show();
        }else {
            SIGUN = args.getString("SIGUN");
            DONG = args.getString("DONG");
        }

        View rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        listView = (ListView) rootView.findViewById(R.id.listview);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);
        searchView = (SearchView) rootView.findViewById(R.id.searchView);
        scrollView.setScrollbarFadingEnabled(true);

        StoreAdapter storeAdapter = new StoreAdapter(getActivity(), searchStores, listView);
        listView.setAdapter(storeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getActivity(), PopUpActivity.class);

                Store obj = (Store) listView.getAdapter().getItem(position);

                Bundle mybundle = new Bundle();
                mybundle.putParcelable("store", obj);
                mybundle.putString("SIGUN", SIGUN);
                mybundle.putString("DONG", DONG);
                intent.putExtras(mybundle);
                startActivity(intent);

            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                searchView.clearFocus();

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        GetApi parser = new GetApi();
                        searchStores  = parser.getXmlData(SIGUN, DONG, 1000, 0, 1, query);


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                StoreAdapter storeAdapter = new StoreAdapter(getActivity(), searchStores, listView);
                                listView.setAdapter(storeAdapter);
                                if(searchStores.size() == 0){
                                    Toast.makeText(getContext(), "결과값이 없습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }).start();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return rootView;

    }
}
