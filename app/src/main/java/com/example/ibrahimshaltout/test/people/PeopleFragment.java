package com.example.ibrahimshaltout.test.people;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.IndividualDataClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PeopleFragment extends Fragment {

    private RecyclerView people_recycler_view;
    private IndividualAdapter individualAdapter;

    ArrayList<IndividualDataClass> PeopleIcon = new ArrayList<>();

    DatabaseReference db;

    public PeopleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.people_fragment, viewGroup, false);
        db = FirebaseDatabase.getInstance().getReference();

        initViewPeople(v);
        getPeoplesData();
        return v;
    }


    private void initViewPeople(View v) {
        people_recycler_view = v.findViewById(R.id.people_recycler_view);
        individualAdapter = new IndividualAdapter(getActivity(), PeopleIcon);
        RecyclerView.LayoutManager peopleLayoutManager = new LinearLayoutManager(getActivity());
        int numberOfColumns = 2;
        people_recycler_view.setLayoutManager(peopleLayoutManager);
        people_recycler_view.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        people_recycler_view.setNestedScrollingEnabled(true);
        people_recycler_view.setHasFixedSize(true);
        people_recycler_view.setAdapter(individualAdapter);
    }

    private void getPeoplesData() {
        db.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parsePeopleData(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void parsePeopleData(DataSnapshot dataSnapshot) {
        ArrayList<IndividualDataClass> listOfPeople = new ArrayList<>();
        Iterable<DataSnapshot> list = dataSnapshot.getChildren();
        for (DataSnapshot x : list) {
            listOfPeople.add(x.getValue(IndividualDataClass.class));
        }
        setPeopleData(listOfPeople);
    }

    private void setPeopleData(ArrayList<IndividualDataClass> listOfTracks) {
        PeopleIcon.addAll(listOfTracks);
        individualAdapter.notifyDataSetChanged();
    }
}
