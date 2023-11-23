package com.example.bloodunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class addBlood extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView bloodGroup;
    EditText donorAge, donorName;
    Button btnConfirmAdd;
    Spinner spHospital;
    private static final int photo = 6;
    private Uri ImageUri;
    String name, age, itemKeyRandom, downloadImageUrl, hosp;
    StorageReference reference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blood);

        drawerLayout = findViewById(R.id.drawer);
        bloodGroup = (ImageView) findViewById(R.id.bloodGroup);
        donorAge = (EditText) findViewById(R.id.donorAge);
        donorName = (EditText) findViewById(R.id.donorName);
        btnConfirmAdd = (Button) findViewById(R.id.btnConfirmAdd);
        spHospital = (Spinner) findViewById(R.id.spHospital);

        //adapter sets values for hospital spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hospitals, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHospital.setAdapter(adapter);

        reference = FirebaseStorage.getInstance().getReference().child("Blood Images");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Blood");

        //sets the page header name during on create
        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("Blood Bank");

        //onclick calls openPhotos function
        bloodGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPhotos();
            }
        });

        //confirm button calls validate item data function
        btnConfirmAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateItemData();
            }
        });
    }
    //function for when hamburger icon is clicked, calls openDrawer function
    public void btnAdminMenu(View view){
        openDrawer(drawerLayout);
    }

    //openDrawer function, opens the navigation drawer
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    //function for when logo is clicked, calls closeDrawer function
    public void clickLogo(View view){
        closeDrawer(drawerLayout);
    }

    //closeDrawer function, closes the navigation drawer
    public static void closeDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickViewUsers(View view){redirectActivity(this, ViewUsers.class);}
    public void ClickManageBlood(View view){recreate();}
    public void ClickUser(View view){redirectActivity(this, homepage.class);}
    //function redirectActivity that is called when nav option is clicked
    public static void redirectActivity(Activity activity, Class Class){
        Intent intent = new Intent(activity,Class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
    @Override
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }
    //openPhoto function opens gallery to select image
    public void openPhotos(){
        Intent intent = new Intent ();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, photo);
    }
    //sets imageview to the chosen image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==photo && resultCode==RESULT_OK && data!=null){
            ImageUri = data.getData();
            bloodGroup.setImageURI(ImageUri);
        }
    }
    //validation to prevent empty fields
    public void ValidateItemData(){
        name = donorName.getText().toString();
        age = donorAge.getText().toString();
        if(ImageUri == null){
            Toast.makeText(this, "Item Image Not Selected!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter Donor Name!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(age)){
            Toast.makeText(this, "Enter Donor Age!", Toast.LENGTH_SHORT).show();
        }else{
            StoreItemInformation();
        }
    }
    //
    public void StoreItemInformation(){

        hosp = spHospital.getSelectedItem().toString();

        StorageReference filePath = reference.child(ImageUri.getLastPathSegment() + itemKeyRandom + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(addBlood.this, "Error" + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(addBlood.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT) .show();
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(addBlood.this, "Valid Image URL", Toast.LENGTH_SHORT).show();
                            SaveItemInfotoDB();
                        }
                    }
                });
            }
        });
    }
    //function saves all info into firebase database
    public void SaveItemInfotoDB(){
        HashMap<String, Object> itemMap = new HashMap<>();
        itemMap.put("donorName", name);
        itemMap.put("donorAge", age);
        itemMap.put("bloodGroup", downloadImageUrl);
        itemMap.put("hospital", hosp);

        databaseReference.child(name).updateChildren(itemMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent (addBlood.this, viewBlood.class);
                            startActivity(intent);

                            Toast.makeText(addBlood.this, "Item is added", Toast.LENGTH_SHORT).show();
                        }else{
                            String message = task.getException().toString();
                            Toast.makeText(addBlood.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}