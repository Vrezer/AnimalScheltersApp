package com.example.animalscheltersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSettingsChangePassword extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    EditText passwordLast,passwordNew,passwordNew2;
    String lastPassword;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings_change_password);
        //ANDROID COMPONENTS
        toolbar=findViewById(R.id.toolbarSettingsChangePassword);
        nav=findViewById(R.id.navmenuSettingsChangePassword);
        drawerLayout=findViewById(R.id.UserSettingsChangePasswordID);
        passwordLast=findViewById(R.id.userSettingsChangePasswordLastPassword);
        passwordNew=findViewById(R.id.userSettingsChangePasswordNewPassword);
        passwordNew2=findViewById(R.id.userSettingsChangePasswordNewPassword2);
        button=findViewById(R.id.settingsButtonChangePasswordAccept);
        firebaseAuth = FirebaseAuth.getInstance();
        lastPassword="";

        //Display drawer
        DrawerMenu();

        //FUNCTION
        button.setOnClickListener(new View.OnClickListener() {
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

    private void ChangePassword()
    {
        String tmp=firebaseAuth.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(tmp);
        ValueEventListener getData = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String lolo = user.getPassword();
                if (!Empty(passwordLast)&&!Empty(passwordNew)&&!Empty(passwordNew2)) {
                    if(StrongPassword(passwordNew)) {
                        if (lolo.equals(passwordLast.getText().toString())) {
                            if (EqualsNewPassword(passwordNew, passwordNew2)) {
                                newPassword(passwordNew);
                            } else {
                                Toast.makeText(UserSettingsChangePassword.this, "Nowe hasło nie jest identyczne.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            passwordLast.setText("");
                            passwordLast.setHint("Niepoprawne hasło");
                            passwordLast.setHintTextColor(Color.RED);
                        }
                    }
                    else
                    {
                        Toast.makeText(UserSettingsChangePassword.this, "Słabe hasło.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(UserSettingsChangePassword.this, "Uzupełnij wszystkie pola.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(getData);
    }
    private boolean Empty(EditText e)
    {
        return e.getText().toString().isEmpty();
    }

    private boolean EqualsNewPassword(EditText a,EditText b)
    {
        return a.getText().toString().equals(b.getText().toString());
    }

    private boolean StrongPassword(EditText e)
    {
            String pattern_string = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
            Pattern pattern = Pattern.compile(pattern_string);
            Matcher matcher = pattern.matcher(e.getText().toString());
        return matcher.matches();
    }

    private void newPassword(final EditText e)
    {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User").child(firebaseAuth.getUid());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = e.getText().toString();

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            ref.child("password").setValue(e.getText().toString());
                            startActivity(new Intent(UserSettingsChangePassword.this,Settings.class));
                        }
                    }
                });

    }
    public void PasswordInfo(View view)
    {
        Toast.makeText(UserSettingsChangePassword.this,"Minimum 8 znaków,\nMinimum 1 wielka litera,\nMinimum 1 mała litera,\nMinimum 1 cyfra",Toast.LENGTH_SHORT).show();
    }
}