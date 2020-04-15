package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.print.PageRange;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private ImageView profile_ImageView;
    private TextView profile_nameTextview;
    private  TextView profile_statusTextview;
    private  TextView profile_totalFriendTextview;
    private Button sendFriendRequestButton;

    private  ProgressDialog mProgressDiolouge;


    DatabaseReference databaseReference;
    DatabaseReference mfriendRequestDatabase;
    private FirebaseUser mCurrentUser;



    private  ProgressDialog progressDialog;
    List<Users> usersList;

    private  String mCurrent_state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final String userid=getIntent().getStringExtra("key");

        profile_ImageView=findViewById(R.id.profile_circleImageViewid);
        profile_nameTextview=findViewById(R.id.profile_nameTextviewid);
        profile_statusTextview=findViewById(R.id.profile_statusTextvieid);
        profile_totalFriendTextview=findViewById(R.id.profile_TotalFriendsTextview);
        sendFriendRequestButton=findViewById(R.id.profile_SendFriendRequestButtonid);

        mCurrent_state="not_friends";


        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        mfriendRequestDatabase=FirebaseDatabase.getInstance().getReference().child("Friend_Request");
        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();



         mProgressDiolouge=new ProgressDialog(this);
        mProgressDiolouge.setTitle("Loading User Data");
        mProgressDiolouge.setMessage("Please wait while we load the user Data");
        mProgressDiolouge.setCanceledOnTouchOutside(false);
        mProgressDiolouge.show();

        sendFriendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(mCurrent_state.equals("not_friends")){
                                mfriendRequestDatabase.child(mCurrentUser.getUid()).child(userid).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                                mfriendRequestDatabase.child(userid).child(mCurrentUser.getUid()).child("request_type").setValue("Received").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(ProfileActivity.this, "Request Sent", Toast.LENGTH_SHORT).show();
                                                            }
                                                    }
                                                });
                                        }
                                        else{
                                            Toast.makeText(ProfileActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
            }
        });





        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String  name=dataSnapshot.child("name").getValue().toString();
                    String  status=dataSnapshot.child("status").getValue().toString();
                    String  image=dataSnapshot.child("thumb_image").getValue().toString();

                    profile_nameTextview.setText(name);
                    profile_statusTextview.setText(status);
                Picasso.with(ProfileActivity.this).load(image).placeholder(R.drawable.avatarimage).into(profile_ImageView);
                    mProgressDiolouge.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
    }
}
