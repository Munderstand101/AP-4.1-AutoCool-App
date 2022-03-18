package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AjouterVoitureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_voiture);
        getSupportActionBar().hide();
    }

    public void fCancel_Click(View view) {
        Intent intent = new Intent(AjouterVoitureActivity.this, ListeVoituresActivity.class);
        startActivity(intent);
    }
}