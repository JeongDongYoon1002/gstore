package gachon.mp.gstore;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static Context context;

    // Fragments
    ListViewFragment listViewFragment;
    MapFragment mapFragment;
    SearchFragment searchFragment;
    FavoriteFragment favoriteFragment;
  
    // Buttons
    Button list_btn;
    Button map_btn;
    Button search_btn;
    Button findAdr_btn;
    Button favorite_btn;
    //ImageButton add_btn;

    TextView loadingMessage;
    ArrayList<Store> stores = new ArrayList<>();
    String SIGUN = "";
    String DONG = "";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        switch (curId) {
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.contact:
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/Text");
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"eurohand@naver.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "<" + getString(R.string.app_name) + " 문의>");
                email.putExtra(Intent.EXTRA_TEXT, "기기명 (Device):\n안드로이드 OS (Android OS):\n내용 (Content):\n");
                email.setType("message/rfc822");
                startActivity(email);
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
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.logo1); //제목앞에 아이콘 넣기'
        setSupportActionBar(toolbar);

       // add_btn = (ImageButton) findViewById(R.id.add_btn);
        map_btn = (Button) findViewById(R.id.map_btn);
        list_btn = (Button) findViewById(R.id.list_btn);
        search_btn = (Button) findViewById(R.id.search_btn);
        findAdr_btn = (Button) findViewById(R.id.findAdr_btn);
        favorite_btn=(Button) findViewById(R.id.favorite_btn);

        loadingMessage = (TextView) findViewById(R.id.loadingMessage);

        listViewFragment = new ListViewFragment();
        mapFragment = new MapFragment();
        searchFragment = new SearchFragment();
        favoriteFragment = new FavoriteFragment();

        Intent data = getIntent();
        if(data != null) {
            Bundle bundle = data.getExtras();
            SIGUN = bundle.getString("SIGUN");
            DONG = bundle.getString(("DONG"));
            String location = SIGUN + " " + DONG;
            findAdr_btn.setText(location);

//            stores = bundle.getParcelableArrayList("all_stores");
            new Thread(new Runnable() {
                @Override
                public void run() {

                    GetApi parser = new GetApi();
                    stores = parser.getAllXmlData(SIGUN, DONG);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingMessage.setText("");
                            list_btn.performClick();
                        }
                    });
                }
            }).start();
        }







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
                loadingMessage.setText("");
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("stores_key", stores);
                bundle.putString("SIGUN", SIGUN);
                bundle.putString("DONG", DONG);
                mapFragment.setArguments(bundle);
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
                loadingMessage.setText("");
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("stores_key", stores);
                bundle.putString("SIGUN", SIGUN);
                bundle.putString("DONG", DONG);
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
                loadingMessage.setText("");
                Bundle bundle = new Bundle();
                bundle.putString("SIGUN", SIGUN);
                bundle.putString("DONG", DONG);
                searchFragment.setArguments(bundle);
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
                loadingMessage.setText("");
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
                Bundle bundle = new Bundle();
                bundle.putString("SIGUN", SIGUN);
                bundle.putString("DONG", DONG);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1122);
                finish();
            }
        });

    }


}