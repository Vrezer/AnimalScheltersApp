package com.example.animalscheltersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        firebaseAuth= FirebaseAuth.getInstance();
        getSupportActionBar().hide();

    }

    public void LogOut(View view)
    {
        Toast.makeText(this,"Wylogowanie",Toast.LENGTH_LONG).show();
        firebaseAuth.signOut();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void CreateAnimal(View view)
    {
        startActivity(new Intent(this, AddAnimal.class));
    }

}
