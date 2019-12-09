package com.example.moncandidature.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.moncandidature.R;
import com.example.moncandidature.adapter.CandidatureItemAdapter;
import com.example.moncandidature.helper.RealmAdapter;
import com.example.moncandidature.models.Candidature;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ListCandidatureActivity extends AppCompatActivity {

    protected ArrayList<Candidature> candidatureList;
    protected  Realm realm;
    FloatingActionButton btnToAddPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_candidature);

        // get realm object
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();

        // get Realm Adapter

        RealmAdapter realmAdapter = new RealmAdapter(realm);

        candidatureList = realmAdapter.retrieveAll();

        CandidatureItemAdapter itemAdapter = new CandidatureItemAdapter(this, candidatureList);

        // set List Item Adapter
        ListView listCandidate = findViewById(R.id.listCandidature);
        listCandidate.setAdapter(itemAdapter);

        // set action for button

        btnToAddPage = findViewById(R.id.fab_addPage);
        btnToAddPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CandidatureActivity.class);
                startActivity(intent);
            }
        });


    }

 }

