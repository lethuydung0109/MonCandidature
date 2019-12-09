package com.example.moncandidature.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.moncandidature.R;
import com.example.moncandidature.helper.RealmAdapter;
import com.example.moncandidature.models.Candidature;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ModificationActivity extends AppCompatActivity {
    String realmName = "myrealm.realm";
    EditText id_candidature;
    EditText poste;
    EditText entreprise;
    EditText url;
    EditText commentaire;
    Button save;
    Button showall;
    Candidature c;


    private String myFormat = "dd/MM/yy";
    final Calendar myCalendar = Calendar.getInstance();
    private EditText edittextDateC;
    private EditText edittextDateE;
    private EditText edittextDateR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // get Realm Adapter - initialize a private instance of realm, managed by RealmAdapter

        RealmAdapter realmAdapter = new RealmAdapter(realmName, getApplicationContext());

        setContentView(R.layout.activity_add_candidature);
        id_candidature= (EditText) findViewById(R.id.id_candidature);
        poste= (EditText) findViewById(R.id.poste);
        entreprise= (EditText) findViewById(R.id.entreprise);
        url= (EditText) findViewById(R.id.lien);
        commentaire= (EditText) findViewById(R.id.commentaires);


        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        String searchId = sharedPreferences.getString("id","");




        edittextDateC= findViewById(R.id.date_candidature);
        edittextDateE= findViewById(R.id.date_entretien);
        edittextDateR= findViewById(R.id.date_rappel);

        DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDateC();
            }
        };

        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDateE();
            }
        };

        DatePickerDialog.OnDateSetListener date3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDateR();
            }
        };

        edittextDateC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ModificationActivity.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edittextDateE.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ModificationActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        edittextDateR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(ModificationActivity.this, date3, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        if(searchId != ""){
            c = realmAdapter.searchById(Integer.parseInt(searchId));
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
            // set text for the element
            id_candidature.setText(Integer.toString(c.getApplication_id()));
            poste.setText(c.getPostName());
            entreprise.setText(c.getCompany());
            url.setText(c.getLink());
            commentaire.setText(c.getComment());
            if(c.getDate_applied() != null){
                edittextDateC.setText(sdf.format(c.getDate_applied()));
            }
            if(c.getDate_interview() != null){
                edittextDateE.setText(sdf.format(c.getDate_interview()));
            }
            if(c.getDate_reminder() != null){
                edittextDateR.setText(sdf.format(c.getDate_reminder()));
            }

        }



        save= findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realmAdapter.UpdateData(c);

                realmAdapter.UpdateData(id_candidature.getText().toString().trim()
                        , poste.getText().toString().trim()
                        , entreprise.getText().toString().trim()
                        , url.getText().toString().trim()
                        , commentaire.getText().toString().trim()
                        , edittextDateC.getText().toString().trim()
                        , edittextDateE.getText().toString().trim()
                        , edittextDateR.getText().toString().trim()
                );

                Toast.makeText(getApplicationContext(),"Candidature mise à jour ",Toast.LENGTH_SHORT).show();

            }
        });

        showall = findViewById(R.id.showall);
        showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListCandidatureActivity.class);
                startActivity(intent);
            }
        });



    }

    private void updateLabelDateC() {
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        edittextDateC.setText(sdf.format(myCalendar.getTime()));
    }


    private void updateLabelDateE() {
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        edittextDateE.setText(sdf.format(myCalendar.getTime()));
    }


    private void updateLabelDateR() {
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        edittextDateR.setText(sdf.format(myCalendar.getTime()));
    }

}