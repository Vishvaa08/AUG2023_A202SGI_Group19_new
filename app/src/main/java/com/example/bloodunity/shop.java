package com.example.bloodunity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class shop extends AppCompatActivity {

    RecyclerView recyclerView;
    String itemName[], itemDesc[], itemPrice[];
    int itemImages[] = {R.drawable.hand_sanitizer,R.drawable.face_mask,R.drawable.latex_glove,R.drawable.face_shield,R.drawable.first_aid_kit,R.drawable.plaster,R.drawable.antiseptic,R.drawable.thermometer,R.drawable.cotton_buds,};
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_recycler_view);

        recyclerView = findViewById(R.id.shop_recycler_view);

        itemName = getResources().getStringArray(R.array.shop_items);
        itemDesc = getResources().getStringArray(R.array.item_desc);
        itemPrice = getResources().getStringArray(R.array.item_price);

        drawerLayout = findViewById(R.id.drawer);

        recyclerViewAdapter myAdapter = new recyclerViewAdapter(this, itemName, itemDesc, itemPrice, itemImages);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("Shop");
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
        redirectActivity(this, register.class);
    }
    public void ClickShop(View view){
        recreate();
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
