package com.example.ibrahimshaltout.test.tracks;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.TrackDataClass;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TracksFragment extends Fragment {

    private FloatingActionButton addNewTrackButton;
    private RecyclerView tracks_recyclerView;
    private TrackAdapter trackAdapter;
    TabHost host;

    ArrayList<TrackDataClass> tracks = new ArrayList<>();

    public TracksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.tracks_fragment, container, false);

        addNewTrackButton = (FloatingActionButton) v.findViewById(R.id.add_new_track_button);
        addNewTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddNewTrackActivity.class);
                startActivity(i);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        tracks_recyclerView = v.findViewById(R.id.recycler_view_tracks_screen);
        trackAdapter = new TrackAdapter(getActivity(), tracks);
        RecyclerView.LayoutManager trackLayoutManager = new LinearLayoutManager(getActivity());
        tracks_recyclerView.setLayoutManager(trackLayoutManager);
        tracks_recyclerView.setNestedScrollingEnabled(true);
        tracks_recyclerView.setHasFixedSize(true);
//        tracks_recyclerView.requestFocus();
        tracks_recyclerView.setAdapter(trackAdapter);

        tracks.add(new TrackDataClass());
        trackAdapter.notifyDataSetChanged();


        host = (TabHost)v.findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Current").setContent(R.id.track_tab1).setIndicator("Current");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Achievement").setContent(R.id.track_tab2).setIndicator("Achievement");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Saved").setContent(R.id.track_tab3).setIndicator("Saved");
        host.addTab(spec);


        return v;
    }

}
