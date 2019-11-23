package com.example.ibrahimshaltout.test.tracks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.ibrahimshaltout.test.R;

public class TrackProfileActivity extends AppCompatActivity {
    Button track_More_Details;
    Toolbar toolbarTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_profile);

        toolbarTop = findViewById(R.id.track_profile_Screen1);
        setSupportActionBar(toolbarTop);
        toolbarTop.setTitleMarginStart(80);

        // add back arrow to main_tool_bar
        toolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });

        track_More_Details=(Button)findViewById(R.id.track_More_Details);

        track_More_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityMethod();
            }
        });
    }

    private void startActivityMethod() {
        startActivity(new Intent(this, TrackProfileDetailsActivity.class));

    }
}
