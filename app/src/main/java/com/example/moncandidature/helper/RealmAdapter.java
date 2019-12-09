package com.example.moncandidature.helper;

import android.content.Context;

import com.example.moncandidature.models.Candidature;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmAdapter {
    private Realm realm;
    private String myFormat;

    public RealmAdapter(Realm realm) {
        this.realm = realm;
        myFormat = "dd/MM/yy";
    }
    public RealmAdapter(Realm realm, String myFormat){
        this.realm = realm;
        this.myFormat = myFormat;
    }

    public RealmAdapter(String realmName, Context context){
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder().name(realmName).build();
        Realm.setDefaultConfiguration(config);
        this.realm = Realm.getDefaultInstance();
        myFormat = "dd/MM/yy";
    }

    public RealmAdapter(String realmName, Context context, String myFormat){
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder().name(realmName).build();
        Realm.setDefaultConfiguration(config);
        this.realm = Realm.getDefaultInstance();
        this.myFormat = myFormat;
    }

    public void addToSGBD(String parseInt, String trim, String trim1, String trim2,String trim3, String trim4,String trim5, String trim6) {
        realm.beginTransaction();
        String primaryKeyValue= parseInt;
        Candidature candidature = realm
                .createObject(Candidature.class, primaryKeyValue);
        candidature.setPostName(trim);
        candidature.setCompany(trim1);
        candidature.setLink(trim2);
        candidature.setComment(trim3);
        try{
            candidature.setDate_applied(new SimpleDateFormat(this.myFormat).parse(trim4));
            candidature.setDate_interview(new SimpleDateFormat(this.myFormat).parse(trim5));
            candidature.setDate_reminder(new SimpleDateFormat(this.myFormat).parse(trim6));
        }
        catch (Exception e)
        {
            System.out.println("Erreur!");
            System.err.println(e.getMessage());
        }

        realm.commitTransaction();
    }

    public String retrieveAllAsString(){

        RealmResults <Candidature> candidatures= realm.where(Candidature.class).findAll();
        String show= "";
        for (Candidature candidature: candidatures) {
            show+= candidature.toString() +"\n";
        }
        return show;

    }

    public ArrayList<Candidature> retrieveAll(){

        ArrayList<Candidature> listAllCandidature = new ArrayList<>();
        RealmResults <Candidature> candidatures= realm.where(Candidature.class).findAll();
        for (Candidature candidature : candidatures) {
            listAllCandidature.add(candidature);
        }
        return listAllCandidature;

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


    public void UpdateData(String parseInt, String trim, String trim1, String trim2,String trim3, String trim4,String trim5, String trim6){
        realm.beginTransaction();
        RealmResults<Candidature> candidatures= realm.where(Candidature.class).findAll();
        for (Candidature candidature: candidatures){
            if(candidature.getApplication_id()== Integer.parseInt(parseInt)){
                candidature.setPostName(trim);
                candidature.setCompany(trim1);
                candidature.setLink(trim2);
                candidature.setComment(trim3);
                try{
                    candidature.setDate_applied(new SimpleDateFormat(this.myFormat).parse(trim4));
                    candidature.setDate_interview(new SimpleDateFormat(this.myFormat).parse(trim5));
                    candidature.setDate_reminder(new SimpleDateFormat(this.myFormat).parse(trim6));
                }
                catch (Exception e)
                {
                    System.out.println("Erreur!");
                    System.err.println(e.getMessage());
                }

                realm.commitTransaction();
            }
        }

    }

    public Candidature searchById(int id){

        RealmResults<Candidature> candidatures= realm.where(Candidature.class).findAll();
        for (Candidature candidature: candidatures){
            if(candidature.getApplication_id() == id ){
                return candidature;
            }
        }

        return null;
    }

    public void deleteById(int id){
//        realm.beginTransaction();
//        if (o.isValid()) o.deleteFromRealm();
//        realm.commitTransaction();
        // to implement
    }

    public void deleteAll(){
        RealmResults<Candidature> candidatures= realm.where(Candidature.class).findAll();
        for (Candidature candidature: candidatures){

            realm.beginTransaction();
            candidature.deleteFromRealm();
            realm.commitTransaction();
        }
    }

    public void closeConnection(){
        realm.close();
    }
}
