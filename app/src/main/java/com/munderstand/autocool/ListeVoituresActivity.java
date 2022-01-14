package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ListeVoituresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_voitures);
        getSupportActionBar().hide();
    }

    public void fBack_Click(View view) {
        Intent intent = new Intent(ListeVoituresActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void fAjouter_Click(View view) {
    }
}