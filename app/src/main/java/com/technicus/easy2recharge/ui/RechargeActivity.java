package com.technicus.easy2recharge.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconCompat;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.adapters.NavigationDrawerAdapter;
import com.technicus.easy2recharge.utils.ApiUtils;
import com.technicus.easy2recharge.utils.Constants;
import com.technicus.easy2recharge.utils.UserDetail;


public class RechargeActivity extends ActionBarActivity {

    MaterialMenuIconCompat materialMenu;
    BootstrapEditText number, amount;
    BootstrapButton rechargeButton;
    SharedPreferences prefs;
    Spinner operator;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBar actionBar;
    private boolean isDrawerOpened;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        materialMenu = new MaterialMenuIconCompat(this, Color.DKGRAY,
                MaterialMenuDrawable.Stroke.THIN);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        operator = (Spinner) findViewById(R.id.operator1);
        number = (BootstrapEditText) findViewById(R.id.phn_number);
        amount = (BootstrapEditText) findViewById(R.id.amnt);
        radioGroup = (RadioGroup) findViewById(R.id.radio_grp1);
        rechargeButton = (BootstrapButton) findViewById(R.id.recharge_button);

        actionBar = getSupportActionBar();
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.log1);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getBaseContext(), android.R.layout.simple_dropdown_item_1line,
                        new String[]{"Select Provider", "airtel",
                                "aircel", "Reliance", "Vodafone"});

        mDrawerList.setAdapter(new NavigationDrawerAdapter(getBaseContext()));
        operator.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ProgressDialog progressDialog;
                switch (position) {
                    case 0:
                        if (prefs.getBoolean(Constants.PREF_LOGIN, false)) {
                            startActivity(new Intent(RechargeActivity.this, MyAccount.class));
                        } else {
                            startActivity(new Intent(RechargeActivity.this, LoginActivity.class));
                            Toast.makeText(getBaseContext(), "Please Login First", Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        }
                        break;
                    case 1:
                        if (prefs.getBoolean(Constants.PREF_LOGIN, false)) {
                            Intent intent = new Intent(getBaseContext(), EWalletActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getBaseContext(), "Please Login First", Toast.LENGTH_SHORT)
                                    .show();
                            startActivity(new Intent(RechargeActivity.this, LoginActivity.class));
                            finish();
                        }
                        break;
                    case 2:
                        if (prefs.getBoolean(Constants.PREF_LOGIN, false)) {
                            Intent intent = new Intent(getBaseContext(), OrderHistory.class);
                            startActivity(intent);
                        } else {
                            startActivity(new Intent(RechargeActivity.this, LoginActivity.class));
                            finish();
                        }

                        break;
                    case 3:
                        if (prefs.getBoolean(Constants.PREF_LOGIN, false)) {
                            startActivity(new Intent(getBaseContext(), ChangePasswordActivity.class));
                        } else {
                            startActivity(new Intent(RechargeActivity.this, LoginActivity.class));
                            finish();
                        }
                        break;
                    case 4:

                        break;
                }
            }

        });

        mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                materialMenu.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        isDrawerOpened ? 2 - slideOffset : slideOffset
                );
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isDrawerOpened = true;
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawerOpened = false;
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_IDLE) {
                    if (isDrawerOpened)
                        materialMenu.setState(MaterialMenuDrawable.IconState.ARROW);

                    else
                        materialMenu.setState(MaterialMenuDrawable.IconState.BURGER);
                }
            }
        });

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

                if (actionBar.getSelectedTab().getText().equals("DTH")) {
                    number.setHint("Enter DTH Account Number");
                    radioGroup.setVisibility(View.INVISIBLE);
                } else if (actionBar.getSelectedTab().getText().equals("Mobile")) {
                    radioGroup.setVisibility(View.VISIBLE);
                    number.setHint("Enter Mobile Number");
                } else if (actionBar.getSelectedTab().getText().equals("DataCard")) {
                    number.setHint("Enter Data Card Number");
                    radioGroup.setVisibility(View.INVISIBLE);
                }
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }
        };
        rechargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number.getText().toString().length() == 10) {

                } else {

                }

            }
        });
        actionBar.addTab(actionBar.newTab().setText("Mobile").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("DTH").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("DataCard").setTabListener(tabListener));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        materialMenu.syncState(savedInstanceState);
        isDrawerOpened = mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        materialMenu.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recharge, menu);
        /*
        check if we are signed in and set visibility menu items according to it
         */
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if (prefs.getBoolean(Constants.PREF_LOGIN, false)) {

            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
        } else {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        // menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (!(isDrawerOpened)) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            } else if (isDrawerOpened) {
                mDrawerLayout.closeDrawer(GravityCompat.START);

            }
        }
        if (id == R.id.action_signout) {
            //sign out
            Toast.makeText(getBaseContext(), "sign out", Toast.LENGTH_SHORT)
                    .show();
            ApiUtils utils = new ApiUtils(getBaseContext());
            utils.logOutOfCurrentSession();
            invalidateOptionsMenu();
        } else if (id == R.id.action_signin) {
            Toast.makeText(getBaseContext(), "sign in", Toast.LENGTH_SHORT)
                    .show();
            invalidateOptionsMenu();
            startActivity(new Intent(getBaseContext(), LoginActivity.class));

        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }


    private class LoginTask extends AsyncTask<String, Void, UserDetail> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RechargeActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected UserDetail doInBackground(String... strings) {
            ApiUtils apiUtils = new ApiUtils(getBaseContext());
            UserDetail userDetail;
            userDetail = apiUtils.SignIn(ApiUtils.LOGIN_TASK, strings[0], strings[1]);
            return userDetail;
        }

        @Override
        protected void onPostExecute(UserDetail userDetail) {
            super.onPostExecute(userDetail);
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }

}
