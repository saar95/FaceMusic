package com.lampa.emotionrecognition;


import java.util.Random;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;










public class YoutubePlayer extends AppCompatActivity{
    private YouTubePlayerView youTubePlayerView;
    private String choosenEmotion = MainActivity.determine_emotion;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public String playlistToPlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_player);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        database=FirebaseDatabase.getInstance("https://face-c2bc7-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef=database.getReference();
        myRef.child("Songs").child(choosenEmotion).child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String data = snapshot.getValue().toString();
                    playlistToPlay=data;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        youTubePlayerView = findViewById(R.id.youtube_player_youtubeplayerview);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                //ChoosePlaylist(choosenEmotion);
                String videoId = playlistToPlay;

                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel channel = new NotificationChannel("my not", "my not", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }


                NotificationCompat.Builder builder = new NotificationCompat.Builder(YoutubePlayer.this, "note");
                builder.setContentTitle("FaceMusic");
                builder.setContentText("Here is a song for your mood - " + choosenEmotion);
                builder.setSmallIcon(R.drawable.ic_notification);
                builder.setAutoCancel(true);
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(YoutubePlayer.this);
                managerCompat.notify(1,builder.build());

*/


                youTubePlayer.loadVideo(videoId,0);

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                return true;
            case R.id.menu_home_screen:
                startActivity(new Intent(this,WelcomeActivity.class));
                return true;
            case R.id.menu_back:
                startActivity(new Intent(this,MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private int randomSong(int length){
        Random rand = new Random();
        int rand_int = rand.nextInt(length);
        return  rand_int;
    }




}
