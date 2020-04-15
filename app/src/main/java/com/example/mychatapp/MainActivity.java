package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private FirebaseAuth mAuth;

    private ViewPager mviewPager;
    private  SectionPagerAdapter msectionPagerAdapter;
    private TabLayout mTablayot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mToolbar= findViewById(R.id.main_page_toolber);
       setSupportActionBar(mToolbar);
       getSupportActionBar().setTitle("Chat App");


       // finding view pager;
        mTablayot=findViewById(R.id.main_tabsid);
        mviewPager=findViewById(R.id.mainTabpagerid);

        msectionPagerAdapter=new SectionPagerAdapter(getSupportFragmentManager());
        mviewPager.setAdapter(msectionPagerAdapter);
        mTablayot.setupWithViewPager(mviewPager);


    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null){
          sendtoStart();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     super.onCreateOptionsMenu(menu);

     getMenuInflater().inflate(R.menu.main_menu,menu);


     return  true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_logoutMenuButtonid) {
            FirebaseAuth.getInstance().signOut();
            sendtoStart();
        }

       else if(item.getItemId()==R.id.main_settingMenubuttonid){
            Intent intent=new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
        }
       else if(item.getItemId()==R.id.main_alluserMenuButtonid){
           Intent intent=new Intent(MainActivity.this,UsersActivity.class);
           startActivity(intent);
        }

      return  true;
    }
    private void sendtoStart() {
        Intent startIntent=new Intent(MainActivity.this,StartActivity.class);
        startActivity(startIntent);
        finish();
    }
}
