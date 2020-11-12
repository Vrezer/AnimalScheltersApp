package com.example.animalscheltersapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Document;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class AddAnimal extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private Animal animal;
    private static final int PICK_IMAGE = 500;
    Uri imagePath;
    private StorageReference storageReference;
    ImageView animalPicture;
    EditText nameEditText,ageEditText,breedEditText,descriptionEditText;
    String name,breed,description,age,sex_tmp,AnimalId;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                animalPicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);
        getSupportActionBar().hide();
        nameEditText=findViewById(R.id.NameAnimalCreate);
        ageEditText=findViewById(R.id.AgeCreateAnimal);
        breedEditText=findViewById(R.id.BreedCreateAnimal);
        descriptionEditText=findViewById(R.id.DescriptionCreateAniml);
        firebaseAuth= FirebaseAuth.getInstance();

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        animalPicture = findViewById(R.id.ImageCreateAnimalFirst);
        final Button registerButton = findViewById(R.id.AddButtonNewAnimal);

        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(ValidateFinally()) {
                    AnimalData();
                    Toast.makeText(AddAnimal.this, "Zwierzak dodany pomyślnie! ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddAnimal.this, AdminActivity.class));
                }
                else
                {
                    ErrorRegister();
                }
            }
        });


        //setAnimalPicture

        animalPicture.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");    //application/*  audio/mp3 doc/pdf
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"),PICK_IMAGE);
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


         name=nameEditText.getText().toString();
         breed=breedEditText.getText().toString();
         description=descriptionEditText.getText().toString();
         age=ageEditText.getText().toString();
         sex_tmp=CheckSex();


        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref=firebaseDatabase.getReference("Animal");
        DatabaseReference newPost=ref.push();

         AnimalId=newPost.getKey();

        StorageReference imageReference = storageReference.child("Animal").child(AnimalId); // Path: // Animal/AnimalID
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
        animal = new Animal(name,age,breed,sex_tmp,description);
        newPost.setValue(animal);

    }

    private boolean ValidateName()
    {
        nameEditText.setTextColor(Color.BLACK);

        if (!nameEditText.getText().toString().isEmpty())
            return true;
        else
        {
            nameEditText.setTextColor(Color.RED);
            nameEditText.setHintTextColor(Color.RED);
            return false;
        }
    }

    private boolean ValidateAge()
    {
        int age_tmp;
        ageEditText.setTextColor(Color.BLACK);
        if(!ageEditText.getText().toString().isEmpty()) {
            age_tmp=Integer.valueOf(ageEditText.getText().toString().trim());
            if (age_tmp >= 0 && age_tmp < 99)
                return true;
            else {
                ageEditText.setTextColor(Color.RED);
                return false;
            }
        }
        else {
            ageEditText.setHintTextColor(Color.RED);
            return false;
        }
    }

    private boolean ValidateBreed()
    {
        breedEditText.setTextColor(Color.BLACK);
        if (!breedEditText.getText().toString().isEmpty())

            return true;
        else
            {
                breedEditText.setTextColor(Color.RED);
                breedEditText.setHintTextColor(Color.RED);
                return false;
                }
    }
    private boolean ValidateDescription()
    {
        descriptionEditText.setTextColor(Color.BLACK);
        if (!descriptionEditText.getText().toString().isEmpty())
            return true;
        else
        {
            descriptionEditText.setTextColor(Color.RED);
            descriptionEditText.setHintTextColor(Color.RED);
            return false;
        }
    }

    private void ErrorRegister()
    {
        Toast.makeText(this,"Sprawdz dane! ",Toast.LENGTH_LONG).show();
    }

    private boolean ValidateFinally()
    {
        if(ValidateName()&&ValidateBreed()&&ValidateAge() && ValidateDescription())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}