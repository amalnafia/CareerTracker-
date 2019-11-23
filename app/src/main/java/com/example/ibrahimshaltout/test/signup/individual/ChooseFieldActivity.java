package com.example.ibrahimshaltout.test.signup.individual;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.FieldDataClass;
import com.example.ibrahimshaltout.test.dataclass.IndividualDataClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChooseFieldActivity extends AppCompatActivity {
    AutoCompleteTextView choose_field;
    Button continue_field;

    private FirebaseAuth auth;
    DatabaseReference db;
    DatabaseReference databaseReference;

    ArrayList fieldDataSnapShot = new ArrayList<>();
    IndividualDataClass individualDataClass = new IndividualDataClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_field);

        final String currentUser = auth.getCurrentUser().getUid();


        ArrayAdapter<String> fieldNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, fieldDataSnapShot);
        choose_field = (AutoCompleteTextView) findViewById(R.id.choose_field);
        choose_field.setThreshold(1);
        choose_field.setAdapter(fieldNameAdapter);

        continue_field=findViewById(R.id.continue_field);
        continue_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser);

                final String interestedField = choose_field.getText().toString().trim();
                individualDataClass.setInterestedField(interestedField);
                databaseReference.child("interestedField").setValue(individualDataClass.interestedField);

            }
        });

        db = FirebaseDatabase.getInstance().getReference();
        db.child("Fields").child("Fields_List").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fetchFieldData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void fetchFieldData(DataSnapshot dataSnapshot) {
        FieldDataClass fieldDataClass = null;
        Iterable<DataSnapshot> list = dataSnapshot.getChildren();
        for (DataSnapshot x : list) {
            fieldDataClass = x.getValue(FieldDataClass.class);
            fieldDataSnapShot.add(fieldDataClass.getField_Name());
        }
    }
}
