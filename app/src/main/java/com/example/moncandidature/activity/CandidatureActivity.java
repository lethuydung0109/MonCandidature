package com.example.moncandidature.activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.moncandidature.R;
import com.example.moncandidature.helper.RealmAdapter;
import com.example.moncandidature.receiver.NotificationPublisher;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class CandidatureActivity extends AppCompatActivity {
    String realmName = "myrealm.realm";
    EditText id_candidature;
    EditText poste;
    EditText entreprise;
    EditText url;
    EditText commentaire;
    TextView affichageTxt;
    Button save;
    Button showall;

    private String myFormat="dd/MM/yy";
    final Calendar myCalendar = Calendar.getInstance();
    private EditText edittextDateC;
    private EditText edittextDateE;
    private EditText edittextDateR;

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
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

        save= findViewById(R.id.save);


        showall = findViewById(R.id.showall);
        showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListCandidatureActivity.class);
                startActivity(intent);
            }
        });

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

        TimePickerDialog.OnTimeSetListener time3 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);

                Date date = myCalendar.getTime() ;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm:ss", Locale.FRANCE);
                Log.i("onTimeSet", Long.toString(date.getTime()));
                Log.i("onTimeSet", sdf.format(date).toString());
                scheduleNotification(getNotification("Remind : " + poste.getText().toString()
                        + " @ " + entreprise.getText().toString() + " applied on " + edittextDateC.getText().toString()), date.getTime());

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

                new TimePickerDialog(CandidatureActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog, time3, myCalendar
                        .get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), true).show();

            }
        };



        edittextDateC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(CandidatureActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog,
                        date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        edittextDateE.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(CandidatureActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        edittextDateR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(CandidatureActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog, date3, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNumeric(id_candidature.getText().toString())){
                    realmAdapter.addToSGBD(id_candidature.getText().toString().trim()
                            , poste.getText().toString().trim()
                            , entreprise.getText().toString().trim()
                            , url.getText().toString().trim()
                            , commentaire.getText().toString().trim()
                            , edittextDateC.getText().toString().trim()
                            , edittextDateE.getText().toString().trim()
                            , edittextDateR.getText().toString().trim()
                    );

                    Toast.makeText(getApplicationContext(),"Candidature enregistré ",Toast.LENGTH_SHORT).show();
                }else{
                    id_candidature.setError("Enter an numeric value for application id !");
                    Toast.makeText(getApplicationContext(),"Numeric value required ! ",Toast.LENGTH_SHORT).show();
                }
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

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    private void scheduleNotification(Notification notification, long delay) {

        Intent notificationIntent = new Intent( this, NotificationPublisher.class ) ;
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 ,
                notificationIntent ,
                PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager.RTC_WAKEUP, delay , pendingIntent) ;
    }

    private Notification getNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "Remind: Application to be reminded" ) ;
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(content)) ;
        builder.setSmallIcon(R.drawable.ic_launcher_foreground) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        Log.i("Check Notification: ", "Inside getNotification - " + content );
        return builder.build() ;
    }

}
