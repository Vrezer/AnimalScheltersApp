package com.example.animalscheltersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.EventListener;
import java.util.Objects;

public class DisplayAnimal extends AppCompatActivity {

    TextView name,age,breed,sex,description;
    EditText id;
    Button button;
    StorageReference storageReference;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_animal);
        Objects.requireNonNull(getSupportActionBar()).hide();

        name=findViewById(R.id.textViewDisplayName);
        age=findViewById(R.id.textViewDisplayAge);
        breed=findViewById(R.id.textViewDisplayBreed);
        sex=findViewById(R.id.textViewDisplaySex);
        description=findViewById(R.id.textViewDisplayDescription);

        id=findViewById(R.id.idDispalyAnimal);
        button=findViewById(R.id.displayAnimalButton);
        img=findViewById(R.id.imageDisplayAnimal);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DisplayImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void DisplayImage() throws IOException {
        storageReference = FirebaseStorage.getInstance().getReference("Animal").child(id.getText().toString());
        final File file =File.createTempFile("image","jpeg");
        storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                img.setImageBitmap(bitmap);
                GetDataFirebase tmp=new GetDataFirebase();
                tmp.DisplayData(id.getText().toString(),age,name,breed,description,sex);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DisplayAnimal.this, "Bledne ID ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DisplayAnimal.this, DisplayAnimal.class));
            }
        });
    }

}