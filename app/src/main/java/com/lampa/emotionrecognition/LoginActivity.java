package com.lampa.emotionrecognition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String creator="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance("https://face-c2bc7-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef=database.getReference();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
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
                                String key = emailEditText.getText().toString().replace(".","@");
                                myRef.child("Users").child(key).child("creator").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        }
                                        else {
                                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                            creator=String.valueOf(task.getResult().getValue());
                                            if(creator.equals("true"))
                                                startActivity(new Intent(LoginActivity.this,CreatorActivity.class));
                                            else
                                                startActivity(new Intent(LoginActivity.this,WelcomeActivity.class));
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(LoginActivity.this,"Login failed, please try again",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
