package com.example.ibrahimshaltout.test.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ibrahimshaltout.test.IntroActivity;
import com.example.ibrahimshaltout.test.MainActivity;
import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.signup.SignupAsActivity;
import com.example.ibrahimshaltout.test.signup.VerifyEmail;
import com.example.ibrahimshaltout.test.signup.YourLocation;
import com.example.ibrahimshaltout.test.signup.individual.IndividuaGeneralinfoActivity;
import com.example.ibrahimshaltout.test.signup.individual.IndividualCareerinfoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import xyz.hasnat.sweettoast.SweetToast;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText inputEmail, inputPassword;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private TextInputLayout inputPasswordLayout;

    //Firebase Auth
    private FirebaseAuth auth;
    private FirebaseUser user;

    private DatabaseReference userDatabaseReference;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        progressDialog = new ProgressDialog(this);

        inputPasswordLayout = (TextInputLayout) findViewById(R.id.textinputlayoutPassword);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupAsActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("You need to Enter an Email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    inputPasswordLayout.setPasswordVisibilityToggleEnabled(false);
                    inputPassword.setError("You need to Enter a Password");
                    return;
                }

                inputPasswordLayout.setPasswordVisibilityToggleEnabled(true);

                progressBar.setVisibility(View.VISIBLE);

                //progress bar
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    String userUID = auth.getCurrentUser().getUid();
                                    String userDeiceToken = FirebaseInstanceId.getInstance().getToken();
                                    userDatabaseReference.child(userUID).child("device_token").setValue(userDeiceToken)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    checkIfEmailVerified();
                                                }
                                            });
                                }

                                progressBar.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            }
                        });
            }
        });
    }

    /**
     * checking email verified or NOT
     */
    public void checkIfEmailVerified() {
        user = auth.getCurrentUser();
        boolean isVerified = false;
        if (user != null) {
            isVerified = user.isEmailVerified();
        }
        if (isVerified) {
            String UID = auth.getCurrentUser().getUid();
            userDatabaseReference.child(UID).child("verified").setValue("true");

            // saving in local cache through Shared Preferences

                startActivity(new Intent(LoginActivity.this, IntroActivity.class));
                finish();

        } else {
            sendVerificationEmail();
            SweetToast.info(LoginActivity.this, "Email is not verified. Please verify first");
            auth.signOut();
        }
//         user = FirebaseAuth.getInstance().getCurrentUser();
//
//
//        boolean isVerified = false;
//
//        if (user != null) {
//            isVerified = user.isEmailVerified();
//        }
//        if (isVerified)
//        {
//
//            String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            userDatabaseReference.child(UID).child("verified").setValue("true");
////
////            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////            startActivity(intent);
////            finish();
////
////            // user is verified, so you can finish this activity or send user to activity which you want.
//
//            Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        else
//        {
//            // email is not verified, so just prompt the message to the user and restart this activity.
//            // NOTE: don't forget to log out the user.
//            Toast.makeText(LoginActivity.this, "Your Email is not verified", Toast.LENGTH_SHORT).show();
////            Intent intent = new Intent(LoginActivity.this, verifyEmail.class);
////            startActivity(intent);
//            finish();
//            //restart this activity

//        }
    }

    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            Toast.makeText(LoginActivity.this, "Your Email is sent", Toast.LENGTH_SHORT).show();
//
                        } else {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            //restart this activity
                            Toast.makeText(LoginActivity.this, "Your Email is sent", Toast.LENGTH_SHORT).show();
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }
}
