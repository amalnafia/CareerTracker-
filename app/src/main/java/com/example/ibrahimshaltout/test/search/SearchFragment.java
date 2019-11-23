package com.example.ibrahimshaltout.test.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.newsfeed.post.PostAdapter;
import com.example.ibrahimshaltout.test.newsfeed.post.PostDataClass;
import com.example.ibrahimshaltout.test.newsfeed.track.TrackAdapterRecommendation;
import com.example.ibrahimshaltout.test.dataclass.TrackDataClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements View.OnClickListener {


    private Toolbar toolbar;

    TrackDataClass trackDataClass =new TrackDataClass();
    private FirebaseAuth auth;
    private int show_hide_tracks = 1;

    private RecyclerView tracks_recyclerView;
    private RecyclerView posts_recyclerView;

    private TrackAdapterRecommendation trackAdapter;
    private PostAdapter postAdapter;
    private Button showHide;
    private TextView showHideView;

    ArrayList<TrackDataClass> trackDataClassess = new ArrayList<>();
    ArrayList<PostDataClass> postDataClasses = new ArrayList<>();
    DatabaseReference db;


    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.search_fragment, container, false);
        db = FirebaseDatabase.getInstance().getReference();

        // appbar / toolbar
        toolbar = view.findViewById(R.id.search_appbar);
//        toolbar.setTitle("Search Here");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));

            }
        });

        initViewsPosts(view);
        getPostsData();

        initViewsTracks(view);
        getTracksData();
        showHideTracks(view);
        return view;
    }
    @Override
    public void onClick(View v) {

    }
    //Posts Methods
    private void initViewsPosts(View rootView) {
        posts_recyclerView = rootView.findViewById(R.id.recycler_view_posts_search);
        postAdapter = new PostAdapter(getActivity(), postDataClasses);
        RecyclerView.LayoutManager postLayoutManager = new LinearLayoutManager(getActivity());
        posts_recyclerView.setLayoutManager(postLayoutManager);
        posts_recyclerView.setNestedScrollingEnabled(true);
        posts_recyclerView.setHasFixedSize(true);
        posts_recyclerView.setAdapter(postAdapter);
    }
    private void getPostsData() {
        db.child("Posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parsePostsData(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void setPostsData(ArrayList<PostDataClass> dataRetrieved) {
        postDataClasses.addAll(dataRetrieved);
        postAdapter.notifyDataSetChanged();
    }
    private void parsePostsData(DataSnapshot dataSnapshot) {
        ArrayList<PostDataClass> listOfPosts = new ArrayList<>();
        Iterable<DataSnapshot> list = dataSnapshot.getChildren();
        for (DataSnapshot x : list) {
            listOfPosts.add(x.getValue(PostDataClass.class));
        }
        setPostsData(listOfPosts);
    }
    //End of Post Methods
    // Tracks Methods
    private void initViewsTracks(View rootView) {

        tracks_recyclerView = rootView.findViewById(R.id.recycler_view_tracks_search);
        trackAdapter = new TrackAdapterRecommendation(getActivity(), trackDataClassess);
        RecyclerView.LayoutManager trackLayoutManager = new LinearLayoutManager(getActivity(),
        LinearLayoutManager.HORIZONTAL, false);
        tracks_recyclerView.setLayoutManager(trackLayoutManager);
        tracks_recyclerView.setNestedScrollingEnabled(true);
        tracks_recyclerView.setHasFixedSize(true);
        tracks_recyclerView.setAdapter(trackAdapter);
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
        trackDataClassess.addAll(listOfTracks);
        trackAdapter.notifyDataSetChanged();
    }
    private void parseTracksData(DataSnapshot dataSnapshot) {
        ArrayList<TrackDataClass> listOfTracks = new ArrayList<>();
        Iterable<DataSnapshot> list = dataSnapshot.getChildren();
        for (DataSnapshot x : list) {
            listOfTracks.add(x.getValue(TrackDataClass.class));
        }
        setTracksData(listOfTracks);
    }

    private void showHideTracks(View rootView) {

        showHide = rootView.findViewById(R.id.button_hide_show_search);
        showHideView = rootView.findViewById(R.id.view_hide_show_search);
        showHide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (show_hide_tracks == 1) {
                    tracks_recyclerView.setVisibility(View.GONE);
                    show_hide_tracks = 0;
                    showHide.setBackground(posts_recyclerView.getResources().getDrawable(R.drawable.ic_menu_down));
                } else {
                    tracks_recyclerView.setVisibility(View.VISIBLE);
                    show_hide_tracks = 1;
                    showHide.setBackground(posts_recyclerView.getResources().getDrawable(R.drawable.ic_menu_up));
                    showHide.setPadding(0, 0, 0, 10);
                }
            }
        });
    }




}
