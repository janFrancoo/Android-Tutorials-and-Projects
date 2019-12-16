package com.janfranco.instaclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    ArrayList<UserPost> userPosts;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    FeedRecyclerAdapter feedRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userPosts = new ArrayList<>();

        getDataFromFireStore();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedRecyclerAdapter = new FeedRecyclerAdapter(userPosts);
        recyclerView.setAdapter(feedRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.insta_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.upload){
            Intent intentToUp = new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(intentToUp);
        } else if (item.getItemId() == R.id.signOut) {
            firebaseAuth.signOut();
            Intent intentToSign = new Intent(FeedActivity.this, LoginActivity.class);
            startActivity(intentToSign);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getDataFromFireStore() {
        CollectionReference collectionRef = firebaseFirestore.collection("Posts");
        collectionRef.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(
                new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                if(e != null)
                    Toast.makeText(FeedActivity.this, e.getLocalizedMessage().toString(),
                            Toast.LENGTH_LONG).show();

                if(queryDocumentSnapshots != null) {
                    for(DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        Map<String, Object> data = snapshot.getData();
                        String comment = (String) data.get("comment");
                        String email = (String) data.get("userEmail");
                        String downloadUrl = (String) data.get("downloadUrl");

                        userPosts.add(new UserPost(email, comment, downloadUrl));
                        feedRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

}
