package com.example.moncandidature.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moncandidature.R;
import com.example.moncandidature.adapter.CandidatureItemAdapter;
import com.example.moncandidature.helper.RealmAdapter;
import com.example.moncandidature.models.Candidature;
import com.example.moncandidature.receiver.NotificationPublisher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import io.realm.Realm;
import io.realm.RealmResults;

public class ListCandidatureActivity extends AppCompatActivity {

    protected RealmResults<Candidature> candidatureList;
    protected  Realm realm;
    private String realmName = "myrealm.realm";
    FloatingActionButton btnToAddPage;
    RecyclerView recyclerView;

    private final String CHANNEL_ID = "reminder_notification";
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_candidature);


        // get Realm Adapter

        RealmAdapter realmAdapter = new RealmAdapter(realmName, getApplicationContext());

        candidatureList = realmAdapter.retrieveAll();
        Log.i("List candidature = ", Integer.toString(candidatureList.size()));

        // set up Recycler View

        recyclerView = findViewById(R.id.rv_list_candidature);
        CandidatureItemAdapter itemAdapter = new CandidatureItemAdapter(candidatureList);
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

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent( this, NotificationPublisher.class ) ;
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 ,
                                                                    notificationIntent ,
                                                                    PendingIntent. FLAG_UPDATE_CURRENT ) ;
        long futureInMillis = SystemClock. elapsedRealtime () + delay ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , futureInMillis , pendingIntent) ;
        Log.i("Check Notification: ", "Inside scheduleNotification " );
    }

    private Notification getNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "Scheduled Notification" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable.ic_launcher_foreground) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        Log.i("Check Notification: ", "Inside getNotification - " + content );
        return builder.build() ;
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Personal Notifications";
            String description = "Include all personal notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

 }

