package com.example.ibrahimshaltout.test.Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.tracks.CurrentTrackProfileActivity;
import com.example.ibrahimshaltout.test.tracks.TracksFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import xyz.hasnat.sweettoast.SweetToast;

public class UserProfileActivity extends AppCompatActivity {

    Toolbar toolbarTop;
    TextView profile_university, profile_college, profile_spec, profile_grade, profile_skill, profile_interest,
            profile_mobile, profile_email, profile_name;
    ImageButton edit_profile_btn;
    String profileUniversityName;
    String profileCollegeName;
    String profileSpec;
    String profileGrade;
    String profileSkill;
    String profileInterest;
    String profileMobile;
    String profileEmail;
    String main_profileName;
    ArrayList<String> skillList = new ArrayList<>();
    ArrayList<String> interestList = new ArrayList<>();
    String currentID;

    private CircleImageView profile_settings_image;
    private TextView display_status;
    private ImageView editPhotoIcon;

    private DatabaseReference getUserDatabaseReference;
    private FirebaseAuth mAuth;
    private StorageReference mProfileImgStorageRef;
    private StorageReference thumb_image_ref;

    private ProgressDialog progressDialog;

    private final static int GALLERY_PICK_CODE = 1;
    Bitmap thumb_Bitmap = null;

    private String profile_download_url;
    private String profile_thumb_download_url;

    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        toolbarTop = findViewById(R.id.user_profile_top_bar);
        setSupportActionBar(toolbarTop);
        toolbarTop.setTitleMarginStart(80);

        // add back arrow to main_tool_bar
        toolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        getUserDatabaseReference.keepSynced(true); // for offline

        mProfileImgStorageRef = FirebaseStorage.getInstance().getReference().child("profile_image");
        thumb_image_ref = FirebaseStorage.getInstance().getReference().child("thumb_image");

        profile_settings_image = findViewById(R.id.profile_img);
        editPhotoIcon = findViewById(R.id.editPhotoIcon);

        progressDialog = new ProgressDialog(this);

        // Retrieve data from database
        getUserDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final String image = dataSnapshot.child("user_image").getValue().toString();
                String thumbImage = dataSnapshot.child("user_thumb_image").getValue().toString();

                if (!image.equals("default_image")) { // default image condition for new user
                    Picasso.get()
                            .load(image)
                            .networkPolicy(NetworkPolicy.OFFLINE) // for offline
                            .placeholder(R.drawable.default_profile_image)
                            .error(R.drawable.default_profile_image)
                            .into(profile_settings_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

/** Change profile photo from GALLERY */
        editPhotoIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallery
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(galleryIntent, GALLERY_PICK_CODE);
            }
        });

        edit_profile_btn = (ImageButton) findViewById(R.id.edit_profile_btn);
        profile_university = findViewById(R.id.profile_university);
        profile_college = findViewById(R.id.profile_college);
        profile_spec = findViewById(R.id.profile_spec);
        profile_grade = findViewById(R.id.profile_grade);
        profile_skill = findViewById(R.id.profile_skill);
        profile_interest = findViewById(R.id.profile_interest);
        profile_mobile = findViewById(R.id.profile_mobile);
        profile_email = findViewById(R.id.profile_email);
        profile_name = findViewById(R.id.profile_name);


        setProfileData();

    } // ending OnCreate


    private void setProfileData() {

        currentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference();
        db.child("Users").child(currentID).child("universityName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<String> t = new GenericTypeIndicator<String>() {
                };
                profileUniversityName = dataSnapshot.getValue(t);
                profile_university.setText(profileUniversityName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        db.child("Users").child(currentID).child("collegeName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<String> t = new GenericTypeIndicator<String>() {
                };
                profileCollegeName = dataSnapshot.getValue(t);
                profile_college.setText(profileCollegeName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        db.child("Users").child(currentID).child("depSpecialization").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<String> t = new GenericTypeIndicator<String>() {
                };
                profileSpec = dataSnapshot.getValue(t);
                profile_spec.setText(profileSpec);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        db.child("Users").child(currentID).child("grade").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<String> t = new GenericTypeIndicator<String>() {
                };
                profileGrade = dataSnapshot.getValue(t);
                profile_grade.setText(profileGrade);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        db.child("Users").child(currentID).child("skillsList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fetchSkillData(dataSnapshot);
//                GenericTypeIndicator<String> t = new GenericTypeIndicator<String>() {
//                };
//                profileSkill = dataSnapshot.getValue(t);
                if (profileSkill != null) {
                    profile_skill.setText(profileSkill);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        db.child("Users").child(currentID).child("interestsList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fetchInterestData(dataSnapshot);
                profile_interest.setText(profileInterest);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        db.child("Users").child(currentID).child("user_mobile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<String> t = new GenericTypeIndicator<String>() {
                };
                profileMobile = dataSnapshot.getValue(t);
                profile_mobile.setText(profileMobile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        db.child("Users").child(currentID).child("user_email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<String> t = new GenericTypeIndicator<String>() {
                };
                profileEmail = dataSnapshot.getValue(t);
                profile_email.setText(profileEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        db.child("Users").child(currentID).child("user_name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<String> t = new GenericTypeIndicator<String>() {
                };
                main_profileName = dataSnapshot.getValue(t);
                profile_name.setText(main_profileName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void fetchSkillData(DataSnapshot dataSnapshot) {
        GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {
        };
        ArrayList<String> yourStringArray = dataSnapshot.getValue(t);
        skillList.addAll(yourStringArray);
        profileSkill = dataSnapshot.getValue(t).toString();

    }

    private void fetchInterestData(DataSnapshot dataSnapshot) {
        GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {
        };
        ArrayList<String> yourStringArray = dataSnapshot.getValue(t);
        interestList.addAll(yourStringArray);
        profileInterest = dataSnapshot.getValue(t).toString();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /** Cropping image functionality
         * Library Link- https://github.com/ArthurHub/Android-Image-Cropper
         * */
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            // start picker to get image for cropping and then use the image in cropping activity
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                final Uri resultUri = result.getUri();

                File thumb_filePath_Uri = new File(resultUri.getPath());

                final String user_id = mAuth.getCurrentUser().getUid();

                /**
                 * compress image using compressor library
                 * link - https://github.com/zetbaitsu/Compressor
                 * */
                try {
                    thumb_Bitmap = new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(45)
                            .compressToBitmap(thumb_filePath_Uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                // firebase storage for uploading the cropped image
                final StorageReference filePath = mProfileImgStorageRef.child(user_id + ".jpg");

                UploadTask uploadTask = filePath.putFile(resultUri);
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (!task.isSuccessful()) {
                            SweetToast.error(UserProfileActivity.this, "Profile Photo Error: " + task.getException().getMessage());
                            //throw task.getException();
                        }
                        profile_download_url = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            //Toasty.info(SettingsActivity.this, "Your profile photo is uploaded successfully.", Toast.LENGTH_SHORT).show();
                            // retrieve the stored image as profile photo
                            profile_download_url = task.getResult().toString();
                            Log.e("tag", "profile url: " + profile_download_url);

                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                            thumb_Bitmap.compress(Bitmap.CompressFormat.JPEG, 45, outputStream);
                            final byte[] thumb_byte = outputStream.toByteArray();

                            // firebase storage for uploading the cropped and compressed image
                            final StorageReference thumb_filePath = thumb_image_ref.child(user_id + "jpg");
                            UploadTask thumb_uploadTask = thumb_filePath.putBytes(thumb_byte);

                            Task<Uri> thumbUriTask = thumb_uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (!task.isSuccessful()) {
                                        SweetToast.error(UserProfileActivity.this, "Thumb Image Error: " + task.getException().getMessage());
                                    }
                                    profile_thumb_download_url = thumb_filePath.getDownloadUrl().toString();
                                    return thumb_filePath.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    profile_thumb_download_url = task.getResult().toString();
                                    Log.e("tag", "thumb url: " + profile_thumb_download_url);
                                    if (task.isSuccessful()) {
                                        Log.e("tag", "thumb profile updated");

                                        HashMap<String, Object> update_user_data = new HashMap<>();
                                        update_user_data.put("user_image", profile_download_url);
                                        update_user_data.put("user_thumb_image", profile_thumb_download_url);

                                        getUserDatabaseReference.updateChildren(new HashMap<String, Object>(update_user_data))
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.e("tag", "thumb profile updated");
                                                        progressDialog.dismiss();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e("tag", "for thumb profile: " + e.getMessage());
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }

                                }
                            });

                        }

                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                //Exception error = result.getError();
                // handling more event
                SweetToast.info(UserProfileActivity.this, "Image cropping failed.");
            }
        }

    }
}

