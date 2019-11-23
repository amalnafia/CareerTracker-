package com.example.ibrahimshaltout.test.newsfeed.post;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ibrahimshaltout.test.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.NewsFeedPostViewHolder> {

    private Context trackContext;
    private ArrayList<PostDataClass> posts;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    //    final String key = database.getReference("Posts").getKey();
    //    final String key = FirebaseDatabase.getInstance().getReference("shared_posts").push().getKey();
    DatabaseReference db;
    StringBuilder builder = new StringBuilder();

    public PostAdapter(Context mContext, ArrayList<PostDataClass> posts) {
        this.trackContext = mContext;
        this.posts = posts;
    }

    @NonNull
    @Override
    public NewsFeedPostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(trackContext)
                .inflate(R.layout.post_item, viewGroup, false);

        return new NewsFeedPostViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final NewsFeedPostViewHolder newsFeedViewHolder, final int position) {

        final PostDataClass item = posts.get(position);


        if (item.getUser_image_Post() != null) {
            Picasso.get()
                    .load(item.getUser_image_Post())
                    .into(newsFeedViewHolder.imageView);
        }

        if (item.getUser_Profile_Photo() != null) {
            Picasso.get()
                    .load(item.getUser_Profile_Photo())
                    .into(newsFeedViewHolder.profilePic);
        }


        newsFeedViewHolder.desc.setText(item.getPostData());
        if (item.getPostName() != null) {
            newsFeedViewHolder.profileName.setText(item.getPostName());
        }


        if (item.getHashTage() != null) {

            for (String details : item.getHashTage()) {
                builder.append("#" + details + " ");
            }
            newsFeedViewHolder.post_hashTags.setText(builder.toString());
        }

        if (item.getPostName() != null) {
            newsFeedViewHolder.profileName.setText(item.getPostName());
        }
        if (item.getTimeAndDate() != null) {
            newsFeedViewHolder.dateAndTime.setText(item.getTimeAndDate());
        }

        newsFeedViewHolder.number_of_like.setText(item.getNumber_of_likes() + "");
        newsFeedViewHolder.thumbs_up.setOnClickListener(new View.OnClickListener() {
            int mTrashFlag = 0;

            @Override

            public void onClick(View v) {

                if (mTrashFlag == 0) {
                    mTrashFlag = 1;

                    newsFeedViewHolder.thumbs_up.setBackgroundDrawable(trackContext.getDrawable(R.drawable.like_icon_reaction));
                    posts.get(position).setNumber_of_likes(posts.get(position).getNumber_of_likes() + 1);
                    newsFeedViewHolder.number_of_like.setText(String.valueOf(posts.get(position).getNumber_of_likes()));
                    int n = posts.get(position).getNumber_of_likes();
                    FirebaseDatabase.getInstance().getReference("Posts").child(item.getPost_ID())
                            .child("number_of_likes")
                            .setValue(n);
                } else if (mTrashFlag == 1) {
                    mTrashFlag = 0;
                    newsFeedViewHolder.thumbs_up.setBackgroundDrawable(trackContext.getDrawable(R.drawable.like_icon));
                    posts.get(position).setNumber_of_likes(posts.get(position).getNumber_of_likes() - 1);
                    newsFeedViewHolder.number_of_like.setText(String.valueOf(posts.get(position).getNumber_of_likes()));
                    int n = posts.get(position).getNumber_of_likes();
                    FirebaseDatabase.getInstance().getReference("Posts").child(item.getPost_ID())
                            .child("number_of_likes")
                            .setValue(n);
                }
            }
        });
//        newsFeedViewHolder.number_of_like.setText(String.valueOf(posts.get(position).getCount()));

//        if (item.getImageURL() != null) {
//            newsFeedViewHolder.imageView.setImageURI(item.getPostPhotoUrl());
//        PostDataClass UploadInfo = ImagePostList.get(position);
//        newsFeedViewHolder.imageNameTextView.setText(UploadInfo.getImageName());}
        //Loading image from Glide library.


        newsFeedViewHolder.post_comment_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(trackContext, CommentActivity.class);
                intent.putExtra("name", item.getPostName());
                intent.putExtra("decs", item.getPostData());
                intent.putExtra("timeAndDate", item.getTimeAndDate());
                intent.putExtra("postID", item.getPost_ID());
                intent.putExtra("postUserProfile", item.getUser_Profile_Photo());
                intent.putExtra("numOfLikes", item.getNumber_of_likes() + "");
                intent.putExtra("imageView", item.getUser_image_Post());
                intent.putExtra("post_hashTags", builder.toString());
                trackContext.startActivity(intent);
            }
        });
        newsFeedViewHolder.post_share_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseDatabase.getInstance().getReference("Users").
                        child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("shared_posts").push();
                db.child("post_name").setValue(item.getPostName());
                db.child("post_data").setValue(item.getPostData());
                db.child("post_date").setValue(item.getTimeAndDate());
                db.child("post_id").setValue(item.getPost_ID());
                db.child("post_likes").setValue(item.getNumber_of_likes() + "");
//                db.child("post_photo").child(item.getUser_Profile_Photo());
//                db.child("post_user_photo").child(item.getUser_image_Post());
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts == null ? 0 : posts.size();
    }

//    @Override
//    public void onClick(View v) {
//        thumbs_up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int id = (int)thumbs_up.getTag();
//                if( id == R.drawable.ic_thumb_up){
//
//                    thumbs_up.setTag(R.drawable.ic_thumb_up);
//                    thumbs_up.setImageResource(R.drawable.ic_thumb_up);
//
//                } else {
//
//                }
//
//            }
//        });
//    }

    class NewsFeedPostViewHolder extends RecyclerView.ViewHolder {
        public TextView desc;
        public TextView profileName;
        public CircleImageView profilePic;
        public TextView dateAndTime;
        public ImageView imageView;
        public TextView post_hashTags;
        public TextView number_of_like;
        public ImageButton thumbs_up;
        public ImageButton post_comment;
        public LinearLayout post_comment_layout;
        public LinearLayout post_share_layout;

        NewsFeedPostViewHolder(View view) {
            super(view);
            desc = (TextView) view.findViewById(R.id.txtStatusMsgPost);
            profileName = (TextView) view.findViewById(R.id.Profile_name);
            profilePic = (CircleImageView) view.findViewById(R.id.profilePic);
            dateAndTime = (TextView) view.findViewById(R.id.timestamp);
            imageView = (ImageView) view.findViewById(R.id.post_image);
            number_of_like = (TextView) view.findViewById(R.id.Number_Of_Likes);
            post_hashTags = (TextView) view.findViewById(R.id.post_hashTags);
            thumbs_up = (ImageButton) view.findViewById(R.id.thumbs_up);
            post_comment = (ImageButton) view.findViewById(R.id.post_comment1);
            post_comment_layout = (LinearLayout) view.findViewById(R.id.post_comment_layout);
            post_share_layout = (LinearLayout) view.findViewById(R.id.post_share_layout);
        }
    }
}
