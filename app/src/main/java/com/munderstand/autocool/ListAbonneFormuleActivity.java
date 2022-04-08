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

public class ListAbonneFormuleActivity extends AppCompatActivity {

    String responseStr ;
    OkHttpClient client = new OkHttpClient();
    ArrayList<Integer> ListIdAbonne = new ArrayList();
    long itemId = 0;
    int idForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_abonne_formule);

        try{
            Intent receiveIntent = getIntent();
            idForm = receiveIntent.getExtras().getInt("idFormule");
            getAdherant(idForm);
        }catch(Exception e){
            e.printStackTrace();
        }

        ListView listview = findViewById(R.id.list_item);
        listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemId = id;
                Toast.makeText(ListAbonneFormuleActivity.this, "Personne selectionné : " + (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ListAbonneFormuleActivity.this, InformationAbonneFormuleActivity.class);
                intent.putExtra("idAbonne", ListIdAbonne.get((int) itemId));
                startActivity(intent);

            }
        });
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

                        if(arrayAdapterClasses.isEmpty()){
                            Toast.makeText(ListAbonneFormuleActivity.this, "Pas d'adherent", Toast.LENGTH_SHORT).show();
                        }
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