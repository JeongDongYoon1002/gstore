package gachon.mp.gstore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


public class PopUpActivity extends Activity {

    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";
    TextView store_name;
    TextView store_type;
    TextView store_addr;
    TextView store_roadAddr;
    TextView store_tel;
    Button map_btn;
    Button call_btn;
    Button ok_btn;
    Button favorite_btn;
    String SIGUN = "";
    String DONG = "";

    Store store;
    ArrayList<Store> stores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);


        store_name = (TextView) findViewById(R.id.store_name);
        store_type = (TextView) findViewById(R.id.store_type);
        store_addr = (TextView) findViewById(R.id.store_addr);
        store_roadAddr = (TextView) findViewById(R.id.store_roadAddr);
        store_tel = (TextView) findViewById(R.id.store_tel);

        map_btn = (Button) findViewById(R.id.map_btn);
        call_btn = (Button) findViewById(R.id.call_btn);
        favorite_btn = (Button) findViewById(R.id.favorite_btn);
        ok_btn = (Button) findViewById(R.id.ok_btn);

        stores = new ArrayList<Store>();

        Intent data = getIntent();
        if (data != null) {

            Bundle bundle = data.getExtras();
            store = bundle.getParcelable("store");
            SIGUN = bundle.getString("SIGUN");
            DONG = bundle.getString("DONG");
            store_name.setText(store.getName());
            store_type.setText(store.getType());
            store_roadAddr.setText(store.getRoadAddr());

            if (DONG.equals("")) {
                store_addr.setText(store.getAddr());
            } else {
                store_addr.setText(store.getAddr().split(DONG)[1]);
            }

            store_tel.setText(store.getTel());
        }

        if (getStoreArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON) != null) {
            stores = getStoreArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON);
            int flag = 0;

            for (Store str : stores) {
                if (str.getName().equals(store.getName())) {
                    if (store.getType() == null || str.getType() == null) {
                        if (str.getRoadAddr() == null || store.getRoadAddr() == null) {
                            flag = 1;
                            break;
                        } else if (str.getRoadAddr() != null && store.getRoadAddr() != null) {
                            if (str.getRoadAddr().equals(store.getRoadAddr())) {
                                flag = 1;
                                break;
                            } else
                                flag = 0;
                        }
                    } else if (str.getType() != null && store.getType() != null) {
                        if (str.getType().equals(store.getType())) {
                            if (str.getRoadAddr() == null || store.getRoadAddr() == null) {
                                flag = 1;
                                break;
                            } else if (str.getRoadAddr() != null && store.getRoadAddr() != null) {
                                if (str.getRoadAddr().equals(store.getRoadAddr())) {
                                    flag = 1;
                                    break;
                                } else
                                    flag = 0;
                            }
                        } else
                            flag = 0;
                    }
                } else {
                    flag = 0;
                }
            }

            if (flag == 1) {
                favorite_btn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_black_24dp, 0, 0);
            } else if (flag == 0) {
                favorite_btn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_border_black_24dp, 0, 0);
            }
        }

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListViewFragment.class);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), storeViewAcitvity.class);
                Bundle mybundle = new Bundle();
                mybundle.putParcelable("store", store);
                intent.putExtras(mybundle);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "지도보기", Toast.LENGTH_LONG).show();
            }
        });

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = store_tel.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(("tel:" + phone)));
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "전화걸기", Toast.LENGTH_LONG).show();
            }
        });

        favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getStoreArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON) != null) {
                    stores = getStoreArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON);
                    int flag = 0;

                    for (Store str : stores) {
                        if (str.getName().equals(store.getName())) {
                            if (store.getType() == null || str.getType() == null) {
                                if (str.getRoadAddr() == null || store.getRoadAddr() == null) {
                                    flag = 1;
                                    break;
                                } else if (str.getRoadAddr() != null && store.getRoadAddr() != null) {
                                    if (str.getRoadAddr().equals(store.getRoadAddr())) {
                                        flag = 1;
                                        break;
                                    } else
                                        flag = 0;
                                }
                            } else if (str.getType() != null && store.getType() != null) {
                                if (str.getType().equals(store.getType())) {
                                    if (str.getRoadAddr() == null || store.getRoadAddr() == null) {
                                        flag = 1;
                                        break;
                                    } else if (str.getRoadAddr() != null && store.getRoadAddr() != null) {
                                        if (str.getRoadAddr().equals(store.getRoadAddr())) {
                                            flag = 1;
                                            break;
                                        } else
                                            flag = 0;
                                    }
                                } else
                                    flag = 0;
                            }
                        } else {
                            flag = 0;
                        }
                    }


                    if (flag == 0) {
                        stores.add(store);
                        try {
                            setStoreArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON, stores);
                            Toast.makeText(getApplicationContext(), "즐겨찾기 추가되었습니다.", Toast.LENGTH_LONG).show();
                            stores = getStoreArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON);
                            favorite_btn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_black_24dp, 0, 0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (flag == 1) {
                        try {
                            deleteStore(getApplicationContext(), SETTINGS_PLAYER_JSON, store, stores);
                            Toast.makeText(getApplicationContext(), "즐겨찾기 해제되었습니다.", Toast.LENGTH_LONG).show();
                            stores = getStoreArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON);
                            favorite_btn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_border_black_24dp, 0, 0);
                            System.out.println(stores);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    stores.add(store);
                    try {
                        setStoreArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON, stores);
                        Toast.makeText(getApplicationContext(), "즐겨찾기 추가되었습니다.", Toast.LENGTH_LONG).show();
                        stores = getStoreArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON);
                        favorite_btn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_black_24dp, 0, 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

    private void setStoreArrayPref(Context context, String key, ArrayList<Store> values) throws JSONException {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(values);

        if (!values.isEmpty()) {
            editor.putString(key, json.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
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

    private void deleteStore(Context context, String key, Store str, ArrayList<Store> strs) throws JSONException {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        int index = 0;
        for (Store s : strs) {
            if (s.getName().equals(str.getName())) {
                if (s.getType() == null || str.getType() == null) {
                    if (s.getRoadAddr() == null || str.getRoadAddr() == null) {
                        break;
                    } else if (s.getRoadAddr() != null && str.getRoadAddr() != null) {
                        if (s.getRoadAddr().equals(str.getRoadAddr())) {
                            break;
                        } else {
                            index += 1;
                        }
                    }
                } else if (s.getType() != null && s.getType() != null) {
                    if (s.getType().equals(str.getType())) {
                        if (s.getRoadAddr() == null || str.getRoadAddr() == null) {
                            break;
                        } else if (s.getRoadAddr() != null && str.getRoadAddr() != null) {
                            if (s.getRoadAddr().equals(str.getRoadAddr())) {
                                break;
                            } else {
                                index += 1;
                            }
                        }
                    } else {
                        index += 1;
                    }
                }
            } else {
                index += 1;
            }
        }

        if (index < strs.size()) {
            strs.remove(index);
        }

        System.out.println("Remove_STR");
        System.out.println(strs);

        editor.clear();
        editor.commit();

        setStoreArrayPref(context, key, strs);
    }
}
