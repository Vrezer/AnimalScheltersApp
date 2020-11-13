package com.example.animalscheltersapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class DeleteAnimal extends AppCompatActivity {

    Button button;
    EditText id;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_animal);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //ANDROID COMPONENT
        button=findViewById(R.id.DeleteAnimalButton);
        id=findViewById(R.id.IdDeleteAnimal);


        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DeleteAnimalProfile();
            }
        });

    }

    private void DeleteAnimalProfile()
    {
        new AlertDialog.Builder(this)
                .setTitle("Warning")
                .setMessage("Czy na pewno chcesz usunąć tego zwierzaka?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {


        storageReference = FirebaseStorage.getInstance().getReference("Animal").child(id.getText().toString());
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DeleteAnimalFun();
                makeToast("Usunięto Zwierzaka.");
                startActivity(new Intent(DeleteAnimal.this, AdminActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                makeToast("Sprawdź Id.");
                startActivity(new Intent(DeleteAnimal.this, DeleteAnimal.class));
            }
        });

                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }
    public void DeleteAnimalFun()
    {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Animal").child(id.getText().toString());
                        ref.removeValue();
    }

    private void makeToast(String message)
    {
        Toast.makeText(DeleteAnimal.this, message, Toast.LENGTH_SHORT).show();
    }
}
