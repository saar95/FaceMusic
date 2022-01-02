package com.lampa.emotionrecognition;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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






public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText emailEditText,passwordEditText,firstnameEditText,lastnameEditText,ageEditText;
    Button regBtn,retrunToLoginBtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private static final String USER ="User";
    private User user;
    public static boolean creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize User Details
        emailEditText=findViewById(R.id.editText_email);
        passwordEditText=findViewById(R.id.editText_password);
        firstnameEditText=findViewById(R.id.editText_First_Name);
        lastnameEditText=findViewById(R.id.editText_Last_Name);
        ageEditText=findViewById(R.id.editText_Age);
        regBtn=findViewById(R.id.signup_btn);
        retrunToLoginBtn=findViewById(R.id.signin_btn);

        Spinner emotionspinner = findViewById(R.id.emotionspinner);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.creator, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emotionspinner.setAdapter(adapter);
        emotionspinner.setOnItemSelectedListener(this);



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Write a message to the database


    }

    public void SignUPOnClick(View view) {
        database=FirebaseDatabase.getInstance("https://face-c2bc7-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef=database.getReference();
        if (emailEditText.getText().toString().trim().length() == 0 || passwordEditText.getText().toString().trim().length() == 0 || firstnameEditText.getText().toString().trim().length() == 0 || lastnameEditText.getText().toString().trim().length() == 0 || ageEditText.getText().toString().trim().length() == 0){
            Toast.makeText(RegisterActivity.this,"fill all the required fields ",Toast.LENGTH_LONG).show();
        }
        else{
            user = new User(emailEditText.getText().toString(),passwordEditText.getText().toString(),firstnameEditText.getText().toString(),lastnameEditText.getText().toString(),ageEditText.getText().toString(),creator);
            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //FirebaseUser curr_user =mAuth.getCurrentUser();
                                updateUI();
                                if(user.isCreator())
                                    startActivity(new Intent(RegisterActivity.this,CreatorActivity.class));
                                else
                                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                //Toast.makeText(RegisterActivity.this,emailEditText.getText().toString()+passwordEditText.getText().toString()+ageEditText.getText().toString()+lastnameEditText.getText().toString(),Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(RegisterActivity.this,"User already exists",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }

    }


    public void ReturnToLoginScreenOnClick(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }
    public void updateUI(){
        String keyId = emailEditText.getText().toString().replace(".","@");;
        myRef.child("Users").child(keyId).setValue(user);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        if(text.equals("True"))
            creator=true;
        else
            creator=false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home_screen:
            case R.id.menu_logout:
            case R.id.menu_back:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                return true;
            case R.id.menu_refresh:
                startActivity(new Intent(this,RegisterActivity.class));
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



}
