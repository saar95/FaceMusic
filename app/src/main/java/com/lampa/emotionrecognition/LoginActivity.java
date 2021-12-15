package com.lampa.emotionrecognition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(LoginActivity.this,WelcomeActivity.class));
        }
    }

    public void registerOnClick(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

    }

    public void loginOnClick(View view) {
        EditText emailEditText=findViewById(R.id.editText_email);
        EditText passwordEditText=findViewById(R.id.editText_password);
        if (emailEditText.getText().toString().trim().length() == 0 || passwordEditText.getText().toString().trim().length() == 0){
            Toast.makeText(LoginActivity.this,"Please fill all the required fields ",Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this,WelcomeActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this,"Login failed, please try again",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }



    }


}