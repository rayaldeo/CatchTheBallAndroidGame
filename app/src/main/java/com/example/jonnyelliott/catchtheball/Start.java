package com.example.jonnyelliott.catchtheball;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class Start extends AppCompatActivity {
    boolean running;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

       //Create the interstitial
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");//This is a Test ID
        /*
        App IDs are unique identifiers given to mobile apps
        when they're registered in the AdMob console.
        To find your app ID, click the App management
        option under the settings dropdown
        (located in the upper right-hand corner)
        on the AdMob account page. App IDs have
        the form ca-app-pub-XXXXXXXXXXXXXXXX~NNNNNNNNNN.
        https://firebase.google.com/docs/admob/android/quick-start
         */
        //Create Request
        AdRequest adRequest  = new AdRequest.Builder().build();

        //Start Loading...
        interstitialAd.loadAd(adRequest);

        //Once request is loaded,display ad.
        interstitialAd.setAdListener(new AdListener() {

            public void onAdLoaded(){
               displayInterstitialad();
            }
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }
        });


        //Play Music
        running = getIntent().getBooleanExtra("RUNNING",false);
        if(!running){
            BackgroundSound runner = new BackgroundSound();
            runner.execute();
        }
    }

    public void displayInterstitialad(){
        if(interstitialAd.isLoaded()){
            interstitialAd.show();
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
