package gachon.mp.gstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {

    String[] names = {"LEE", "CHOI", "KIM", "JEONG", "RHO", "LEE", "CHOI", "KIM", "JEONG", "RHO", "LEE", "CHOI", "KIM", "JEONG", "RHO"};

    ListView listView;
    ScrollView scrollView;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        listView = (ListView) rootView.findViewById(R.id.listview);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);
        searchView = (SearchView) rootView.findViewById(R.id.searchView);
        scrollView.setScrollbarFadingEnabled(true);


        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), R.layout.listviewitem, names);
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
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getActivity(), "[검색버튼클릭] 검색어 = " + query, Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getActivity(), "입력하고있는 단어 = " + newText, Toast.LENGTH_LONG).show();
                return true;
            }
        });

        return rootView;

    }
}
