package com.example.dietooo;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.progresviews.ProgressWheel;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class StartWorkout extends AppCompatActivity {
    ProgressWheel progressWheel;
    TextView countdowntext, workoutnametext;
    Button startpause, reset;
    ImageView workimage;
    private static final long START_TIME_IN_MILLIS = 720000;
    private CountDownTimer countDownTimer;
    private boolean timerrunning;
    private long timeleftinmills = START_TIME_IN_MILLIS;
    int progress = 0;
    String image, workoutname;
    CardView cardView, startcard;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference, databaseReference;
    FirebaseUser firebaseUser;

    String WorkCount;
    Integer workcounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

        progressWheel = findViewById(R.id.wheelprogress);
        countdowntext = findViewById(R.id.countdown);
        startpause = findViewById(R.id.start_pause);
        reset = findViewById(R.id.restart);
        workoutnametext = findViewById(R.id.workname);
        workimage = findViewById(R.id.timergif);
        cardView = findViewById(R.id.cardtohide);
        startcard = findViewById(R.id.cardtohidestart);


        Intent startwork = getIntent();
        image = startwork.getStringExtra("pic");
        workoutname = startwork.getStringExtra("work");
        progressWheel.setPercentage(0);

        workoutnametext.setText(workoutname);
        Picasso.get().load(image).into(workimage);
        startpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(StartWorkout.this).load(image).into(workimage);
                if (timerrunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeleftinmills, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressWheel = findViewById(R.id.wheelprogress);
                timeleftinmills = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                progress=0;
                progressWheel.setPercentage(progress);
                timerrunning = false;
                reset.setText("I want to do this again!");
                startpause.setVisibility(View.INVISIBLE);
                startcard.setVisibility(View.INVISIBLE);
                reset.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.VISIBLE);
                Picasso.get().load(image).into(workimage);
                Toast.makeText(StartWorkout.this, "finished", Toast.LENGTH_LONG).show();

                firebaseAuth = FirebaseAuth.getInstance();
                firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseUser = firebaseAuth.getCurrentUser();

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("users").child(firebaseUser.getUid()).child("profile");
                databaseReference.setValue("Performing Workout");


                reference = firebaseDatabase.getReference("users").child(firebaseUser.getUid());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        WorkCount = dataSnapshot.child("workcount").getValue().toString();
                        workcounter = Integer.parseInt(WorkCount);
                        workcounter = workcounter + 1;
                        reference.child("workcount").setValue(workcounter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }.start();
        timerrunning = true;
        startpause.setText("I need a break!");
        reset.setVisibility(View.INVISIBLE);
        cardView.setVisibility(View.INVISIBLE);

    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerrunning = false;
        startpause.setText("Let's finish this!");
        reset.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.VISIBLE);
        Picasso.get().load(image).into(workimage);
    }

    private void resetTimer() {
        timeleftinmills = START_TIME_IN_MILLIS;
        updateCountDownText();
        reset.setVisibility(View.INVISIBLE);
        cardView.setVisibility(View.INVISIBLE);
        startpause.setVisibility(View.VISIBLE);
        startcard.setVisibility(View.VISIBLE);
        progressWheel.setPercentage(0);
        progress = 0;
        Picasso.get().load(image).into(workimage);
        startpause.setText("Let's do this!");
    }

    private void updateCountDownText() {
        int minutes = (int) (timeleftinmills / 1000) / 60;
        int seconds = (int) (timeleftinmills / 1000) % 60;

        String timeleftformatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countdowntext.setText(timeleftformatted);
        if (timeleftformatted.equals("00:00")) {
            progressWheel.setPercentage(0);
        }
        if (seconds % 2 == 0 && seconds != 0) {
            progress++;
            progressWheel.setPercentage(progress);
        }
        if (timeleftformatted.equals("00:01")) {
            progress=360;
            progressWheel.setPercentage(progress);
        }

    }
}
