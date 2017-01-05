package com.example.jonnyelliott.catchtheball;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box, fish, fly, slime;
    //Initialize  Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    //position
    private int boxY;

    //Status Check
    private boolean action_flg = false;
    private boolean start_flg = false;

    //Size
    private int frameHeight;
    private int boxSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        box = (ImageView) findViewById(R.id.box);
        fish = (ImageView) findViewById(R.id.fishswim);
        fly = (ImageView) findViewById(R.id.fly);
        slime = (ImageView) findViewById(R.id.slim);

        //Move to out of Screen
        fish.setX(getResources().getDimension(R.dimen.starting_position));
        fish.setX(getResources().getDimension(R.dimen.starting_position));

        fly.setX(getResources().getDimension(R.dimen.starting_position));
        fly.setX(getResources().getDimension(R.dimen.starting_position));

        slime.setX(getResources().getDimension(R.dimen.starting_position));
        slime.setX(getResources().getDimension(R.dimen.starting_position));
 }

    public boolean onTouchEvent(MotionEvent me) {
        if(start_flg ==false){
            start_flg=true;
            //Why get frame height and box height here?
            //Because the UI has not been set on the screen in OnCreate();
            FrameLayout frame =(FrameLayout) findViewById(R.id.frame);
            frameHeight=frame.getHeight();

            boxY =(int)box.getY();
            //The box is a square.(height and width are the same)
            boxSize = box.getHeight();
            //Temporary
            boxY = 500;
            startLabel.setVisibility(View.GONE);
            timer.schedule(
                    new TimerTask(){
                        public void run(){
                            handler.post(new Runnable(){
                                public void run(){
                                    changePos();
                                }
                            });
                        }
                    },0,20 /*Change every 20 Milliseconds*/
            );
        }else {
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;

            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;
            }
        }
        return true;
    }

    public void changePos(){

        //Move Box
        if(action_flg){
            boxY -=20;
        }else{
            //Releasing
           boxY+=20;
        }
        if(boxY<0) boxY=0;
        if(boxY> frameHeight - boxSize)boxY = frameHeight-boxSize;
        box.setY(boxY);
    }



}
