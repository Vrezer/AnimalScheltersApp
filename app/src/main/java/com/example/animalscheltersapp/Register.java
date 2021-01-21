package com.example.animalscheltersapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {


    //
    private FirebaseAuth firebaseAuth;
    final boolean admin = false;
    Button registerButton,termsButton;
    public EditText nameEditText,surnameEditText,ageEditText,emailEditText,passwordEditText,phoneEditText;
    String sex_tmp;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    RadioButton man,woman;
    public Pattern pattern;
    public String pattern_string;
    public Matcher matcher;
    String email,password,name,surname,age,phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //ANDROID COMPONENT
        registerButton = findViewById(R.id.RegisterButton);
        nameEditText=findViewById(R.id.NameRegisterText);
        surnameEditText=findViewById(R.id.SurnameRegiserText);
        ageEditText=findViewById(R.id.AgeRegisterText);
        emailEditText=findViewById(R.id.EmailRegisterText);
        passwordEditText=findViewById(R.id.PasswordRegisterText);
        phoneEditText=findViewById(R.id.PhoneRegisterText);
        final Switch tmp=findViewById(R.id.TermsSwitchRgister);
        man=findViewById(R.id.SettingsEditProfileMaleSex);
        woman=findViewById(R.id.SettingsEditProfileWomanSex);
        termsButton=findViewById(R.id.AcceptButtonTerms);

        registerButton.setEnabled(false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();


        //Accept terms
        tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerButton.setEnabled(tmp.isChecked());
            }
        });

        //Register form
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateFinally())
                {
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                sendActivateEmail();
                                userData();
                                makeToast("Rejestracja przebiegła pomyślnie!");
                                finish();
                                startActivity(new Intent(Register.this, MainActivity.class));
                                firebaseAuth.signOut();
                            }
                        }
                    });
                }
                else
                    ErrorRegister();
            }
        });

    }
    // Get value
    private String Email() { return email=emailEditText.getText().toString(); }
    private String Password() { return password=passwordEditText.getText().toString(); }
    private String Name() { return name=nameEditText.getText().toString(); }
    private String Surname() { return surname=surnameEditText.getText().toString(); }
    private String Age() { return age=ageEditText.getText().toString(); }
    private String Phone() { return phone=phoneEditText.getText().toString(); }
    private String Sex() { return sex_tmp=CheckSex(); }
    //function
    private void sendActivateEmail()
    {
        FirebaseUser firebaseUser2=firebaseAuth.getCurrentUser();
        if (firebaseUser2!=null)
        {
            firebaseUser2.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) { }});
        }
    }
    private void userData()
    {

        firebaseDatabase = FirebaseDatabase.getInstance();
        //Path: User/UserUID/
        DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(firebaseAuth.getUid());
        User user = new User(email, password, name, surname, age, phone, sex_tmp, admin,firebaseAuth.getUid());
        databaseReference.setValue(user);
    }
    public void goTerms(View view)
    {
        startActivity(new Intent(Register.this, Terms.class));
        makeToast("Warunki korzystania z użytkowania. ");
    }
    // VALIDATE //
    private boolean ValidateFinally()
    {
        Email();
        Password();
        Name();
        Surname();
        Age();
        Phone();
        Sex();
        return ValidateEmail() && ValidatePassword() && ValidateName() && ValidateSurname() && ValidateAge() && ValidateNumber() && ValidateSex();
    }
    private boolean ValidateEmail()
    {
        if(!email.isEmpty())
        {
            pattern_string = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";
            pattern = Pattern.compile(pattern_string);
            matcher = pattern.matcher(email);
            emailEditText.setTextColor(Color.BLACK);
            if (matcher.matches()) {
                return true;
            }else {
                emailEditText.setTextColor(Color.RED);
                return false;
            }
        }
        else
        {
            emailEditText.setHintTextColor(Color.RED);
            return false;
        }

    }
    private boolean ValidatePassword()
    {
        if(!password.isEmpty())
        {
            pattern_string = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
            pattern = Pattern.compile(pattern_string);
            matcher = pattern.matcher(password);
            passwordEditText.setTextColor(Color.BLACK);
            if (matcher.matches())
                return true;
            else {
                passwordEditText.setTextColor(Color.RED);
                return false;
            }
        }
        else
        {
            passwordEditText.setHintTextColor(Color.RED);
            return false;
        }
    }
    private boolean ValidateName()
    {
        if(!name.isEmpty()) {
            pattern_string = "^(?=.*[a-z])(?=.*[A-Z]).{3,}$";
            pattern = Pattern.compile(pattern_string);
            matcher = pattern.matcher(name);
            nameEditText.setTextColor(Color.BLACK);
            if (matcher.matches())
                return true;

            else {
                nameEditText.setTextColor(Color.RED);
                return false;
            }
        }
        else
        {
            nameEditText.setHintTextColor(Color.RED);
            return false;
        }
    }
    private boolean ValidateSurname()
    {
        if(!surname.isEmpty()) {
            pattern_string = "^(?=.*[a-z])(?=.*[A-Z]).{2,}$";
            pattern = Pattern.compile(pattern_string);
            matcher = pattern.matcher(surname);
            surnameEditText.setTextColor(Color.BLACK);
            if (matcher.matches())
                return true;

            else {
                surnameEditText.setTextColor(Color.RED);
                return false;
            }
        }
        else
        {
            surnameEditText.setHintTextColor(Color.RED);
            return false;
        }
    }
    private boolean ValidateAge()
    {
        int age_tmp;
        ageEditText.setTextColor(Color.BLACK);
        if(!age.isEmpty()) {
            age_tmp=Integer.parseInt(age);
            if (age_tmp >= 18 && age_tmp < 99)
                return true;
            else {
                ageEditText.setTextColor(Color.RED);
                makeToast("Jesteś niepełnoletni!");
                return false;
            }
        }
        else {
            ageEditText.setHintTextColor(Color.RED);
            return false;
        }
    }
    private boolean ValidateNumber()
    {
        phoneEditText.setTextColor(Color.BLACK);
        if (!phone.isEmpty()) {
            if(phone.length()!=9)
            {
                phoneEditText.setTextColor(Color.RED);
                return false;
            }
            else
                return true;
        }
        else
        {
            phoneEditText.setHintTextColor(Color.RED);
            return false;
        }
    }
    private boolean ValidateSex()
    {
        man.setTextColor(Color.BLACK);
        woman.setTextColor(Color.BLACK);
        if(man.isChecked()||woman.isChecked())
        { return true; }
        else
        {
            man.setTextColor(Color.RED);
            woman.setTextColor(Color.RED);
            return false;
        }
    }
    // CHECK Sex //
    private String CheckSex()
    { if (man.isChecked())
            return "M";
        else return "K"; }
    // Error and InfoButton
    public void PasswordInfo(View view) { makeToast("Minimum 8 znaków,\nMinimum 1 wielka litera,\nMinimum 1 mała litera,\nMinimum 1 cyfra"); }
    private void ErrorRegister() { makeToast("Błąd w rejestracji! "); }
    private void makeToast(String message) { Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show(); }
}
