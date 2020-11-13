package com.example.animalscheltersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class GetAccessAdmin extends AppCompatActivity {

    EditText id;
    Button button,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_access_admin);
        Objects.requireNonNull(getSupportActionBar()).hide();

        id=findViewById(R.id.idAccessAdmin);
        button=findViewById(R.id.accessAdminButton);
        button2=findViewById(R.id.accessAdminButton2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessAdministration(true);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessAdministration(false);
            }
        });
    }

    private void accessAdministration(final boolean flag)
    {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(id.getText().toString());
        if(!id.getText().toString().isEmpty()) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        if (flag) {
                            ref.child("admin").setValue(true);
                            makeToast("Nadano Administratora!  ");
                            startActivity(new Intent(GetAccessAdmin.this, AdminActivity.class));
                        } else {
                            ref.child("admin").setValue(false);
                            makeToast("Zabrano Administratora!  ");
                            startActivity(new Intent(GetAccessAdmin.this, AdminActivity.class));
                        }
                    } else {
                       makeToast("Błędne ID");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else
        {
            makeToast("Podaj ID");
        }
    }

    private void makeToast(String message)
    {
        Toast.makeText(GetAccessAdmin.this, message, Toast.LENGTH_SHORT).show();
    }

}