package com.example.moncandidature.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.moncandidature.R;
import com.example.moncandidature.activity.ListCandidatureActivity;
import com.example.moncandidature.models.Candidature;

import java.util.ArrayList;
import java.util.Date;

public class CandidatureItemAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Candidature> candidatureList;
    public CandidatureItemAdapter(Context context, ArrayList<Candidature> candidatureList) {
        this.context = context;
        this.candidatureList = candidatureList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return candidatureList.size();
    }

    @Override
    public Candidature getItem(int position) {
        return candidatureList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.adapter_item, null);

        //get the current item
        Candidature currentItem = getItem(position);

        // get info for this item
        String pName = currentItem.getPostName();
        String cName = currentItem.getCompany();
        Date date_applied = currentItem.getDate_applied();
        Date date_interview = currentItem.getDate_interview();
        Date date_accepted = currentItem.getDate_accepted();
        boolean rejected = currentItem.isRejected();

        // set to the view
        TextView pNameView = convertView.findViewById(R.id.item_pName);
        TextView cNameView = convertView.findViewById(R.id.item_cName);
        TextView dateAppliedView = convertView.findViewById(R.id.item_date_applied);
        TextView status = convertView.findViewById(R.id.item_status);

        pNameView.setText(pName);
        cNameView.setText(cName);
        dateAppliedView.setText(date_applied.toString());

        if(date_accepted != null){
            status.setText("Accepted !");
        }else if(date_interview != null && date_interview.after(new Date())){
            status.setText("Wait for the next interview");
        }else if(date_interview != null && date_interview.before(new Date())){
            status.setText("Interviewed. Wait for the result");
        }else if(rejected){
            status.setText("Rejected");
        }else{
            status.setText("Not contacted yet");
        }

        return convertView;
    }
}
