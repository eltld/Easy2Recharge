package com.technicus.easy2recharge.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.Toast;

import com.splunk.mint.Mint;
import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.utils.MiscUtils;


public class SplashScreen extends Activity {
    private static final int SPLASH_DURATION = 2500;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mint.initAndStartSession(SplashScreen.this, "80359e6b");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if (!(MiscUtils.hasInternetConnectivity(getBaseContext()))) {
            Toast.makeText(getBaseContext(), getString(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //TODO authentication from server
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, SPLASH_DURATION);

    }

}
