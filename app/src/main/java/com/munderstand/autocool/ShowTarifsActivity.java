
package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShowTarifsActivity extends AppCompatActivity {

    String responseStr ;
    OkHttpClient client = new OkHttpClient();
    ArrayList<String> arrayListTarif = new ArrayList();
    long itemId = 0;
    //Les infos de l'activity précédente pour bien choisir le bon tarif
    int idForm;
    int idCateg;
    EditText editTextV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tarif);
        editTextV=(EditText)findViewById(R.id.update);

        try{
            Intent receiveIntent = getIntent();
            idForm = receiveIntent.getExtras().getInt("idFormule");
            idCateg = receiveIntent.getExtras().getInt("idCateg");

            getTarif(idCateg,idForm);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                /***
                 * Je suis obligé de mettre un délai de 10 secondes pour remplir le tableau car
                 * si je le remplit en meme temps que j'appelle la fonction getTarif() le tableau  est vide.
                 * 50 ms devrait suffire mais mon ordinateur est lent
                 *
                 */

                public void run() {

                    fillTabTarif();
                }
            }, 10000);


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /***
     * On remplit les données du tableau avec l'arraylist des valeurs du tarif obtenus gràace a getTarif
     */
    public void fillTabTarif(){

        // données du tableau {"OneHour":"2.40","twentyFourHour":"28.04","sevenDays":"159","kmFifty":"0.35","fiftyKmTwohundred":"0.24","kmTwoHundred":"0.18"}
        final String [] col1 = {"1h:","24h:","7 jours:","km =< 50","50 =< km =< 200","km =< 200"};
        TableLayout table = (TableLayout) findViewById(R.id.idTable); // on prend le tableau défini dans le layout
        TableRow row; // création d'un élément : ligne
        TextView tv1,tv3; // création des cellules

        // pour chaque ligne
        for(int i=0;i<col1.length;i++) {
            row = new TableRow(this); // création d'une nouvelle ligne
            final int id=i;
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String name= editTextV.getText().toString();
                    // le id correspond a l'odre vertical de la valeur dans la colonne, et le name correspond et l'edit text
                    updateTarif(id,name);
                }
            });
            tv1 = new TextView(this); // création cellule
            tv1.setText(col1[i]); // ajout du texte
            tv1.setGravity(Gravity.CENTER); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            tv1.setLayoutParams( new TableRow.LayoutParams( 0, ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );
            // idem 2ème cellule
            tv3 = new TextView(this);
            tv3.setText(arrayListTarif.get(i));
            tv3.setGravity(Gravity.CENTER);
            tv3.setLayoutParams( new TableRow.LayoutParams( 0, ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );



            // ajout des cellules à la ligne
            row.addView(tv1);
            row.addView(tv3);


            // ajout de la ligne au tableau
            table.addView(row);
        }
    }

    public void updateTarif(int id,String name){

        JSONObject jsonObject = new JSONObject();
        try {
            //Quand on clqiue sur un élément du tableau, on a son ID mais un jsonObject fonctionne avec
            //des clés valeurs donc j'ai créé ce tableau pour avoir le nom des clés et pouvoir les associée avec l'id du tablau
            String [] temp = {"OneHour","twentyFourHour","sevenDays","kmFifty","fiftyKmTwohundred","kmTwoHundred","id"};
            //Quand on update dans l'api on update tous les champs donc je remplis le jsonObject avec toutes les valeurs du tarif selectionné
            jsonObject.put("OneHour",arrayListTarif.get(0) );
            jsonObject.put("twentyFourHour", arrayListTarif.get(1));
            jsonObject.put("sevenDays",arrayListTarif.get(2));
            jsonObject.put("kmFifty", arrayListTarif.get(3));
            jsonObject.put("fiftyKmTwohundred", arrayListTarif.get(4));
            jsonObject.put("kmTwoHundred", arrayListTarif.get(3));
            //une fois que le jsonObject est remplus on va écrire par dessus la valeur qui a été selectionné
            jsonObject.put(temp[id], name);

        } catch (JSONException e) {
            Log.d("JSONException", String.valueOf(e));
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());
        Request request = null;
        Log.d("TestUpdate",jsonObject.toString());
        Request Request = new Request.Builder()
        .url(ParamAPI.url+"/api/edit/tarif/"+ arrayListTarif.get(6))
        .post(body)
        .build();

        Log.d("request", String.valueOf(request));

        Call call = client.newCall(Request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {
                responseStr = response.body().string();
                Log.d("responseStr", String.valueOf(responseStr));
                try {
                    JSONObject reponseJson = new JSONObject(responseStr);
                    if (reponseJson.has("id")) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Toast.makeText(ShowTarifsActivity.this, reponseJson.getString("id"), Toast.LENGTH_LONG).show();
                                    finish();
                                    startActivity(getIntent());
                                } catch (JSONException e) {
                                    Log.d("JSONException", String.valueOf(e));
                                }
                            }
                        });

                    }
                    if (reponseJson.has("message")) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Toast.makeText(ShowTarifsActivity.this, reponseJson.getString("message"), Toast.LENGTH_LONG).show();
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



    public void getTarif(int idCateg, long idFormule){


        Request request = new Request.Builder()
                .url(ParamAPI.url+"/api/tarif/"+idCateg+"/"+idFormule)
                .build();

        Log.d("request", String.valueOf(request));
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                JSONArray jsonArrayTarif = null;
                try {
                    jsonArrayTarif = new JSONArray(responseStr);
                    Log.d("responseStr", String.valueOf(responseStr));
                    for (int i = 0; i < jsonArrayTarif.length(); i++) {
                        JSONObject jsonTarif = null;
                        jsonTarif = jsonArrayTarif.getJSONObject(i);

                        Log.d("testjsonFormule", String.valueOf(jsonTarif));

                        arrayListTarif.add(jsonTarif.getString("OneHour"));
                        arrayListTarif.add(jsonTarif.getString("twentyFourHour"));
                        arrayListTarif.add(jsonTarif.getString("sevenDays"));
                        arrayListTarif.add(jsonTarif.getString("kmFifty"));
                        arrayListTarif.add(jsonTarif.getString("fiftyKmTwohundred"));
                        arrayListTarif.add(jsonTarif.getString("kmTwoHundred"));
                        arrayListTarif.add(jsonTarif.getString("id"));
                    };
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            public void onFailure(Call call, IOException e)
            {
                Log.d("Error","Erreur, connexion impossible");
            }

        });

    }



    public void fSettings_Click(View view) {
    }

    public void fUser_Click(View view) {
    }
}