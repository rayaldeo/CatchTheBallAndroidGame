package com.example.jonnyelliott.catchtheball;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/ultimate_gameplayer_pixel.ttf");
        TextView scoreLabel =(TextView) findViewById(R.id.scoreLabel);
        scoreLabel.setTypeface(custom_font);
        TextView highScoreLabel = (TextView) findViewById(R.id.highScoreLabel);
        highScoreLabel.setTypeface(custom_font);

        int score = getIntent().getIntExtra("SCORE",0);
        scoreLabel.setText(score+"");
        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE",0);

        if(score> highScore) {
            highScoreLabel.setText("High Score: " + score);

            //Save
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.commit();
        }else{
            highScoreLabel.setText("High Score: "+highScore);

         }

        }

    public void tryAgain(View view){
        startActivity(new Intent(getApplicationContext(),Start.class));

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

}

