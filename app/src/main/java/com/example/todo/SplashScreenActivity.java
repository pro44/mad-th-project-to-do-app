package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {
    Animation anim_to,anim_do;
    TextView to,_do;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        to=findViewById(R.id.to);
        _do=findViewById(R.id._do);

        anim_to = AnimationUtils.loadAnimation(this,R.anim.to_anim);
        anim_do = AnimationUtils.loadAnimation(this,R.anim.do_anim);

        to.setAnimation(anim_to);
        _do.setAnimation(anim_do);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();

            }
        },5000);


    }
}