package com.example.animalscheltersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Terms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        getSupportActionBar().hide();
        SetTermsText();

    }

    public void AcceptTerms(View view)
    {
        startActivity(new Intent(Terms.this, Register.class));
    }

    public void SetTermsText()
    {
        TextView text = findViewById(R.id.TermsTextView);
        text.setText("dasdasdhsamdbnsvfjdshgfjkszjvbjdfiluhskgufhmglukgffgvxhlchbvmhdgfhdxhkzgjkkzdkjfdhshkfhhlxckvflg");
    }

}
