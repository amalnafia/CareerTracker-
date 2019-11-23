package com.example.ibrahimshaltout.test.signup;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.ibrahimshaltout.test.MainActivity;
import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.CountriesListClass;
import com.example.ibrahimshaltout.test.dataclass.IndividualDataClass;
import com.example.ibrahimshaltout.test.signup.individual.IndividuaGeneralinfoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;


public class YourLocation extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String user_UId;
    private DatabaseReference userDatabaseReference;
    String location;


    AutoCompleteTextView locationCountriesSpinner,locationSpinnerCity;
    ArrayAdapter<String> arrayAdapterCountries,arrayAdapterCities;
    private int spinnerItemSelcectedCountries;
    private Button btnCountinueLocation;
    private FirebaseAuth auth;


    CountriesListClass countriesListClass = new CountriesListClass();
    List countries = countriesListClass.Countries();
    List cities = Arrays.asList(new String[]{"city1"});

    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_location);


        // check if GPS enabled
//        GPSTracker gpsTracker = new GPSTracker(this);
//
//        if (gpsTracker.getIsGPSTrackingEnabled()) {
//            String stringLatitude = String.valueOf(gpsTracker.latitude);
//            textview = (TextView) findViewById(R.id.fieldLatitude);
//            textview.setText(stringLatitude);
//
//            String stringLongitude = String.valueOf(gpsTracker.longitude);
//            textview = (TextView) findViewById(R.id.fieldLongitude);
//            textview.setText(stringLongitude);
//
//            String country = gpsTracker.getCountryName(this);
//            textview = (TextView) findViewById(R.id.fieldCountry);
//            textview.setText(country);
//
//            String city = gpsTracker.getLocality(this);
//            textview = (TextView) findViewById(R.id.fieldCity);
//            textview.setText(city);
//
//            String postalCode = gpsTracker.getPostalCode(this);
//            textview = (TextView) findViewById(R.id.fieldPostalCode);
//            textview.setText(postalCode);
//
//            String addressLine = gpsTracker.getAddressLine(this);
//            textview = (TextView) findViewById(R.id.fieldAddressLine);
//            textview.setText(addressLine);
//        } else {
//            // can't get location
//            // GPS or Network is not enabled
//            // Ask user to enable GPS/network in settings
//            gpsTracker.showSettingsAlert();
//        }
//

        mAuth = FirebaseAuth.getInstance();
        user_UId = mAuth.getCurrentUser().getUid();
        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        userDatabaseReference.child(user_UId).child("location").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<String> t = new GenericTypeIndicator<String>() {
                };
                location = dataSnapshot.getValue(t);

                if (location != null) {
                    startActivity(new Intent(YourLocation.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        auth = FirebaseAuth.getInstance();
        btnCountinueLocation = (Button) findViewById(R.id.countinue_location_button);

        arrayAdapterCities = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cities);
        locationSpinnerCity = (AutoCompleteTextView) findViewById(R.id.locationSpinnerCity);
        locationSpinnerCity.setAdapter(arrayAdapterCities);

        arrayAdapterCountries = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, countries);
        locationCountriesSpinner = (AutoCompleteTextView) findViewById(R.id.locationSpinnerCountry);
        locationCountriesSpinner.setAdapter(arrayAdapterCountries);

        locationCountriesSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinnerItemSelcectedCountries = position;
                String x = CountryIs();
            }
        });

        btnCountinueLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IndividualDataClass individualDataClass=new IndividualDataClass();
                String individualId = auth.getUid();
                String country = locationCountriesSpinner.getText().toString();
                String city = locationSpinnerCity.getText().toString();

                individualDataClass.setUserCountry(country);
                individualDataClass.setUserCity(city);

                FirebaseDatabase.getInstance().getReference("Users").child(individualId).child("location").child("userCountry")
                        .setValue(individualDataClass.getUserCountry());
                FirebaseDatabase.getInstance().getReference("Users").child(individualId).child("location").child("userCity")
                        .setValue(individualDataClass.getUserCity());
                Intent intent = new Intent(YourLocation.this, IndividuaGeneralinfoActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.varna_lab_geo_locations, menu);
        return true;
    }

    private String CountryIs() {
        String theLevel = "null";
        for (int i = 0; i < countries.size(); i++) {
            if (spinnerItemSelcectedCountries == i) {
                theLevel = (String) countries.get(i);
            }
        }
        return (theLevel);
    }

}
