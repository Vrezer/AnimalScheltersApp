package com.example.animalscheltersapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DisplayUser extends AppCompatActivity {

    TextView name,age,surname,sex,mail;
    EditText id;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //ANDROID COMPONENT
        name = findViewById(R.id.textViewDisplayNameUser);
        age = findViewById(R.id.textViewDisplayAgeUser);
        surname =findViewById(R.id.textViewDisplaySurnameUser);
        sex=findViewById(R.id.textViewDisplaySexUser);
        mail=findViewById(R.id.textViewDisplayEmailUser);
        id=findViewById(R.id.idDispalyUser);
        button=findViewById(R.id.displayUserButton);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            DisplayData();
            }
        });
    }


    private void DisplayData() {
        if (!id.getText().toString().isEmpty()) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(id.getText().toString());
            final ValueEventListener getData = new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    User user = snapshot.getValue(User.class);
                    if(snapshot.exists()) {
                        name.setText(user.getName());
                        surname.setText(user.getSurname());
                        mail.setText(user.getEmail());
                        age.setText(user.getAge());
                        if (user.getSex().equals("M"))
                            sex.setText("Meżczyzna");
                        else
                            sex.setText("Kobieta");
                    }
                    else
                    {
                        makeToast("Sprawdź Id.");
                        startActivity(new Intent(DisplayUser.this, DisplayUser.class));
                    }
                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    startActivity(new Intent(DisplayUser.this, DisplayUser.class));
                }
            };
                ref.addValueEventListener(getData);
        }
        else
            makeToast("Wpisz Id.");
    }


    private void makeToast(String message)
    {
        Toast.makeText(DisplayUser.this, message, Toast.LENGTH_SHORT).show();
    }
}