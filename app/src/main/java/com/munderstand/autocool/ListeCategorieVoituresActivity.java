package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListeCategorieVoituresActivity extends AppCompatActivity {


    String responseStr ;
    OkHttpClient client = new OkHttpClient();
    int id_selected = 0;
    private ArrayList<VoitureVilleLieuModel> carsModelArrayList;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_categorie_voitures);
        getSupportActionBar().hide();


        Spinner spinner = (Spinner)findViewById(R.id.dropdown_menu);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                populateList3((String)parentView.getItemAtPosition(position));
            }

            private void populateList3(String categ){
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(ParamAPI.url+"/api/vehicule/AllVehiculeVilleLieuByCate/"+categ).build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {

                    public void onResponse(Call call, Response response) throws IOException {
                        String responseStr = response.body().string();
                        ArrayList<VoitureVilleLieuModel> list = new ArrayList<>();

                        JSONArray jsonArrayClasses = null;
                        try {
                            jsonArrayClasses = new JSONArray(responseStr);

                            for (int i = 0; i < jsonArrayClasses.length(); i++) {
                                JSONObject jsonClasse = null;
                                jsonClasse = jsonArrayClasses.getJSONObject(i);
                                VoitureVilleLieuModel carModel = new VoitureVilleLieuModel();
                                carModel.setLieu(jsonClasse.getString("libelle_lieu"));
                                carModel.setVille(jsonClasse.getString("libelle_ville"));
                                carModel.setId(jsonClasse.getInt("id"));
                                list.add(carModel);
                            }


                            CarsAdapter carAdapter = new CarsAdapter(ListeCategorieVoituresActivity.this, list);

                            runOnUiThread(()->{
                                listView.setAdapter(carAdapter);
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    public void onFailure(Call call, IOException e) {
                        Log.d("Test", "erreur!!! connexion impossible");
                        Log.d("Test", String.valueOf(e));
                    }

                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        listView = findViewById(R.id.lv_Voitures);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListeCategorieVoituresActivity.this, DetailsVoitureActivity.class);
                String id_categ= ((TextView)view.findViewById(R.id.tvId)).getText().toString();
                intent.putExtra("id", Integer.parseInt(id_categ));
                startActivity(intent);
            }
        });

        try {
            getAllCategs();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Spinner s = (Spinner) findViewById(R.id.dropdown_menu);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListeCategorieVoituresActivity.this,
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
                Log.d("Test", "Erreur!!! connexion impossible \n" + String.valueOf(e));
            }

        });
    }

    public void fBack_Click(View view) {
        super.onBackPressed();
    }

    public void fAdd_Click(View view) {
        Intent intent = new Intent(ListeCategorieVoituresActivity.this, AjouterVoitureActivity.class);
        startActivity(intent);
    }
}