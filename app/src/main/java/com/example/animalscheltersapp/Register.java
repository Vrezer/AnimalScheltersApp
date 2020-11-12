package com.example.animalscheltersapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private User user;
    final boolean admin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        firebaseAuth=FirebaseAuth.getInstance();

        final Button registerButton = findViewById(R.id.RegisterButton);
        registerButton.setEnabled(false);
        final Switch tmp=findViewById(R.id.TermsSwitchRgister);

        //Accept terms
        tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tmp.isChecked()) {
                    registerButton.setEnabled(true);
                }
                else {
                    registerButton.setEnabled(false);
                    Toast.makeText(Register.this, "Zaakceptuj warunki użytkowania! ", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Register form
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEditText=findViewById(R.id.EmailRegisterText);
                EditText passwordEditText=findViewById(R.id.PasswordRegisterText);
                if (ValidateFinally())
                {
                    String email=emailEditText.getText().toString();
                    String password=passwordEditText.getText().toString();
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {

                            if(task.isSuccessful()) {
                                sendActivateEmail();
                                userData();
                                Toast.makeText(Register.this, "Rejestracja przebiegła pomyślnie! ", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(Register.this, MainActivity.class));
                                firebaseAuth.signOut();
                            }
                            else
                            {
                                Toast.makeText(Register.this, "Rejestracja nie powiodła się! ", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });

    }


    private void sendActivateEmail()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if (firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Log.i("Register","Succesfull");

                }
                else
                    Log.i("Register","Error");
                }
            });

        }
        else
        {

        }
    }

    private void userData()
    {

        EditText nameEditText=findViewById(R.id.NameRegisterText);
        EditText surnameEditText=findViewById(R.id.SurnameRegiserText);
        EditText ageEditText=findViewById(R.id.AgeRegisterText);
        EditText emailEditText=findViewById(R.id.EmailRegisterText);
        EditText passwordEditText=findViewById(R.id.PasswordRegisterText);
        EditText phoneEditText=findViewById(R.id.PhoneRegisterText);

        String email=emailEditText.getText().toString();
        String password=passwordEditText.getText().toString();
        String name=nameEditText.getText().toString();
        String surname=surnameEditText.getText().toString();
        String age=ageEditText.getText().toString();
        String phone=phoneEditText.getText().toString();
        String sex_tmp=CheckSex();


        //work
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(firebaseAuth.getUid()); //Path: User/UserUID/
        user = new User(email,password,name,surname,age,phone,sex_tmp,admin);
        databaseReference.setValue(user);

    }


    public void goTerms(View view)
    {
        startActivity(new Intent(Register.this, Terms.class));
        Toast.makeText(Register.this, "Warunki korzystania z użytkowania ", Toast.LENGTH_LONG).show();
    }
 // VALIDATE //
   private boolean ValidateFinally()
    {
         if(ValidateEmail()&&ValidatePassword()&&ValidateName()&&ValidateSurname() && ValidateAge()&&ValidateNumber()&&ValidateSex())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean ValidateEmail()
    {
        EditText email_tmp=findViewById(R.id.EmailRegisterText);
        if(!email_tmp.getText().toString().isEmpty()) {
            String email_pattern = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
            Pattern pattern = Pattern.compile(email_pattern);
            Matcher matcher;
            matcher = pattern.matcher(email_tmp.getText().toString());
            email_tmp.setTextColor(Color.BLACK);
            if (matcher.matches())
                return true;
            else {
                email_tmp.setTextColor(Color.RED);
                ErrorRegister();
                return false;
            }
        }
        else
        {
            email_tmp.setHintTextColor(Color.RED);
            ErrorRegister();
            return false;
        }
    }

    private boolean ValidatePassword()
    {
        EditText password_tmp=findViewById(R.id.PasswordRegisterText);
        if(!password_tmp.getText().toString().isEmpty())
        {
            String password_pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
            Pattern pattern = Pattern.compile(password_pattern);
            Matcher matcher;
            matcher = pattern.matcher(password_tmp.getText().toString());
            password_tmp.setTextColor(Color.BLACK);
            if (matcher.matches())
                return true;
            else {
                password_tmp.setTextColor(Color.RED);
                ErrorRegister();
                return false;
            }
        }
        else
        {
            password_tmp.setHintTextColor(Color.RED);
            ErrorRegister();
            return false;
        }
    }

    private boolean ValidateName()
    {
        EditText name_tmp=findViewById(R.id.NameRegisterText);
        if(!name_tmp.getText().toString().isEmpty()) {
            String name_pattern = "^(?=.*[a-z])(?=.*[A-Z]).{3,}$";
            Pattern pattern = Pattern.compile(name_pattern);
            Matcher matcher;
            matcher = pattern.matcher(name_tmp.getText().toString());
            name_tmp.setTextColor(Color.BLACK);
            if (matcher.matches())
                return true;

            else {
                name_tmp.setTextColor(Color.RED);
                ErrorRegister();
                return false;
            }
        }
        else
        {
            name_tmp.setHintTextColor(Color.RED);
            ErrorRegister();
            return false;
        }
    }

    private boolean ValidateSurname()
    {
        EditText surname_tmp=findViewById(R.id.SurnameRegiserText);
        if(!surname_tmp.getText().toString().isEmpty()) {
            String name_pattern = "^(?=.*[a-z])(?=.*[A-Z]).{2,}$";
            Pattern pattern = Pattern.compile(name_pattern);
            Matcher matcher;
            matcher = pattern.matcher(surname_tmp.getText().toString());
            surname_tmp.setTextColor(Color.BLACK);
            if (matcher.matches())
                return true;

            else {
                surname_tmp.setTextColor(Color.RED);
                ErrorRegister();
                return false;
            }
        }
        else
        {
            surname_tmp.setHintTextColor(Color.RED);
            ErrorRegister();
            return false;
        }
    }

    private boolean ValidateAge()
    {
        int age_tmp;
        EditText age=findViewById(R.id.AgeRegisterText);
        age.setTextColor(Color.BLACK);
        if(!age.getText().toString().isEmpty()) {
            age_tmp=Integer.valueOf(age.getText().toString().trim());
            if (age_tmp >= 18 && age_tmp < 99)
                return true;
            else {
                age.setTextColor(Color.RED);
                Toast.makeText(this,"Jesteś niepełnoletni!",Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else {
            age.setHintTextColor(Color.RED);
            ErrorRegister();
            return false;
        }
    }

    private boolean ValidateNumber()
    {
        EditText number_tmp = findViewById(R.id.PhoneRegisterText);
        number_tmp.setTextColor(Color.BLACK);
        if (!number_tmp.getText().toString().isEmpty()) {
            if(number_tmp.length()!=9)
            {
                number_tmp.setTextColor(Color.RED);
                ErrorRegister();
                return false;
            }
            else
                return true;
        }
        else
        {
            number_tmp.setHintTextColor(Color.RED);
            ErrorRegister();
            return false;
        }
    }

    private boolean ValidateSex()
    {
        RadioButton man=findViewById(R.id.MaleRegisterButton);
        RadioButton woman=findViewById(R.id.WomanRegisterButton);
        man.setTextColor(Color.BLACK);
        woman.setTextColor(Color.BLACK);
        if(man.isChecked()||woman.isChecked())
        {
            return true;
        }
        else
        {
            man.setTextColor(Color.RED);
            woman.setTextColor(Color.RED);
            return false;
        }
    }

    // CHECK Sex //

    private String CheckSex()
    {

        RadioButton man=findViewById(R.id.MaleRegisterButton);

            if (man.isChecked())
                return "M";
            else
                return "K";

    }

    // Error and InfoButton
    public void PasswordInfo(View view)
    {
        Toast.makeText(this,"Minimum 8 znaków,\nMinimum 1 wielka litera,\nMinimum 1 mała litera,\nMinimum 1 cyfra",Toast.LENGTH_LONG).show();
    }
    private void ErrorRegister()
    {
        Toast.makeText(this,"Błąd w rejestracji! ",Toast.LENGTH_LONG).show();
    }

}
