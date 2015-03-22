package com.technicus.easy2recharge.ui;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.utils.ApiUtils;
import com.technicus.easy2recharge.utils.Constants;
import com.technicus.easy2recharge.utils.UserDetail;

public class MyAccountFragment extends Fragment {

    TextView customerID;
    TextView mobileNo;
    TextView emailAddress;
    TextView name;
    SharedPreferences prefs;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_my_account, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();

        customerID = (TextView) view.findViewById(R.id.cust_id_details);
        name = (TextView) view.findViewById(R.id.my_profile_name);
        mobileNo = (TextView) view.findViewById(R.id.my_profile_mobile);
        emailAddress = (TextView) view.findViewById(R.id.my_profile_email);
        prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        String uid = prefs.getString(Constants.PREF_UID, "");
        if (uid != null)
            new AccountDetailsTask().execute(uid);
    }

    private class AccountDetailsTask extends AsyncTask<String, Void, UserDetail> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected UserDetail doInBackground(String... strings) {
            UserDetail userDetail = new ApiUtils(view.getContext()).getUserInfo(strings[0]);
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
                Toast.makeText(view.getContext(), userDetail.getFAILED_REASON(), Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
