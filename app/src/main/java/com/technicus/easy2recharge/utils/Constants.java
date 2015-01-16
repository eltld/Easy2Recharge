package com.technicus.easy2recharge.utils;

import com.technicus.easy2recharge.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by the PhantomOfTheOpera
 */
public abstract class Constants {

    public static final String PREF_LOGIN = "pref_login";
    public static final String PREF_USER = "pref_user";
    public static final String PREF_PASS = "pref_pass";
    public static final String PREF_UID = "pref_uid";
    public static final String PREF_USERNAME = "pref_username";
    public static final String USERDATA = "userdata";

    public static final String[] drawerListItems = new String[]{
            "My Account", "E-Wallet", "Your Orders", "Change Password", "FeedBack"
    };

    public static final int[] drawerListIcons = new int[]{
            R.drawable.my_account, R.drawable.ewallet, R.drawable.history, R.drawable.password, R.drawable.feedback,
            R.drawable.sign_out
    };
    public static final Map<String, String> PrepaidOperator = new HashMap<String, String>() {
        {
            put("AT", "Airtel");
            put("BS", "Bsnl");
            put("VF", "Vodafone");
            put("BSS", "Bsnl Special");
            put("ID", "Idea");
            put("MTD", "Mtnl Delhi");
            put("TD", "Tata Docomo GSM Special");
            put("AT", "AIRTEL");
            put("MTM", "MTNL MUMBAI");
        }
    };
}

