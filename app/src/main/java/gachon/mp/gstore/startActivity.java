package gachon.mp.gstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class startActivity extends AppCompatActivity {

    Button start_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        start_btn = (Button) findViewById(R.id.start_btn);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.transparent);
        start_btn.startAnimation(anim);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangeAreaActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}
