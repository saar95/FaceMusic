package com.lampa.emotionrecognition;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreatorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText songEditText;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private int ans;
    private String emotion;
    private Button uploadSong,removeSong;
    public static CreatorActivity instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator);
        instance=this;
        mAuth = FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance("https://face-c2bc7-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef=database.getReference();
        songEditText=findViewById(R.id.editText_song);
        Spinner emotionspinner = findViewById(R.id.emotionspinner);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.emotions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emotionspinner.setAdapter(adapter);
        emotionspinner.setOnItemSelectedListener(this);
        uploadSong = (Button) findViewById(R.id.uploadsong_btn);
        removeSong = (Button) findViewById(R.id.removesong_btn);
        uploadSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUploadSongPopUp();
            }
        });
        removeSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRemoveSongPopUp();

            }
        });


    }

    private void openUploadSongPopUp() {
        UploadSongPopUp pop = new UploadSongPopUp();
        pop.show(getSupportFragmentManager(),"uploadSong");
    }
    private void openRemoveSongPopUp() {
        RemoveSongPopUp pop = new RemoveSongPopUp();
        pop.show(getSupportFragmentManager(),"removeSong");
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

    public void watchHistoryOnClick(){

    }

    public void uploadSongOnClick() {
        fireBaseLength("Songs/"+emotion);

    }

    public void removeSongOnClick() {
            myRef.child("Songs").child(emotion).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    try {
                        for (DataSnapshot postsnapshot : snapshot.getChildren()) {
                            if (postsnapshot.getValue().equals(songEditText.getText().toString())) {
                                try {
                                    postsnapshot.getRef().removeValue();
                                    Toast.makeText(CreatorActivity.this, "The song was removed successfully", Toast.LENGTH_LONG).show();
                                    break;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            else
                                Toast.makeText(CreatorActivity.this, "The song isn't exist", Toast.LENGTH_LONG).show();
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("onCancelled", " cancelled");
                }
            });


    }

    private void fireBaseLength(String child){
        myRef.child(child)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get total available quest
                        int size = (int) dataSnapshot.getChildrenCount();
                        myRef.child("Songs").child(emotion).child(String.valueOf(size)).setValue(songEditText.getText().toString());
                        Toast.makeText(CreatorActivity.this, "The song was uploaded successfully", Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(CreatorActivity.this, "Something went wrong, please try again", Toast.LENGTH_LONG).show();

                    }
                });
    }
//Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        emotion=text;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}