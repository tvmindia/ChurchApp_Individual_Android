package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
     //   Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.hyperspace_jump);
     //   ImageView image =(ImageView)findViewById(R.id.splashImage);
      //  image.startAnimation(hyperspaceJump);

        Animation appnametext = AnimationUtils.loadAnimation(this, R.anim.appnamezoomin);
        TextView txtapp =(TextView)findViewById(R.id.title);
        txtapp.startAnimation(appnametext);

        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/oldeng.ttf");
        txtapp.setTypeface(type);

     /*   Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {*/
                startActivity(new Intent(SplashScreen.this, Home.class));
                finish();
       /*     }
        },3000);*/
    }
}
