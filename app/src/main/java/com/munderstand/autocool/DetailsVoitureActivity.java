package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsVoitureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_voiture);

        final EditText textNumeroVehicule = findViewById(R.id.et_numero_Vehicule);
        final EditText textStation = findViewById(R.id.et_station);
        final EditText textVille = findViewById(R.id.et_ville);
        final EditText textTypeVehicule = findViewById(R.id.et_type_Vehicule);
        final EditText textNombrePlaces = findViewById(R.id.et_nombre_places);
        final EditText textAutomatique = findViewById(R.id.et_automatique);
        final EditText textKilometrage = findViewById(R.id.et_kilometrage);
        final EditText textNiveauEssence = findViewById(R.id.et_niveau_essence);

        textNumeroVehicule.setText("1");
        textStation.setText("graviere");
        textVille.setText("bordeaux");
        textTypeVehicule.setText("fghfg");
        textNombrePlaces.setText("4");
        textAutomatique.setText("oui");
        textKilometrage.setText("656454654");
        textNiveauEssence.setText("50%");
    }

    public void fillCarDetails() throws IOException {


    }


    public void fBack_Click(View view) {
    }

    public void fEdit_Click(View view) {
        final EditText textNumeroVehicule = findViewById(R.id.et_numero_Vehicule);
        final EditText textStation = findViewById(R.id.et_station);
        final EditText textVille = findViewById(R.id.et_ville);
        final EditText textTypeVehicule = findViewById(R.id.et_type_Vehicule);
        final EditText textNombrePlaces = findViewById(R.id.et_nombre_places);
        final EditText textAutomatique = findViewById(R.id.et_automatique);
        final EditText textKilometrage = findViewById(R.id.et_kilometrage);
        final EditText textNiveauEssence = findViewById(R.id.et_niveau_essence);

        textNumeroVehicule.setEnabled(true);
        textNumeroVehicule.setFocusable(true);

        textStation.setEnabled(true);
        textStation.setFocusable(true);

        textVille.setEnabled(true);
        textVille.setFocusable(true);

        textTypeVehicule.setEnabled(true);
        textTypeVehicule.setFocusable(true);

        textNombrePlaces.setEnabled(true);
        textNombrePlaces.setFocusable(true);

        textAutomatique.setEnabled(true);
        textAutomatique.setFocusable(true);

        textKilometrage.setEnabled(true);
        textKilometrage.setFocusable(true);

        textNiveauEssence.setEnabled(true);
        textNiveauEssence.setFocusable(true);


    }
}