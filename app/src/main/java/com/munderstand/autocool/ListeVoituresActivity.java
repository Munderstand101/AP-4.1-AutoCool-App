package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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




public class ListeVoituresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_voitures);
        getSupportActionBar().hide();

        try {
            listeVoitures();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void listeVoitures() throws IOException {

        OkHttpClient client = new OkHttpClient();
        ArrayList arrayListNomClasses = new ArrayList<String>();

        Request request = new Request.Builder()
                .url(ParamAPI.url+"/classe")
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
                        arrayListNomClasses.add(jsonClasse.getString("nomClasse"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ListView listViewClasses = findViewById(R.id.lv_Voitures);

                ArrayAdapter<String> arrayAdapterClasses = new ArrayAdapter<String>(ListeVoituresActivity.this, android.R.layout.simple_list_item_1, arrayListNomClasses);

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


    public void fBack_Click(View view) {
        Intent intent = new Intent(ListeVoituresActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void fAjouter_Click(View view) {
        Intent intent = new Intent(ListeVoituresActivity.this, AjouterVoitureActivity.class);
        startActivity(intent);
    }
}