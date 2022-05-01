package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.BreakIterator;

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

        Request request = new Request.Builder()
                .url(ParamAPI.url+"/api/main/login")
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            public  void onResponse(Call call, Response response) throws IOException {
                responseStr = response.body().string();
                try {
                    JSONObject user = new JSONObject(responseStr);
                    if (user.has("id")) {
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        intent.putExtra("user", user.toString());
                        startActivity(intent);
                    } else {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Toast.makeText(LoginActivity.this, "Erreur de connexion ! \n "+user.getString("message"), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    Log.d("JSONException", String.valueOf(e));
                }
            }
            public void onFailure(Call call, IOException e)
            {
                Log.d("IOException", String.valueOf(e));
            }

        });
    }

}