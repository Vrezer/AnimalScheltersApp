package com.example.animalscheltersapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AboutUs extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //ANDROID COMPONENTS
        toolbar=findViewById(R.id.toolbarAbout);
        nav=findViewById(R.id.navmenuAbout);
        drawerLayout=findViewById(R.id.aboutUsId);
        firebaseAuth = FirebaseAuth.getInstance();
        //Display drawer
        DrawerMenu();
    }

    private void DrawerMenu()
    {
        drawerMenuHelper drawerMenuHelper=new drawerMenuHelper(nav,actionBarDrawerToggle,toolbar,drawerLayout);
        setSupportActionBar(drawerMenuHelper.getToolbar());
        drawerMenuHelper.DrawerMenu(this,firebaseAuth);
    }
}