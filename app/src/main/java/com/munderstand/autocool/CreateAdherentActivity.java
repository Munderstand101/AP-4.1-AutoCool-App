package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateAdherentActivity extends AppCompatActivity {

    int idForm;
    String nameForm;
    ArrayList truc;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_adherent);

        try{
            Intent receiveIntent = getIntent();
            idForm = receiveIntent.getExtras().getInt("idFormule");
            nameForm = receiveIntent.getExtras().getString("nomFormule");

            EditText nomFormule = (EditText) findViewById(R.id.et_nomFormule);
            nomFormule.setText(nameForm);

            String[] arraySpinner = new String[] {
                    "Femme", "Homme", "Autre"
            };

            Spinner s = (Spinner) findViewById(R.id.civilite);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, arraySpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adapter);

            //set la date max des DatePicker a aujourd'hui
            DatePicker dateAdhesion = (DatePicker) findViewById(R.id.datepicker_adhesion);
            dateAdhesion.setMaxDate(new Date().getTime());

            DatePicker dateNaissance = (DatePicker) findViewById(R.id.datepicker_naissance);
            dateNaissance.setMaxDate(new Date().getTime());

            DatePicker datePermis = (DatePicker) findViewById(R.id.datepicker_obtentionpermis);
            datePermis.setMaxDate(new Date().getTime());

        }catch(Exception e){
            e.printStackTrace();
        }

        final Button btnCreerUser = (Button)findViewById(R.id.btn_creerUser);
        btnCreerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean breakval = false;

                EditText nom = (EditText) findViewById(R.id.et_nom);
                String nomVal = nom.getText().toString();

                EditText prenom = (EditText) findViewById(R.id.et_prenom);
                String prenomVal = prenom.getText().toString();

                EditText adresse = (EditText) findViewById(R.id.et_adresse);
                String adresseVal = adresse.getText().toString();

                EditText ville = (EditText) findViewById(R.id.et_ville);
                String villeVal = ville.getText().toString();

                EditText codepostal = (EditText) findViewById(R.id.et_codepostal);
                String codepostalVal = codepostal.getText().toString();

                EditText email = (EditText) findViewById(R.id.et_email);
                String emailVal = email.getText().toString();

                EditText numfixe = (EditText) findViewById(R.id.et_numfixe);
                String numfixeVal = numfixe.getText().toString();

                EditText numportable = (EditText) findViewById(R.id.et_numportable);
                String numportableVal = numportable.getText().toString();

                EditText numpermis = (EditText) findViewById(R.id.et_numpermis);
                String numpermisVal = numpermis.getText().toString();

                EditText lieuobtention = (EditText) findViewById(R.id.et_lieuobtention);
                String lieuobtentionVal = lieuobtention.getText().toString();

                Spinner civilite = (Spinner) findViewById(R.id.civilite);
                String civiliteVal = civilite.getSelectedItem().toString();

                DatePicker dateAdhesion = (DatePicker) findViewById(R.id.datepicker_adhesion);
                int daydateAdhesion = dateAdhesion.getDayOfMonth();
                int monthdateAdhesion = dateAdhesion.getMonth();
                int yeardateAdhesion = dateAdhesion.getYear();

                String dateAdhesionEnvoi = yeardateAdhesion + "-" + monthdateAdhesion + "-" + daydateAdhesion;

                DatePicker dateNaissance = (DatePicker) findViewById(R.id.datepicker_naissance);
                int daydateNaissance = dateNaissance.getDayOfMonth();
                int monthdateNaissance = dateNaissance.getMonth();
                int yeardateNaissance = dateNaissance.getYear();

                String dateNaissanceEnvoi = yeardateNaissance + "-" + monthdateNaissance + "-" + daydateNaissance;

                DatePicker datePermis = (DatePicker) findViewById(R.id.datepicker_obtentionpermis);
                int daydatePermis = datePermis.getDayOfMonth();
                int monthdatePermis = datePermis.getMonth();
                int yeardatePermis = datePermis.getYear();

                String datePermisEnvoi = yeardatePermis + "-" + monthdatePermis + "-" + daydatePermis;

                String checkPaiementAdhesion = "NON";
                String checkPaiementCaution = "NON";
                String checkRibFourni = "NON";

                CheckBox paiementAdhesion = (CheckBox)  findViewById(R.id.chk_paiementadhesion);
                if (paiementAdhesion.isChecked() == true){
                    checkPaiementAdhesion = "OUI";
                }

                CheckBox paiementCaution = (CheckBox)  findViewById(R.id.chk_paiementcaution);
                if (paiementCaution.isChecked() == true){
                    checkPaiementCaution = "OUI";
                }

                CheckBox ribFourni = (CheckBox)  findViewById(R.id.chk_ribfourni);
                if (ribFourni.isChecked() == true){
                    checkRibFourni = "OUI";
                }

//                Pattern p = Pattern.compile("/^[a-z ,.'-]+$/i");
                Pattern p = Pattern.compile("[a-zA-Z ,.'-]+");
                Pattern p2 = Pattern.compile("[0-9]+");
                Pattern phoneNumber = Pattern.compile("^(?:(?:\\+|00)33[\\s.-]{0,3}(?:\\(0\\)[\\s.-]{0,3})?|0)[1-9](?:(?:[\\s.-]?\\d{2}){4}|\\d{2}(?:[\\s.-]?\\d{3}){2})$");
                Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]{2,}\\.[a-z]{2,4}$");
                Pattern p3 = Pattern.compile("[0-9]{5}");

                Matcher m = p.matcher(nomVal);
                if (m.matches()) {
                }else{
                    Toast.makeText(CreateAdherentActivity.this, "Probleme avec votre nom", Toast.LENGTH_SHORT).show();
                    Log.d("test", "1");
                    breakval = true;
                }

                Matcher m2 = p.matcher(prenomVal);
                if (m2.matches()) {
                }else{
                    Toast.makeText(CreateAdherentActivity.this, "Probleme avec votre prenom", Toast.LENGTH_SHORT).show();
                    Log.d("test", "2");
                    breakval = true;
                }

                Matcher m3 = p.matcher(villeVal);
                if (m3.matches()) {
                }else{
                    Toast.makeText(CreateAdherentActivity.this, "Probleme avec votre ville", Toast.LENGTH_SHORT).show();
                    Log.d("test", "3");
                    breakval = true;
                }

                Matcher m4 = phoneNumber.matcher(numfixeVal);
                if (m4.matches()) {
                }else{
                    Toast.makeText(CreateAdherentActivity.this, "Probleme avec votre numero fixe", Toast.LENGTH_SHORT).show();
                    Log.d("test", "4");
                    breakval = true;
                }

                Matcher m5 = phoneNumber.matcher(numportableVal);
                if (m5.matches()) {
                }else{
                    Toast.makeText(CreateAdherentActivity.this, "Probleme avec votre portable", Toast.LENGTH_SHORT).show();
                    Log.d("test", "5");
                    breakval = true;
                }

                Matcher m6 = p2.matcher(numpermisVal);
                if (m6.matches()) {
                }else{
                    Toast.makeText(CreateAdherentActivity.this, "Probleme avec votre numero permis", Toast.LENGTH_SHORT).show();
                    Log.d("test", "6");
                    breakval = true;
                }

                Matcher m7 = p3.matcher(codepostalVal);
                if (m7.matches()) {
                }else{
                    Toast.makeText(CreateAdherentActivity.this, "Probleme avec votre code postal", Toast.LENGTH_SHORT).show();
                    Log.d("test", "7");
                    breakval = true;
                }

                Matcher m8 = emailPattern.matcher(emailVal);
                if (m8.matches()) {
                }else{
                    Toast.makeText(CreateAdherentActivity.this, "Probleme avec votre email", Toast.LENGTH_SHORT).show();
                    Log.d("test", "8");
                    breakval = true;
                }




                EditText formule = (EditText) findViewById(R.id.et_nomFormule);
                String formuleVal = formule.getText().toString();

                if(breakval == false){

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("nom", nomVal);
                        jsonObject.put("prenom", prenomVal);
                        jsonObject.put("date_naissance", dateNaissanceEnvoi);
                        jsonObject.put("rue", adresseVal);
                        jsonObject.put("ville", villeVal);
                        jsonObject.put("code_postal", codepostalVal);
                        jsonObject.put("tel", numfixeVal);
                        jsonObject.put("tel_mobile", numportableVal);
                        jsonObject.put("email", emailVal);
                        jsonObject.put("num_permis", numpermisVal);
                        jsonObject.put("lieu_permis", lieuobtentionVal);
                        jsonObject.put("date_permis", datePermisEnvoi);
                        jsonObject.put("paiement_adhesion", checkPaiementAdhesion);
                        jsonObject.put("paiement_caution", checkPaiementCaution);
                        jsonObject.put("rib_fourni", checkRibFourni);
                        jsonObject.put("civilite", civiliteVal);
                        jsonObject.put("idformule", String.valueOf(idForm));
                        jsonObject.put("dateAdhesion", dateAdhesionEnvoi);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, jsonObject.toString());

                    Request request = new Request.Builder()
                            .url(ParamAPI.url+"/api/create/adhere/")
                            .post(body)
                            .build();

                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {

                        public  void onResponse(Call call, Response response) throws IOException {
                            Log.d("oui", response.toString());
                            Intent intent = new Intent(CreateAdherentActivity.this, ListFormuleActivity.class);
                            startActivity(intent);
                        }
                        public void onFailure(Call call, IOException e){
                            Log.d("Test","erreur!!! connexion impossible");
                        }
                    });
                }else{
                    Log.d("ttt", "FFFF");
                }
            }
        });
    }
}