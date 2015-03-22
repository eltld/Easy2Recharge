package com.technicus.easy2recharge.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.technicus.easy2recharge.R;


public class CouponSelection extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_selection);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
