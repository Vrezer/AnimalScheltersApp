package com.example.animalscheltersapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Admin_adoptForm_display extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_adopt_form_display);
        //ANDROID COMPONENTS
        toolbar=findViewById(R.id.toolbarAdminAdoptFormDisplay);
        nav=findViewById(R.id.navmenuAdminAdoptFormDisplay);
        drawerLayout=findViewById(R.id.AdminAdoptFormDisplayLayout);
        firebaseAuth = FirebaseAuth.getInstance();

        //Display drawer
        DrawerMenu();

    }

    private void DrawerMenu()
    {
        drawerMenuHelperAdmin drawerMenuHelperAdmin=new drawerMenuHelperAdmin(nav,actionBarDrawerToggle,toolbar,drawerLayout);
        setSupportActionBar(drawerMenuHelperAdmin.getToolbar());
        drawerMenuHelperAdmin.DrawerMenu(this,firebaseAuth);
    }

}