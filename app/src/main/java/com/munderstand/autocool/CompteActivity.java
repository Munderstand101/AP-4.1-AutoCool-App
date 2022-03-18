package com.munderstand.autocool;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.munderstand.autocool.databinding.ActivityCompteBinding;

public class CompteActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCompteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);
    }

}