package com.example.moncandidature.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.moncandidature.R;
import com.example.moncandidature.models.Candidature;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListCandidatureActivity extends AppCompatActivity {

    protected ArrayList<Candidature> candidatureList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_candidature);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 11, 31, 59, 59, 59);

        Date happyNewYearDate = calendar.getTime();

        candidatureList = new ArrayList<>();
//        calendar.set(2019, 5, 4);
//        candidatureList.add(new Candidature("Project Manager", "Crédit Agricole", calendar.getTime()));
//        calendar.set(2019, 4, 28);
//        candidatureList.add(new Candidature("Project Manager International Banking", "Natixis", calendar.getTime()));
//        calendar.set(2019, 4, 23);
//        candidatureList.add(new Candidature("International Relation Scrum Master", "Caisse Epargne", calendar.getTime()));
    }

 }

