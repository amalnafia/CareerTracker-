package com.example.ibrahimshaltout.test.newsfeed.post;

import java.util.List;

public class PostDataClass {

    private String postData;
    private String postName;
    private String timeAndDate;
    public String imageURL;
    private String user_ID;
    private String post_ID;
    private String share_for;
    private String user_image_Post;
    private String user_Profile_Photo;
    private int number_of_likes;
    private List<String> HashTage;


    private String commentHead;
    private String writerID;
    private String writerName;


    public String getUser_Profile_Photo() {
        return user_Profile_Photo;
    }

    public void setUser_Profile_Photo(String user_Profile_Photo) {
        this.user_Profile_Photo = user_Profile_Photo;
    }

    public String getUser_image_Post() {
        return user_image_Post;
    }

    public void setUser_image_Post(String user_image_Post) {
        this.user_image_Post = user_image_Post;
    }

    public List<String> getHashTage() {
        return HashTage;
    }

    public void setHashTage(List<String> hashTage) {
        this.HashTage = hashTage;
    }

    public String getShare_for() {
        return share_for;
    }

    public void setShare_for(String share_for) {
        this.share_for = share_for;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getCommentHead() {
        return commentHead;
    }

    public void setCommentHead(String commentHead) {
        this.commentHead = commentHead;
    }

    public String getWriterID() {
        return writerID;
    }

    public void setWriterID(String writerID) {
        this.writerID = writerID;
    }

    public String getPost_ID() {
        return post_ID;
    }

    public void setPost_ID(String post_ID) {
        this.post_ID = post_ID;
    }

    public int getNumber_of_likes() {
        return number_of_likes;
    }

    public void setNumber_of_likes(int number_of_likes) {
        this.number_of_likes = number_of_likes;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public PostDataClass() {

    }



    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTimeAndDate() {
        return timeAndDate;
    }

    public void setTimeAndDate(String timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public String getPostData() {
        return postData;
    }

    public void setPostData(String postData) {
        this.postData = postData;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }


}
