package com.example.bloodunity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class homepage extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //find view by id for nav drawer from activity homepage
        drawerLayout = findViewById(R.id.drawer);

        //for setting page title
        TextView page_title = findViewById(R.id.page_title);
        page_title.setText("Homepage");

        TextView title1 = (TextView) findViewById(R.id.pic1Title);
        TextView title2 = (TextView) findViewById(R.id.pic2Title);
        TextView title3 = (TextView) findViewById(R.id.pic3Title);

        //for underlining titles
        title1.setPaintFlags(title1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        title2.setPaintFlags(title2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        title3.setPaintFlags(title3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
    //line 40 to 79 is for nav drawer
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
    //onclick for nav drawer items
    public void ClickHome(View view){recreate();}
    public void ClickRegister(View view){
        redirectActivity(this, register.class);
    }
    public void ClickShop(View view){
        redirectActivity(this, shop.class);
    }
    public void ClickAdmin(View view){
        redirectActivity(this, adminLock.class);
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