package com.example.ibrahimshaltout.test.newsfeed.post;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;

import com.example.ibrahimshaltout.test.MainActivity;
import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.FieldDataClass;
import com.example.ibrahimshaltout.test.dataclass.IndividualDataClass;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;
import xyz.hasnat.sweettoast.SweetToast;

public class AddNewPostActivity extends AppCompatActivity {

    private Toolbar toolbarTop;
    private EditText inputPost;

    MultiAutoCompleteTextView add_hashTags;
    ArrayList hashtagArray = new ArrayList<>();


    private Button btnAddPost;
    Bitmap thumb_Bitmap = null;
    private String profile_download_url;
    private StorageReference thumb_image_ref;
    private String profile_thumb_download_url;
    private DatabaseReference getUserDatabaseReference;

    private final static int GALLERY_PICK_CODE = 1;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private StorageReference mProfileImgStorageRef;
    private Button addPostImage;
    private ImageView new_post_image;

    MaterialBetterSpinner materialBetterSpinner;
    private int spinnerItemSelcected;

    private String[] shareForList = {"Anyone", "Same TrackDataClass Participants"};
    ArrayAdapter<String> stringArrayAdapter;

    MultiAutoCompleteTextView hashtage_Post;

    String[] hashtageArray = {"Dwight D. Eisenhower", "John F. Kennedy", "Lyndon B. Johnson", "Richard Nixon", "Gerald Ford", "Jimmy Carter",
            "Ronald Reagan", "George H. W. Bush", "Bill Clinton", "George W. Bush", "Barack Obama"};

    final PostDataClass postDataClass = new PostDataClass();

    //Firebase
    DatabaseReference db;
    public String post_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();

        db = FirebaseDatabase.getInstance().getReference();
        post_id = db.push().getKey();


        ArrayAdapter<String> hashtagAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, hashtagArray);
        add_hashTags = (MultiAutoCompleteTextView) findViewById(R.id.add_hashTags);
        add_hashTags.setThreshold(1);
        add_hashTags.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        add_hashTags.setAdapter(hashtagAdapter);
        add_hashTags.setVisibility(View.GONE);

        thumb_image_ref = FirebaseStorage.getInstance().getReference().child("thumb_image_Post");
        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(post_id);
        getUserDatabaseReference.keepSynced(true); // for offline
        mProfileImgStorageRef = FirebaseStorage.getInstance().getReference().child("post_image");

//        // Retrieve data from database
//        getUserDatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                final String image = dataSnapshot.child("user_image_Post").getValue().toString();
//                String thumbImage = dataSnapshot.child("user_thumb_image_Post").getValue().toString();
//
//                if (!image.equals("default_image")) { // default image condition for new user
//                    Picasso.get()
//                            .load(image)
//                            .networkPolicy(NetworkPolicy.OFFLINE) // for offline
//                            .placeholder(R.drawable.default_profile_image)
//                            .error(R.drawable.default_profile_image)
//                            .into(new_post_image);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String individualId = currentFirebaseUser.getUid();


        toolbarTop = findViewById(R.id.add_new_Post_top_bar);
        toolbarTop.setTitle("New Post");
        toolbarTop.setTitleMarginStart(80);
        setSupportActionBar(toolbarTop);


        db = FirebaseDatabase.getInstance().getReference();
        // add back arrow to main_tool_bar
        toolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                FirebaseDatabase.getInstance().getReference().child("Posts").child(post_id).removeValue();
                finish();

            }
        });


        btnAddPost = (Button) findViewById(R.id.write_new_post_Button_add);
        inputPost = (EditText) findViewById(R.id.write_new_post);

        addPostImage = (Button) findViewById(R.id.new_post_image_add);
        new_post_image = (ImageView) findViewById(R.id.new_post_image);


        stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, shareForList);
        materialBetterSpinner = (MaterialBetterSpinner) findViewById(R.id.share_For_List);
        materialBetterSpinner.setAdapter(stringArrayAdapter);
        materialBetterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spinnerItemSelcected = position;
                if (spinnerItemSelcected == 0) {

                    add_hashTags.setVisibility(View.VISIBLE);
                } else if (spinnerItemSelcected == 1) {

                    add_hashTags.setVisibility(View.GONE);
                }

            }
        });

        btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String arrayHashtag = add_hashTags.getText().toString();
                final String arrayHashtagSp[] = arrayHashtag.split(",");
                final List trackHashtagList = new ArrayList<String>(Arrays.asList(arrayHashtagSp));

                FirebaseDatabase.getInstance().getReference().child("Users").child(individualId)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                final String post = inputPost.getText().toString().trim();
                                DateFormat dateFormat = new SimpleDateFormat(" h:mm aa  dd/MM/YYYY ");
                                Date date = new Date(System.currentTimeMillis());
                                String strDate = dateFormat.format(date);

                                IndividualDataClass individualDataClass = dataSnapshot.getValue(IndividualDataClass.class);
                                postDataClass.setPostName(individualDataClass.user_name);
                                postDataClass.setUser_Profile_Photo(individualDataClass.getUser_image());

                                if (spinnerItemSelcected == 0) {

                                    postDataClass.setShare_for("Anyone");
                                    postDataClass.setHashTage(trackHashtagList);
                                    FirebaseDatabase.getInstance().getReference("Posts").child(post_id).child("HashTage").setValue(trackHashtagList);


                                } else if (spinnerItemSelcected == 1) {

                                    postDataClass.setShare_for("Same TrackDataClass Participants");
                                    postDataClass.setHashTage(individualDataClass.interestsList);
                                    FirebaseDatabase.getInstance().getReference("Posts").child(post_id).child("HashTage").setValue(postDataClass.getHashTage());

                                }

                                postDataClass.setHashTage(individualDataClass.interestsList);
                                postDataClass.setPostData(post);
                                postDataClass.setTimeAndDate(strDate);
                                postDataClass.setUser_ID(individualId);
                                postDataClass.setNumber_of_likes(0);
                                postDataClass.setPost_ID(post_id);

                                FirebaseDatabase.getInstance().getReference("Posts").child(post_id).child("postName").setValue(postDataClass.getPostName());
                                FirebaseDatabase.getInstance().getReference("Posts").child(post_id).child("postData").setValue(postDataClass.getPostData());
                                FirebaseDatabase.getInstance().getReference("Posts").child(post_id).child("timeAndDate").setValue(postDataClass.getTimeAndDate());
                                FirebaseDatabase.getInstance().getReference("Posts").child(post_id).child("number_of_likes").setValue(postDataClass.getNumber_of_likes());
                                FirebaseDatabase.getInstance().getReference("Posts").child(post_id).child("post_ID").setValue(postDataClass.getPost_ID());
                                FirebaseDatabase.getInstance().getReference("Posts").child(post_id).child("user_ID").setValue(postDataClass.getUser_ID());
                                FirebaseDatabase.getInstance().getReference("Posts").child(post_id).child("share_for").setValue(postDataClass.getShare_for());
                                FirebaseDatabase.getInstance().getReference("Posts").child(post_id).child("user_Profile_Photo").setValue(postDataClass.getUser_Profile_Photo());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                Intent i = new Intent(AddNewPostActivity.this, MainActivity.class);
                startActivity(i);

            }

        });


        /** Change profile photo from GALLERY */
        addPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallery
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(galleryIntent, GALLERY_PICK_CODE);
            }
        });

        db.child("Fields").child("Fields_List").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fetchFieldName(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void fetchFieldName(DataSnapshot dataSnapshot) {
        FieldDataClass fieldDataClass = null;
        Iterable<DataSnapshot> list = dataSnapshot.getChildren();
        for (DataSnapshot x : list) {
            fieldDataClass = x.getValue(FieldDataClass.class);
            hashtagArray.add(fieldDataClass.getField_Name());

        }
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
                    .setAspectRatio(4, 3)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                final Uri resultUri = result.getUri();

                File thumb_filePath_Uri = new File(resultUri.getPath());


                /**
                 * compress image using compressor library
                 * link - https://github.com/zetbaitsu/Compressor
                 * */
                try {
                    thumb_Bitmap = new Compressor(this)
                            .setMaxWidth(390)
                            .setMaxHeight(250)
                            .setQuality(45)
                            .compressToBitmap(thumb_filePath_Uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                // firebase storage for uploading the cropped image
                final StorageReference filePath = mProfileImgStorageRef.child(post_id + ".jpg");

                UploadTask uploadTask = filePath.putFile(resultUri);
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (!task.isSuccessful()) {
                            SweetToast.error(AddNewPostActivity.this, "Post Photo Error: " + task.getException().getMessage());
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
                            final StorageReference thumb_filePath = thumb_image_ref.child(post_id + "jpg");
                            UploadTask thumb_uploadTask = thumb_filePath.putBytes(thumb_byte);

                            Task<Uri> thumbUriTask = thumb_uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (!task.isSuccessful()) {
                                        SweetToast.error(AddNewPostActivity.this, "Thumb Image Error: " + task.getException().getMessage());
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
                                        Log.e("tag", "thumb Post updated");

                                        HashMap<String, Object> update_user_data = new HashMap<>();
                                        update_user_data.put("user_image_Post", profile_download_url);
                                        update_user_data.put("user_thumb_image", profile_thumb_download_url);


                                        Picasso.get()
                                                .load(profile_download_url)
                                                .into(new_post_image);


                                        getUserDatabaseReference.updateChildren(new HashMap<String, Object>(update_user_data))
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.e("tag", "thumb Post updated");
                                                        progressDialog.dismiss();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e("tag", "for thumb Post: " + e.getMessage());
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
                SweetToast.info(AddNewPostActivity.this, "Image cropping failed.");
            }
        }

    }


}
