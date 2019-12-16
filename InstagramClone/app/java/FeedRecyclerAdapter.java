package com.janfranco.instaclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedRecyclerAdapter extends RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder> {

    private ArrayList<UserPost> userPosts;

    public FeedRecyclerAdapter(ArrayList<UserPost> userPosts) {
        this.userPosts = userPosts;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_row, parent, false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        UserPost userPost = userPosts.get(position);
        holder.userEmailText.setText(userPost.userEmail);
        holder.userCommentText.setText(userPost.userComment);
        Picasso.get().load(userPost.userImage).into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        return userPosts.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView userCommentText, userEmailText;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.recyclerview_row_image);
            userCommentText = itemView.findViewById(R.id.recyclerview_row_comment_text);
            userEmailText = itemView.findViewById(R.id.recyclerview_row_useremail_text);
        }
    }

}
