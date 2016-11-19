package com.it.turtle.embrapashare.socialnetwork.views.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.it.turtle.embrapashare.R;
import com.it.turtle.embrapashare.socialnetwork.controller.CameraController;
import com.it.turtle.embrapashare.socialnetwork.controller.FirebaseManager;
import com.it.turtle.embrapashare.socialnetwork.controller.FirebaseMap;
import com.it.turtle.embrapashare.socialnetwork.models.Culture;
import com.it.turtle.embrapashare.socialnetwork.models.Post;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddPostActivity extends AppCompatActivity {

    private CameraController cameraController;
    private ImageView pictureImageView;
    private Button okButton;
    private EditText nameEditText;

    private String pathToNode;

    private FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_culture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        pathToNode = savedInstanceState.getString("path") + "/" + FirebaseMap.POSTS;

        cameraController = new CameraController(this, "Embrapa", "IMAGE");
    }

    @Override
    protected void onResume() {
        super.onResume();

        cameraController.takePicture();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CameraController.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d("Vinicius", "Path = " + cameraController.getImagePath());
            CameraController.loadFileToImageView(cameraController.getImagePath(), pictureImageView);

            okButton.setEnabled(true);
        }
    }

    private void initView () {
        pictureImageView = (ImageView) findViewById(R.id.pictureImageView);

        okButton = (Button) findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImageToFirebase();
                finish();
            }
        });

        nameEditText = (EditText) findViewById(R.id.nameEditText);
    }

    private void saveImageToFirebase () {
        // Get the data from an ImageView as bytes
        pictureImageView.setDrawingCacheEnabled(true);
        pictureImageView.buildDrawingCache();
        Bitmap bitmap = pictureImageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        String path = "Images" + cameraController.getImageFileName();

        StorageReference imageRef = storage.getReference(path);

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                FirebaseManager firebaseManager = new FirebaseManager(pathToNode, Post.class);

                //TODO colocar usuario do banco
                firebaseManager.addToNode(new Post(("Culture_" + (new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()))),
                        "","", downloadUrl.toString(), nameEditText.getText().toString()));
//                firebaseManager.addToNode(new Culture(nameEditText.getText().toString(), "", downloadUrl.toString()));

                AddPostActivity.this.finish();
            }
        });
    }

}
