package com.example.bloodunity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class register extends AppCompatActivity {

    EditText firstName, lastName, userEmail, userPassword, userAddress;
    Spinner bloodType;
    DatabaseReference reference;
    private StorageReference storageReference;
    ImageView userPicture;
    String imageUrl;
    Uri uri;
    DrawerLayout drawerLayout;
    boolean allFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        TextView pageTitle = (TextView) findViewById(R.id.page_title); //sets page header (line 40)
        pageTitle.setText("Register");

        Spinner spinner = (Spinner) findViewById(R.id.blood_type); //spinner dropdown for blood type (line 42-47)

        drawerLayout = findViewById(R.id.drawer);

        //creating the array adapter (line 43)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.blood_type, android.R.layout.simple_spinner_item);
        //choosing layout for choices (line 45)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //add adapter to the spinner (line 47)
        spinner.setAdapter(adapter);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        userAddress = findViewById(R.id.user_address);
        bloodType = findViewById(R.id.blood_type);
        userPicture = findViewById(R.id.user_picture);

        reference = FirebaseDatabase.getInstance().getReference().child("users");

        Button btnRegister = (Button) findViewById(R.id.register_button);
        Button btnLogin = (Button) findViewById(R.id.login_button);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            userPicture.setImageURI(uri);
                        }else {
                            Toast.makeText(register.this, "No Image", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        userPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });


        //onclick for when register button is pressed
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                allFieldsChecked = CheckFields();

                if(allFieldsChecked){
                    saveData();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });

    }
    public void btnMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){redirectActivity(this, homepage.class);}
    public void ClickRegister(View view){
        recreate();
    }
    public void ClickShop(View view){
        redirectActivity(this, shop.class);
    }
    public void ClickAdmin(View view){redirectActivity(this, adminLock.class);}

    // when user click Home in the navigation menu, it will direct user from login page to Account page
    public void ClickAccount(View view) {redirectActivity(this, account.class);}

    // when user click Help Center in the navigation menu, it will direct user from login page to help center page
    public void ClickHelpCenter(View view) {redirectActivity(this, helpcenter.class);}

    // when user click Help Center in the navigation menu, it will direct user from login page to help center page
    public void ClickAppointment(View view) {redirectActivity(this, appointment.class);}

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

    //13 Nov Validation testing
    private boolean CheckFields(){

        if(firstName.length() == 0){
            firstName.setError("Field is required!");
            return false;
        }else if(firstName.length() <= 2){
            firstName.setError("Input too short!");
            return false;
        }else if(firstName.length() >= 15){
            firstName.setError("Input too long!");
            return false;
        }

        if(lastName.length() == 0){
            lastName.setError("Field is required!");
            return false;
        }else if(lastName.length() <= 2){
            lastName.setError("Input too short!");
            return false;
        }else if(lastName.length() >= 15){
            lastName.setError("Input too long!");
            return false;
        }

        String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        if(userEmail.length() == 0){
            userEmail.setError("Field is required!");
            return false;
        }else if(!userEmail.getText().toString().matches(emailPattern)){
            userEmail.setError("Invalid Email!");
            return false;
        }

        if(userPassword.length() == 0){
            userPassword.setError("Field is required!");
            return false;
        }else if(userPassword.length() <= 7){
            userPassword.setError("Password too short!");
            return false;
        }

        if(userAddress.length() == 0){
            userAddress.setError("Field is required!");
            return false;
        }

        saveData();
        return true;
    }


    public void saveData(){

        storageReference = FirebaseStorage.getInstance().getReference().child("uploads")
                .child(uri.getLastPathSegment());

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                uploadData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(register.this, "No image selected!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void uploadData(){
        String fname = firstName.getText().toString();
        String lname = lastName.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        String address = userAddress.getText().toString();
        String blood = bloodType.getSelectedItem().toString();

        RegisterHelperClass registerHelperClass = new RegisterHelperClass(fname, lname, email, password, address, blood, imageUrl);

        FirebaseDatabase.getInstance().getReference("users").child(fname)
                .setValue(registerHelperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(register.this, "Registered!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(register.this, login.class);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(register.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}