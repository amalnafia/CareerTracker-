package com.example.ibrahimshaltout.test.signup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.signup.individual.SignUpIndividualActivity;

public class SignupAsActivity extends AppCompatActivity {

    private Button individual, organization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_as);

        individual = (Button)findViewById(R.id.individual);
        organization = (Button)findViewById(R.id.organization);

        individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupAsActivity.this,SignUpIndividualActivity.class));
                finish();
            }
        });
        organization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(SignupAsActivity.this,SignupOrgActivity.class));
                finish();
            }
        });
    }
}
