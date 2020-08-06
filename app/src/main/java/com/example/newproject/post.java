package com.example.newproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class post extends AppCompatActivity {
    private EditText description, title, author;
    private Uri postImageUri=null;
    Button save;
    RatingBar rating;
    ImageView image;
    private Uri filepath;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase database;
    DatabaseReference myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        author = findViewById(R.id.author);
        rating = findViewById(R.id.ratingBar);
        save = findViewById(R.id.button);
        image = findViewById(R.id.imageView3);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        myref = FirebaseDatabase.getInstance().getReference();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadimage();
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadimage() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(post.this);
        builder.setSmallIcon(R.drawable.ic_notifications_black_24dp) ;
        builder.setContentText("Hurray ! Bokk is added");
        builder.setContentTitle("Notification");
        NotificationManager manager = (NotificationManager) getApplication().getSystemService(post.this.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
        if (filepath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("uploading ...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString() + ".jpg");
            Task<Uri> uriTask = ref.putFile(filepath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful())
                    {
                       throw task.getException();

                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful())
                    {
                        String  downlaoduri = task.getResult().toString();
                        String mtitle = title.getText().toString();
                   String mdescription =description.getText().toString();
                   String mauthor =author.getText().toString();
//                    String url = ref.getDownloadUrl().toString();

                        HashMap<String,Object> map= new HashMap<>();

                    map.put("title",mtitle);
                    map.put("description",mdescription);
                    map.put("author",mauthor);
                    map.put("url",downlaoduri);
                        myref.child("Books").child(UUID.randomUUID().toString()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(post.this,"Book is uploaded",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                            else
                            {
                                Toast.makeText(post.this,"uploading is failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     progressDialog.dismiss();
                     Toast.makeText(post.this,"Failed",Toast.LENGTH_LONG).show();
                 }
             });


















//            ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    progressDialog.dismiss();
////                    Toast.makeText(post.this,"uploaded",Toast.LENGTH_LONG).show();
//                    String mtitle = title.getText().toString();
//                    String mdescription =description.getText().toString();
//                    String mauthor =author.getText().toString();
//                    String url = ref.getDownloadUrl().toString();
//
//                    HashMap<String,Object> map= new HashMap<>();
//
//                    map.put("title",mtitle);
//                    map.put("description",mdescription);
//                    map.put("author",mauthor);
//                    map.put("url",url);
//                    myref.child("Books").child(UUID.randomUUID().toString()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful())
//                            {
//                                Toast.makeText(post.this,"Book is uploaded",Toast.LENGTH_LONG).show();
//
//                            }
//                            else
//                            {
//                                Toast.makeText(post.this,"uploading is failed",Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
//
//
//                }
//            })
//             .addOnFailureListener(new OnFailureListener() {
//                 @Override
//                 public void onFailure(@NonNull Exception e) {
//                     progressDialog.dismiss();
//                     Toast.makeText(post.this,"Failed",Toast.LENGTH_LONG).show();
//                 }
//             })
//             .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                 @Override
//                 public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                     progressDialog.dismiss();
//                     Toast.makeText(post.this,"uploading",Toast.LENGTH_LONG).show();
//                 }
//             }) ;
//        }
        }

    }
}
