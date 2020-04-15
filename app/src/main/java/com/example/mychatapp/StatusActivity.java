package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    private Toolbar stToolbar;
    private EditText mStatus;
    private Button mSaveButton;
    String settingsstatusvalue;


    //Firebase Auth
    private DatabaseReference mStatusDatabase;
    private  FirebaseUser current_user;


    //ProgressDiolouge
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);


        //Firebase
        current_user= FirebaseAuth.getInstance().getCurrentUser();
        String uid=current_user.getUid();
        mStatusDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        stToolbar=findViewById(R.id.status_page_toolbar);
        setSupportActionBar(stToolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settingsstatusvalue=getIntent().getStringExtra("statusvalue");

        progressDialog=new ProgressDialog(this);



        mStatus=findViewById(R.id.status_StatusEdittextid);
        mSaveButton=findViewById(R.id.status_savebuttonid);
            mStatus.setText(settingsstatusvalue);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Saving Changes");
                progressDialog.setMessage("Please Wait While We Are Saving The changes");
                progressDialog.show();

                String status=mStatus.getText().toString();
                mStatusDatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                mStatus.setText("");
                            }
                            else{
                                Toast.makeText(StatusActivity.this, "Sorry Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                    }
                });
            }
        });



    }
}
