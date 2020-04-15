package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText login_Email,login_Password;
    private Button login_LoginButton;
    Toolbar mtoolbar;

    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mtoolbar=findViewById(R.id.login_page_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();


        progressDialog=new ProgressDialog(this);

        login_Email=findViewById(R.id.login_emailEdittextid);
        login_Password=findViewById(R.id.login_passwordEdittextid);
        login_LoginButton=findViewById(R.id.login_Loginbuttonid);

        login_LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        String email=login_Email.getText().toString().trim();
                        String password=login_Password.getText().toString().trim();

                        if(email.isEmpty()){
                                login_Email.setError("Please Enter An Email");
                                login_Email.requestFocus();
                                return;
                        }
                        else if(password.isEmpty()){
                            login_Password.setError("Please Enter A Password");
                            login_Password.requestFocus();
                            return;
                        }
                        else{
                            progressDialog.setTitle("Logging In");
                            progressDialog.setMessage("Please Wait while we are check your credentials.");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                            loginuser(email,password);


                        }

            }
        });




    }

    private void loginuser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Intent mainIntent =new Intent(LoginActivity.this,MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();

                        }
                        else{
                            progressDialog.hide();
                            Toast.makeText(LoginActivity.this, "Cant Sign in . Check the form .and try again", Toast.LENGTH_SHORT).show();
                        }
            }
        });

    }
}
