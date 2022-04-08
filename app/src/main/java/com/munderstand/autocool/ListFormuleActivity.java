package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
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

public class ListFormuleActivity extends AppCompatActivity {

    String responseStr ;
    OkHttpClient client = new OkHttpClient();
    ArrayList<Integer> ListData = new ArrayList();
    long itemId = 0;
    String nameFormule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_formule);


        try{
            listeFormules();
        }catch(IOException e) {
            e.printStackTrace();
        }

        ListView listview = findViewById(R.id.list_item);
        listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                itemId = id;
                nameFormule = (String) parent.getItemAtPosition(position);
                Toast.makeText(ListFormuleActivity.this, "Element selectionné : " + (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

//                Log.d("test", String.valueOf(item));

            }
        });

        final Button btnConsulterItem = (Button)findViewById(R.id.btn_consulter);
        btnConsulterItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListFormuleActivity.this, ListAbonneFormuleActivity.class);
                intent.putExtra("idFormule", ListData.get((int) itemId));
                startActivity(intent);
            }
        });

        final Button btnCreerUser = (Button)findViewById(R.id.btn_creerUser);
        btnCreerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListFormuleActivity.this, CreateAdherentActivity.class);
                Log.d("test !!!!", nameFormule);
                intent.putExtra("idFormule", ListData.get((int) itemId));
                intent.putExtra("nomFormule", nameFormule);
                startActivity(intent);
            }
        });

    }

//    public void getItem(){
//
//        ListView listview = findViewById(R.id.list_item);
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                LauncherActivity.ListItem item = (LauncherActivity.ListItem) parent.getItemAtPosition(position);
//
//                Log.d("test", String.valueOf(item));

//                //Intent intent = new Intent(this, destinationActivity.class);
//                //based on item add info to intent
//                //startActivity(intent);
//            }
//        });
//        }

    public void listeFormules() throws IOException {

        ArrayList arrayListNomFormules = new ArrayList<String>();

        Request request = new Request.Builder()
                .url(ParamAPI.url+"/api/formule")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayFormules = null;
                try {
                    jsonArrayFormules = new JSONArray(responseStr);
//                    nameFormule = jsonArrayFormules.get(0).

                    for (int i = 0; i < jsonArrayFormules.length(); i++) {

                        JSONObject jsonClasse = null;
                        jsonClasse = jsonArrayFormules.getJSONObject(i);

                        if(i == 0){
                            nameFormule = jsonClasse.getString("libelle");
                        }

                        arrayListNomFormules.add(jsonClasse.getString("libelle"));
                        ListData.add(jsonClasse.getInt("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListView listViewClasses = findViewById(R.id.list_item);

                        ArrayAdapter<String> arrayAdapterClasses = new ArrayAdapter<String>(ListFormuleActivity.this,android.R.layout.simple_list_item_1, arrayListNomFormules);

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