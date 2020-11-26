package com.example.animalscheltersapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_adoptForm_display extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    TextView nameAnimal,breedAnimal;
    TextView nameUser,surnameUser,phoneUser;
    TextView question1,question2,question3,question4,question5,question6,question7,question8;
    TextView question9,question10,question11,question12,question13,question14,question15,question16;
    TextView question17,question18;
    Button removeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_adopt_form_display);
        //ANDROID COMPONENTS
        nameAnimal=findViewById(R.id.textViewAdoptDisplayNameAnimal);
        breedAnimal=findViewById(R.id.textViewAdoptDisplayBreedAnimal);
        nameUser=findViewById(R.id.textViewAdoptDisplayNameUser);
        surnameUser=findViewById(R.id.textViewAdoptDisplaySurnameUser);
        phoneUser=findViewById(R.id.textViewAdoptDisplayPhoneUser);
        question1=findViewById(R.id.textViewAdoptDisplayQuestion1);
        question2=findViewById(R.id.textViewAdoptDisplayQuestion2);
        question3=findViewById(R.id.textViewAdoptDisplayQuestion3);
        question4=findViewById(R.id.textViewAdoptDisplayQuestion4);
        question5=findViewById(R.id.textViewAdoptDisplayQuestion5);
        question6=findViewById(R.id.textViewAdoptDisplayQuestion6);
        question7=findViewById(R.id.textViewAdoptDisplayQuestion7);
        question8=findViewById(R.id.textViewAdoptDisplayQuestion8);
        question9=findViewById(R.id.textViewAdoptDisplayQuestion9);
        question10=findViewById(R.id.textViewAdoptDisplayQuestion10);
        question11=findViewById(R.id.textViewAdoptDisplayQuestion11);
        question12=findViewById(R.id.textViewAdoptDisplayQuestion12);
        question13=findViewById(R.id.textViewAdoptDisplayQuestion13);
        question14=findViewById(R.id.textViewAdoptDisplayQuestion14);
        question15=findViewById(R.id.textViewAdoptDisplayQuestion15);
        question16=findViewById(R.id.textViewAdoptDisplayQuestion16);
        question17=findViewById(R.id.textViewAdoptDisplayQuestion17);
        question18=findViewById(R.id.textViewAdoptDisplayQuestion18);
        removeButton=findViewById(R.id.AdoptDisplayRemoveButton);

        toolbar=findViewById(R.id.toolbarAdminAdoptFormDisplay);
        nav=findViewById(R.id.navmenuAdminAdoptFormDisplay);
        drawerLayout=findViewById(R.id.AdminAdoptFormDisplayLayout);
        firebaseAuth = FirebaseAuth.getInstance();

        //Display drawer
        DrawerMenu();
        //DISPLAY DATA
        DisplayDataAnimal(getIntent().getStringExtra("idAnimal"));
        DisplayDataUser(getIntent().getStringExtra("idUser"));
        DisplayDataAdoptForm(getIntent().getStringExtra("idAdopt"));


        //delete form
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAdoptForm(getIntent().getStringExtra("idAdopt"));
            }
        });



    }

    private void DrawerMenu()
    {
        drawerMenuHelperAdmin drawerMenuHelperAdmin=new drawerMenuHelperAdmin(nav,actionBarDrawerToggle,toolbar,drawerLayout);
        setSupportActionBar(drawerMenuHelperAdmin.getToolbar());
        drawerMenuHelperAdmin.DrawerMenu(this,firebaseAuth);
    }

    private void DisplayDataAnimal(String tmp)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Animal").child(tmp);

        ValueEventListener getData = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Animal animal= snapshot.getValue(Animal.class);
                nameAnimal.setText(animal.getName());
                breedAnimal.setText(animal.getBreed());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(getData);
    }

    private void DisplayDataUser(String tmp)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(tmp);

        ValueEventListener getData = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user= snapshot.getValue(User.class);
                nameUser.setText(user.getName());
                surnameUser.setText(user.getSurname());
                phoneUser.setText(user.getNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(getData);
    }

    private void DisplayDataAdoptForm(String tmp)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AdoptForm").child(tmp);

        ValueEventListener getData = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AdoptModel model= snapshot.getValue(AdoptModel.class);
                assert model != null;
                question1.setText(model.getQuestion1());
                question2.setText(model.getQuestion2());
                question3.setText(model.getQuestion3());
                question4.setText(model.getQuestion4());
                question5.setText(model.getQuestion5());
                question6.setText(model.getQuestion6());
                question7.setText(model.getQuestion7());
                question8.setText(model.getQuestion8());
                question9.setText(model.getQuestion9());
                question10.setText(model.getQuestion10());
                question11.setText(model.getQuestion11());
                question12.setText(model.getQuestion12());
                question13.setText(model.getQuestion13());
                question14.setText(model.getQuestion14());
                question15.setText(model.getQuestion15());
                question16.setText(model.getQuestion16());
                question17.setText(model.getQuestion17());
                question18.setText(model.getQuestion18());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(getData);
    }

    private void DeleteAdoptForm(final String tmp)
    {
        new AlertDialog.Builder(this)
                .setTitle("Uwaga! ")
                .setMessage("Czy na pewno chcesz usunąć zgłoszenie?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AdoptForm").child(tmp);
                                ref.removeValue();
                                startActivity(new Intent(Admin_adoptForm_display.this, AdoptAdmin.class));
                            }})
                .setNegativeButton(android.R.string.no, null).show();
                            }

}