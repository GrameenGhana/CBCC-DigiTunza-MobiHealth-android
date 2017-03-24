package org.cbccessence.mobihealth.activity;

import org.cbccessence.mobihealth.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;


public class WelcomeActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 3000;
    private int logInCheck;
    private Context ctx;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.splash_activity);

        if(getSupportActionBar() != null) getSupportActionBar().hide();


        //create an instance of the database
        prefs = PreferenceManager.getDefaultSharedPreferences(this);


        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 2500) {
                        sleep(100);
                        waited += 100;
                    }



                    if(prefs.getBoolean("isSignedIn", false)) startActivity(new Intent(WelcomeActivity.this, MenuActivity.class));

                    else startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));

                    finish();


                }
                catch (InterruptedException e) {
                }
            }
        };
        splashTread.start();


    }







}