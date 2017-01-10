package com.example.jonnyelliott.catchtheball;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box, fish, fly, slime;
    private ImageView skyBox;
    private Animation mAnimation;

    //Initialize  Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private SoundPlayer sound;

    //position
    private int boxY;
    private int fishX;
    private int fishY;
    private int flyX;
    private int flyY;
    private int slimeX;
    private int slimeY;

    //Speed
    private int boxSpeed;
    private int fishSpeed;
    private int flySpeed;
    private int slimeSpeed;

    //Score
    private int score =0;

    //Status Check
    private boolean action_flg = false;
    private boolean start_flg = false;


    //Size
    private int frameHeight;
    private int boxSize;

    private int screenHeight, screenWidth;

    private ArrayList<Integer> skybiomes;
    private int thisBiome =0;
    private Random rand = new Random();

    boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/ultimate_gameplayer_pixel.ttf");
        sound = new SoundPlayer(this);
        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        scoreLabel.setTypeface(custom_font);
        startLabel = (TextView) findViewById(R.id.startLabel);
        startLabel.setTypeface(custom_font);
        box = (ImageView) findViewById(R.id.box);
        fish = (ImageView) findViewById(R.id.fishswim);
        fly = (ImageView) findViewById(R.id.fly);
        slime = (ImageView) findViewById(R.id.slim);


        //Get screen size
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        //Add All biomes to an ArrayList
        skybiomes =new ArrayList<>();
        skybiomes.add(R.mipmap.rusted_sky);  skybiomes.add(R.mipmap.violet_sky);
        thisBiome = rand.nextInt((skybiomes.size() - 1) + 1);
        //Set the SkyBox For the Beginning of the Game
        //setSkyBox(skybiomes.get(thisBiome));
        setSkyBox(skybiomes.get(thisBiome));


        ///
        //Now
        //Nexus witdth:768 height:1184
        //Spped box:20 fish:12 fly:20 slime:16
        boxSpeed = Math.round(screenHeight/60F);//1184/60 =19.733...=>20
        fishSpeed = Math.round(screenWidth/60F);//768/60 =12.8...=>13
        flySpeed = Math.round(screenWidth/36F);//768/36 =21.333...=>21
        slimeSpeed = Math.round(screenWidth/46F);//768/45 =17.06...=>17


        Log.v("SPEED_BOX",boxSpeed+"");
        Log.v("SPEED_FISH",fishSpeed+"");
        Log.v("SPEED_FLY",flySpeed+"");
        Log.v("SPEED_SLIME",slimeSpeed+"");

        //Move to out of Screen
        fish.setX(getResources().getDimension(R.dimen.starting_position));
        fish.setX(getResources().getDimension(R.dimen.starting_position));

        fly.setX(getResources().getDimension(R.dimen.starting_position));
        fly.setX(getResources().getDimension(R.dimen.starting_position));

        slime.setX(getResources().getDimension(R.dimen.starting_position));
        slime.setX(getResources().getDimension(R.dimen.starting_position));
        //Temporary
        boxY = 500;
        scoreLabel.setText("Score: "+score);

        running = getIntent().getBooleanExtra("RUNNING",false);
    }

    public boolean onTouchEvent(MotionEvent me) {
        if(!start_flg){
            start_flg=true;
            //Why get frame height and box height here?
            //Because the UI has not been set on the screen in OnCreate();
            FrameLayout frame =(FrameLayout) findViewById(R.id.frame);
            frameHeight=frame.getHeight();

            boxY =(int)box.getY();
            //The box is a square.(height and width are the same)
            boxSize = box.getHeight();
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
        hitCheck();
        fishX -=fishSpeed;
        if(fishX<0){
            fishX = screenWidth+20;
            fishY = (int)Math.floor(Math.random()* (frameHeight - fish.getHeight()));
            //System.out.println(fishX);
        }
        fish.setX(fishX);
        fish.setY(fishY);

        flyX -= flySpeed;
        if(flyX<0){
            flyX = screenWidth+20;
            flyY = (int)Math.floor(Math.random()* (frameHeight - fish.getHeight()));
            //System.out.println(fishX);
        }
        fly.setX(flyX);
        fly.setY(flyY);

        slimeX -= slimeSpeed;
        if(slimeX<0){
            slimeX = screenWidth+5000;
            slimeY = (int)Math.floor(Math.random()* (frameHeight - fish.getHeight()));
            //System.out.println(fishX);
        }
        slime.setX(slimeX);
        slime.setY(slimeY);

        //Move Box
        if(action_flg){
            boxY -=boxSpeed;
        }else{
            //Releasing
           boxY+=boxSpeed;
        }
        if(boxY<0) boxY=0;
        if(boxY> frameHeight - boxSize)boxY = frameHeight-boxSize;
        box.setY(boxY);
        scoreLabel.setText("Score: "+score);
    }

    public void hitCheck(){
        //If the center of the ball is in the box, it counts as a hit.
        int fishCenterX = fishX+ fish.getWidth()/2;
        int fishCenterY = fishY+ fish.getHeight()/2;

        //0 <= fishCenterX <= boxWidth
        //boxY <= fishCenterY <=boxY + boxHeight

        if(0<= fishCenterX && fishCenterX <= boxSize
                && boxY <=fishCenterY&& fishCenterY <= boxY+boxSize){
            score+=10;
            fishX =-10;
            sound.playHitSound();
        }

        int flyCenterX = flyX+ fly.getWidth()/2;
        int flyCenterY = flyY+ fly.getHeight()/2;

        //0 <= fishCenterX <= boxWidth
        //boxY <= fishCenterY <=boxY + boxHeight

        if(0<= flyCenterX && flyCenterX <= boxSize
                && boxY <=flyCenterY&& flyCenterY <= boxY+boxSize){
            score+=30;
            flyX =-10;
            sound.playHitSound();
        }

        int slimeCenterX = slimeX+ slime.getWidth()/2;
        int slimeCenterY = slimeY+ slime.getHeight()/2;

        //0 <= fishCenterX <= boxWidth
        //boxY <= fishCenterY <=boxY + boxHeight

        if(0<= slimeCenterX && slimeCenterX <= boxSize
                && boxY <=slimeCenterY&& slimeCenterY <= boxY+boxSize){

            //mAnimation.cancel();
            //mAnimation.reset();
            //Stop Timer
            timer.cancel();
            //timer=null;
            sound.playOverSound();
            //Show Result
            Intent intent = new Intent(getApplicationContext(),Result.class);
            intent.putExtra("SCORE",score);
            intent.putExtra("RUNNING",running);
            startActivity(intent);
        }


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

    public void setSkyBox(int icon){
        //Setting up the Infinite SkyBox
        skyBox = (ImageView) findViewById(R.id.skybox);
        skyBox.setImageResource(icon);
        final int skyBoxWidth = ContextCompat.getDrawable(this, icon).getIntrinsicWidth();
        int animatedViewWidth = 0;
        while (animatedViewWidth < screenWidth) {
            animatedViewWidth += skyBoxWidth;
        }
        animatedViewWidth += skyBoxWidth;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) skyBox.getLayoutParams();
        layoutParams.width = animatedViewWidth;
        skyBox.setLayoutParams(layoutParams);

        //Move the Image to the Left
        final int repeatingBackGroundDrawable = ContextCompat.getDrawable(this, R.drawable.repeating_background).getIntrinsicWidth();
        mAnimation = new TranslateAnimation(0, -repeatingBackGroundDrawable, 0, 0);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setDuration(2500);
        skyBox.startAnimation(mAnimation);
    }

    public void onDestroy(){

        super.onDestroy();

    }
}
