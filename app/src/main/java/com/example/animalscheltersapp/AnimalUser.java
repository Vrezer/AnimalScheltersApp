package com.example.animalscheltersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class AnimalUser extends AppCompatActivity {

    ImageView img;
    Button buttonHome,buttonAdopt;
    TextView name,breed,sex,age,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_user);
        Objects.requireNonNull(getSupportActionBar()).hide();


        //ANDROID COMPONENT
        img=findViewById(R.id.animalUserImage);
        name=findViewById(R.id.textViewAnimalUserName);
        breed=findViewById(R.id.textViewAnimalUserBreed);
        sex=findViewById(R.id.textViewAnimalUserSex);
        age=findViewById(R.id.textViewAnimalUserAge);
        description=findViewById(R.id.textViewAnimalUserDescription);
        buttonHome=findViewById(R.id.BackButtonAnimalUser);
        buttonAdopt=findViewById(R.id.adoptButton);

        setData();

    }

    public void Home(View view){startActivity(new Intent(AnimalUser.this, UserActivity.class));}

    private void setData()
    {
        Uri uri = Uri.parse(getIntent().getStringExtra("img"));
        Glide.with(this).load(uri).into(img);
        name.setText(getIntent().getStringExtra("name"));
        breed.setText(getIntent().getStringExtra("breed"));
        age.setText(getIntent().getStringExtra("age"));
        description.setText(getIntent().getStringExtra("description"));
        sex.setText(CheckSex());

    }

    private String CheckSex()
    {
        if (Objects.equals(getIntent().getStringExtra("sex"), "M"))
            return "Samiec";
        else
            return "Samica";
    }
}