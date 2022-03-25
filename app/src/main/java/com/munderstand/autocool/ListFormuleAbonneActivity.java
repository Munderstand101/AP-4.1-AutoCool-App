package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ListFormuleAbonneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_formule_abonne);

        String[] arraySpinner = new String[] {
                "1", "2", "3", "4", "5", "6", "7"
        };
        Spinner s = (Spinner) findViewById(R.id.dropdown_menu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);


    }

    public void fSettings_Click(View view) {
    }

    public void fUser_Click(View view) {
    }
}