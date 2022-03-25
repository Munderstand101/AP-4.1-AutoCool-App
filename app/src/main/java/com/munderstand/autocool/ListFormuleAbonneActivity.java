package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

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

public class ListFormuleAbonneActivity extends AppCompatActivity {

    String responseStr ;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_formule_abonne);

        try{
            listeClasses();
        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    public void getFormule() throws IOException {

        ArrayList arrayNomFormule = new ArrayList<String>();

        Request request = new Request.Builder()
                .url(ParamAPI.url+"/api/formule")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {

                responseStr = response.body().string();

                if (responseStr.compareTo("false")!=0){
                    try {
                        JSONArray jsonNomFormule = new JSONArray(responseStr);

                        for (int i = 0; i < jsonNomFormule.length(); i++) {
                            JSONObject jsonNom = null;
                            jsonNom = jsonNomFormule.getJSONObject(i);
                            arrayNomFormule.add(jsonNom.getString("libelle"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Spinner s = (Spinner) findViewById(R.id.dropdown_menu);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListFormuleAbonneActivity.this,
                                    android.R.layout.simple_spinner_item, arrayNomFormule);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            s.setAdapter(adapter);
                        }
                    });

                } else {
                    Log.d("Test","Erreur obtention des libelle formule !");
                }
            }

            public void onFailure(Call call, IOException e)
            {
                Log.d("Test", "Erreur!!! connexion impossible \n"+String.valueOf(e));
            }

        });
    }

    public void listeClasses() throws IOException {

        ArrayList arrayListNomClasses = new ArrayList<String>();

        Request request = new Request.Builder()
                .url(ParamAPI.url+"/api/formule")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
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



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListView listViewClasses = findViewById(R.id.list_item);

                        ArrayAdapter<String> arrayAdapterClasses = new ArrayAdapter<String>(ListFormuleAbonneActivity.this,android.R.layout.simple_list_item_1, arrayListNomClasses);

                        listViewClasses.setAdapter(arrayAdapterClasses);
                    }
                });

            }

            public void onFailure(Call call, IOException e)
            {
                Log.d("Test","erreur!!! connexion impossible");
            }

        });
    }


        public void fSettings_Click(View view) {
    }

    public void fUser_Click(View view) {
    }
}