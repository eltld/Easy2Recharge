package com.technicus.easy2recharge.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.utils.ApiUtils;
import com.technicus.easy2recharge.utils.MiscUtils;
import com.technicus.easy2recharge.utils.UserDetail;


public class LoginActivity extends Activity {

    BootstrapButton signIn, signUp;
    BootstrapEditText userName, passWord;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_login);

        signIn = (BootstrapButton) findViewById(R.id.email_sign_in_button);
        signUp = (BootstrapButton) findViewById(R.id.email_sign_up_button);
        userName = (BootstrapEditText) findViewById(R.id.email);
        passWord = (BootstrapEditText) findViewById(R.id.password);

        String email = MiscUtils.getGoogleAccount(getBaseContext());
        if (email != null) {
            userName.setText(email);
        }

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user, pass;
                user = userName.getText().toString();
                pass = passWord.getText().toString();
                if (user != null || pass != null) {
                    if ((MiscUtils.hasInternetConnectivity(getBaseContext()))) {
                        String md5Pass = MiscUtils.MD5(pass);
                        new LoginTask().execute(user, md5Pass);
                    } else
                        Toast.makeText(getBaseContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT)
                                .show();

                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

    }


    private class LoginTask extends AsyncTask<String, Void, UserDetail> {
        ProgressDialog pDialog;
        UserDetail userDetail;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected UserDetail doInBackground(String... strings) {
            ApiUtils apiUtils = new ApiUtils(getBaseContext());
            userDetail = apiUtils.SignIn(ApiUtils.LOGIN_TASK,
                    strings[0], //username
                    strings[1]); //password
            return userDetail;

        }

        @Override
        protected void onPostExecute(UserDetail userDetail) {
            super.onPostExecute(userDetail);

            if (pDialog.isShowing())
                pDialog.dismiss();
            if (userDetail.getLogin().equals(ApiUtils.SUCCESS)) {
                startActivity(new Intent(getBaseContext(), RechargeActivity.class));
                Toast.makeText(getBaseContext(), "Welcome " +
                                userDetail.getFname(),
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getBaseContext(), userDetail.getFAILED_REASON(), Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
