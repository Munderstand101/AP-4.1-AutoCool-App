package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class InformationAbonneFormuleActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();
    ArrayList<Integer> ListIdAbonne = new ArrayList();
    JSONArray jsonData;
    ArrayList<String> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_abonne_formule);

        try{
            Intent receiveIntent = getIntent();
            int id = receiveIntent.getExtras().getInt("idAbonne");
            getInfoAdherant(id);
        }catch(Exception e){
            e.printStackTrace();
        }

        final Button btnConsulterItem = (Button)findViewById(R.id.btn_back);
        btnConsulterItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getInfoAdherant(int id){
        ArrayList arrayListNomPrenomAdherent = new ArrayList<String>();

        Request request = new Request.Builder()
                .url(ParamAPI.url+"/api/adhere/"+id+"/info")
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
                        Log.d("testjsonFormule", String.valueOf(jsonFormule.length()));

                        EditText dateAdhesion = (EditText) findViewById(R.id.et_dateadhesion);
                        dateAdhesion.setText(jsonFormule.getString("dateAdhesion"));

                        EditText nom = (EditText) findViewById(R.id.et_nom);
                        nom.setText(jsonFormule.getString("nom"));

                        EditText prenom = (EditText) findViewById(R.id.et_prenom);
                        prenom.setText(jsonFormule.getString("prenom"));

                        EditText adresse = (EditText) findViewById(R.id.et_adresse);
                        adresse.setText(jsonFormule.getString("rue"));

                        EditText ville = (EditText) findViewById(R.id.et_ville);
                        ville.setText(jsonFormule.getString("ville"));

                        EditText codepostal = (EditText) findViewById(R.id.et_codepostal);
                        codepostal.setText(jsonFormule.getString("code_postal"));
//
                        EditText datenais = (EditText) findViewById(R.id.et_datenaissance);
                        datenais.setText(jsonFormule.getString("date_naissance"));
//
                        EditText email = (EditText) findViewById(R.id.et_email);
                        email.setText(jsonFormule.getString("email"));
//
                        EditText numfixe = (EditText) findViewById(R.id.et_numfixe);
                        numfixe.setText(jsonFormule.getString("tel"));
//
                        EditText numportable = (EditText) findViewById(R.id.et_numportable);
                        numportable.setText(jsonFormule.getString("tel_mobile"));

                        EditText numpermis = (EditText) findViewById(R.id.et_numpermis);
                        numpermis.setText(jsonFormule.getString("num_permis"));
//
                        EditText lieuobtention = (EditText) findViewById(R.id.et_lieuobtention);
                        lieuobtention.setText(jsonFormule.getString("lieu_permis"));
//
                        EditText dateobtention = (EditText) findViewById(R.id.et_dateobtention);
                        dateobtention.setText(jsonFormule.getString("date_permis"));

                        EditText paiementadhesion = (EditText) findViewById(R.id.et_paiementadhesion);
                        paiementadhesion.setText(jsonFormule.getString("paiement_adhesion"));
//
                        EditText paiementcaution = (EditText) findViewById(R.id.et_paiementcaution);
                        paiementcaution.setText(jsonFormule.getString("paiement_caution"));
//
                        EditText ribfourni = (EditText) findViewById(R.id.et_ribfourni);
                        ribfourni.setText(jsonFormule.getString("rib_fourni"));

                        EditText civilite = (EditText) findViewById(R.id.et_civilite);
                        civilite.setText(jsonFormule.getString("civilite"));
//
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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