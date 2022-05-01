package com.munderstand.autocool;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.munderstand.autocool.databinding.ActivityCompteBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class CompteActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCompteBinding binding;
    JSONObject user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte);
        Bundle extras = getIntent().getExtras();
        final TextView tvUsername = findViewById(R.id.tv_username);

        try {
            user = new JSONObject(extras.getString("user"));
//            Log.d("Test", String.valueOf(user.getString("id")));
//            Log.d("Test", String.valueOf(user.getString("username")));

            tvUsername.setText(user.getString("username"));

            String roles = new String(user.getString("roles"));

             if (roles.contains("ROLE_USER")||roles.contains("ROLE_ADMIN")){
//                 Log.d("Test","Admin !");
                 tvUsername.setText(user.getString("username")+"(Admin)");
             }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void fBack_Click(View view) {
        super.onBackPressed();
    }

    public void fEdit_Click(View view) {
    }
}