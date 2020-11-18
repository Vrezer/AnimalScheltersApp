package com.example.animalscheltersapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterToRecyleView myAdapter;
    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //ANDROID COMPONENTS
        recyclerView=findViewById(R.id.recylewView);
        toolbar=findViewById(R.id.toolbarUser);
        nav=findViewById(R.id.navmenu);
        drawerLayout=findViewById(R.id.UserLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        //Display drawer
        DrawerMenu();

        // RecyleView
        RecyleView();

    }


    private void RecyleView()
    {
        //REVERSE
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<Animal> options = new FirebaseRecyclerOptions.Builder<Animal>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Animal").orderByChild("date"), Animal.class)
                .build();

        myAdapter=new AdapterToRecyleView(options,getApplicationContext());
        recyclerView.setAdapter(myAdapter);
    }


    private void DrawerMenu()
    {
        drawerMenuHelper drawerMenuHelper=new drawerMenuHelper(nav,actionBarDrawerToggle,toolbar,drawerLayout);
        setSupportActionBar(drawerMenuHelper.getToolbar());
        drawerMenuHelper.DrawerMenu(this,firebaseAuth);
    }


    //FUNCTION TO RYCLEVIEW
    @Override
    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }

}
