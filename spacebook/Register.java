package com.example.spacebook;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spacebook.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends BaseActivity {
    private EditText registeremail, registerpassword,registersecondpassword;
    private TextView logintext;
    private FirebaseAuth mAuth;
    Button registerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registeremail = findViewById(R.id.email);
        registerpassword = findViewById(R.id.password);
        registersecondpassword = findViewById(R.id.secondpassword);
        registerbtn = findViewById(R.id.registerBtn);
        logintext = findViewById(R.id.logintext);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }
    }

    private void onAuthSuccess(FirebaseUser firebaseUser) {
        String email = firebaseUser.getEmail();
        String username = email;
        if (email != null && email.contains("@")) {
            username = email.split("@")[0];
        }

        User user = new User(username, email);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(firebaseUser.getUid()).setValue(user);

        startActivity(new Intent(this, MainActivity.class));
        finish();

    }



    private void signIn() {
        String email = registeremail.getText().toString().trim();
        String password = registerpassword.getText().toString().trim();
        String secondpassword = registersecondpassword.getText().toString().trim();

        if (validateForm(email, password,secondpassword)) {
            showProgressDialog();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    hideProgressDialog();
                    if (task.isSuccessful()) {
                        onAuthSuccess(task.getResult().getUser());
                    } else {
                        Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void signUp() {
        String email = registeremail.getText().toString().trim();
        String password = registerpassword.getText().toString().trim();
        String secondpassword = registersecondpassword.getText().toString().trim();

        if (validateForm(email, password,secondpassword)) {
            showProgressDialog();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    hideProgressDialog();
                    if (task.isSuccessful()) {
                        onAuthSuccess(task.getResult().getUser());
                    } else {
                        Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean validateForm(String email, String password, String registersecondpassword) {
        if (TextUtils.isEmpty(email)) {
            registeremail.setError("Please enter your email!");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            registerpassword.setError("Please enter password!");
            return false;
        }else if (!password.equals(registersecondpassword)) {
            registerpassword.setError("Password not the same!");
            return false;
        }else if (password.length() < 8){
            registerpassword.setError("Password must >8 letter!");
            return false;
        } else {
            registeremail.setError(null);
            registerpassword.setError(null);
            return true;
        }
    }
}
