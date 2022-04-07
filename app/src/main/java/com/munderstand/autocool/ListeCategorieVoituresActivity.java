package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_categorie_voitures);
        getSupportActionBar().hide();




        Spinner spinner = (Spinner)findViewById(R.id.dropdown_menu);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String item = parentView.getItemAtPosition(position).toString();

                Toast.makeText(parentView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        ListView lv_Voitures = (ListView)findViewById(R.id.lv_Voitures);
        lv_Voitures.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String item = parentView.getItemAtPosition(position).toString();

                Toast.makeText(parentView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

//                Intent mIntent=new Intent(dynamic_spinner_main.this,sampleLocalization.class);
//                mIntent.putExtra("lang", m_myDynamicSpinner.getItemIdAtPosition(position));
//                System.out.println("Spinner value...."+m_myDynamicSpinner.getSelectedItem().toString());
//                startActivity(mIntent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        try {
            getAllCategs();
            getAllVoitures();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAllCategs() throws IOException {

        ArrayList arrayNomFormule = new ArrayList<String>();

        Request request = new Request.Builder()
                .url(ParamAPI.url + "/api/carcateg")
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
                            arrayNomFormule.add(jsonNom.getString("nom"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Spinner s = (Spinner) findViewById(R.id.dropdown_menu);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListeCategorieVoituresActivity.this,
                                    android.R.layout.simple_spinner_item, arrayNomFormule);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            s.setAdapter(adapter);
                        }
                    });

                } else {
                    Log.d("Test", "Erreur obtention des libelle formule !");
                }
            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "Erreur!!! connexion impossible \n" + String.valueOf(e));
            }

        });
    }


    public void getAllVoitures() throws IOException {

        OkHttpClient client = new OkHttpClient();
        ArrayList arrayListNomClasses = new ArrayList<String>();

        Request request = new Request.Builder()
                .url(ParamAPI.url+"/api/mission3/cars")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                //Log.d("Test", String.valueOf(responseStr));


                JSONArray jsonArrayClasses = null;
                try {
                    jsonArrayClasses = new JSONArray(responseStr);

                    for (int i = 0; i < jsonArrayClasses.length(); i++) {
                        JSONObject jsonClasse = null;
                        jsonClasse = jsonArrayClasses.getJSONObject(i);
                        arrayListNomClasses.add(jsonClasse.getString("libelle"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

/*
                try {
                    JSONObject jsonObject = new JSONObject(responseStr);
                    JSONArray jsonArray = jsonObject.getJSONArray("features");
                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        if (jsonObject1.has("geomerty")){
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("geomerty");
                            for (int j = 0; j <= jsonArray1.length(); j++){
                                if (jsonObject1.has("coordinates")){
                                    JSONArray jsonArray2 = jsonObject1.getJSONArray("coordinates");
                                    for (int k = 0; k <= jsonArray2.length();k++){
                                        JSONArray jsonArray3 = jsonArray2.getJSONArray(k);
                                        for (int l =0; l <= jsonArray3.length(); l++){
                                            Log.d("ABC", String.valueOf(jsonArray3.getDouble(l)));
                                        }

                                    }
                                }
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                */

                ListView listViewClasses = findViewById(R.id.lv_Voitures);

                ArrayAdapter<String> arrayAdapterClasses = new ArrayAdapter<String>(ListeCategorieVoituresActivity.this, android.R.layout.simple_list_item_1, arrayListNomClasses);

                runOnUiThread(()->{
                    listViewClasses.setAdapter(arrayAdapterClasses);
                });

            }

            public void onFailure(Call call, IOException e) {
                Log.d("Test", "erreur!!! connexion impossible");
                Log.d("Test", String.valueOf(e));
            }

        });

    }


}