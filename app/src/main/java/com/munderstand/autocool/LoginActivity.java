package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    String responseStr ;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.setContentView(R.layout.activity_login);

        //Remove title bar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        final EditText textLogin = findViewById(R.id.et_username);
        final EditText textMdp = findViewById(R.id.et_password);
        textLogin.setText("Munderstand");
        textMdp.setText("H4kun4m4t4t4");

        final Button buttonValiderAuthentification = (Button)findViewById(R.id.btn_login);
        buttonValiderAuthentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Appel de la fonction authentification
                try {
                    authentification();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        

    }
    public void fRegister_Click(View view){
        Log.d("Test","Register !");
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void authentification() throws IOException {

        final EditText textLogin = findViewById(R.id.et_username);
        final EditText textMdp = findViewById(R.id.et_password);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", textLogin.getText().toString());
            jsonObject.put("password", textMdp.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, jsonObject.toString());

        Log.d("Test",jsonObject.toString());

        Request request = new Request.Builder()
                .url(ParamAPI.url+"/api/login")
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {

                responseStr = response.body().string();

                if (responseStr.compareTo("false")!=0){
                    try {
                        JSONObject etudiant = new JSONObject(responseStr);
                        Log.d("Test",etudiant.getString("username") + " est  connecté");

                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        intent.putExtra("username", etudiant.toString());
                        startActivity(intent);

                    }
                    catch(JSONException e){
                        Log.d("Test", String.valueOf(e));
                        //  Toast.makeText(MainActivity.this, "Erreur de connexion !!!! !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Test","Login ou mot de  passe non valide !");
                }
            }

            public void onFailure(Call call, IOException e)
            {
                Log.d("Test", "Erreur!!! connexion impossible \n"+String.valueOf(e));
            }

        });
    }


}