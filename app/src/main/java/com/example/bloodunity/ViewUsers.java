package com.example.bloodunity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ViewUsers extends AppCompatActivity {

    RecyclerView recyclerView;
    ViewUsersAdapter mainAdapter;
    EditText searchbar;
    ImageView btnSearch;
    String searchValue;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_users_recycler_view);

        drawerLayout = findViewById(R.id.drawer);

        searchbar = findViewById(R.id.searchbar);
        btnSearch = findViewById(R.id.btnSearch);
        //search test
        searchValue = searchbar.getText().toString();
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchValue = searchbar.getText().toString();
                Intent intent = new Intent(ViewUsers.this, editPoints.class);
                intent.putExtra("val", searchValue);
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.view_users_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewUsers.this));

        FirebaseRecyclerOptions<ViewUsersHelperClass> options =
                new FirebaseRecyclerOptions.Builder<ViewUsersHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), ViewUsersHelperClass.class)
                        .build();

        mainAdapter = new ViewUsersAdapter(options);
        recyclerView.setAdapter(mainAdapter);
    }
    public void btnAdminMenu(View view){
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
    public void ClickViewUsers(View view){recreate();}
    public void ClickManageBlood(View view){redirectActivity(this, viewBlood.class);}
    public void ClickUser(View view){redirectActivity(this, homepage.class);}

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
