package com.example.loginproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity {
    List<Product> productList;
    String downlink,category,user;
    //the recyclerview
    RecyclerView recyclerView;
    ArrayList<String> imagelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Intent intent = getIntent();
        downlink= intent.getStringExtra("path");
        category= intent.getStringExtra("value");
        user= intent.getStringExtra("user");
       // StorageReference listRef = FirebaseStorage.getInstance().getReference();
//        listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
//            @Override
//            public void onSuccess(ListResult listResult) {
//                for(StorageReference file:listResult.getItems()){
//                    file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            imagelist.add(uri.toString());
//                            Log.e("Itemvalue",uri.toString());
//                        }
//                    }).addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//
////                            recyclerView.setAdapter(adapter);
////                            progressBar.setVisibility(View.GONE);
//                        }
//                    });
//                }
//            }
//        });

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
                        Toast.makeText(MainActivity4.this,filePath,Toast.LENGTH_SHORT).show();
                        // Do something with the file path
                        System.out.println(filePath);
                        Log.d("File Path", filePath);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(MainActivity4.this,"ERROR",Toast.LENGTH_SHORT).show();
                Log.d("File Path", "error");
                System.out.println("error");
            }
        });



        Animation animation = AnimationUtils.loadAnimation(MainActivity4.this, R.anim.anim_about_card_show);
                    RelativeLayout relativeLayout = findViewById(R.id.rl);
                    relativeLayout.startAnimation(animation);
                    //getting the recyclerview from xml
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity4.this));


                    //initializing the productlist
                    productList = new ArrayList<>();

        System.out.println(path);
                    //adding some items to our list
                    productList.add(
                            new Product(
                                    1,
                                    category,

                                    R.drawable.baseline_add_box_24,
                                    downlink

                            ));
                    for(String s:path){
                        productList.add(
                                new Product(
                                        1,
                                        category,

                                        R.drawable.baseline_add_box_24,s

                                ));
                    }


                    //creating recyclerview adapter
                    ProductAdapter adapter = new ProductAdapter(MainActivity4.this, productList);

                    //setting adapter to recyclerview
                    recyclerView.setAdapter(adapter);
                }

            }
