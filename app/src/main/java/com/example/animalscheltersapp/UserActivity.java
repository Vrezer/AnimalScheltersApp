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

    private FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    AdapterToRecyleView myAdapter;
    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //ANDROID COMPONENTS
        recyclerView=findViewById(R.id.recylewView);
        toolbar=findViewById(R.id.toolbarUser);
        setSupportActionBar(toolbar);
        nav=findViewById(R.id.navmenu);
        drawerLayout=findViewById(R.id.UserLayout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home :
                        recreate();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.aboutUs :
                        makeToast("ABOUT US");
                        break;
                }
                return true;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // RecyleView
        FirebaseRecyclerOptions<Animal> options = new FirebaseRecyclerOptions.Builder<Animal>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Animal"), Animal.class)
                        .build();

        myAdapter=new AdapterToRecyleView(options,getApplicationContext());
        recyclerView.setAdapter(myAdapter);




        firebaseAuth= FirebaseAuth.getInstance();
    }




    public void ClickHome(View view) {recreate();}
    public void ClickContact(View view){}
    public void ClickSettings(View view){}
    public void ClickLogOut(View view){
        //firebaseAuth.signOut();
        makeToast("Wylogowanie...");
        intentActivity(this,testowanie_usunac.class);
    }





    //BUTTON OPTIONS



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

    private static void intentActivity(Activity activity,Class aclass)
    {
        Intent intent = new Intent(activity,aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
