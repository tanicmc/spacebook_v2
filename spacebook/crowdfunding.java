package com.example.spacebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class crowdfunding extends AppCompatActivity {

    ImageButton cf_backbtn;
    String userId;
    FirebaseUser user;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowdfunding);
        addListenerOnButton();
        user = fAuth.getInstance().getCurrentUser();
        if(user != null) {

        }else{
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }

    };




    private void addListenerOnButton() {
        cf_backbtn = (ImageButton) findViewById(R.id.cf_back);
        cf_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}