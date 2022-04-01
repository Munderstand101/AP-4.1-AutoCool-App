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

public class ListAbonneFormuleActivity extends AppCompatActivity {

    String responseStr ;
    OkHttpClient client = new OkHttpClient();
    ArrayList<Integer> ListIdAbonne = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_abonne_formule);

        try{
            Intent receiveIntent = getIntent();
            int id = receiveIntent.getExtras().getInt("idFormule");
            Log.d("testAbonneFormule", String.valueOf(id));
            getAdherant(id);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getAdherant(int id){
        ArrayList arrayListNomPrenomAdherent = new ArrayList<String>();

        Request request = new Request.Builder()
                .url(ParamAPI.url+"/api/adhere/"+id)
                .build();

        Log.d("request", String.valueOf(request));
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayFormules = null;
                try {
                    jsonArrayFormules = new JSONArray(responseStr);

                    for (int i = 0; i < jsonArrayFormules.length(); i++) {
                        JSONObject jsonFormule = null;
                        jsonFormule = jsonArrayFormules.getJSONObject(i);

                        Log.d("testjsonFormule", String.valueOf(jsonFormule));

                        arrayListNomPrenomAdherent.add(jsonFormule.getString("nomprenom"));

                        Log.d("id json", String.valueOf(jsonFormule.getInt("id")));
                        ListIdAbonne.add(jsonFormule.getInt("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListView listView = findViewById(R.id.list_item);

                        ArrayAdapter<String> arrayAdapterClasses = new ArrayAdapter<String>(ListAbonneFormuleActivity.this,android.R.layout.simple_list_item_1, arrayListNomPrenomAdherent);

                        listView.setAdapter(arrayAdapterClasses);
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