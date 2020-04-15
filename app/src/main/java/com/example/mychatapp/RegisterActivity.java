package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText mDisplayname;
    private EditText mEmail;
    private EditText mPassword;
    private Button mCreteButton;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private ProgressDialog regprogressDialog;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar=findViewById(R.id.register_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    regprogressDialog=new ProgressDialog(this);


        mAuth = FirebaseAuth.getInstance();

        mDisplayname=findViewById(R.id.reg_displaynameEdittextid);
        mEmail=findViewById(R.id.reg_emailEdittextid);
        mPassword=findViewById(R.id.reg_passwordEdittextid);
        mCreteButton=findViewById(R.id.reg_createButtonid);




        mCreteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                       String displayname=mDisplayname.getText().toString();
                       String email=mEmail.getText().toString();
                       String password=mPassword.getText().toString();

                if(email.isEmpty()){
                    emailblankerror();
                }
                else if(password.isEmpty()){
                    passwordblankerror();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    wrongemailerror();
                }
                else if(password.length()<6){
                    passwordlengtherror();
                }
                else  if(displayname.isEmpty()){
                    mDisplayname.setError("A Name Must Be neded");
                    mDisplayname.requestFocus();
                    return;
                }
                else {
                    regprogressDialog.setTitle("Registering User");
                    regprogressDialog.setMessage("Please wait while we create your account");
                    regprogressDialog.setCanceledOnTouchOutside(false);
                    regprogressDialog.show();
                    register_user(displayname, email, password);
                }
           }
        });



    }


    public void emailblankerror(){
        mEmail.setError("Enter An Email");
        mEmail.requestFocus();
        return;
    }
    public void wrongemailerror(){
        mEmail.setError("Enter A Valid Email");
        mEmail.requestFocus();
        return;
    }
    public void passwordblankerror(){
        mPassword.setError("Enter your password");
        mPassword.requestFocus();
        return;
    }
    public void passwordlengtherror(){
        mPassword.setError("password should be 6 characters");
        mPassword.requestFocus();
        return;
    }

    private void register_user(final String displayname, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
                        String uid=current_user.getUid();
                        mDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                        HashMap<String,String> usermap=new HashMap<>();
                        usermap.put("name",displayname);
                        usermap.put("status","Hi guys i am Using Chat App");
                        usermap.put("image","default");
                        usermap.put("thumb_image","default");
                    mDatabase.setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                regprogressDialog.dismiss();
                                Intent mainIntent=new Intent(RegisterActivity.this,MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                    });
                    




                    }
                    else{
                        regprogressDialog.hide();
                        Toast.makeText(RegisterActivity.this, "Can not sign in please check the box and try again" +
                                ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}
