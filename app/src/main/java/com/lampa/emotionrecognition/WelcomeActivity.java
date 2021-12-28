package com.lampa.emotionrecognition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    }

    public void logoutOnClick(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    public void facerecOnClick(View view) {
        startActivity(new Intent(this,MainActivity.class));

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
                Toast.makeText(this, "you are already in this screen :)", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_logout:
            case R.id.menu_back:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}