package com.technicus.easy2recharge.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.utils.ApiUtils;
import com.technicus.easy2recharge.utils.MiscUtils;
import com.technicus.easy2recharge.utils.UserDetail;

public class SignUpActivity extends ActionBarActivity {

    ActionBar actionBar;
    BootstrapEditText number, name, email, password;
    BootstrapButton signUpButton;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = (BootstrapEditText) findViewById(R.id.name_edittext);
        number = (BootstrapEditText) findViewById(R.id.mobile_edittext);
        email = (BootstrapEditText) findViewById(R.id.email_edittext);
        password = (BootstrapEditText) findViewById(R.id.password_edditext);
        signUpButton = (BootstrapButton) findViewById(R.id.signup_button);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = number.getText().toString();
                String fname = name.getText().toString();
                String email_address = email.getText().toString();
                String pass = password.getText().toString();
                //validations

                if (fname != null || mobile != null || email_address != null || pass != null) {
                    if ((MiscUtils.hasInternetConnectivity(getBaseContext()))) {
                        if (mobile.length() == 10 && email_address.contains("@"))
                            new SignUpTask().execute(fname, mobile, email_address,
                                    MiscUtils.MD5(pass));
                        else
                            Toast.makeText(getBaseContext(), "Entered Data is Invalid", Toast.LENGTH_SHORT)
                                    .show();
                    } else {
                        Toast.makeText(getBaseContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.log1);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class SignUpTask extends AsyncTask<String, Void, UserDetail> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignUpActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected UserDetail doInBackground(String... strings) {
            ApiUtils apiUtils = new ApiUtils(getBaseContext());
            UserDetail userDetail;
            userDetail = apiUtils.signUp(ApiUtils.SIGNUP_TASK, strings[0],
                    strings[1],
                    strings[2],
                    strings[3]);
            return userDetail;
        }

        @Override
        protected void onPostExecute(UserDetail userDetail) {
            super.onPostExecute(userDetail);
            if (pDialog.isShowing())
                pDialog.dismiss();
            String status = userDetail.getSignup();
            if (status.equals(ApiUtils.SUCCESS)) {
                Intent mIntent = new Intent(getBaseContext(), RechargeActivity.class);

                startActivity(mIntent);
                Toast.makeText(getBaseContext(), "Successfull Signup", Toast.LENGTH_SHORT)
                        .show();

            } else {
                Toast.makeText(getBaseContext(), userDetail.getFAILED_REASON(), Toast.LENGTH_SHORT)
                        .show();
            }
            finish();
        }
    }
}
