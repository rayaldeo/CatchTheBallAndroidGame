package com.example.jonnyelliott.catchtheball;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class Start extends AppCompatActivity {
    BackgroundSound runner;
    boolean running;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        running = getIntent().getBooleanExtra("RUNNING",false);
        if(!running){
            BackgroundSound runner = new BackgroundSound();
            runner.execute();
        }
    }


    public void startGame(View view){
        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        running=true;
        intent.putExtra("RUNNING",running);
        startActivity(intent);

    }

    //Disable Return Button
    public boolean dispatchKeyEvent(KeyEvent event){
        if(event.getAction() ==KeyEvent.ACTION_DOWN){
            switch(event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public class BackgroundSound extends AsyncTask<Void, Void, Void> {
        MediaPlayer player = new MediaPlayer();
        @Override
        protected Void doInBackground(Void... params) {
            //if(isCancelled()) {
                player = MediaPlayer.create(Start.this, R.raw.action);
                player.setLooping(true); // Set looping
                player.setVolume(100, 100);
                player.start();
            //}
            return null;
        }
    }

    public void onDestroy(){
        super.onDestroy();
    }

}
