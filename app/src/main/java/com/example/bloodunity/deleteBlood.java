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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class deleteBlood extends AppCompatActivity {

    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    deleteBloodAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_blood);

        drawerLayout = findViewById(R.id.drawer);

        //receiving search value from manageMenu
        Intent intent = getIntent();
        String val = intent.getStringExtra("val");

        recyclerView = findViewById(R.id.delete_blood_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(deleteBlood.this));
        //sets view for recycler view; retrieving from DB
        FirebaseRecyclerOptions<viewBloodHelperClass> options =
                new FirebaseRecyclerOptions.Builder<viewBloodHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Blood").orderByChild("hospital").startAt(val).endAt(val), viewBloodHelperClass.class)
                        .build();

        mainAdapter = new deleteBloodAdapter(options);
        recyclerView.setAdapter(mainAdapter);
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
    public void ClickManageBlood(View view){redirectActivity(this, viewBlood.class);}
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