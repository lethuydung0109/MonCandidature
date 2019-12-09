package com.example.moncandidature.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moncandidature.R;
import com.example.moncandidature.adapter.CandidatureItemAdapter;
import com.example.moncandidature.helper.RealmAdapter;
import com.example.moncandidature.models.Candidature;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ListCandidatureActivity extends AppCompatActivity {

    protected ArrayList<Candidature> candidatureList;
    protected  Realm realm;
    FloatingActionButton btnToAddPage;
    RecyclerView recyclerView;
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
        Log.i("List candidature = ", Integer.toString(candidatureList.size()));


        // set up Recycler View

        recyclerView = findViewById(R.id.rv_list_candidature);
        CandidatureItemAdapter itemAdapter = new CandidatureItemAdapter(this, candidatureList);
        LinearLayoutManager lm= new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new
                DividerItemDecoration(recyclerView.getContext(), lm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setHasFixedSize(true);



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

