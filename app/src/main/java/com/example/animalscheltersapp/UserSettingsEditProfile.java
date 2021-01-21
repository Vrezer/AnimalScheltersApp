package com.example.animalscheltersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserSettingsEditProfile extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    EditText nameEditext,surnameEdittext,ageEdittext,phoneEditText;
    RadioButton man,woman;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings_edit_profile);
        //ANDROID COMPONENTS
        toolbar=findViewById(R.id.toolbarSettingsEditProfile);
        nav=findViewById(R.id.navmenuSettingsEditProfile);
        drawerLayout=findViewById(R.id.settingsEditProfile);
        nameEditext=findViewById(R.id.userSettingsEditProfileName);
        surnameEdittext=findViewById(R.id.userSettingsEditProfileSurname);
        ageEdittext=findViewById(R.id.userSettingsEditProfileAge);
        phoneEditText=findViewById(R.id.userSettingsEditProfileNumber);
        man=findViewById(R.id.SettingsEditProfileMaleSex);
        woman=findViewById(R.id.SettingsEditProfileWomanSex);
        button=findViewById(R.id.settingsButtonEditProfile);
        firebaseAuth=FirebaseAuth.getInstance();

        //Display drawer
        DrawerMenu();
        //DISPLAY USER
        DisplayDate();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData();
            }
        });
    }

    private void DrawerMenu()
    {
        drawerMenuHelper drawerMenuHelper=new drawerMenuHelper(nav,actionBarDrawerToggle,toolbar,drawerLayout);
        setSupportActionBar(drawerMenuHelper.getToolbar());
        drawerMenuHelper.DrawerMenu(this,firebaseAuth);
    }

    private void UpdateData()
    {
        if (nullForm())
        {
            makeToast("Wypełnij wszystkie pola.");
        }
        else if(checkAge())
        {
            if (checkPhone())
            {
                update();
            }
                else
            {
                makeToast("Zły numer telefonu.");
            }
        }
        else
        {
            makeToast("Jesteś niepełnoletni!");
        }
    }

    private void update()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(firebaseAuth.getUid());
        ref.child("name").setValue(nameEditext.getText().toString());
        ref.child("surname").setValue(surnameEdittext.getText().toString());
        ref.child("age").setValue(ageEdittext.getText().toString());
        ref.child("number").setValue(phoneEditText.getText().toString());
        if (man.isChecked())
        ref.child("sex").setValue("M");
        else ref.child("sex").setValue("K");
    }

    private void DisplayDate()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(firebaseAuth.getUid());
        ValueEventListener getData = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user= snapshot.getValue(User.class);
                nameEditext.setText(user.getName());
                surnameEdittext.setText(user.getSurname());
                ageEdittext.setText(user.getAge());
                phoneEditText.setText(user.getNumber());
                if(user.getSex().equals("M"))
                    man.setChecked(true);
                else
                    woman.setChecked(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(getData);
    }

    private boolean nullEdittext(EditText e){return e.getText().toString().isEmpty();}

    private boolean nullForm()
    {
     return (nullEdittext(nameEditext)||nullEdittext(surnameEdittext)||nullEdittext(ageEdittext)||nullEdittext(phoneEditText)) ;
    }

    private void makeToast(String message)
    {
        Toast.makeText(UserSettingsEditProfile.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean checkPhone()
    {
        String phone=phoneEditText.getText().toString();
        return phone.length() == 9;
        }

    private boolean checkAge()
    {
        int age_tmp;
            age_tmp=Integer.parseInt(ageEdittext.getText().toString());
            return (age_tmp >= 18 && age_tmp < 99);
        }

    }
