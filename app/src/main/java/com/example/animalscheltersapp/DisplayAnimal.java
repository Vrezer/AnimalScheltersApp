package com.example.animalscheltersapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DisplayAnimal extends AppCompatActivity {

    TextView name,age,breed,sex,description;
    EditText id;
    Button button;
    StorageReference storageReference;
    ImageView img;
    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_animal);

        //ANDROID COMPONENT
        name=findViewById(R.id.textViewDisplayName);
        age=findViewById(R.id.textViewDisplayAge);
        breed=findViewById(R.id.textViewDisplayBreed);
        sex=findViewById(R.id.textViewDisplaySex);
        description=findViewById(R.id.textViewDisplayDescription);
        id=findViewById(R.id.idDispalyAnimal);
        button=findViewById(R.id.displayAnimalButton);
        img=findViewById(R.id.imageDisplayAnimal);
        toolbar=findViewById(R.id.toolbarAdminDisplayAnimal);
        nav=findViewById(R.id.navmenuAdminDisplayAnimal);
        drawerLayout=findViewById(R.id.DisplayAnimalLayout);
        firebaseAuth = FirebaseAuth.getInstance();

        //Display drawer
        DrawerMenu();


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

    private void DrawerMenu()
    {
        drawerMenuHelperAdmin drawerMenuHelperAdmin=new drawerMenuHelperAdmin(nav,actionBarDrawerToggle,toolbar,drawerLayout);
        setSupportActionBar(drawerMenuHelperAdmin.getToolbar());
        drawerMenuHelperAdmin.DrawerMenu(this,firebaseAuth);
    }

    private void DisplayImage() throws IOException {
        storageReference = FirebaseStorage.getInstance().getReference("Animal").child(id.getText().toString());
        final File file =File.createTempFile("image","jpeg");
        storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                img.setImageBitmap(bitmap);
                // DISPLAY ANIMAL DATA
                GetDataFirebase tmp=new GetDataFirebase();
                tmp.DisplayData(id.getText().toString(),age,name,breed,description,sex);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                makeToast("Sprawdź Id.");
                startActivity(new Intent(DisplayAnimal.this, DisplayAnimal.class));
            }
        });
    }

    private void makeToast(String message)
    {
        Toast.makeText(DisplayAnimal.this, message, Toast.LENGTH_SHORT).show();
    }

}