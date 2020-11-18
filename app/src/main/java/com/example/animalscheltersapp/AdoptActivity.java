package com.example.animalscheltersapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.ConversationActions;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AdoptActivity extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayout;
    //Animal
    String question1,question2,question3,question4,question5,question6,question7,question8;
    String question9,question10,question11,question12,question13,question14,question15,question16;
    String question17,question18;
    EditText q1,q2,q3,q4,q5,q6,q7,q8;
    EditText q9,q10,q11,q12,q13,q14,q15,q16;
    EditText q17,q18;
    Button sendButton;
    Switch terms;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt);
        //ANDROID COMPONENTS
        toolbar=findViewById(R.id.toolbarAdopt);
        nav=findViewById(R.id.navmenuAdopt);
        drawerLayout=findViewById(R.id.AdoptActivityy);
        firebaseAuth = FirebaseAuth.getInstance();

        q1=findViewById(R.id.adoptQuestion1);
        q2=findViewById(R.id.adoptQuestion2);
        q3=findViewById(R.id.adoptQuestion3);
        q4=findViewById(R.id.adoptQuestion4);
        q5=findViewById(R.id.adoptQuestion5);
        q6=findViewById(R.id.adoptQuestion6);
        q7=findViewById(R.id.adoptQuestion7);
        q8=findViewById(R.id.adoptQuestion8);
        q9=findViewById(R.id.adoptQuestion9);
        q10=findViewById(R.id.adoptQuestion10);
        q11=findViewById(R.id.adoptQuestion11);
        q12=findViewById(R.id.adoptQuestion12);
        q13=findViewById(R.id.adoptQuestion13);
        q14=findViewById(R.id.adoptQuestion14);
        q15=findViewById(R.id.adoptQuestion15);
        q16=findViewById(R.id.adoptQuestion16);
        q17=findViewById(R.id.adoptQuestion17);
        q18=findViewById(R.id.adoptQuestion18);
        terms=findViewById(R.id.switchAdoptForm);
        sendButton=findViewById(R.id.adoptFormButton);
        //Display drawer
        DrawerMenu();

        sendButton.setEnabled(false);

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendButton.setEnabled(terms.isChecked());
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidateFinally())
                { makeToast("Wypełnij wszystkie pola."); }
                else
                { BuildDialog();
                SendData();}
            }
        });
    }




    private void DrawerMenu()
    {
        drawerMenuHelper drawerMenuHelper=new drawerMenuHelper(nav,actionBarDrawerToggle,toolbar,drawerLayout);
        setSupportActionBar(drawerMenuHelper.getToolbar());
        drawerMenuHelper.DrawerMenu(this,firebaseAuth);
    }

    private void BuildDialog()
    {
        new AlertDialog.Builder(this)
                .setTitle("Informacja")
                .setMessage("W dalszej etapie nasi pracownicy bedą kontaktować się telefonicznie.")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(AdoptActivity.this,UserActivity.class));
                    }
                }).show();
    }

    private String getString(EditText e){return e.getText().toString();}


    private void SendData()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String tmp=formatter.format(date);
        question1 = getString(q1);  question2 = getString(q2); question3 = getString(q3); question4 = getString(q4);
        question5 = getString(q5);  question6 = getString(q6); question7 = getString(q7); question8 = getString(q8);
        question9 = getString(q9);  question10 = getString(q10); question11 = getString(q11); question12 = getString(q12);
        question13 = getString(q13);  question14 = getString(q14); question15 = getString(q15); question16 = getString(q16);
        question17 = getString(q17);  question18 = getString(q18);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference ref=firebaseDatabase.getReference("AdoptForm");
        DatabaseReference ref_tmp=ref.push();
        String key=ref_tmp.getKey();
        AdoptModel model=new AdoptModel(firebaseAuth.getUid(),getIntent().getStringExtra("id"),key
        ,question1,question2,question3,question4,question5,question6,question7,question8,
        question9,question10,question11,question12,question13,question14,question15,question16,question17,question18,tmp);
        ref_tmp.setValue(model);
    }

    private boolean geTextEmpty(EditText e)
    {
        return e.getText().toString().isEmpty();
    }

    private boolean ValidateFinally()
    {
        return geTextEmpty(q1) && geTextEmpty(q2) && geTextEmpty(q3) && geTextEmpty(q4) && geTextEmpty(q5) && geTextEmpty(q6) &&
                geTextEmpty(q7) && geTextEmpty(q8) && geTextEmpty(q9) && geTextEmpty(q10) && geTextEmpty(q11) && geTextEmpty(q12) &&
                geTextEmpty(q13) && geTextEmpty(q14) && geTextEmpty(q15) && geTextEmpty(q16) && geTextEmpty(q17) && geTextEmpty(q18);
    }

    private void makeToast(String message)
    {
        Toast.makeText(AdoptActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}