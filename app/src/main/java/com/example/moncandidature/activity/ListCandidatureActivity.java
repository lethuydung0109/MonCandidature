package com.example.moncandidature.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.moncandidature.R;
import com.example.moncandidature.adapter.CandidatureItemAdapter;
import com.example.moncandidature.helper.RealmAdapter;
import com.example.moncandidature.models.Candidature;

import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ListCandidatureActivity extends AppCompatActivity {

    protected ArrayList<Candidature> candidatureList;
    protected  Realm realm;
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

        candidatureList = realmAdapter.RetrieveAll();

        CandidatureItemAdapter itemAdapter = new CandidatureItemAdapter(this, candidatureList);

        // set List Item Adapter
        ListView listCandidate = findViewById(R.id.listCandidature);
        listCandidate.setAdapter(itemAdapter);

    }

 }

