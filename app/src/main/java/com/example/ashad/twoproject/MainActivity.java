package com.example.ashad.twoproject;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    StorageReference mstoragerefrence;
    StorageReference secondaryRef;
    Button anotherdatabase,thisdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anotherdatabase=findViewById(R.id.anotherdatabase);
        thisdatabase=findViewById(R.id.thisdatabase);
        mstoragerefrence=FirebaseStorage.getInstance().getReference();


        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:973605940358:android:d35f3bc4d0ea95b6") // Required for Analytics.
                .setApiKey("AIzaSyBDxCczyQMvpeipwXhueXuU1FVhazI14D4") // Required for Auth.
                .setDatabaseUrl("https://loginwithemail29may.firebaseio.com/") // Required for RTDB.
//                .setStorageBucket("gs://loginwithemail29may.appspot.com")
                .build();

//  FirebaseApp secondApp = FirebaseApp.initializeApp(getApplicationContext(), options, "second app");
//    //storage
//    FirebaseStorage storage = FirebaseStorage.getInstance(secondApp);
//    StorageReference storageRef = storage.getReferenceFromUrl("gs://.......");
        // Initialize with secondary app.

// Retrieve secondary app.
        FirebaseApp secondary = FirebaseApp.initializeApp(this /* Context */, options, "secondary");

// Get the database for the other app.
        FirebaseStorage secondaryDatabase = FirebaseStorage.getInstance(secondary);
        StorageReference secRef = secondaryDatabase.getReferenceFromUrl("gs://loginwithemail29may.appspot.com");
        secondaryRef = secRef.child("yo");

        anotherdatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,1);

            }
        });

        thisdatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i,0);

            }
        });







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0)
        {
            try {
                Uri uri=data.getData();

                StorageReference filename=mstoragerefrence.child("status/"+uri.getLastPathSegment()+".png");
                filename.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(),"well done",Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "NO IMAGE SELECTED", Toast.LENGTH_SHORT).show();

            }


        }
        else if (requestCode==1)
        {

            try {
                Uri uri=data.getData();

                StorageReference filename=secondaryRef.child("status/"+uri.getLastPathSegment()+".png");
                filename.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(),"well done",Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "NO IMAGE SELECTED", Toast.LENGTH_SHORT).show();

            }

        }
    }


    public void doyourwork()
    {


    }
}
