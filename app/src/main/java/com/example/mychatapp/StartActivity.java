package com.example.mychatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button mRegButton,mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mLoginButton=findViewById(R.id.start_loginbuttonid);
        mRegButton=findViewById(R.id.startRegButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent=new Intent(StartActivity.this,LoginActivity.class);
                        startActivity(intent);
            }
        });
        mRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_intent=new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(reg_intent);
            }
        });

    }
}
