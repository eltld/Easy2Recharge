package com.technicus.easy2recharge.ui;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.utils.ApiUtils;
import com.technicus.easy2recharge.utils.MiscUtils;
import com.technicus.easy2recharge.utils.UserDetail;

public class SignUpFragment extends Fragment {

    BootstrapEditText number, name, email, password;
    BootstrapButton signUpButton;
    SharedPreferences prefs;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_sign_up, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        name = (BootstrapEditText) view.findViewById(R.id.name_edittext);
        number = (BootstrapEditText) view.findViewById(R.id.mobile_edittext);
        email = (BootstrapEditText) view.findViewById(R.id.email_edittext);
        password = (BootstrapEditText) view.findViewById(R.id.password_edditext);
        signUpButton = (BootstrapButton) view.findViewById(R.id.signup_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = number.getText().toString();
                String fname = name.getText().toString();
                String email_address = email.getText().toString();
                String pass = password.getText().toString();
                //validations

                if (fname != null || mobile != null || email_address != null || pass != null) {
                    if ((MiscUtils.hasInternetConnectivity(view.getContext()))) {
                        if (mobile.length() == 10 && email_address.contains("@"))
                            new SignUpTask().execute(fname, mobile, email_address,
                                    MiscUtils.MD5(pass));
                        else
                            Toast.makeText(view.getContext(), "Entered Data is Invalid", Toast.LENGTH_SHORT)
                                    .show();
                    } else {
                        Toast.makeText(view.getContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        });

    }

    private class SignUpTask extends AsyncTask<String, Void, UserDetail> {
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
            ApiUtils apiUtils = new ApiUtils(view.getContext());
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

                getFragmentManager().beginTransaction().replace(R.id.content_frame
                        , new RechargeFragment()).commit();
                Toast.makeText(view.getContext(), "Successfull Signup", Toast.LENGTH_SHORT)
                        .show();

            } else {
                Toast.makeText(view.getContext(), userDetail.getFAILED_REASON(), Toast.LENGTH_SHORT)
                        .show();
            }

        }
    }
}
