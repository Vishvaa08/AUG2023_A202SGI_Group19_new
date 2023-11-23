package com.example.bloodunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class login extends AppCompatActivity {

    EditText _email, _password;
    Button btnLogin, btnRegister;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        drawerLayout = findViewById(R.id.drawer);
        ImageView menu_button = (ImageView) findViewById(R.id.menu_button);


        // page title
        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("Log In");

        _email = findViewById(R.id.editEmail);
        _password = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.login_button);
        btnRegister = findViewById(R.id.register_button);

        // set Login button Onclick
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmailAddress = _email.getText().toString();
                String textPassword = _password.getText().toString();

                if (textEmailAddress.isEmpty() || textPassword.isEmpty()) {

                    // error message if user has not entered email or password
                    Toast.makeText(login.this, "Please Enter Your Email Address Or Password",
                            Toast.LENGTH_SHORT).show();
                } else {
                    checkUser();
                }
            }

            //check the validation is same as database
            public void checkUser() {
                String userEmail = _email.getText().toString().trim();
                String userPassword = _password.getText().toString().trim();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUserDatabase = reference.orderByChild("email").equalTo(userEmail);

                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.exists()){

                            _email.setError(null);
                            _email.setEnabled(false);
                            String passwordFromDatabase = snapshot.child(userEmail).child("password").getValue(String.class);

                            //check password same as database or not
                            if(!Objects.equals(passwordFromDatabase, userPassword)) {
                                _email.setError(null);
                                _email.setEnabled(false);
                                //if email & password same as database, prompt toast message
                                Toast.makeText(login.this, "Login Successfully",
                                        Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(login.this, homepage.class);
                                startActivity(intent);

                                //if password not same as database, prompt error message
                            } else {
                                _password.setError("Invalid Password");
                                _password.requestFocus();
                            }
                            //if email not same as database, prompt error message
                        } else {
                            _email.setError("User does not exist");
                            _email.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

        });

        // press register button will direct user to Register page
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });
    }




    public void btnMenu(View view) {
        openDrawer(drawerLayout);
    }


    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }



    // when user click Home in the navigation menu, it will direct user from login page to Account page
    public void ClickAccount(View view) {redirectActivity(this, account.class);}

    // when user click Register in the navigation menu, it will direct user from login page to register page
    public void ClickRegister(View view) {redirectActivity(this, register.class);}

    // when user click Help Center in the navigation menu, it will direct user from login page to help center page
    public void ClickHelpCenter(View view) {redirectActivity(this, helpcenter.class);}

    // when user click Help Center in the navigation menu, it will direct user from login page to help center page
    public void ClickAppointment(View view) {redirectActivity(this, appointment.class);}

    public void ClickHome(View view){redirectActivity(this, homepage.class);}
    public void ClickShop(View view){
        redirectActivity(this, shop.class);
    }
    public void ClickAdmin(View view){redirectActivity(this, adminLock.class);}



    public static void redirectActivity(Activity activity, Class Class) {
        Intent intent = new Intent(activity, Class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);


    }
}