package com.example.ibrahimshaltout.test.signup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.IndividualDataClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import xyz.hasnat.sweettoast.SweetToast;

public class VerifyEmail extends AppCompatActivity {

    private Button btnResendEmail, btnSkipVerify, btnDoneVerify;

    private EditText Email;

    private DatabaseReference userDatabaseReference;



    //Firebase Auth
    private FirebaseAuth auth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        IndividualDataClass individualDataClass = new IndividualDataClass();

        btnResendEmail = (Button) findViewById(R.id.Resend_Email_button);
        btnSkipVerify = (Button) findViewById(R.id.Skip_Email_button);
        btnDoneVerify = (Button) findViewById(R.id.email_verified_button);

        Email = (EditText) findViewById(R.id.email_verify);
        String a = auth.getCurrentUser().getEmail();
        Email.setText(a);

        Toast.makeText(VerifyEmail.this, a, Toast.LENGTH_LONG).show();

        btnDoneVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkVerifiedEmail();
            }
        });

        btnResendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationEmail();
            }
        });

        btnSkipVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetToast.info(VerifyEmail.this, "You Should Verify Your Account To Login");
                Intent intent = new Intent(VerifyEmail.this, YourLocation.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkVerifiedEmail() {
        user = auth.getCurrentUser();
        boolean isVerified = false;
        if (user != null) {
            isVerified = user.isEmailVerified();
        }
        if (isVerified) {
            String UID = auth.getCurrentUser().getUid();
            userDatabaseReference.child(UID).child("verified").setValue("true");

            Intent intent = new Intent(VerifyEmail.this, YourLocation.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            SweetToast.info(VerifyEmail.this, "Email is not verified. Please verify first");
        }
    }

//    public void checkIfEmailVerified()
//    {
//        recreate();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.isEmailVerified();
//
//        if (user.isEmailVerified())
//        {
//            // user is verified, so you can finish this activity or send user to activity which you want.
////            finish();
//            Toast.makeText(VerifyEmail.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
////            Intent intent = new Intent(VerifyEmail.this, YourLocation.class);
////            startActivity(intent);
////            finish();
//        }
//        else
//        {
//            Toast.makeText(VerifyEmail.this, "Your Email is not verified", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            Toast.makeText(VerifyEmail.this, "Your Email is sent", Toast.LENGTH_SHORT).show();
//
                        } else {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            //restart this activity
                            Toast.makeText(VerifyEmail.this, "Your Email is sent", Toast.LENGTH_SHORT).show();
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }


}