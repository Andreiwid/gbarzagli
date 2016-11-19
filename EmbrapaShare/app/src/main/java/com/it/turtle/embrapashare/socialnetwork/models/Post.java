package com.it.turtle.embrapashare.socialnetwork.models;

/**
 * Represents a Post in our social network.
 * Created by Gabriel on 19/11/2016.
 */
public class Post extends FirebaseObject {
    private String userPhoto;
    private String userName;
    private String postImage;
    private String postDescription;

    public Post() {}

    public Post(String id) {
        super(id);
    }

    public Post(String id, String userPhoto, String userName, String postImage, String postDescription) {
        this(id);
        this.userPhoto = userPhoto != null ? userPhoto : "";
        this.userName = userName;
        this.postImage = postImage != null ? postImage : "";
        this.postDescription = postDescription;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }
}
