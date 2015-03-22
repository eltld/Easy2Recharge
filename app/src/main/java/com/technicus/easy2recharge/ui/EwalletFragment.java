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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.adapters.WalletHistoryAdapter;
import com.technicus.easy2recharge.utils.ApiUtils;
import com.technicus.easy2recharge.utils.Constants;
import com.technicus.easy2recharge.utils.EWallet;

public class EwalletFragment extends Fragment {

    TextView eWalletBalance;
    EditText submitAmount;
    Button addToEwallet;
    ListView eWalletHistoryListView;
    SharedPreferences prefs;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_ewallet, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();

        eWalletBalance = (TextView) view.findViewById(R.id.ewallet_amount);
        submitAmount = (EditText) view.findViewById(R.id.ewallet_cash_edittext);
        addToEwallet = (Button) view.findViewById(R.id.add_cash_button);
        eWalletHistoryListView = (ListView) view.findViewById(R.id.listView_ewallet);
        prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());

        addToEwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = prefs.getString(Constants.PREF_UID, "");
                try {
                    int amount = Integer.parseInt(submitAmount.getText().toString());
                    if (amount > 10) {
                        Fragment addToWalletTranscationFragment = new AddToWalletTransactionFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("amount", submitAmount.getText().toString());
                        bundle.putString("uid", uid);
                        addToWalletTranscationFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, addToWalletTranscationFragment)
                                .commit();
                    } else {
                        Toast.makeText(view.getContext(), "Please enter amount more than Rs 10",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    Toast.makeText(view.getContext(), "Please Enter a Valid Number", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        String uid = prefs.getString(Constants.PREF_UID, "");
        if (uid != null) {
            new GetEwalletAmountTask().execute(uid);
            new GetEwalletHistoryTask().execute(uid);
        }
    }

    private class GetEwalletAmountTask extends AsyncTask<String, Void, EWallet> {
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
        protected EWallet doInBackground(String... strings) {
            return new ApiUtils(view.getContext()).
                    getWalletBalance(ApiUtils.GET_EWALLET_BALANCE_TASK, strings[0]);
        }

        @Override
        protected void onPostExecute(EWallet eWallet) {
            super.onPostExecute(eWallet);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (eWallet.getStatus().equals("Sucessfull")) {
                eWalletBalance.setText(eWallet.getWalletAmount() + "");
            } else {
                Toast.makeText(view.getContext(), eWallet.getFAILED_REASON(), Toast.LENGTH_SHORT)
                        .show();
            }

        }
    }

    private class GetEwalletHistoryTask extends AsyncTask<String, Void, WalletHistoryAdapter> {

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
        protected WalletHistoryAdapter doInBackground(String... strings) {
            WalletHistoryAdapter historyAdapter = new WalletHistoryAdapter(view.getContext(), strings[0]);
            return historyAdapter;
        }

        @Override
        protected void onPostExecute(WalletHistoryAdapter adapter) {
            super.onPostExecute(adapter);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (adapter != null) {
                eWalletHistoryListView.setAdapter(adapter);
            }
        }
    }

}
