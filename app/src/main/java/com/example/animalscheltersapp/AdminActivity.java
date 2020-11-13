package com.example.animalscheltersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class AdminActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        firebaseAuth= FirebaseAuth.getInstance();
        Objects.requireNonNull(getSupportActionBar()).hide();

    }

    public void LogOut(View view)
    {
        Toast.makeText(this,"Wylogowanie...",Toast.LENGTH_SHORT).show();
        firebaseAuth.signOut();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void CreateAnimal(View view) { startActivity(new Intent(this, AddAnimal.class)); }

    public void DeleteAnimal(View view) { startActivity(new Intent(this, DeleteAnimal.class)); }

    public void DisplayAnimal(View view) {startActivity(new Intent(this, DisplayAnimal.class));}

    public void UpdateAnimal(View view) {startActivity(new Intent(this, UpdateAnimal.class));}

    public void DisplayUser(View view) {startActivity(new Intent(this, DisplayUser.class));}

    public void AccessAdmin(View view) {startActivity(new Intent(this, GetAccessAdmin.class));}
}
