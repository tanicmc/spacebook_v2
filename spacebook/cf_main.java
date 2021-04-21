package com.example.spacebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class cf_main extends AppCompatActivity {

    ImageButton cf_backbtn;
    String userId;
    FirebaseUser user;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cf_main);



        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new cfhomefragment()).commit();


    }




    private  BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId())
            {
                case R.id.home:
                    selectedFragment= new cfhomefragment();
                    break;
                case R.id.post:
                    selectedFragment= new Fragment();
                   startActivity(new Intent(getApplicationContext(),PostActivity.class));
                    break;
                case R.id.my:
                    selectedFragment= new Fragment();
                    startActivity(new Intent(getApplicationContext(),cfmyfragment.class));
                    break;
                case R.id. notification:
                    selectedFragment= new cfnotificationfragment();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };



}