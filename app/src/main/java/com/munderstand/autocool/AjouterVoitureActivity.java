package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AjouterVoitureActivity extends AppCompatActivity {

    String responseStr ;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_voiture);
        getSupportActionBar().hide();

        loadActivity();

        String[] arraySpinner = new String[] {"Oui", "Non"};
        Spinner s = (Spinner) findViewById(R.id.ddm_auto);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(null);
        s.setAdapter(adapter);

        Spinner ddmVille = (Spinner)findViewById(R.id.ddm_ville);
        ddmVille.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Spinner sLieu = (Spinner) findViewById(R.id.ddm_lieu);
                sLieu.setAdapter(null);
                populateLieux((String)parentView.getItemAtPosition(position));
                ddmVille.setSelection(0);
            }

            private void populateLieux(String ville) {
                ArrayList arrayCategs = new ArrayList<String>();

                Request request = new Request.Builder()
                        .url(ParamAPI.url + "/api/lieu/AllByIdVille/"+ville)
                        .build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {

                    public void onResponse(Call call, Response response) throws IOException {

                        responseStr = response.body().string();

                        if (responseStr.compareTo("false") != 0) {
                            try {
                                JSONArray jsonNomFormule = new JSONArray(responseStr);

                                for (int i = 0; i < jsonNomFormule.length(); i++) {
                                    JSONObject jsonNom = null;
                                    jsonNom = jsonNomFormule.getJSONObject(i);
                                    arrayCategs.add(jsonNom.getString("libelle"));
                                }

                            } catch (JSONException e) {
                                Log.d("JSONException", String.valueOf(e));
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Spinner s = (Spinner) findViewById(R.id.ddm_lieu);
                                    s.setAdapter(null);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AjouterVoitureActivity.this,
                                            android.R.layout.simple_spinner_item, arrayCategs);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    s.setAdapter(adapter);
                                }
                            });

                        } else {
                            Log.d("Test", "Erreur !");
                        }
                    }

                    public void onFailure(Call call, IOException e) {
                        Log.d("IOException", String.valueOf(e));
                    }

                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        Spinner ddmCateg = (Spinner)findViewById(R.id.ddm_categ);
        ddmCateg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Spinner sType = (Spinner) findViewById(R.id.ddm_type);
                sType.setAdapter(null);
                populateTypes((String)parentView.getItemAtPosition(position));

                ddmCateg.setSelection(0);


            }

            private void populateTypes(String categ) {
                ArrayList arrayCategs = new ArrayList<String>();

                Request request = new Request.Builder()
                        .url(ParamAPI.url + "/api/type/AllByCateg/"+categ)
                        .build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {

                    public void onResponse(Call call, Response response) throws IOException {

                        responseStr = response.body().string();

                        if (responseStr.compareTo("false") != 0) {
                            try {
                                JSONArray jsonNomFormule = new JSONArray(responseStr);

                                for (int i = 0; i < jsonNomFormule.length(); i++) {
                                    JSONObject jsonNom = null;
                                    jsonNom = jsonNomFormule.getJSONObject(i);
                                    arrayCategs.add(jsonNom.getString("libelle"));
                                }

                            } catch (JSONException e) {
                                Log.d("JSONException", String.valueOf(e));
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Spinner sType = (Spinner) findViewById(R.id.ddm_type);
                                    sType.setAdapter(null);
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(AjouterVoitureActivity.this,
                                            android.R.layout.simple_spinner_item, arrayCategs);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    sType.setAdapter(adapter);
                                }
                            });

                        } else {
                            Log.d("Test", "Erreur !");
                        }
                    }

                    public void onFailure(Call call, IOException e) {
                        Log.d("IOException", String.valueOf(e));
                    }

                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        final Button buttonAjouter = (Button) findViewById(R.id.btn_ajouter);
        buttonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final Spinner ddmCateg = findViewById(R.id.ddm_categ);
                    final Spinner ddmType = findViewById(R.id.ddm_type);
                    final Spinner ddmLieu = findViewById(R.id.ddm_lieu);
                    final Spinner ddmVille = findViewById(R.id.ddm_ville);
                    final Spinner ddmAuto = findViewById(R.id.ddm_auto);
                    final EditText textNumeroVehicule = findViewById(R.id.et_numero_Vehicule);
                    final EditText textNombrePlaces = findViewById(R.id.et_nombre_places);
                    final EditText textKilometrage = findViewById(R.id.et_kilometrage);
                    final EditText textNiveauEssence = findViewById(R.id.et_niveau_essence);


                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("libelle", textNumeroVehicule.getText());
                        jsonObject.put("kilometrage", textKilometrage.getText());
                        jsonObject.put("niveau_essence", textNiveauEssence.getText());
                        jsonObject.put("nb_place", textNombrePlaces.getText());
                        jsonObject.put("estAutomatique", ddmAuto.getSelectedItem());
                        jsonObject.put("typeVehicule", ddmType.getSelectedItem());
                        jsonObject.put("lieu", ddmLieu.getSelectedItem());
                    } catch (JSONException e) {
                        Log.d("JSONException", String.valueOf(e));
                    }
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, jsonObject.toString());

                    Request request = new Request.Builder()
                            .url(ParamAPI.url+"/api/vehicule/new")
                            .post(body)
                            .build();

                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {

                        public  void onResponse(Call call, Response response) throws IOException {
                            responseStr = response.body().string();
                            Log.d("responseStr", String.valueOf(responseStr));
                            try {
                                JSONObject reponseJson = new JSONObject(responseStr);
                                if (reponseJson.has("id")) {
                                    Intent intent = new Intent(AjouterVoitureActivity.this, ListeCategorieVoituresActivity.class);
                                    startActivity(intent);
                                }
                                if (reponseJson.has("message")) {
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Toast.makeText(AjouterVoitureActivity.this, reponseJson.getString("message"), Toast.LENGTH_LONG).show();
                                            } catch (JSONException e) {
                                                Log.d("JSONException", String.valueOf(e));
                                            }
                                        }
                                    });
                                }

                            } catch (JSONException e) {
                                Log.d("JSONException", String.valueOf(e));
                            }

                        }
                        public void onFailure(Call call, IOException e){
                            Log.d("IOException", String.valueOf(e));
                        }
                    });


            }
        });

    }

    public void loadActivity(){

        final TextView tvNumeroVehicule = findViewById(R.id.tv_numero_Vehicule);
        final TextView tvTypeVehicule = findViewById(R.id.tv_type_Vehicule);
        final TextView tvTypeVehicule2 = findViewById(R.id.tv_type_Vehicule2);
        final ImageView ivLeftArrow1 = findViewById(R.id.iv_Left_Arrow1);

        final Spinner ddmCateg = findViewById(R.id.ddm_categ);
        final Spinner ddmType = findViewById(R.id.ddm_type);

        final Spinner ddmLieu = findViewById(R.id.ddm_lieu);
        final Spinner ddmVille = findViewById(R.id.ddm_ville);

        final Spinner ddmAuto = findViewById(R.id.ddm_auto);


        final EditText textNumeroVehicule = findViewById(R.id.et_numero_Vehicule);
        final EditText textStation = findViewById(R.id.et_station);
        final EditText textVille = findViewById(R.id.et_ville);
        final EditText textTypeVehicule = findViewById(R.id.et_type_Vehicule);
        final EditText textNombrePlaces = findViewById(R.id.et_nombre_places);
        final EditText textAutomatique = findViewById(R.id.et_automatique);
        final EditText textKilometrage = findViewById(R.id.et_kilometrage);
        final EditText textNiveauEssence = findViewById(R.id.et_niveau_essence);


        final Button buttonBackSave = (Button) findViewById(R.id.btn_back);

        try {
            getAllCategs();
            getAllVilles();

        } catch (IOException e) {
            Log.d("IOException", String.valueOf(e));
        }

        textNumeroVehicule.setEnabled(true);
        textNumeroVehicule.setFocusable(true);

        textNombrePlaces.setEnabled(true);
        textNombrePlaces.setFocusable(true);

        textAutomatique.setEnabled(true);
        textAutomatique.setFocusable(true);

        textKilometrage.setEnabled(true);
        textKilometrage.setFocusable(true);

        textNiveauEssence.setEnabled(true);
        textNiveauEssence.setFocusable(true);

        tvNumeroVehicule.setText("Nom du véhicule :");
        tvTypeVehicule.setText("Categorie du véhicule :");

        textTypeVehicule.setVisibility(4);
        ddmCateg.setVisibility(0);
        ddmType.setVisibility(0);

        textVille.setVisibility(4);
        ddmVille.setVisibility(0);

        textStation.setVisibility(4);
        ddmLieu.setVisibility(0);

        ddmLieu.setEnabled(true);
        ddmLieu.setFocusable(true);

        tvTypeVehicule2.setVisibility(0);
        textAutomatique.setVisibility(4);

        ddmAuto.setVisibility(0);
        ddmAuto.setEnabled(true);
        ddmAuto.setFocusable(true);
    }

    public void getAllCategs() throws IOException {

        ArrayList arrayCategs = new ArrayList<String>();

        Request request = new Request.Builder()
                .url(ParamAPI.url + "/api/categorie-vehicule/All")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {

                responseStr = response.body().string();

                if (responseStr.compareTo("false") != 0) {
                    try {
                        JSONArray jsonNomFormule = new JSONArray(responseStr);

                        for (int i = 0; i < jsonNomFormule.length(); i++) {
                            JSONObject jsonNom = null;
                            jsonNom = jsonNomFormule.getJSONObject(i);
                            arrayCategs.add(jsonNom.getString("libelle"));
                        }

                    } catch (JSONException e) {
                        Log.d("JSONException", String.valueOf(e));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {



                            Spinner s = (Spinner) findViewById(R.id.ddm_categ);
                            s.setAdapter(null);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AjouterVoitureActivity.this,
                                    android.R.layout.simple_spinner_item, arrayCategs);

                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            s.setAdapter(adapter);
                        }
                    });

                } else {
                    Log.d("Test", "Erreur !");
                }
            }

            public void onFailure(Call call, IOException e) {
                Log.d("IOException", String.valueOf(e));
            }

        });
    }

    public void getAllVilles() throws IOException {

        ArrayList arrayCategs = new ArrayList<String>();

        Request request = new Request.Builder()
                .url(ParamAPI.url + "/api/ville/All")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {

                responseStr = response.body().string();

                if (responseStr.compareTo("false") != 0) {
                    try {
                        JSONArray jsonNomFormule = new JSONArray(responseStr);

                        for (int i = 0; i < jsonNomFormule.length(); i++) {
                            JSONObject jsonNom = null;
                            jsonNom = jsonNomFormule.getJSONObject(i);
                            arrayCategs.add(jsonNom.getString("libelle"));
                        }

                    } catch (JSONException e) {
                        Log.d("JSONException", String.valueOf(e));
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Spinner s = (Spinner) findViewById(R.id.ddm_ville);
                            s.setAdapter(null);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AjouterVoitureActivity.this,
                                    android.R.layout.simple_spinner_item, arrayCategs);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            s.setAdapter(adapter);
                        }
                    });

                } else {
                    Log.d("Test", "Erreur !");
                }
            }

            public void onFailure(Call call, IOException e) {
                Log.d("IOException", String.valueOf(e));
            }

        });
    }


    public void fCancel_Click(View view) {
        Intent intent = new Intent(AjouterVoitureActivity.this, ListeCategorieVoituresActivity.class);
        startActivity(intent);
    }

    public void fPrev_Click(View view) {
        super.onBackPressed();
    }
}