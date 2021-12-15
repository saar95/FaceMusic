package com.lampa.emotionrecognition;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;





public class RegisterActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void SignUPOnClick(View view) {
        EditText emailEditText=findViewById(R.id.editText_email);
        EditText passwordEditText=findViewById(R.id.editText_password);
        EditText firstnameEditText=findViewById(R.id.editText_First_Name);
        EditText lastnameEditText=findViewById(R.id.editText_Last_Name);
        EditText ageEditText=findViewById(R.id.editText_Age);


        if (emailEditText.getText().toString().trim().length() == 0 || passwordEditText.getText().toString().trim().length() == 0 || firstnameEditText.getText().toString().trim().length() == 0 || lastnameEditText.getText().toString().trim().length() == 0 || ageEditText.getText().toString().trim().length() == 0){
            Toast.makeText(RegisterActivity.this,"fill all the required fields ",Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this,"User already exists",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }


    public void ReturnToLoginScreenOnClick(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }
}
