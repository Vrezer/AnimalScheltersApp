package com.example.animalscheltersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    static boolean tmp_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Button login=findViewById(R.id.LoginButton);
        firebaseAuth = FirebaseAuth.getInstance();



        //LOGIN
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                EditText email=findViewById(R.id.EmailLoginText);
                EditText password=findViewById(R.id.PasswordLoginText);
                String email_tmp=email.getText().toString();
                String password_tmp=password.getText().toString();
                if (Validate())
                {
                    firebaseAuth.signInWithEmailAndPassword(email_tmp,password_tmp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            CheckEmailVerification();
                            else
                            {
                                Toast.makeText(MainActivity.this,"Niepoprawny E-mail lub Hasło",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    }
    //Go to register Form
    public void Register(View view)
    {
        startActivity(new Intent(MainActivity.this, Register.class));
    }
    //CHECK EMAILVERIFICATION
    private void CheckEmailVerification()
    {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        boolean emailVerified = firebaseUser.isEmailVerified();

        if(!emailVerified)
        {
            Toast.makeText(this,"Zwerifikuj E-mail! ",Toast.LENGTH_LONG).show();
            firebaseAuth.signOut();
        }
        else
            {
                isAdmin();
        }
    }
    // CHECK USER STATUS
    private void isAdmin()
    {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              User user = dataSnapshot.getValue(User.class);
               tmp_admin = user.isAdmin();
                if(!tmp_admin)
                    startActivity(new Intent(MainActivity.this, UserActivity.class));

                else
                    startActivity(new Intent(MainActivity.this, AdminActivity.class));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    //VALIDATE STRING EMPTY
    private boolean Validate()
    {
        EditText email=findViewById(R.id.EmailLoginText);
        EditText password=findViewById(R.id.PasswordLoginText);
        if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty())
        {
            return true;
        }
        else
        {
            password.setHintTextColor(Color.RED);
            email.setHintTextColor(Color.RED);
            Toast.makeText(this,"Wpisz E-mail lub Hasło! ",Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
