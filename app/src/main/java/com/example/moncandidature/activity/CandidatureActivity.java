package com.example.moncandidature.activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moncandidature.R;
import com.example.moncandidature.models.Candidature;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class CandidatureActivity extends AppCompatActivity implements View.OnClickListener   {
    Realm realm = null;
    EditText id_candidature;
    EditText poste;
    EditText entreprise;
    EditText url;
    EditText commentaire;

    TextView affichageTxt;
    Button save;

    private String myFormat="dd/MM/yy";

    final Calendar myCalendar = Calendar.getInstance();
    private EditText edittextDateC;
    private EditText edittextDateE;
    private EditText edittextDateR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();

        deleteAll();

        setContentView(R.layout.activity_candidature);
        id_candidature= (EditText) findViewById(R.id.id_candidature);
        poste= (EditText) findViewById(R.id.poste);
        entreprise= (EditText) findViewById(R.id.entreprise);
        url= (EditText) findViewById(R.id.lien);
        commentaire= (EditText) findViewById(R.id.commentaires);
        affichageTxt= (TextView) findViewById(R.id.log);
        save= findViewById(R.id.save);
        save.setOnClickListener(this);

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
                new DatePickerDialog(CandidatureActivity.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edittextDateE.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(CandidatureActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        edittextDateR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(CandidatureActivity.this, date3, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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

    @Override
    public void onClick(View v) {
        addToSGBD(id_candidature.getText().toString().trim()
                , poste.getText().toString().trim()
                , entreprise.getText().toString().trim()
                , url.getText().toString().trim()
                , commentaire.getText().toString().trim()
                , edittextDateC.getText().toString().trim()
                , edittextDateE.getText().toString().trim()
                , edittextDateR.getText().toString().trim()
        );

        ShowAll();
    }

    private void addToSGBD(String parseInt, String trim, String trim1, String trim2,String trim3, String trim4,String trim5, String trim6) {
        realm.beginTransaction();
        String primaryKeyValue= parseInt;
        Candidature candidature = realm
                .createObject(Candidature.class, primaryKeyValue);
        candidature.setPostName(trim);
        candidature.setCompany(trim1);
        candidature.setLink(trim2);
        candidature.setComment(trim3);
        try{
            candidature.setDate_applied(new SimpleDateFormat(myFormat).parse(trim4));
            candidature.setDate_applied(new SimpleDateFormat(myFormat).parse(trim5));
            candidature.setDate_applied(new SimpleDateFormat(myFormat).parse(trim6));
        }
        catch (Exception e)
        {
            System.out.println("Erreur!");
            System.err.println(e.getMessage());
        }

        realm.commitTransaction();
    }
    private void ShowAll(){
        realm.beginTransaction();
        RealmResults <Candidature> candidatures= realm.where(Candidature.class).findAll();
        String show= "";
        for (Candidature candidature: candidatures) {
            show+= candidature.toString() +"\n";
        }
        affichageTxt.setText(show);
    }

    public void UpdateData(Candidature c){
        realm.beginTransaction();
        RealmResults<Candidature> candidatures= realm.where(Candidature.class).findAll();
        for (Candidature candidature: candidatures){
            if(candidature.getApplication_id()==c.getApplication_id()){
                candidature.setPostName(c.getPostName());
                candidature.setCompany(c.getCompany());
                candidature.setLink(c.getLink());
                candidature.setComment(c.getComment());
                realm.commitTransaction();
            }
        }
    }

    public void deleteAll(){
        RealmResults<Candidature> candidatures= realm.where(Candidature.class).findAll();
        for (Candidature candidature: candidatures){

                realm.beginTransaction();
                candidature.deleteFromRealm();
                realm.commitTransaction();
        }
    }


//private void findData() {
//        realm.beginTransaction();
//        RealmResults<Student> results = realm
//        .where(Student.class)
//        .equalTo(StudentFields.NAME, "d")
//        .findAll();
//        String tableContent= "";
//        for (Student student: results){
//        tableContent+=student.toString();
//        }
//        affichageTxt.setText(tableContent);
//        realm.commitTransaction();
//
//        }
//
//
//
//        @Override
//        public void onDestroy()
//        {
//                super.onDestroy();
//                realm.close();
//        }

}
