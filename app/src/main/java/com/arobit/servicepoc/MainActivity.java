package com.arobit.servicepoc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button play, pause, back, next;

    ArrayList<String> song;
    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int[] songCount = {0};
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        song = new ArrayList<>();

        File direct = new File(Environment.getExternalStorageDirectory().toString() + "/BigAudioBook");
        if (direct.exists()) {
            Toast.makeText(this, Uri.fromFile(new File(direct+"/SoundHelix-Song-1.mp3"))+"", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "False", Toast.LENGTH_LONG).show();
        }

        song.add(Uri.fromFile(new File(direct+"/SoundHelix-Song-1"))+"");
        song.add(Uri.fromFile(new File(direct+"/SoundHelix-Song-2"))+"");
        song.add(Uri.fromFile(new File(direct+"/SoundHelix-Song-3"))+"");
        song.add(Uri.fromFile(new File(direct+"/SoundHelix-Song-4"))+"");



        setMedia(song.get(songCount[0]));

        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        back = findViewById(R.id.back);
        next = findViewById(R.id.next);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.start();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.pause();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (song.size() - 1 > songCount[0]) {
                    int i = ++songCount[0];
                    mPlayer.reset();
                    setMedia(song.get(i));
                } else {
                    Toast.makeText(MainActivity.this, "Stack full", Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (songCount[0] > 0) {
                    int i = --songCount[0];
                    mPlayer.reset();
                    setMedia(song.get(i));
                } else {
                    Toast.makeText(MainActivity.this, "Stack empty", Toast.LENGTH_LONG).show();
                }
            }
        });

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (song.size() - 1 > songCount[0]) {
                    int i = ++songCount[0];
                    mPlayer.reset();
                    setMedia(song.get(i));
                } else {
                    Toast.makeText(MainActivity.this, "Stack full", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void setMedia(String url) {
        try {
            mPlayer.setDataSource(url);
            mPlayer.prepare();
            mPlayer.start();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}
