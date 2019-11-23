package com.example.ibrahimshaltout.test.tracks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ibrahimshaltout.test.MainActivity;
import com.example.ibrahimshaltout.test.R;

public class CurrentTrackProfileActivity extends AppCompatActivity {

    LinearLayout linearLayout1;
    TextView main_station_one;
    private int show_hide1 = 0;

    TextView note;

    LinearLayout linearLayout2;
    TextView main_station_two;
    private int show_hide2 = 0;


    LinearLayout linearLayout3;
    TextView main_station_Three;
    private int show_hide3 = 0;

    Toolbar toolbarTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_track_profile);


        toolbarTop = findViewById(R.id.Current_Track_profile_Screen);
        setSupportActionBar(toolbarTop);
        toolbarTop.setTitleMarginStart(80);

        // add back arrow to main_tool_bar
        toolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CurrentTrackProfileActivity.this, MainActivity.class));
                finish();
            }
        });

//        note = (TextView) findViewById(R.id.note);
//        note.setVisibility(View.VISIBLE);

//        note.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                note.setVisibility(View.GONE);
//
//            }
//        });


//        linearLayout1.setVisibility(View.GONE);
        linearLayout1 = (LinearLayout) findViewById(R.id.Current_linear1);
        main_station_one = (TextView) findViewById(R.id.Current_main_station_one);

        linearLayout2 = (LinearLayout) findViewById(R.id.Current_linear2);
        main_station_two = (TextView) findViewById(R.id.Current_main_station_two);

        linearLayout3 = (LinearLayout) findViewById(R.id.Current_linear3);
        main_station_Three = (TextView) findViewById(R.id.Current_main_station_three);

        linearLayout1.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.GONE);

        main_station_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (show_hide1 == 1) {
                    linearLayout1.setVisibility(View.GONE);
                    show_hide1 = 0;
                } else {
                    linearLayout1.setVisibility(View.VISIBLE);
                    show_hide1 = 1;
                }
            }
        });

        main_station_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (show_hide2 == 1) {
                    linearLayout2.setVisibility(View.GONE);
                    show_hide2 = 0;
                } else {
                    linearLayout2.setVisibility(View.VISIBLE);
                    show_hide2 = 1;
                }
            }
        });

        main_station_Three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (show_hide3 == 1) {
                    linearLayout3.setVisibility(View.GONE);
                    show_hide3 = 0;
                } else {
                    linearLayout3.setVisibility(View.VISIBLE);
                    show_hide3 = 1;
                }
            }
        });
    }
}
