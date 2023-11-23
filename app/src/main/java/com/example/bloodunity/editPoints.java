package com.example.bloodunity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class editPoints extends AppCompatActivity {

    RecyclerView recyclerView;
    editPointsAdapter mainAdapter;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_points);

        //find view from activity edit points for nav drawer
        drawerLayout = findViewById(R.id.drawer);

        //setting page title on create
        TextView pageTitle = (TextView) findViewById(R.id.page_title);
        pageTitle.setText("Edit Students");

        //receiving search value from manageMenu
        Intent intent = getIntent();
        String val = intent.getStringExtra("val");

        recyclerView = findViewById(R.id.edit_students_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(editPoints.this));
        //sets view for recycler view; retrieving from DB
        FirebaseRecyclerOptions<ViewUsersHelperClass> options =
                new FirebaseRecyclerOptions.Builder<ViewUsersHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users").orderByChild("first_name").startAt(val).endAt(val), ViewUsersHelperClass.class)
                        .build();

        mainAdapter = new editPointsAdapter(options);
        recyclerView.setAdapter(mainAdapter);
    }
    //line 53 to 92 is for navigation drawer
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
    public void ClickHome(View view){redirectActivity(this, homepage.class);}
    public void ClickRegister(View view){
        redirectActivity(this, register.class);
    }
    public void ClickShop(View view){
        redirectActivity(this, shop.class);
    }
    public void ClickAdmin(View view){
        redirectActivity(this, adminLock.class);
    }

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
    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}
