package com.technicus.easy2recharge.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.utils.ApiUtils;
import com.technicus.easy2recharge.utils.Constants;
import com.technicus.easy2recharge.utils.MiscUtils;

public class ChangePasswordActivity extends ActionBarActivity {

    BootstrapEditText oldPassword, newPassword, confirmPassword;
    BootstrapButton passwordChangeSubmit;
    SharedPreferences prefs;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        oldPassword = (BootstrapEditText) findViewById(R.id.old_password);
        newPassword = (BootstrapEditText) findViewById(R.id.new_password);
        confirmPassword = (BootstrapEditText) findViewById(R.id.new_password_confirm);
        passwordChangeSubmit = (BootstrapButton) findViewById(R.id.password_change_submit);

        passwordChangeSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass = oldPassword.getText().toString();
                String newPass = newPassword.getText().toString();
                String newPassConfirm = confirmPassword.getText().toString();
                String oldPassMd5 = MiscUtils.MD5(oldPass);

                String uid = prefs.getString(Constants.PREF_UID, "");

                String old = prefs.getString(Constants.PREF_PASS, "");

                if ((old.equals(oldPassMd5)) && (newPass.equals(newPassConfirm))) {
                    if (MiscUtils.hasInternetConnectivity(getBaseContext())) {
                        new ChangePasswordTask().execute(
                                uid  //uid
                                , MiscUtils.MD5(newPass) //password in md5 hash
                        );
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Please Enter Correct Values", Toast.LENGTH_SHORT)
                            .show();
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

    private class ChangePasswordTask extends AsyncTask<String, Void, String> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChangePasswordActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            ApiUtils apiUtils = new ApiUtils(getBaseContext());
            String status = apiUtils.changePassWord(ApiUtils.CHANGE_PASSWORD_TASK, strings[0],
                    strings[1]);
            //update password
            prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(Constants.PREF_PASS, strings[1]);
            editor.commit();
            return status;
        }

        @Override
        protected void onPostExecute(String status) {
            super.onPostExecute(status);
            if (pDialog.isShowing())
                pDialog.dismiss();

            Toast.makeText(getBaseContext(), status, Toast.LENGTH_SHORT)
                    .show();
            if (status.equals(ApiUtils.SUCCESS)) {
                startActivity(new Intent(getBaseContext(), RechargeActivity.class));
                finish();
            }
        }
    }


}
