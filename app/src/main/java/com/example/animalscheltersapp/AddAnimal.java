package com.example.animalscheltersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAnimal extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private Animal animal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);
        getSupportActionBar().hide();
        firebaseAuth= FirebaseAuth.getInstance();

        final Button registerButton = findViewById(R.id.AddButtonNewAnimal);

        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AnimalData();
                finish();
                Toast.makeText(AddAnimal.this, "Zwierzak dodany pomyślnie! ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(AddAnimal.this, AdminActivity.class));
                firebaseAuth.signOut();
            }
        });

    }


    private String CheckSex() {

        RadioButton man = findViewById(R.id.RadioMaleCreate);

        if (man.isChecked())
            return "M";
        else
            return "K";
    }

    private void AnimalData()
    {

        EditText nameEditText=findViewById(R.id.NameAnimalCreate);
        EditText ageEditText=findViewById(R.id.AgeCreateAnimal);
        EditText breedEditText=findViewById(R.id.BreedCreateAnimal);
        EditText descriptionEditText=findViewById(R.id.DescriptionCreateAniml);


        String name=nameEditText.getText().toString();
        String breed=breedEditText.getText().toString();
        String description=descriptionEditText.getText().toString();
        String age=ageEditText.getText().toString();
        String sex_tmp=CheckSex();


        //work
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Animal").child(firebaseAuth.getUid()); //Path: Animal/AnimalID/
        animal = new Animal(name,breed,age,description,sex_tmp);
        databaseReference.setValue(animal);

    }
}