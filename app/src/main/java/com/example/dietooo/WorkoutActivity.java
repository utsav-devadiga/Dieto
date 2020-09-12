package com.example.dietooo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference, Workref, genderef;
    WorkoutData workoutData;
    RecyclerView WorkCycle;
    List<WorkoutData> myWorkoutlist;
    String datagender;
    Userhelper userhelper;


    final LoadingDialog loadingDialog = new LoadingDialog(WorkoutActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.pink));
        }

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Workouts");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users").child(firebaseUser.getUid());
        reference.child("profile").setValue("Workouts");

        WorkCycle = findViewById(R.id.recyclerViewWorkoutLAY);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(WorkoutActivity.this, 1
        );
        WorkCycle.setLayoutManager(gridLayoutManager);

        myWorkoutlist = new ArrayList<>();



        WorkoutAdapter workoutAdapter = new WorkoutAdapter(WorkoutActivity.this, myWorkoutlist);
        WorkCycle.setAdapter(workoutAdapter);
        workoutAdapter.notifyDataSetChanged();
        loadingDialog.startLoadingDialog();

        genderef = firebaseDatabase.getReference("users").child(firebaseUser.getUid());
        genderef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                datagender = dataSnapshot.child("gender").getValue().toString();
               if(datagender.toLowerCase().equals("male")){
                   Workref = firebaseDatabase.getReference("workout").child("male");
               }else{
                   Workref = firebaseDatabase.getReference("workout").child("female");
               }

                Workref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            myWorkoutlist.clear();

                            for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {

                                workoutData = itemSnapshot.getValue(WorkoutData.class);
                                myWorkoutlist.add(workoutData);

                            }
                            workoutAdapter.notifyDataSetChanged();
                        }
                        loadingDialog.dissmissDialog();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchreport,menu);
        MenuItem searchItem = menu.findItem(R.id.searchdietsinmenu);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<WorkoutData> mysearchlistwork = new ArrayList<>();

                for (WorkoutData workoutData : myWorkoutlist) {
                    if (workoutData.getWorktitle().toLowerCase().contains(s.toLowerCase())) {
                        mysearchlistwork.add(workoutData);
                    }
                }
                WorkoutAdapter apdapterlist = new WorkoutAdapter(WorkoutActivity.this, mysearchlistwork);
                WorkCycle.setAdapter(apdapterlist);
                return false;
            }
        });

        MenuItem reportbutton = menu.findItem(R.id.reportingSystem);
        reportbutton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Report report = new Report(WorkoutActivity.this);
                report.sendReport();
                return false;
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("users").child(firebaseUser.getUid());
        reference.child("profile").setValue("Workouts");
        super.onStart();
    }
}
