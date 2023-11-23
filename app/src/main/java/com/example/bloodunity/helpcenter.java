package com.example.bloodunity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class helpcenter extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpcenter);

        // page title
        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("Help Center");

        drawerLayout = findViewById(R.id.drawer);
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

    // when user click Help Center in the navigation menu, it will recreate the help center page
    public void ClickHelpCenter(View view){
        recreate();
    }

    // when user click Register in the navigation menu, it will direct user from help center page to register page
    public void ClickRegister(View view) {redirectActivity(this, register.class);}

    // when user click Register in the navigation menu, it will direct user from help center page to appointment page
    public void ClickAppointment(View view) {redirectActivity(this, appointment.class);}

    // when user click Register in the navigation menu, it will direct user from help center page to Account page
    public void ClickAccount(View view) {redirectActivity(this, account.class);}

    public void ClickHome(View view){redirectActivity(this, homepage.class);}
    public void ClickShop(View view){
        redirectActivity(this, shop.class);
    }
    public void ClickAdmin(View view){redirectActivity(this, adminLock.class);}


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