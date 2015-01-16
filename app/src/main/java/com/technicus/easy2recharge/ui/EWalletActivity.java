package com.technicus.easy2recharge.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
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

public class EWalletActivity extends ActionBarActivity {

    TextView eWalletBalance;
    EditText submitAmount;
    Button addToEwallet;
    ListView eWalletHistoryListView;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewallet);
        eWalletBalance = (TextView) findViewById(R.id.ewallet_amount);
        submitAmount = (EditText) findViewById(R.id.ewallet_cash_edittext);
        addToEwallet = (Button) findViewById(R.id.add_cash_button);
        eWalletHistoryListView = (ListView) findViewById(R.id.listView_ewallet);
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        addToEwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = prefs.getString(Constants.PREF_UID, "");
                try {
                    int amount = Integer.parseInt(submitAmount.getText().toString());
                    if (amount > 10) {
                        Intent intent = new Intent(EWalletActivity.this, AddToWalletTransaction.class);
                        intent.putExtra("amount", submitAmount.getText().toString());
                        intent.putExtra("uid", uid);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getBaseContext(), "Please enter amount more than Rs 10", Toast.LENGTH_SHORT)
                                .show();
                    }
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    Toast.makeText(getBaseContext(), "Please Enter a Valid Number", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
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
            pDialog = new ProgressDialog(EWalletActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected EWallet doInBackground(String... strings) {
            return new ApiUtils(getBaseContext()).
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
                Toast.makeText(getBaseContext(), eWallet.getFAILED_REASON(), Toast.LENGTH_SHORT)
                        .show();
            }

        }
    }

    private class GetEwalletHistoryTask extends AsyncTask<String, Void, WalletHistoryAdapter> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EWalletActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected WalletHistoryAdapter doInBackground(String... strings) {
            WalletHistoryAdapter historyAdapter = new WalletHistoryAdapter(getBaseContext(), strings[0]);
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
