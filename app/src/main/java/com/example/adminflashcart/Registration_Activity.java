package com.example.adminflashcart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class Registration_Activity extends AppCompatActivity {


    ImageView profile;

    EditText email,pass,name,phone,countryET,stateET,cityET,addressET;
    Button registerbtn;
    TextView registerseller;


    private String[] cameraPermission;
    private String[] storagePermission;


    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;



    private Uri image_uri;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        profile = findViewById(R.id.profilepic);
        email = findViewById(R.id.loginEmail);
        pass = findViewById(R.id.loginPass);
        name = findViewById(R.id.loginName);
        phone = findViewById(R.id.loginPhone);

        registerbtn = findViewById(R.id.registerbtn);


        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait a movenment ....");
        progressDialog.setCanceledOnTouchOutside(false);


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //register the user here
                inputdata();

            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pick image
                showImagePickDialoge();
            }
        });



    }


    String inputname,inputemail,inputpass,inputphone;
    private void inputdata(){
        inputname = name.getText().toString().trim();
        inputemail = email.getText().toString().trim();
        inputpass = pass.getText().toString().trim();
        inputphone = phone.getText().toString().trim();


        if(TextUtils.isEmpty(inputname)){
            Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(inputemail)){
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(inputpass)){
            Toast.makeText(this, "Please Enter Your Pass", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(inputphone)){
            Toast.makeText(this, "Please Enter Your Phone", Toast.LENGTH_SHORT).show();
            return;
        }





        if(!Patterns.EMAIL_ADDRESS.matcher(inputemail).matches()){
            Toast.makeText(this, "Please Enter Your Valid Email Address", Toast.LENGTH_SHORT).show();
            return;
        }

        if(inputpass.length() <6){
            Toast.makeText(this, "Password Should not be less than 6 digit or character", Toast.LENGTH_SHORT).show();
            return;
        }


        //if all condition exist than we will call create acount function for further process

        createAccount();



    }

    private void createAccount() {

        progressDialog.setMessage("Please Wait while we are creating your Account on our server");
        progressDialog.show();

        //create a account

        firebaseAuth.createUserWithEmailAndPassword(inputemail,inputpass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //account created
                        saveFirebaseData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //account creation fail
                        progressDialog.dismiss();
                        Toast.makeText(Registration_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void saveFirebaseData() {

        progressDialog.setMessage("Saving Account");

        String timestamp = ""+System.currentTimeMillis();

        if(image_uri == null){
            //then even without image we will save the data to firebase

            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("uid", "" +firebaseAuth.getUid());
            hashMap.put("email", "" +inputemail);
            hashMap.put("name","" + inputname);
            hashMap.put("Phone","" + inputphone);
            hashMap.put("timestamp", "" +timestamp);
            hashMap.put("accountType", "Admin");
            hashMap.put("Online",  "true");
            hashMap.put("profileImage", "");

            //now we will save all this detail in database

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Admin");
            ref.child(firebaseAuth.getUid()).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //update database
                            progressDialog.dismiss();
                            startActivity(new Intent(Registration_Activity.this, MainActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //failed to update the database
                            progressDialog.dismiss();
                            startActivity(new Intent(Registration_Activity.this,MainActivity.class));
                            finish();
                        }
                    });


        }else{
            //we will save information with image

            String filePathAndName = "profiles_images/" + ""+ firebaseAuth.getUid();

            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(image_uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //here we will try to get url for uploaded image
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri downloadImageUri = uriTask.getResult();

                            if(uriTask.isSuccessful()){

                                //save actual data
                                HashMap<String, Object> hashMap = new HashMap<>();

                                hashMap.put("uid", "" +firebaseAuth.getUid());
                                hashMap.put("email", "" +inputemail);
                                hashMap.put("name","" + inputname);
                                hashMap.put("Phone","" + inputphone);
                                hashMap.put("timestamp", "" +timestamp);
                                hashMap.put("accountType", "Admin");
                                hashMap.put("profileImage", ""+downloadImageUri); //url of upoaded image

                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //fail to get image url
                            progressDialog.dismiss();
                            Toast.makeText(Registration_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        }

    }



    private void showImagePickDialoge() {
        String[] option = {"Camera", "Gallery"};

        //show dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Your Image")
                .setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == 0) {
                            // then camera clicked

                            if (checkCameraPermission()) {
                                //then camera permission allowed

                                pickFromCamera();


                            } else {
                                //camera permission not allowed, request

                                requestcameraPermission();

                            }


                        } else {
                            //otherwise gallery clicked

                            if (checkStoragePermission()) {
                                //storage permission allowed

                                pickedFromGallery();

                            } else {

                                // storage permission not allowed, requesting
                                requestStoragePermission();

                            }

                        }

                    }
                })
                .show();

    }


    private void pickedFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_Image Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Image Descritption");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);


    }

    private boolean checkStoragePermission(){
        boolean result  = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);
    }


    private boolean checkCameraPermission(){
        boolean result  = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);


        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestcameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);
    }


}