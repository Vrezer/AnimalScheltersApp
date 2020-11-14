package com.example.animalscheltersapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UserActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    AdapterToRecyleView myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //ANDROID COMPONENTS
        recyclerView=findViewById(R.id.recylewView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // RecyleView
        FirebaseRecyclerOptions<Animal> options = new FirebaseRecyclerOptions.Builder<Animal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Animal"), Animal.class)
                        .build();

        myAdapter=new AdapterToRecyleView(options,getApplicationContext());
        recyclerView.setAdapter(myAdapter);


        firebaseAuth= FirebaseAuth.getInstance();
    }

    //BUTTON OPTIONS
    public void HomePage(){}
    public void InfoPage(){}
    public void ContactPage(){}
    public void SettingsPage(){}
    public void LogOut(View view)
    {
        firebaseAuth.signOut();
        makeToast("Wylogowanie...");
        startActivity(new Intent(this, MainActivity.class));
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


    public void makeToast(String message) {Toast.makeText(this,message,Toast.LENGTH_SHORT).show();}

}
