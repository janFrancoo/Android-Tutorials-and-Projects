package com.janfranco.instacloneparse;

import android.graphics.Bitmap;

public class Post {

    Bitmap image;
    String username, comment;

    public Post(String username, String comment, Bitmap image) {
        this.username = username;
        this.comment = comment;
        this.image = image;
    }

}
