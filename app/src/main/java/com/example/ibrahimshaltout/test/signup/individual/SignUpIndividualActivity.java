package com.example.ibrahimshaltout.test.signup.individual;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ibrahimshaltout.test.MainActivity;
import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.IndividualDataClass;
import com.example.ibrahimshaltout.test.login.LoginActivity;
import com.example.ibrahimshaltout.test.login.ResetPasswordActivity;
import com.example.ibrahimshaltout.test.signup.VerifyEmail;
import com.example.ibrahimshaltout.test.signup.YourLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.iid.FirebaseInstanceId;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import xyz.hasnat.sweettoast.SweetToast;

public class SignUpIndividualActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private Context myContext = SignUpIndividualActivity.this;

    private EditText
            inputFullName,
            inputEmail,
            inputPassword,
            inputPhoneNumber;

    private Button btnSignIn, btnSignUp;

    //Firebase
    private FirebaseAuth auth;
    private FirebaseUser user;

    DatabaseReference userDatabaseReference;

    private ProgressDialog progressDialog;

//    private String[] skillsList = {""};
//    private String[] InterestsList = {""};
//    private String[] experienceList = {""};
//    private List inputSkillsList = new ArrayList<String>(Arrays.asList(skillsList));
//    private List inputInterestsList = new ArrayList<String>(Arrays.asList(InterestsList));
//    private List inputExperienceList = new ArrayList<String>(Arrays.asList(experienceList));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_individual);
        Log.d(TAG, "on Create : started");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        inputFullName = (EditText) findViewById(R.id.full_name);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        inputPassword = (EditText) findViewById(R.id.password);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);

//        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user_name = inputFullName.getText().toString().trim();
                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                final String phoneNumber = inputPhoneNumber.getText().toString().trim();
                final String verified = "false";

                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("You need to enter an Email");
                    return;
                } else {
                    inputEmail.setError(null);
                }
                if (TextUtils.isEmpty(password)) {
                    inputPassword.setError("You need to enter a Password");
                    return;
                } else {
                    inputPassword.setError(null);
                }
//
//                if (TextUtils.isEmpty(fullName)) {
//                    inputFullName.setError("You need to enter first name ");
//                    return;
//                } else {
//                    inputFullName.setError(null);
//                }
//                if (TextUtils.isEmpty(phoneNumber)) {
//                    inputPhoneNumber.setError("You need to enter phone number");
//                    return;
//                } else {
//                    inputPhoneNumber.setError(null);
//                }
//                if (password.length() < 6) {
//                    inputPassword.setError("Password is less than 6 characters");
//                    return;
//                } else {
//                    inputPassword.setError(null);
//                }

//                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

//                                Toast.makeText(SignUpIndividualActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                if (!task.isSuccessful()) {

                                    Toast.makeText(SignUpIndividualActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    String deviceToken = FirebaseInstanceId.getInstance().getToken();

                                    // get and link storage
                                    String current_userID = auth.getCurrentUser().getUid();

                                    userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(current_userID);

                                    userDatabaseReference.child("user_name").setValue(user_name);
                                    userDatabaseReference.child("verified").setValue("false");
                                    userDatabaseReference.child("search_name").setValue(user_name.toLowerCase());
                                    userDatabaseReference.child("user_mobile").setValue(phoneNumber);
                                    userDatabaseReference.child("user_email").setValue(email);
                                    userDatabaseReference.child("user_nickname").setValue("");
                                    userDatabaseReference.child("user_profession").setValue("");
                                    userDatabaseReference.child("created_at").setValue(ServerValue.TIMESTAMP);
                                    userDatabaseReference.child("user_status").setValue("Hi, I'm new uMe user");
                                    userDatabaseReference.child("user_image").setValue("default_image"); // Original image
                                    userDatabaseReference.child("device_token").setValue(deviceToken);
                                    userDatabaseReference.child("user_thumb_image").setValue("default_image")
//                                    IndividualDataClass individualDataClass = new IndividualDataClass(user_name, email, phoneNumber, verified);
//                                    individualDataClass.setSearch_name(user_name.toLowerCase());
////                                    individualDataClass.setCreated_at((Time) ServerValue.TIMESTAMP);
////                                    individualDataClass.setSchoolType("null");
////                                    individualDataClass.setUniversityName("null");
////                                    individualDataClass.setCollegeName("null");
////                                    individualDataClass.setDepSpecialization("null");
////                                    individualDataClass.setGrade("null");
////                                    individualDataClass.setDiplomaField("null");
////                                    individualDataClass.setMasterField("null");
////                                    individualDataClass.setDoctorateField("null");
////                                    individualDataClass.setInterestsList(inputInterestsList);
////                                    individualDataClass.setSkillsList(inputSkillsList);
////                                    individualDataClass.setExperience(inputExperienceList);
////                                    individualDataClass.setCompanyName("null");
////                                    individualDataClass.setJobTitle("null");
////                                    individualDataClass.setDepartment("null");
//                                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
//                                            .getCurrentUser().getUid())
//                                            .setValue(individualDataClass)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // SENDING VERIFICATION EMAIL TO THE REGISTERED USER'S EMAIL
                                                        user = auth.getCurrentUser();
                                                        if (user != null) {
                                                            user.sendEmailVerification()
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                registerSuccessPopUp();

                                                                                // LAUNCH activity after certain time period
                                                                                new Timer().schedule(new TimerTask() {
                                                                                    public void run() {
                                                                                        SignUpIndividualActivity.this.runOnUiThread(new Runnable() {
                                                                                            public void run() {
//                                                                                                auth.signOut();

                                                                                                Intent mainIntent = new Intent(SignUpIndividualActivity.this, YourLocation.class);
                                                                                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                                startActivity(mainIntent);
                                                                                                finish();

                                                                                                SweetToast.info(SignUpIndividualActivity.this, "Please check your email & verify.");

                                                                                            }
                                                                                        });
                                                                                    }
                                                                                }, 8000);
                                                                            } else {
                                                                                auth.signOut();
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                        Toast.makeText(SignUpIndividualActivity.this, "registration_success", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
//                                    Intent intent = new Intent(SignUpIndividualActivity.this, VerifyEmail.class);
//                                    startActivity(intent);
//                                    finish();
                                }
                                progressDialog.dismiss();

                            }
                        });
                //config progressbar
                progressDialog.setTitle("Creating new account");
                progressDialog.setMessage("Please wait a moment....");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
            }
        });

        progressDialog = new ProgressDialog(myContext);
    }

    private void registerSuccessPopUp() {
        // Custom Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpIndividualActivity.this);
        View view = LayoutInflater.from(SignUpIndividualActivity.this).inflate(R.layout.register_success_popup, null);

        //ImageButton imageButton = view.findViewById(R.id.successIcon);
        //imageButton.setImageResource(R.drawable.logout);
        builder.setCancelable(false);
        builder.setView(view);
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        progressBar.setVisibility(View.GONE);
    }
//    public void sendVerificationEmail() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.sendEmailVerification()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            // email sent
//                            // after email is sent just logout the user and finish this activity
////                            FirebaseAuth.getInstance().signOut();
////                                startActivity(new Intent(verifyEmail.this, LoginActivity.class));
//                            finish();
//                        } else {
//                            // email not sent, so display message and restart the activity or do whatever you wish to do
//                            //restart this activity
//                            overridePendingTransition(0, 0);
//                            finish();
//                            overridePendingTransition(0, 0);
//                            startActivity(getIntent());
//
//                        }
//                    }
//                });
//    }
}
