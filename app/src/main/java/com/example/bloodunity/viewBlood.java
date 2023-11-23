package com.example.bloodunity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class viewBlood extends AppCompatActivity {

    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    viewBloodAdapter mainAdapter;
    EditText searchbar;
    ImageView btnSearch;
    String searchValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blood);

        drawerLayout = findViewById(R.id.drawer);
        searchbar = findViewById(R.id.searchbar);
        btnSearch = findViewById(R.id.btnSearch);
        //search test
        searchValue = searchbar.getText().toString();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchValue = searchbar.getText().toString();
                Intent intent = new Intent(viewBlood.this, deleteBlood.class);
                intent.putExtra("val", searchValue);
                startActivity(intent);
            }
        });

        FloatingActionButton btnAddBlood = (FloatingActionButton) findViewById(R.id.btnAddBlood);

        btnAddBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (viewBlood.this, addBlood.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.view_blood_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(viewBlood.this));

        FirebaseRecyclerOptions<viewBloodHelperClass> options =
                new FirebaseRecyclerOptions.Builder<viewBloodHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Blood"), viewBloodHelperClass.class)
                        .build();

        mainAdapter = new viewBloodAdapter(options);
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
    //.setQuery(FirebaseDatabase.getInstance().getReference().child("Menu").orderByChild("category").startAt("Drink").endAt("Drink\uf8ff"), viewMenuHelperClass.class)
}