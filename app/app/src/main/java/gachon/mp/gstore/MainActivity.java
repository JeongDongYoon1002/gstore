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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    public static Context context;

    ListViewFragment listViewFragment;
    MapFragment mapFragment;
    SearchFragment searchFragment;
    FavoriteFragment favoriteFragment;
  

    Button list_btn;
    Button map_btn;
    Button search_btn;
    Button findAdr_btn;
    Button favorite_btn;
    //ImageButton add_btn;

    String address;
    ArrayList<Store> stores = new ArrayList<>();
    String SIGUN = "";
    String DONG = "";
    int total_count=0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.add_store:

                Intent intent = new Intent(this, AddStoreActivity.class);
                startActivity(intent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;


        Toolbar toolbar = findViewById(R.id.my_toolbar);
        //toolbar.setTitleTextColor(Color.parseColor("#ffff33")); //제목의 칼라
        toolbar.setTitle("경기도 지역화폐");
        //toolbar.setSubtitle("123123"); //부제목 넣기
        //toolbar.setNavigationIcon(R.mipmap.ic_launcher); //제목앞에 아이콘 넣기
        setSupportActionBar(toolbar);

       // add_btn = (ImageButton) findViewById(R.id.add_btn);
        map_btn = (Button) findViewById(R.id.map_btn);
        list_btn = (Button) findViewById(R.id.list_btn);
        search_btn = (Button) findViewById(R.id.search_btn);
        findAdr_btn = (Button) findViewById(R.id.findAdr_btn);
        favorite_btn=(Button) findViewById(R.id.favorite_btn);

        listViewFragment = new ListViewFragment();
        mapFragment = new MapFragment();
        searchFragment = new SearchFragment();
        favoriteFragment = new FavoriteFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mapFragment).commit();
        map_btn.setBackgroundColor(Color.parseColor("#F2F2F2"));

        Intent data = getIntent();
        if(data != null) {

            Bundle bundle = data.getExtras();
            SIGUN = bundle.getString("SIGUN");
            DONG = bundle.getString(("DONG"));
            String location = SIGUN + " " + DONG;
            findAdr_btn.setText(location);

//            stores = bundle.getParcelableArrayList("all_stores");
        }

        new Thread(new Runnable() {
                    @Override
                    public void run() {

                        GetApi parser = new GetApi();
                        stores = parser.getAllXmlData(SIGUN, DONG);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    }
                }).start();

        /*
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AddStoreActivity.class);
                startActivity(intent);
            }
        });
        */

        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("stores_key", stores);
                bundle.putString("location", (String) findAdr_btn.getText());
                listViewFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, mapFragment).commit();
                map_btn.setBackgroundColor(Color.parseColor("#F2F2F2"));
                list_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                search_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                favorite_btn.setBackgroundColor(Color.parseColor("#00ff0000"));

            }
        });

        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("stores_key", stores);
                bundle.putString("location", (String) findAdr_btn.getText());
                listViewFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, listViewFragment).commit();
                map_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                list_btn.setBackgroundColor(Color.parseColor("#F2F2F2"));
                search_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                favorite_btn.setBackgroundColor(Color.parseColor("#00ff0000"));

            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
                map_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                list_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                search_btn.setBackgroundColor(Color.parseColor("#F2F2F2"));
                favorite_btn.setBackgroundColor(Color.parseColor("#00ff0000"));

            }
        });

        favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, favoriteFragment).commit();
                map_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                list_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                search_btn.setBackgroundColor(Color.parseColor("#00ff0000"));
                favorite_btn.setBackgroundColor(Color.parseColor("#F2F2F2"));
            }
        });


        findAdr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddrSearchActivity.class);
                Bundle mybundle = new Bundle();

                mybundle.putString("addr", address);
                intent.putExtras(mybundle);
                startActivityForResult(intent, 1122);
                finish();
            }
        });

    }


}