package com.example.loginproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        TextView t=(TextView) findViewById(R.id.textView3);
        Intent intent = getIntent();
       String downlink= intent.getStringExtra("path");
      String  category= intent.getStringExtra("value");
       String user= intent.getStringExtra("user");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        ArrayList<String> path=new ArrayList<>();
        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    // Check if item is a file
                    if (item.getMetadata() != null ) {
                        String filePath = item.getPath();
                        path.add(filePath);
                        Toast.makeText(MainActivity5.this,filePath,Toast.LENGTH_SHORT).show();
                        // Do something with the file path
                        System.out.println(filePath);
                        Log.d("File Path", filePath);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(MainActivity5.this,"ERROR",Toast.LENGTH_SHORT).show();
                Log.d("File Path", "error");
                System.out.println("error");
            }
        });
t.setText(path.toString());
    }
}