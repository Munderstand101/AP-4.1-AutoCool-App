package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();


//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);

        /*new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);*/

    }


}