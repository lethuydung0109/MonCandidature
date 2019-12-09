package com.example.moncandidature.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moncandidature.R;

import com.example.moncandidature.activity.ModificationActivity;
import com.example.moncandidature.models.Candidature;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class CandidatureItemAdapter extends RecyclerView.Adapter<CandidatureItemAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Candidature> candidatureList;

    public CandidatureItemAdapter(Context context, ArrayList<Candidature> candidatureList) {
        this.context = context;
        this.candidatureList = candidatureList;
    }


    int clickedPosition= RecyclerView.NO_POSITION;
    public int getClickedPosition(){
        return clickedPosition;
    }

    private void setClickedPosition(int pos){
        notifyItemChanged(clickedPosition);
        clickedPosition=pos;
        notifyItemChanged(clickedPosition);
    }


    @NonNull
    @Override
    public CandidatureItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view= li.inflate(R.layout.adapter_item, parent,false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull CandidatureItemAdapter.ViewHolder holder, int position) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Candidature candidature = candidatureList.get(position);
        holder.pNameView.setText(candidature.getPostName());
        holder.cNameView.setText(candidature.getCompany());
        holder.dateAppliedView.setText(df.format(candidature.getDate_applied()));

        if(candidature.getDate_applied()!= null && candidature.getDate_interview() == null){
            holder.status.setText("Sent ! Not yet responsed");
        }else if(candidature.getDate_interview() != null && candidature.getDate_interview().after(new Date())){
            holder.status.setText("Wait for the next interview");
        }else if(candidature.getDate_interview() != null && candidature.getDate_interview().before(new Date())){
            holder.status.setText("Interviewed. Wait for the result");
        }else if(candidature.isRejected()){
            holder.status.setText("Rejected");
        }else{
            holder.status.setText("Not contacted yet");
        }


        holder.modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // transfer data to another activity, then go to that activity
                Context context = v.getContext();
                String modId = Integer.toString(candidature.getApplication_id());
                SharedPreferences sharedPref = context.getSharedPreferences("myKey", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("id", modId);
                editor.apply();
                Intent intent = new Intent(v.getContext(), ModificationActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm dialog demo !");
                builder.setMessage("You are about to delete all records of database. Do you really want to proceed ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "You've choosen to delete all records", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "You've changed your mind to delete all records", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return candidatureList.get(position).getApplication_id();
    }

    @Override
    public int getItemCount() {
        return candidatureList.size();
    }

    // class pour le layout
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView pNameView, cNameView, dateAppliedView, status;
        MaterialButton modifier, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pNameView = itemView.findViewById(R.id.item_pName);
            cNameView = itemView.findViewById(R.id.item_cName);
            dateAppliedView = itemView.findViewById(R.id.item_date_applied);
            status = itemView.findViewById(R.id.item_status);
            modifier = itemView.findViewById(R.id.item_btn_modifier);
            delete = itemView.findViewById(R.id.item_btn_delete);

        }
    }

}
