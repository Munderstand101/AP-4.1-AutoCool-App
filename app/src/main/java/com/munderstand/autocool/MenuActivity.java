package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        final Button buttonListeVoiture = (Button) findViewById(R.id.btn_ListeVoiture);
        buttonListeVoiture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ListeVoituresActivity.class);
                startActivity(intent);
            }
        });

    }

    public void fSettings_Click(View view) {
    }

    public void fUser_Click(View view) {
    }
}