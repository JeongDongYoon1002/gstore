package gachon.mp.gstore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class storeViewAcitvity extends AppCompatActivity {

    ImageButton back_btn;
    TextView store_name;
    TextView store_addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeview);


        Toolbar toolbar = findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);

        store_name = (TextView) findViewById(R.id.store_name);
        store_addr = (TextView) findViewById(R.id.store_addr);
        back_btn = (ImageButton) findViewById(R.id.back_btn);

        Intent data = getIntent();
        if(data != null) {

            Bundle bundle = data.getExtras();
            String store = bundle.getString("name");
            String addr = bundle.getString("addr");
            store_name.setText(store + "이(가) 선택되엇습니다.");
            store_addr.setText(addr);
        }

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListViewFragment.class);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }
}
