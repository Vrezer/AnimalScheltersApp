package com.example.animalscheltersapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class UserActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Objects.requireNonNull(getSupportActionBar()).hide();


        firebaseAuth= FirebaseAuth.getInstance();
    }


    public void LogOut(View view)
    {
        firebaseAuth.signOut();
        Toast.makeText(this,"Wylogowanie...",Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
    }
}
