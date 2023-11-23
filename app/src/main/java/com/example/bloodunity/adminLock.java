package com.example.bloodunity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class adminLock extends AppCompatActivity {

    DrawerLayout drawerLayout;
    EditText adminPassword;
    Button tryPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lock);

        //For setting the page title on create
        TextView page_title = findViewById(R.id.page_title);
        page_title.setText("Admin Lock");

        //find view by IDs from activity admin lock xml
        drawerLayout = findViewById(R.id.drawer);
        adminPassword = findViewById(R.id.adminPassword);
        tryPass = findViewById(R.id.btnTryPass);

        //onclick when button is pressed to try password
        tryPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pwd = adminPassword.getText().toString();

                if(pwd.equals("hospital")){

                    Toast.makeText(adminLock.this, "Welcome Admin!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(adminLock.this, ViewUsers.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(adminLock.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //line 56 to 95 is for the navigation drawer
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
    //onclicks for navigation drawer items
    public void ClickHome(View view){redirectActivity(this, homepage.class);}
    public void ClickRegister(View view){
        redirectActivity(this, register.class);
    }
    public void ClickShop(View view){
        redirectActivity(this, shop.class);
    }
    public void ClickAdmin(View view){
        recreate();
    }

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
}