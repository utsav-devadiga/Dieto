package com.example.dietooo;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class WorkoutDetail extends AppCompatActivity {

    ImageView imagework,titleimage;
    TextView equipment, longdescription, pmd, smd;
    @Deprecated
    Toolbar toolbar1;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    ProgressBar workbar;
    TextView startworkout_activity;
    String image,workoutname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.transparent));
        }


        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_work);
        toolbar1 = findViewById(R.id.toolbarWork);
        equipment = findViewById(R.id.categorywork);
        longdescription = findViewById(R.id.longdesctext);
        imagework = findViewById(R.id.workoutimagegif);
        pmd = findViewById(R.id.pmdgroup);
        smd = findViewById(R.id.smdgroup);
        titleimage = findViewById(R.id.mealThumbWork);
        workbar = findViewById(R.id.progressBarWork);
        startworkout_activity = findViewById(R.id.startworkout);

        workbar.setIndeterminate(true);

        Bundle wbundle = getIntent().getExtras();

        if (wbundle != null) {
            image=wbundle.getString("image");
            Picasso.get().load(image).into(titleimage);
            collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.pink));
            collapsingToolbarLayout.setTitle(wbundle.getString("title"));
            equipment.setText(wbundle.getString("equipment"));
            longdescription.setText(wbundle.getString("longdesc"));
            pmd.setText(wbundle.getString("pmd"));
            smd.setText(wbundle.getString("smd"));
            Glide.with(this).load(image).into(imagework);
            workoutname = wbundle.getString("title");
        }
        setupActionBar();
workbar.setIndeterminate(false);

startworkout_activity.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent startwork = new Intent(WorkoutDetail.this,StartWorkout.class);
        startwork.putExtra("pic",image);
        startwork.putExtra("work",workoutname);
        startActivity(startwork);
    }
});

    }

    private void setupActionBar() {

        setSupportActionBar(toolbar1);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.pink));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.Black));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
