package com.janfranco.instacloneparse;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PostClass extends ArrayAdapter<Post> {

    private final Activity context;
    private final ArrayList<Post> posts;

    public PostClass(Activity context, ArrayList<Post> posts) {
        super(context, R.layout.custom_view, posts);
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View customView = inflater.inflate(R.layout.custom_view, null, true);
        TextView usernameText = customView.findViewById(R.id.textView);
        TextView commentText = customView.findViewById(R.id.textView2);
        ImageView imageView = customView.findViewById(R.id.imageView);

        usernameText.setText(posts.get(position).username);
        commentText.setText(posts.get(position).comment);
        imageView.setImageBitmap(posts.get(position).image);

        return customView;
    }
}
