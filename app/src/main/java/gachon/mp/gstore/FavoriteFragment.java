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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FavoriteFragment extends Fragment {

    String[] names = {"모바일 프로그래밍", "컴퓨터 프로그래밍", "자료구조", "JEONG", "RHO", "LEE", "CHOI", "KIM", "JEONG", "RHO", "LEE", "CHOI", "KIM", "JEONG", "RHO"};
    ListView listView;
    ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = (ViewGroup) inflater.inflate(R.layout.fragment_listview, container, false);

        listView = (ListView)rootView.findViewById(R.id.listview);
        scrollView = (ScrollView)rootView.findViewById(R.id.scrollView);

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
        return rootView;
    }
}


