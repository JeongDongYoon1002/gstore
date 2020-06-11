package gachon.mp.gstore;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class PopUpActivity extends Activity {

    TextView store_name;
    TextView store_type;
    TextView store_addr;
    TextView store_roadAddr;
    TextView store_tel;
    Button map_btn;
    Button call_btn;
    Button ok_btn;
    Button favorite_btn;
    String location = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);


        store_name = (TextView)findViewById(R.id.store_name);
        store_type = (TextView)findViewById(R.id.store_type);
        store_addr = (TextView)findViewById(R.id.store_addr);
        store_roadAddr = (TextView)findViewById(R.id.store_roadAddr);
        store_tel = (TextView)findViewById(R.id.store_tel);

        map_btn = (Button)findViewById(R.id.map_btn);
        call_btn = (Button)findViewById(R.id.call_btn);
        favorite_btn = (Button) findViewById(R.id.favorite_btn);
        ok_btn = (Button) findViewById(R.id.ok_btn);


        Intent data = getIntent();
        if(data != null) {

            Bundle bundle = data.getExtras();
            Store store = bundle.getParcelable("store");
            location = bundle.getString("location");
            store_name.setText(store.getName());
            store_type.setText(store.getType());
            store_roadAddr.setText(store.getRoadAddr());

            if(location.equals("")){
                store_addr.setText(store.getAddr());
            } else{
                store_addr.setText(store.getAddr().split(location)[1]);
            }

            store_tel.setText(store.getTel());
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

                String name = store_name.getText().toString();
                String addr = store_addr.getText().toString();
                Bundle mybundle = new Bundle();
                mybundle.putString("name", name);
                mybundle.putString("addr", addr);
                intent.putExtras(mybundle);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "지도보기", Toast.LENGTH_LONG).show();
            }
        });

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = store_tel.getText().toString();
                Intent intent= new Intent(Intent.ACTION_DIAL, Uri.parse(("tel:"+phone)));
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "전화걸기", Toast.LENGTH_LONG).show();
            }
        });

        favorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favorite_btn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_black_24dp,0,0);
                //String phone = store_num.getText().toString();
                //Intent intent= new Intent(Intent.ACTION_DIAL, Uri.parse(("tel:"+phone)));
                //startActivity(intent);
                Toast.makeText(getApplicationContext(), "즐겨찾기", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}
