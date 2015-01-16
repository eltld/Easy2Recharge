package com.technicus.easy2recharge.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.adapters.OrderHistoryAdapter;
import com.technicus.easy2recharge.utils.Constants;

public class OrderHistory extends ActionBarActivity {

    ActionBar actionBar;
    ListView listViewOrderHistory;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        actionBar = getSupportActionBar();
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        listViewOrderHistory = (ListView) findViewById(R.id.listView_order_history);

        String uid = prefs.getString(Constants.PREF_UID, "");
        if (uid != null)
            new PopulateListViewTask().execute(uid);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_history, menu);
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

    private class PopulateListViewTask extends AsyncTask<String, Void, OrderHistoryAdapter> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OrderHistory.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected OrderHistoryAdapter doInBackground(String... strings) {
            OrderHistoryAdapter adapter = new OrderHistoryAdapter(OrderHistory.this, strings[0]);
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
