package com.technicus.easy2recharge.ui;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.utils.ApiUtils;
import com.technicus.easy2recharge.utils.MiscUtils;
import com.technicus.easy2recharge.utils.UserDetail;

public class LoginFragment extends Fragment {

    BootstrapButton signIn, signUp;
    BootstrapEditText userName, passWord;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = getView();

        signIn = (BootstrapButton) view.findViewById(R.id.email_sign_in_button);
        signUp = (BootstrapButton) view.findViewById(R.id.email_sign_up_button);
        userName = (BootstrapEditText) view.findViewById(R.id.email);
        passWord = (BootstrapEditText) view.findViewById(R.id.password);

        String email = MiscUtils.getGoogleAccount(view.getContext());

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
                    if ((MiscUtils.hasInternetConnectivity(view.getContext()))) {
                        String md5Pass = MiscUtils.MD5(pass);
                        new LoginTask().execute(user, md5Pass);
                    } else
                        Toast.makeText(view.getContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                                .show();

                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new SignUpFragment())
                        .commit();
            }
        });

    }

    private class LoginTask extends AsyncTask<String, Void, UserDetail> {
        ProgressDialog pDialog;
        UserDetail userDetail;

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
                //   startActivity(new Intent(getBaseContext(), RechargeActivity.class));
                Toast.makeText(view.getContext(), "Welcome " +
                                userDetail.getFname(),
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(view.getContext(), userDetail.getFAILED_REASON(), Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
