package com.technicus.easy2recharge.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.utils.ApiUtils;
import com.technicus.easy2recharge.utils.Constants;
import com.technicus.easy2recharge.utils.UserDetail;


public class MyAccount extends ActionBarActivity {
    TextView customerID;
    TextView mobileNo;
    TextView emailAddress;
    TextView name;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        customerID = (TextView) findViewById(R.id.cust_id_details);
        name = (TextView) findViewById(R.id.my_profile_name);
        mobileNo = (TextView) findViewById(R.id.my_profile_mobile);
        emailAddress = (TextView) findViewById(R.id.my_profile_email);
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String uid = prefs.getString(Constants.PREF_UID, "");
        if (uid != null)
            new AccountDetailsTask().execute(uid);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_account, menu);
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

    private class AccountDetailsTask extends AsyncTask<String, Void, UserDetail> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MyAccount.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected UserDetail doInBackground(String... strings) {
            UserDetail userDetail = new ApiUtils(getBaseContext()).getUserInfo(strings[0]);
            return userDetail;
        }

        @Override
        protected void onPostExecute(UserDetail userDetail) {
            super.onPostExecute(userDetail);
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (userDetail.getDataFetch().equals(ApiUtils.SUCCESS)) {
                name.setText(userDetail.getFname());
                mobileNo.setText(userDetail.getMobileno());
                emailAddress.setText(userDetail.getEmail());
                customerID.setText(userDetail.getUid());
            } else {
                Toast.makeText(getBaseContext(), userDetail.getFAILED_REASON(), Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
