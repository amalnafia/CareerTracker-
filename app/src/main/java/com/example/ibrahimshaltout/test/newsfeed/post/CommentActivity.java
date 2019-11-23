package com.example.ibrahimshaltout.test.newsfeed.post;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.ibrahimshaltout.test.R;
import com.example.ibrahimshaltout.test.dataclass.IndividualDataClass;
import com.example.ibrahimshaltout.test.newsfeed.post.PostDataClass;
import com.example.ibrahimshaltout.test.notification.Notification;
import com.example.ibrahimshaltout.test.notification.NotificationAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.ibrahimshaltout.test.newsfeed.post.CommentAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends AppCompatActivity {


    final PostDataClass postDataClass = new PostDataClass();
    CommentAdapter commentAdapter;
    ArrayList<PostDataClass> listOfComment = new ArrayList<>();

    String userName;
    String userProfilePic;

    FirebaseStorage storage;
    FirebaseDatabase database;
    StorageReference storageReference;
    DatabaseReference db;

    TextView Profile_name_show, txtStatusMsgPost_show, Number_Of_Likes_show, timestamp_show, post_hashTags_post;
    EditText write_comment;
    ImageView add_comment, post_image_show;
    CircleImageView profile_pic_show;
    RecyclerView commentsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String decs = intent.getStringExtra("decs");
        String timeAndDate = intent.getStringExtra("timeAndDate");
        String postUserProfile = intent.getStringExtra("postUserProfile");
        String numOfLikes = intent.getStringExtra("numOfLikes");

        String imageView = intent.getStringExtra("imageView");
        String post_hashTags = intent.getStringExtra("post_hashTags");

        final String postID = intent.getStringExtra("postID");


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String individualId = currentFirebaseUser.getUid();
//        final String individualId = FirebaseAuth.getInstance().getCurrentUser().getUid();



        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db = FirebaseDatabase.getInstance().getReference();

        final String key = database.getReference("Comments").push().getKey();


        Profile_name_show = findViewById(R.id.Profile_name_show);
        post_image_show = findViewById(R.id.post_image_show);
        post_hashTags_post = findViewById(R.id.post_hashTags_post);
        profile_pic_show = findViewById(R.id.profile_pic_show);
        txtStatusMsgPost_show = findViewById(R.id.txtStatusMsgPost_show);
        timestamp_show = findViewById(R.id.timestamp_show);
        Number_Of_Likes_show = findViewById(R.id.Number_Of_Likes_show);
        add_comment = findViewById(R.id.add_comment);
        write_comment = findViewById(R.id.write_comment);
        commentsListView = findViewById(R.id.commentsListView);

        Profile_name_show.setText(name);
        txtStatusMsgPost_show.setText(decs);
        timestamp_show.setText(timeAndDate);
        Number_Of_Likes_show.setText(numOfLikes);

        post_hashTags_post.setText(post_hashTags);


        if (postUserProfile != null) {
            Picasso.get()
                    .load(postUserProfile)
                    .into(profile_pic_show);
        }

        if (imageView != null) {
            Picasso.get()
                    .load(imageView)
                    .into(post_image_show);
        }

        commentAdapter = new CommentAdapter(this, listOfComment);

        RecyclerView.LayoutManager trackLayoutManager = new LinearLayoutManager(this);
        commentsListView.setLayoutManager(trackLayoutManager);
        commentsListView.setNestedScrollingEnabled(true);
        commentsListView.setHasFixedSize(true);
        commentsListView.setAdapter(commentAdapter);

        db.child("Posts").child(postID).child("Comments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                parseCommentsData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        db.child("Users").child(individualId).child("user_name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<String> t = new GenericTypeIndicator<String>() {
                };
                userName = dataSnapshot.getValue(t);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        db.child("Users").child(individualId).child("user_image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<String> t = new GenericTypeIndicator<String>() {
                };
                userProfilePic = dataSnapshot.getValue(t);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String writeComment = write_comment.getText().toString().trim();
                postDataClass.setCommentHead(writeComment);
                postDataClass.setWriterID(individualId);
                postDataClass.setWriterName(userName);
                postDataClass.setUser_Profile_Photo(userProfilePic);

                FirebaseDatabase.getInstance().getReference("Posts").child(postID).child("Comments").
                        child(key).child("writerID").setValue(postDataClass.getWriterID());
                FirebaseDatabase.getInstance().getReference("Posts").child(postID).child("Comments").
                        child(key).child("commentHead").setValue(postDataClass.getCommentHead());
                FirebaseDatabase.getInstance().getReference("Posts").child(postID).child("Comments").
                        child(key).child("writerName").setValue(postDataClass.getWriterName());
                FirebaseDatabase.getInstance().getReference("Posts").child(postID).child("Comments").
                        child(key).child("user_Profile_Photo").setValue(postDataClass.getUser_Profile_Photo());

                startActivity(getIntent());

            }
        });
    }

    private void parseCommentsData(DataSnapshot dataSnapshot) {
        PostDataClass postDataClass1 = null;
        Iterable<DataSnapshot> list = dataSnapshot.getChildren();
        for (DataSnapshot x : list) {
            postDataClass1 = x.getValue(PostDataClass.class);
            listOfComment.add(postDataClass1);
        }
        commentAdapter.notifyDataSetChanged();
    }

}
