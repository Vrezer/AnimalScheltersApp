package com.example.animalscheltersapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DeleteAnimal extends AppCompatActivity {

    TextView header;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_delete_animal);
        header = findViewById(R.id.DeleteAnimalHeadText);
        listView = findViewById(R.id.listDeleteAnimal);
       ShowList();
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DeleteAnimal.this, "Zwierzak ! ", Toast.LENGTH_LONG).show();
            }
        });*/

    }


    public void ShowList()
    {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference ref=firebaseDatabase.getReference("Animal");
        final ArrayList<Long> test = new ArrayList<>();

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()) {
                    long animal = dataSnapshot.getChildrenCount();
                    test.add(animal);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        String tmp=String.valueOf(test.size());
        header.setText(tmp);
/*
        //ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.select_dialog_item,test);

        //listView.setAdapter(arrayAdapter);*/
    }

}
