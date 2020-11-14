package com.example.animalscheltersapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class UpdateAnimal extends AppCompatActivity {


    //VARIABLES

    EditText name,age,breed,sex,description,id;
    Button button,button2;
    StorageReference storageReference;
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_animal);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //ANDROID COMPONENT

         name=findViewById(R.id.nameUpdateAnimal);
         age=findViewById(R.id.ageUpdateAnimal);
         breed=findViewById(R.id.breedUpdateAnimal);
         sex=findViewById(R.id.sexUpdateAnimal);
         description=findViewById(R.id.descriptionUpdateAnimal);
         id=findViewById(R.id.idUpdateAnimal);

         button=findViewById(R.id.updateAnimalButton);
         button2=findViewById(R.id.updateAnimalButton2);
         img =findViewById(R.id.imageUpdateAnimal);



         // Display Animal
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 try {
                     displayData();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }

             }
         });

         //Update
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData();
                makeToast("Dane zaktualizowano!");

            }
        });

        /*

        //setAnimalPicture  in future

        img.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select image"),PICK_IMAGE);
            }
        });
        */

    }


    //Function

    private void displayData() throws IOException {

        final File file =File.createTempFile("image","jpeg");
        storageReference = FirebaseStorage.getInstance().getReference("Animal").child(id.getText().toString());
        storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                img.setImageBitmap(bitmap);
                // Display Data Animal
                GetDataFirebase tmp=new GetDataFirebase();
                tmp.DisplayData(id.getText().toString(),age,name,breed,description,sex);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                makeToast("Błędne Id.");
                startActivity(new Intent(UpdateAnimal.this, UpdateAnimal.class));
            }
        });
    }


    private void UpdateData()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Animal").child(id.getText().toString());
        ref.child("name").setValue(name.getText().toString());
        ref.child("age").setValue(age.getText().toString());
        ref.child("breed").setValue(breed.getText().toString());
        ref.child("description").setValue(description.getText().toString());
    }

    private void makeToast(String message)
    {
        Toast.makeText(UpdateAnimal.this, message, Toast.LENGTH_SHORT).show();
    }
}