package com.example.animalscheltersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class Settings extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    Button remove,changePassword,updateProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //ANDROID COMPONENTS
        toolbar=findViewById(R.id.toolbarSettings);
        nav=findViewById(R.id.navmenuSettings);
        drawerLayout=findViewById(R.id.settingsID);
        remove=findViewById(R.id.settingsButtonDelete);
        changePassword=findViewById(R.id.settingsButtonChangePassword);
        updateProfile=findViewById(R.id.settingsButtonUpdateProfile);
        firebaseAuth = FirebaseAuth.getInstance();

        //Display drawer
        DrawerMenu();

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteUser();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword();
            }
        });


    }

    private void DrawerMenu()
    {
        drawerMenuHelper drawerMenuHelper=new drawerMenuHelper(nav,actionBarDrawerToggle,toolbar,drawerLayout);
        setSupportActionBar(drawerMenuHelper.getToolbar());
        drawerMenuHelper.DrawerMenu(this,firebaseAuth);
    }

    private void DeleteUser()
    {
        new AlertDialog.Builder(this)
                .setTitle("Uwaga! ")
                .setMessage("Czy na pewno chcesz usunąć profil?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(firebaseAuth.getUid());
                        ref.removeValue();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    System.exit(0);
                                    /*android.os.Process.killProcess(android.os.Process.myPid());
                                    onDestroy();*/
                                }
                            }

                        });
                    }})
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.this,Settings.class));
                    }
                }).show();
    }

    private void ChangePassword() { startActivity(new Intent(Settings.this,UserSettingsChangePassword.class));}

}