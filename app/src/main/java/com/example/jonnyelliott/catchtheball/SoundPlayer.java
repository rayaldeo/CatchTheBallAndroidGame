package com.example.jonnyelliott.catchtheball;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by Jonny Elliott on 1/7/2017.
 */

public class SoundPlayer {

    private static SoundPool soundPool;
    private static int hitSound,backgroundMusic;
    private static int overSound;

    public SoundPlayer(Context context){
        //SoundPool is deprecated in API level 21.(Lollipop)
        //SoundPool(int maxStreams, int StreamType, int srcQuality)
        soundPool = new SoundPool(2,AudioManager.STREAM_MUSIC,0);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        }
        hitSound = soundPool.load(context,R.raw.completetask_0,1);
        overSound =soundPool.load(context,R.raw.hit,1);
        backgroundMusic = soundPool.load(context,R.raw.action,1);

    }

    public void playHitSound(){
        //play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
        soundPool.play(hitSound,1.0f,1.0f,1,0,1.0f);
    }

    public void playOverSound(){
        soundPool.play(overSound,1.0f,1.0f,1,0,1.0f);
    }

}
