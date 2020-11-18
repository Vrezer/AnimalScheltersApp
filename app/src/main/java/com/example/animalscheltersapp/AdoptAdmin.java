package com.example.animalscheltersapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AdoptAdmin extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    AdapterRecycleViewAdopt myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_admin);
        recyclerView=findViewById(R.id.recylewViewAdopt);
        toolbar=findViewById(R.id.toolbarAdoptAdmin);
        nav=findViewById(R.id.navmenuAdoptAdmin);
        drawerLayout=findViewById(R.id.AdoptAdminLayout);
        firebaseAuth = FirebaseAuth.getInstance();

        //Display drawer
        DrawerMenu();
        //Recycle View
        RecyleView();
    }


    private void RecyleView()
    {
        // REVERSE
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseRecyclerOptions<AdoptModel> options = new FirebaseRecyclerOptions.Builder<AdoptModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference("AdoptForm").orderByChild("date"), AdoptModel.class)
                .build();
        myAdapter=new AdapterRecycleViewAdopt(options,getApplicationContext());
        recyclerView.setAdapter(myAdapter);
    }

    private void DrawerMenu()
    {
        drawerMenuHelperAdmin drawerMenuHelperAdmin=new drawerMenuHelperAdmin(nav,actionBarDrawerToggle,toolbar,drawerLayout);
        setSupportActionBar(drawerMenuHelperAdmin.getToolbar());
        drawerMenuHelperAdmin.DrawerMenu(this,firebaseAuth);
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