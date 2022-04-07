package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

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
                Intent intent = new Intent(MenuActivity.this, ListeCategorieVoituresActivity.class);
//                Intent intent = new Intent(MenuActivity.this, DetailsVoitureActivity.class);
                startActivity(intent);
            }
        });

//        final ImageView iv_Left_Arrow1 = (ImageView) findViewById(R.id.iv_Left_Arrow1);
//        iv_Left_Arrow1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MenuActivity.this, MainActivity2.class);
//                startActivity(intent);
//            }
//        });

        final ImageView iv_Left_Arrow = (ImageView) findViewById(R.id.iv_Left_Arrow);
        iv_Left_Arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, CompteActivity.class);
                startActivity(intent);
            }
        });



    }




    public void fSettings_Click(View view) {

    }

    public void fUser_Click(View view) {
    }
}