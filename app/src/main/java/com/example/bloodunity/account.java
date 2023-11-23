package com.example.bloodunity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


public class account extends AppCompatActivity {

    EditText editName, editEmail, editAddress, editPassword;
    Button btnEditProfile;

    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // page title
        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("Account");

        drawerLayout = findViewById(R.id.drawer);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editAddress = findViewById(R.id.editAddress);
        editPassword = findViewById(R.id.editPassword);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        showUserData();
    }

    public void showUserData(){

        Intent intent = getIntent();

        String userName = intent.getStringExtra("name");
        String userAddress = intent.getStringExtra("address");
        String userPassword = intent.getStringExtra("password");
        String userEmail = intent.getStringExtra("email");


        editName.setText(userName);
        editAddress.setText(userAddress);
        editPassword.setText(userPassword);
        editEmail.setText(userEmail);


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


    // when user click Account in the navigation menu, it will recreate the Account page
    public void ClickAccount(View view){
        recreate();
    }

    // when user click Register in the navigation menu, it will direct user from Account to register page
    public void ClickRegister(View view) {redirectActivity(this, register.class);}

    // when user click Help Center in the navigation menu, it will direct user from Account page to help center page
    public void ClickHelpCenter(View view) {redirectActivity(this, helpcenter.class);}

    // when user click Help Center in the navigation menu, it will direct user from Account page to help center page
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