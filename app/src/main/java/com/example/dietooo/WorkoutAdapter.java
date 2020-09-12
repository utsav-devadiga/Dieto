package com.example.dietooo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutViewHolder>  {

    private Context Workoutcontext;
    private List<WorkoutData> myWorkoutList;
    private List<WorkoutData> MyWorkListFull;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference, databaseReference;
    FirebaseUser firebaseUser;
    String WorkCount;
    Integer workcounter;

    public WorkoutAdapter(Context workoutcontext, List<WorkoutData> myWorkoutList) {
        Workoutcontext = workoutcontext;
        this.myWorkoutList = myWorkoutList;
        this.MyWorkListFull = new ArrayList<>(myWorkoutList);
    }

    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View workoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_row_workouts, viewGroup, false);

        return new WorkoutViewHolder(workoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder workoutViewHolder, int i) {

        Glide.with(Workoutcontext).load(myWorkoutList.get(i).getImage()).into(workoutViewHolder.workimage);
        // Picasso.get().load(myWorkoutList.get(i).getImage()).into(workoutViewHolder.workimage);
        workoutViewHolder.workname.setText(myWorkoutList.get(i).getWorktitle());
        workoutViewHolder.workshortdesc.setText(myWorkoutList.get(i).getShortdesc());
        workoutViewHolder.workrate.setRating(myWorkoutList.get(i).getWorkrating());
        workoutViewHolder.workequipment.setText(myWorkoutList.get(i).getEquipments());

        workoutViewHolder.workcardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth = FirebaseAuth.getInstance();
                firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseUser = firebaseAuth.getCurrentUser();

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("users").child(firebaseUser.getUid()).child("profile");
                databaseReference.setValue("WorkoutClicked");


                Intent intent = new Intent(Workoutcontext,WorkoutDetail.class);
                intent.putExtra("title",myWorkoutList.get(workoutViewHolder.getAdapterPosition()).getWorktitle());
                intent.putExtra("shortdesc",myWorkoutList.get(workoutViewHolder.getAdapterPosition()).getShortdesc());
                intent.putExtra("longdesc",myWorkoutList.get(workoutViewHolder.getAdapterPosition()).getLongdesc());
                intent.putExtra("pmd",myWorkoutList.get(workoutViewHolder.getAdapterPosition()).getPrimarymd());
                intent.putExtra("smd",myWorkoutList.get(workoutViewHolder.getAdapterPosition()).getSecondarymd());
                intent.putExtra("ratings",myWorkoutList.get(workoutViewHolder.getAdapterPosition()).getWorkrating());
                intent.putExtra("image",myWorkoutList.get(workoutViewHolder.getAdapterPosition()).getImage());
                intent.putExtra("equipment",myWorkoutList.get(workoutViewHolder.getAdapterPosition()).getEquipments());
                Workoutcontext.startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {

        return myWorkoutList.size();
    }
}

class WorkoutViewHolder extends RecyclerView.ViewHolder {

    ImageView workimage;
    TextView  workname, workshortdesc,workequipment;
    RatingBar  workrate;
    CardView workcardview;

    public WorkoutViewHolder(View workView) {
        super(workView);

        workimage = workView.findViewById(R.id.WorkoutImage);
        workname = workView.findViewById(R.id.WorkoutNameText);
        workshortdesc = workView.findViewById(R.id.WorkoutShortDescText);
        workrate = workView.findViewById(R.id.WorkoutRating);
        workcardview = workView.findViewById(R.id.myCardViewWorkout);
        workequipment = workView.findViewById(R.id.equipment);
    }
}

