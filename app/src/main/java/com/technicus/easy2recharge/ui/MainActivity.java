package com.technicus.easy2recharge.ui;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconCompat;
import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.adapters.NavigationDrawerAdapter;
import com.technicus.easy2recharge.utils.Constants;
import com.technicus.easy2recharge.utils.MiscUtils;

public class MainActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar actionBar;
    private boolean isDrawerOpened;
    private SharedPreferences prefs;
    private MaterialMenuIconCompat materialMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        materialMenu = new MaterialMenuIconCompat(this, Color.DKGRAY,
                MaterialMenuDrawable.Stroke.THIN);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new NavigationDrawerAdapter(getBaseContext()));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.log1);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new RechargeFragment())
                .commit();
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //   getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent view in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (!(isDrawerOpened)) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            } else if (isDrawerOpened) {
                mDrawerLayout.closeDrawer(GravityCompat.START);

            }
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        Fragment fragment;

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    if (MiscUtils.hasInternetConnectivity(getBaseContext())) {
                        if (prefs.getBoolean(Constants.PREF_LOGIN, false)) {
                            fragment = new RechargeFragment();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();
                        } else {
                            Toast.makeText(getBaseContext(), "Please Login First", Toast.LENGTH_SHORT)
                                    .show();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, new LoginFragment())
                                    .commit();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case 1:
                    if (MiscUtils.hasInternetConnectivity(getBaseContext())) {
                        if (prefs.getBoolean(Constants.PREF_LOGIN, false)) {
                            fragment = new MyAccountFragment();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();
                        } else {
                            Toast.makeText(getBaseContext(), "Please Login First", Toast.LENGTH_SHORT)
                                    .show();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, new LoginFragment())
                                    .commit();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case 2:
                    if (MiscUtils.hasInternetConnectivity(getBaseContext())) {
                        if (prefs.getBoolean(Constants.PREF_LOGIN, false)) {
                            fragment = new EwalletFragment();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();
                        } else {
                            Toast.makeText(getBaseContext(), "Please Login First", Toast.LENGTH_SHORT)
                                    .show();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, new LoginFragment())
                                    .commit();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case 3:
                    if (MiscUtils.hasInternetConnectivity(getBaseContext())) {
                        if (prefs.getBoolean(Constants.PREF_LOGIN, false)) {
                            fragment = new OrderHistoryFragment();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();
                        } else {
                            Toast.makeText(getBaseContext(), "Please Login First", Toast.LENGTH_SHORT)
                                    .show();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, new LoginFragment())
                                    .commit();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case 4:
                    if (MiscUtils.hasInternetConnectivity(getBaseContext())) {
                        if (prefs.getBoolean(Constants.PREF_LOGIN, false)) {
                            fragment = new ChangePasswordFragment();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();
                        } else {
                            Toast.makeText(getBaseContext(), "Please Login First", Toast.LENGTH_SHORT)
                                    .show();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, new LoginFragment())
                                    .commit();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;

            }
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }
}
