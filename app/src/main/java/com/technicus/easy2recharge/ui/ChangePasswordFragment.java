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
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.utils.ApiUtils;
import com.technicus.easy2recharge.utils.Constants;
import com.technicus.easy2recharge.utils.MiscUtils;

public class ChangePasswordFragment extends Fragment {

    BootstrapEditText oldPassword, newPassword, confirmPassword;
    BootstrapButton passwordChangeSubmit;
    SharedPreferences prefs;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_change_password, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();

        prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        oldPassword = (BootstrapEditText) view.findViewById(R.id.old_password);
        newPassword = (BootstrapEditText) view.findViewById(R.id.new_password);
        confirmPassword = (BootstrapEditText) view.findViewById(R.id.new_password_confirm);
        passwordChangeSubmit = (BootstrapButton) view.findViewById(R.id.password_change_submit);

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
                    if (MiscUtils.hasInternetConnectivity(view.getContext())) {
                        new ChangePasswordTask().execute(
                                uid  //uid
                                , MiscUtils.MD5(newPass) //password in md5 hash
                        );
                    }
                } else {
                    Toast.makeText(view.getContext(), "Please Enter Correct Values", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

    }

    private class ChangePasswordTask extends AsyncTask<String, Void, String> {

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
        protected String doInBackground(String... strings) {
            ApiUtils apiUtils = new ApiUtils(view.getContext());
            String status = apiUtils.changePassWord(ApiUtils.CHANGE_PASSWORD_TASK, strings[0],
                    strings[1]);
            //update password
            prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
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

            Toast.makeText(view.getContext(), status, Toast.LENGTH_SHORT)
                    .show();
            if (status.equals(ApiUtils.SUCCESS)) {
                getFragmentManager().beginTransaction().add(new RechargeFragment(), "").commit();

            }
        }
    }
}
