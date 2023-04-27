package com.example.task_reminderapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myviewholder> {
    ArrayList<Model> dataholder = new ArrayList<Model>();                                               //array list to hold the reminders

    public myAdapter(ArrayList<Model> dataholder) {
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_reminder_file, parent, false);  //inflates the xml file in recyclerview
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.mTitle.setText(dataholder.get(position).getTitle());                                 //Binds the single reminder objects to recycler view
        holder.mDate.setText(dataholder.get(position).getDate());
        holder.mTime.setText(dataholder.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {

        public void Refresh(){
            notifyItemRemoved(getAdapterPosition());
        }

        TextView mTitle, mDate, mTime;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.txtTitle);                               //holds the reference of the materials to show data in recyclerview
            mDate = (TextView) itemView.findViewById(R.id.txtDate);
            mTime = (TextView) itemView.findViewById(R.id.txtTime);

            itemView.findViewById(R.id.deleteButton).setOnClickListener(
                    new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            Context context = v.getContext();
                            dbManager db = new dbManager(context);
                            int result = db.deleteReminder(dataholder.get(getAdapterPosition()).getTitle());

                            dataholder.remove(getAdapterPosition());
                            Refresh();
                        }
                    }
            );

            itemView.findViewById(R.id.editButton).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Get the activity context
                            Context context = v.getContext();

                            // Create an intent to start the ReminderActivity
                            Intent intent = new Intent(context, ReminderActivity.class);
                            context.startActivity(intent);

                            // Delete the reminder
                            dbManager db = new dbManager(context);
                            int result = db.deleteReminder(dataholder.get(getAdapterPosition()).getTitle());

                            dataholder.remove(getAdapterPosition());
                            Refresh();
                        }
                    }
            );

        }
    }
}
