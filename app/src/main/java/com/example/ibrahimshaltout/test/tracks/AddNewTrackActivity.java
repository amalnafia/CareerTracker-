package com.example.ibrahimshaltout.test.tracks;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.TrackDataClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddNewTrackActivity extends AppCompatActivity {
    private AddNewTrackAdapter trackAdapter;
    Toolbar toolbarTop;
    ArrayList<TrackDataClass> tracks = new ArrayList<>();


    DatabaseReference db;
    private FirebaseAuth auth;
    private RecyclerView tracks_recyclerView_new;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new__track);

        toolbarTop = findViewById(R.id.add_new_track_top_bar);
        setSupportActionBar(toolbarTop);
        toolbarTop.setTitle("Add New TrackDataClass ");
        toolbarTop.setTitleMarginStart(80);
        db = FirebaseDatabase.getInstance().getReference();
        // add back arrow to main_tool_bar
        toolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();

            }
        });

        tracks_recyclerView_new = findViewById(R.id.recycler_view_tracks_Recommendation_screen);
        trackAdapter = new AddNewTrackAdapter(this, tracks);
        RecyclerView.LayoutManager trackLayoutManager = new LinearLayoutManager(this);
        tracks_recyclerView_new.setLayoutManager(trackLayoutManager);
        tracks_recyclerView_new.setNestedScrollingEnabled(true);
        tracks_recyclerView_new.setHasFixedSize(true);
        tracks_recyclerView_new.setAdapter(trackAdapter);

        getTracksData();


    }
    private void initViewsTracks(View rootView) {

    }    private void parseTracksData(DataSnapshot dataSnapshot) {
        ArrayList<TrackDataClass> listOfTracks = new ArrayList<>();
        Iterable<DataSnapshot> list = dataSnapshot.getChildren();
        for (DataSnapshot x : list) {
            listOfTracks.add(x.getValue(TrackDataClass.class));
        }
        setTracksData(listOfTracks);
    }
    private void getTracksData() {
        db.child("Tracks").child("Computer and Technology").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parseTracksData(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void setTracksData(ArrayList<TrackDataClass> listOfTracks) {
        tracks.addAll(listOfTracks);
        trackAdapter.notifyDataSetChanged();
    }

}
