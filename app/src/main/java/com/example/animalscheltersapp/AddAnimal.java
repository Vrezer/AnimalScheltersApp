package com.example.animalscheltersapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Document;

import java.io.IOException;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class AddAnimal extends AppCompatActivity {

//VARIABLES
    private static final int PICK_IMAGE = 500;
    Uri imagePath;
    private StorageReference storageReference;
    ImageView animalPicture;
    EditText nameEditText,ageEditText,breedEditText,descriptionEditText;
    String name,breed,description,age,sex_tmp,AnimalId,id;
    Button registerButton;
    String profileImageUrl;
    RadioButton man;
    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;

//set image fun
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

        //ANDROID COMPONENT
        nameEditText=findViewById(R.id.NameAnimalCreate);
        ageEditText=findViewById(R.id.AgeCreateAnimal);
        breedEditText=findViewById(R.id.BreedCreateAnimal);
        descriptionEditText=findViewById(R.id.DescriptionCreateAniml);
        animalPicture = findViewById(R.id.ImageCreateAnimalFirst);
        registerButton = findViewById(R.id.AddButtonNewAnimal);
        man = findViewById(R.id.RadioMaleCreate);
        toolbar=findViewById(R.id.toolbarAdminAddAnimal);
        nav=findViewById(R.id.navmenuAdminAddAnimal);
        drawerLayout=findViewById(R.id.AddAnimalLayout);
        firebaseAuth = FirebaseAuth.getInstance();
        //Display drawer
        DrawerMenu();


        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        //Add to database function
        registerButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(ValidateFinally()) {
                    AnimalData();
                    makeToast("Zwierzak dodany pomyślnie.");
                    startActivity(new Intent(AddAnimal.this, AdminActivity.class));
                }
                else
                {
                    makeToast("Sprawdź dane.");
                }
            }
        });


        //setAnimalPicture

        animalPicture.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"),PICK_IMAGE);
            }
        });
    }

    //VARIABLE
    private String Name() {return  name=nameEditText.getText().toString();}
    private String Breed() {return  breed=breedEditText.getText().toString();}
    private String Description() {return  description=descriptionEditText.getText().toString();}
    private String Age() {return  age=ageEditText.getText().toString();}
    private String Sex() {return  sex_tmp=CheckSex();}

    //FUNCTION
    private void AnimalData()
    {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref=firebaseDatabase.getReference("Animal");
        final DatabaseReference newPost=ref.push();

        AnimalId=newPost.getKey();
        final StorageReference imageReference = storageReference.child("Animal").child(AnimalId); // Path: // Animal
        final UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        profileImageUrl=task.getResult().toString();
                        id=AnimalId;
                        Animal animal = new Animal(name, age, breed, sex_tmp, description,profileImageUrl,id);
                        newPost.setValue(animal);
                    }
                });
            }
        });

    }

    private void DrawerMenu()
    {
        drawerMenuHelperAdmin drawerMenuHelperAdmin=new drawerMenuHelperAdmin(nav,actionBarDrawerToggle,toolbar,drawerLayout);
        setSupportActionBar(drawerMenuHelperAdmin.getToolbar());
        drawerMenuHelperAdmin.DrawerMenu(this,firebaseAuth);
    }
    //VALIDATE

    private String CheckSex() {
        if (man.isChecked())
            return "M";
        else return "K";
    }

    private boolean ValidateName()
    {
        nameEditText.setTextColor(Color.BLACK);

        if (!name.isEmpty())
            return true;
        else
        {
            EditTextColor(nameEditText);
            return false;
        }
    }

    private boolean ValidateAge()
    {
        int age_tmp;
        ageEditText.setTextColor(Color.BLACK);
        if(!age.isEmpty()) {
            age_tmp=Integer.parseInt(age);
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
        if (!breed.isEmpty())

            return true;
        else
            {
                EditTextColor(breedEditText);
                return false;
                }
    }

    private boolean ValidateDescription()
    {
        descriptionEditText.setTextColor(Color.BLACK);
        if (!description.isEmpty())
            return true;
        else
        {
            EditTextColor(descriptionEditText);
            return false;
        }
    }

    private boolean ValidateFinally()
    {
        Name();
        Breed();
        Description();
        Sex();
        Age();
        return ValidateName() && ValidateBreed() && ValidateAge() && ValidateDescription();
    }

    private void makeToast(String message)
    {
        Toast.makeText(AddAnimal.this, message, Toast.LENGTH_SHORT).show();
    }


    private void EditTextColor(EditText e)
    {
        e.setTextColor(Color.RED);
        e.setHintTextColor(Color.RED);
    }
}