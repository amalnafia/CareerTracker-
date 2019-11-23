package com.example.ibrahimshaltout.test.signup.individual;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ibrahimshaltout.test.MainActivity;
import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.signup.YourLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class IndividuaGeneralinfoActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    DatabaseReference databaseReference;
    private FirebaseUser user;
    String birthDate;
    Calendar myCalendar = Calendar.getInstance();
    private Button continuegeneral;
    RadioButton radioButton1;
    RadioGroup radioGroup1;
    String radioValue;
    int getSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individua_generalinfo);
        auth = FirebaseAuth.getInstance();

        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);

        continuegeneral = (Button) findViewById(R.id.countinue_generalIndividualInfo);

        final EditText dateEditText = findViewById(R.id.birthdate);


        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String myFormat = "dd/MM/yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        dateEditText.setText(sdf.format(myCalendar.getTime()));
                        birthDate = dateEditText.getText().toString().trim();
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(IndividuaGeneralinfoActivity.this, dateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup1, @IdRes int i) {
                switch (i) {
                    case R.id.male:
                        radioValue = "Male";
                        break;
                    case R.id.female:
                        radioValue = "Female";
                        break;
                }
            }
        });

        continuegeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentUser = auth.getCurrentUser().getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser);

                databaseReference.child("BirthDate").setValue(birthDate);
                databaseReference.child("user_gender").setValue(radioValue);

                Intent intent = new Intent(IndividuaGeneralinfoActivity.this, IndividualCareerinfoActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

}