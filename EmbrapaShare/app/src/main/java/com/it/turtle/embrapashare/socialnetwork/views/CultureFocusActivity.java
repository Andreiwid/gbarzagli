package com.it.turtle.embrapashare.socialnetwork.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.it.turtle.embrapashare.R;
import com.it.turtle.embrapashare.socialnetwork.controller.CameraController;
import com.it.turtle.embrapashare.socialnetwork.controller.FirebaseManager;
import com.it.turtle.embrapashare.socialnetwork.controller.FirebaseMap;
import com.it.turtle.embrapashare.socialnetwork.models.Post;
import com.it.turtle.embrapashare.socialnetwork.views.adapter.PostAdapter;

import java.util.ArrayList;
import java.util.List;

public class CultureFocusActivity extends AppCompatActivity {

    private CameraController cameraController;
    private FirebaseManager firebaseManager;

    private ListView postListView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String pathToNode = savedInstanceState.getString("path") + "/" + FirebaseMap.POSTS;

        postListView = (ListView) findViewById(R.id.postList);

        firebaseManager = new FirebaseManager(pathToNode, Post.class);

        firebaseManager.setOnDataChangedListener(new FirebaseManager.OnDataChangedListener() {
            @Override
            public void onDatabaseUpdate(DataSnapshot dataSnapshot) {
                postList = convertList(firebaseManager.getChildrenList());
                if (postList != null) {
                    postAdapter = new PostAdapter(CultureFocusActivity.this, postList);

                    postListView.setAdapter(postAdapter);
                } else {
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraController.takePicture();
            }
        });

        cameraController = new CameraController(this, "EmbrapaShareCamera", "ESC_PHOTO");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Gabriel", "Path = " + cameraController.getImagePath());

//        firebaseManager.addToNode();
    }

    private List<Post> convertList (List<Object> sourceList) {
        List<Post> outPut = new ArrayList<>();
        for (Object object : sourceList) {
            outPut.add((Post) object);
        }

        return outPut;
    }
}
