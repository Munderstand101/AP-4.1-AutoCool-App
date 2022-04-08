package com.munderstand.autocool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAdherentActivity extends AppCompatActivity {

    int idForm;
    String nameForm;
    ArrayList truc;

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
        }catch(Exception e){
            e.printStackTrace();
        }

        final Button btnCreerUser = (Button)findViewById(R.id.btn_creerUser);
        btnCreerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                DatePicker dateNaissance = (DatePicker) findViewById(R.id.datepicker_naissance);
                int daydateNaissance = dateAdhesion.getDayOfMonth();
                int monthdateNaissance = dateAdhesion.getMonth();
                int yeardateNaissance = dateAdhesion.getYear();

                DatePicker datePermis = (DatePicker) findViewById(R.id.datepicker_obtentionpermis);
                int daydatePermis = dateAdhesion.getDayOfMonth();
                int monthdatePermis = dateAdhesion.getMonth();
                int yeardatePermis = dateAdhesion.getYear();




//                Pattern p = Pattern.compile(".*?payload\":(.*)}");
//                Matcher m = p.matcher(someJson);
//                if (m.matches()) {
//                    String payload = m.group(1);
//                }



            }
        });
    }
}