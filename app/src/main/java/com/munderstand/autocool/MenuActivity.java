package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivity extends AppCompatActivity {

    JSONObject user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();
        Bundle extras = getIntent().getExtras();

        try {
            user = new JSONObject(extras.getString("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final Button buttonVoiture = (Button) findViewById(R.id.btn_Voitures);
        buttonVoiture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ListeCategorieVoituresActivity.class);
                intent.putExtra("user", String.valueOf(user));
                startActivity(intent);
            }
        });


    }

    public void fSettings_Click(View view) {
        //TODO : SettignsActivity et UserSettings
    }

    public void fUser_Click(View view) {
        Intent intent = new Intent(MenuActivity.this, CompteActivity.class);
        intent.putExtra("user", String.valueOf(user));
        startActivity(intent);
    }

}