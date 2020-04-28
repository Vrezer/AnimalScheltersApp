package com.example.animalscheltersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        firebaseAuth= FirebaseAuth.getInstance();
    }

    public void LogOut(View view)
    {
        firebaseAuth.signOut();
        Toast.makeText(this,"Wylogowanie",Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
    }
}
