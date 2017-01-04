package com.example.jonnyelliott.catchtheball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box, fish, fly, slime;

    //position
    private int boxY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreLabel=(TextView) findViewById(R.id.scoreLabel);
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

        //Temporary
        startLabel.setVisibility(View.INVISIBLE);
        boxY=500;
    }

    public boolean onTouchEvent(MotionEvent me){
        if(me.getAction() == MotionEvent.ACTION_DOWN){
            boxY -=20;
        }

        box.setY(boxY);
        return true;
    }
}
