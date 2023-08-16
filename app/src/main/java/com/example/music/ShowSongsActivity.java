package com.example.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.AudioAttributes;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.example.music.Model.UploadSong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowSongsActivity extends AppCompatActivity implements RecyclerViewInterface{

    RecyclerView recyclerView;
    ProgressBar progressBar;

    List<UploadSong> mUpload;

    DatabaseReference databaseReference;

    ValueEventListener valueEventListener;
    MediaPlayer mediaPlayer;

    JcPlayerView jcPlayerView;
    ArrayList<JcAudio> jcAudios = new ArrayList<>();
    SongsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_songs);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        progressBar = (ProgressBar) findViewById(R.id.progressBarShowSongs);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUpload = new ArrayList<>();

        jcPlayerView =(JcPlayerView)findViewById(R.id.jcplayer);




        adapter= new SongsAdapter(ShowSongsActivity.this,mUpload,this);

        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("songs");

        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 mUpload.clear();
                 for(DataSnapshot dss:dataSnapshot.getChildren())
                 {
                     UploadSong uploadSong = dss.getValue(UploadSong.class);
                     uploadSong.setmKey(dss.getKey());
                     mUpload.add(uploadSong);
                     jcAudios.add(JcAudio.createFromURL(uploadSong.getSongTitle(),uploadSong.getSongLink()));

                     jcPlayerView.initPlaylist(jcAudios,null);
                 }

                 adapter.notifyDataSetChanged();
                 progressBar.setVisibility(View.GONE);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),""+databaseError.getMessage(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        databaseReference.removeEventListener(valueEventListener);
    }

    /*public void playSong(List<UploadSong> arrayListSongs, int adapterPosition) throws IOException {
        UploadSong uploadSong = arrayListSongs.get(adapterPosition);

        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = new MediaPlayer();

        mediaPlayer.setDataSource(uploadSong.getSongLink());
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });


        mediaPlayer.prepareAsync();
    }*/

    @Override
    public void onItemClick(int position) {

        jcPlayerView.playAudio(jcAudios.get(position));
        jcPlayerView.setVisibility(View.VISIBLE);
        jcPlayerView.createNotification();
    }

    /*public void playSong(List<UploadSong> arrayListSongs, int adapterPosition) {
        UploadSong uploadSong = arrayListSongs.get(adapterPosition);

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = new MediaPlayer();

        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build());

        try {
            mediaPlayer.setDataSource(uploadSong.getSongLink());
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                    return false;
                }
            });

            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}