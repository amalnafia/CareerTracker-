package com.example.ibrahimshaltout.test;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ibrahimshaltout.test.signup.YourLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

public class IntroActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String user_UId;
    private DatabaseReference userDatabaseReference;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mAuth = FirebaseAuth.getInstance();
        user_UId = mAuth.getCurrentUser().getUid();
        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        userDatabaseReference.child(user_UId).child("location").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<String> t = new GenericTypeIndicator<String>() {
                };
//                location = dataSnapshot.getValue(t);
//
//                if (location != null) {
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                    finish();
//                } else {
//                    startActivity(new Intent(IntroActivity.this, YourLocation.class));
//                    finish();
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
