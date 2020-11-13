package com.example.animalscheltersapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    Button login;
    EditText emailEdit,passwordEdit;
    static boolean tmp_admin;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        //ANDROID COMPONENT
        login=findViewById(R.id.LoginButton);
        emailEdit=findViewById(R.id.EmailLoginText);
        passwordEdit=findViewById(R.id.PasswordLoginText);

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        //LOGIN
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validate())
                {

                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            CheckEmailVerification();
                            else
                            {
                                makeToast("Blędny e-mail lub hasło.");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            makeToast("Blędny e-mail lub hasło.");
                        }
                    });
                }
            }
        });
    }


    private String Email(){return email=emailEdit.getText().toString();};
    private String Password(){return password=passwordEdit.getText().toString();};
    //Go to register Form
    public void Register(View view)
    {
        startActivity(new Intent(MainActivity.this, Register.class));
    }
    //CHECK EMAIL VERIFICATION
    private void CheckEmailVerification()
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        boolean emailVerified = firebaseUser.isEmailVerified();
        if(!emailVerified)
        {
            makeToast("Zwerifikuj E-mail! ");
            firebaseAuth.signOut();
        }
        else
            { isAdmin(); }
    }
    // CHECK USER STATUS
    private void isAdmin()
    {
        databaseReference = firebaseDatabase.getReference("User").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                tmp_admin = user.isAdmin();
                if (!tmp_admin)
                {
                    makeToast("Logowanie...");
                    startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
                else
                    {
                    makeToast("Logowanie Admin...");
                    startActivity(new Intent(MainActivity.this, AdminActivity.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    //VALIDATE STRING EMPTY
    private boolean Validate()
    {
        Email();
        Password();
        if (!email.isEmpty() && !password.isEmpty())
        { return true; }
        else
        {
            passwordEdit.setHintTextColor(Color.RED);
            emailEdit.setHintTextColor(Color.RED);
            makeToast("Wpisz E-mail lub Hasło! ");
            return false;
        }
    }

    private void makeToast(String message)
    {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
