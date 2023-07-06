package com.example.loginproject;

import static android.content.Intent.ACTION_SEND;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
public class MainActivity3 extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    String[] country = { "Type of event","Literacy", "Cultural", "Finearts & Fashion", "Sports", "Technical","NSS","NCC","NPTEL","Coding","Other"};
    ImageView upload;
    String cat;
    String user;
    String path,link;



    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        if(position==0)
            Toast.makeText(getApplicationContext(),"select any options" , Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(getApplicationContext(), country[position], Toast.LENGTH_LONG).show();
            cat=country[position];
        }
    }
    Uri imageuri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent intent = getIntent();
         user= intent.getStringExtra("message_key");
       // Toast.makeText(getApplicationContext(),user, Toast.LENGTH_LONG).show();
        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        Spinner spin = (Spinner) findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.ic_action_vignan);
        getSupportActionBar().setTitle("Certificate Manager");



        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        ImageView upload = findViewById(R.id.uploadpdf);

        // After Clicking on this we will be
        // redirected to choose pdf
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // We will be redirected to choose pdf
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.hello:
                Intent ni=new Intent(MainActivity3.this,MainActivity.class);
                ni.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(ni);
                break;
            case R.id.search:
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                startActivity(i);
                break;
            case R.id.refresh:
                Toast.makeText(this, "Work in progress", Toast.LENGTH_SHORT).show();
                Intent ini
                        = new Intent(MainActivity3.this,
                        MainActivity3.class);
                startActivity(ini);
                break;
            case R.id.details:
                Toast.makeText(this, "Work in progress", Toast.LENGTH_SHORT).show();
                Intent in
                        = new Intent(MainActivity3.this,
                        MainActivity5.class);
                in.putExtra("path",link);
                in.putExtra("value",cat);
                in.putExtra("user",user+"/");

                startActivity(in);
                break;
            case R.id.activity:
                Intent intent
                        = new Intent(MainActivity3.this,
                        MainActivity4.class);
                intent.putExtra("path",link);
                intent.putExtra("value",cat);
                intent.putExtra("user",user+"/");

                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    ProgressDialog dialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            imageuri = data.getData();
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePushID = timestamp;
            Toast.makeText(MainActivity3.this, imageuri.toString(), Toast.LENGTH_SHORT).show();

            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child(user+"/"+cat+"/"+messagePushID+ "." + "pdf");
            path=user+"/"+cat+"/"+messagePushID+ "." + "pdf";
            Toast.makeText(MainActivity3.this, filepath.getName(), Toast.LENGTH_SHORT).show();
            filepath.putFile(imageuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // After uploading is done it progress
                        // dialog box will be dismissed
                        dialog.dismiss();
                        Uri uri = task.getResult();
                        String myurl;
                        link = uri.toString();
                        myurl= uri.toString();
                        Toast.makeText(MainActivity3.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        NotificationHelper.showNotification(MainActivity3.this, "SCM ", "YOUR DOCUMENT UPLOADED SUCCESSFULLY");

                    } else {
                        dialog.dismiss();
                        Toast.makeText(MainActivity3.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
//public void next(View view){
//    Intent intent
//            = new Intent(MainActivity3.this,
//            MainActivity4.class);
//    intent.putExtra("path",link);
//    intent.putExtra("value",cat);
//    intent.putExtra("user",user+"/");
//
//    startActivity(intent);
//}

    //Performing action onItemSelected and onNothing selected

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}