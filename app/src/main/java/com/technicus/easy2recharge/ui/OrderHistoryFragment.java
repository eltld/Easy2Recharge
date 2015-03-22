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
import android.widget.ListView;

import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.adapters.OrderHistoryAdapter;
import com.technicus.easy2recharge.utils.Constants;

public class OrderHistoryFragment extends Fragment {

    ListView listViewOrderHistory;
    SharedPreferences prefs;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_order_history, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        listViewOrderHistory = (ListView) view.findViewById(R.id.listView_order_history);

        String uid = prefs.getString(Constants.PREF_UID, "");
        if (uid != null)
            new PopulateListViewTask().execute(uid);
    }

    private class PopulateListViewTask extends AsyncTask<String, Void, OrderHistoryAdapter> {
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
        protected OrderHistoryAdapter doInBackground(String... strings) {
            OrderHistoryAdapter adapter = new OrderHistoryAdapter(view.getContext(), strings[0]);
            return adapter;
        }

        @Override
        protected void onPostExecute(OrderHistoryAdapter adapter) {
            super.onPostExecute(adapter);
            if (pDialog.isShowing())
                pDialog.dismiss();

            listViewOrderHistory.setAdapter(adapter);
        }
    }
}
