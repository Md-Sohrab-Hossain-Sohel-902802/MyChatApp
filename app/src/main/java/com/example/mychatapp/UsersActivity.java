package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mychatapp.Adapters.MyAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UsersActivity extends AppCompatActivity {

    private Toolbar mtoolbar;
    private RecyclerView userRecyclerview;
    private DatabaseReference mUsersDatabase;
    MyAdapter myAdapter;
    private List<Users> usersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        mtoolbar=findViewById(R.id.allusers_appber_id);
        setSupportActionBar(mtoolbar);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setTitle("All Users");

         mUsersDatabase= FirebaseDatabase.getInstance().getReference().child("Users");


        userRecyclerview=findViewById(R.id.users_recyclerviewid);
        userRecyclerview.setHasFixedSize(true);
        userRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        usersList=new ArrayList<>();





    }

    @Override
    protected void onStart() {


        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usersList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    Users userss=dataSnapshot1.getValue(Users.class);
                    userss.setKey(dataSnapshot1.getKey());
                    usersList.add(userss);
                }
                myAdapter=new MyAdapter(UsersActivity.this,usersList);
                userRecyclerview.setAdapter(myAdapter);


                myAdapter.setOnitemclickListener(new MyAdapter.OnItemclickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Users selecteditem=usersList.get(position);
                        String key=selecteditem.getKey();
                        Intent prfileIntent=new Intent(UsersActivity.this,ProfileActivity.class);
                        prfileIntent.putExtra("key",key);
                        startActivity(prfileIntent);
                     }

                });







            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UsersActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        super.onStart();


}
public  static class UsersViewHolder extends  RecyclerView.ViewHolder{

        View mview;

    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);


        mview=itemView;
    }

    public void setName(String name){

        TextView userNameView=mview.findViewById(R.id.user_single_name);
        userNameView.setText(name);

    }

}



}


/*    FirebaseRecyclerAdapter<Users,UsersViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Users, UsersViewHolder>(
            Users.class,
            R.layout.users_single_layout,
            UsersViewHolder.class,
            mUsersDatabase

    ) {
        @Override
        protected void populateViewHolder(UsersViewHolder usersViewHolder, Users users, int i) {
            usersViewHolder.setName(users.getName());
        }
    };*/
