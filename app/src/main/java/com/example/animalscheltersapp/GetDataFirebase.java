package com.example.animalscheltersapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

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

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

import static androidx.core.content.ContextCompat.startActivity;

public class GetDataFirebase {


    StorageReference storageReference;
    ImageView img;


    public void DisplayData(String id, final TextView age, final TextView name, final TextView breed, final TextView description, final TextView sex)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Animal").child(id);

        ValueEventListener getData = new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Animal animal= snapshot.getValue(Animal.class);
                age.setText(animal.getAge());
                name.setText(animal.getName());
                breed.setText(animal.getBreed());
                description.setText(animal.getDescription());
                if(animal.getSex().equals("M"))
                    sex.setText("Samiec");
                else
                    sex.setText("Samica");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(getData);
    }



    // in future optimize

    /*private void DisplayImage(final String id, final TextView age, final TextView name, final TextView breed, final TextView description, final TextView sex) throws IOException {
        storageReference = FirebaseStorage.getInstance().getReference("Animal").child(id);
        final File file =File.createTempFile("image","jpeg");
        storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                img.setImageBitmap(bitmap);
                GetDataFirebase tmp=new GetDataFirebase();
                tmp.DisplayData(id,age,name,breed,description,sex);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }*/
}
