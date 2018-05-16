package rrdl.be4care.Views.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rrdl.be4care.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
