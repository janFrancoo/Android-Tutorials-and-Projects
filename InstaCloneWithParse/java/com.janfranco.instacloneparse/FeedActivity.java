package com.janfranco.instacloneparse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    ListView listView;
    PostClass postClass;
    ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        listView = findViewById(R.id.listView);
        posts = new ArrayList<>();
        postClass = new PostClass(this, posts);
        listView.setAdapter(postClass);
        download();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.feed_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.feed_activity_add_post) {
            Intent intent = new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(intent);
        } else if(item.getItemId() == R.id.feed_activity_user_logout) {
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e != null)
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                                Toast.LENGTH_LONG).show();
                    else {
                        Intent intent = new Intent(FeedActivity.this,
                                SignUpActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    public void download() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e != null)
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                            Toast.LENGTH_LONG).show();
                else {
                    if(objects.size() > 0) {
                        for (final ParseObject object : objects) {
                            ParseFile file = (ParseFile) object.get("image");
                            if (file != null) {
                                file.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] data, ParseException e) {
                                        if(e == null && data != null) {
                                            Post post = new Post(object.get("username").toString(),
                                                    object.get("comment").toString(),
                                                    BitmapFactory.decodeByteArray(data, 0,
                                                            data.length));
                                            posts.add(post);
                                            postClass.notifyDataSetChanged();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
    }
}
