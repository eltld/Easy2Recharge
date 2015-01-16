package com.technicus.easy2recharge.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ApiUtils {

    public static final String LOGIN_TASK = "login";
    public static final String SIGNUP_TASK = "signup";
    public static final String GET_EWALLET_BALANCE_TASK = "getEwalletBalance";
    public static final String CHANGE_PASSWORD_TASK = "changePassword";
    public static final String SUCCESS = "Success";

    public static final String CHANGE_PASSWORD_SUCCESS = "Password Changed Successfully";
    public static final String CHANGE_PASSWORD_FAILED = "Password Change Failed";

    private static String API_URL = "http://easy2recharge.in/api/index.php";
    SharedPreferences prefs;
    Context context;


    public ApiUtils(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public UserDetail SignIn(String task, String userName, String password) {
        //    String md5Hex = new String(Hex.encodeHex(DigestUtils.md5(password)));
        String url = API_URL + "?task=" + task + "&username=" + userName + "&password="
                + password;
        Log.d("tag1", url);
        String json = getJsonAsStringFromUrl(url);
        //parse the received json string
        UserDetail userDetail = new UserDetail();
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                userDetail.setLogin(jsonObject.getString("login"));
                if (userDetail.getLogin().equals(SUCCESS)) {
                    userDetail.setUid(jsonObject.getString("uid"));
                    userDetail.setOauth_uid(jsonObject.getString("oauth_uid"));
                    userDetail.setFname(jsonObject.getString("fname"));
                    userDetail.setMobileno(jsonObject.getString("mobileno"));
                    userDetail.setEmail(jsonObject.getString("email"));
                    userDetail.setUpass(jsonObject.getString("upass"));
                    prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(Constants.PREF_LOGIN, true);
                    editor.putString(Constants.PREF_USER, userDetail.getEmail());
                    editor.putString(Constants.PREF_PASS, userDetail.getUpass());
                    editor.putString(Constants.PREF_USERNAME, userDetail.getFname());
                    editor.putString(Constants.PREF_UID, userDetail.getUid());
                    editor.commit();

                    return userDetail;
                } else {
                    //get the reason for failed login
                    userDetail.setFAILED_REASON(jsonObject.getString("reason"));
                    return userDetail;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userDetail;
    }

    public UserDetail signUp(String task, String fname, String mobileno, String email,
                             String password) {

        String url = API_URL + "?task=" + task + "&fname=" + fname + "&mobilenumber=" + mobileno +
                "&emailid=" + email + "&createpassword=" + password;
        String json = getJsonAsStringFromUrl(url);
        UserDetail userDetail = new UserDetail();
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                String successStatus = jsonObject.getString("signup");
                userDetail.setSignup(successStatus);
                if (successStatus.equals(SUCCESS)) {
                    userDetail.setUid(jsonObject.getString("uid"));
                    userDetail.setOauth_uid(jsonObject.getString("oauth_uid"));
                    userDetail.setFname(jsonObject.getString("fname"));
                    userDetail.setMobileno(jsonObject.getString("mobileno"));
                    userDetail.setEmail(jsonObject.getString("email"));
                    userDetail.setUpass(jsonObject.getString("upass"));

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(Constants.PREF_LOGIN, true);
                    editor.putString(Constants.PREF_USER, userDetail.getEmail());
                    editor.putString(Constants.PREF_PASS, userDetail.getUpass());
                    editor.putString(Constants.PREF_USERNAME, userDetail.getFname());
                    editor.putString(Constants.PREF_UID, userDetail.getUid());
                    editor.commit();
                    return userDetail;
                } else {
                    userDetail.setFAILED_REASON(jsonObject.getString("reason"));
                    return userDetail;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return userDetail;
    }

    public EWallet getWalletBalance(String task, String uid) {
        //cast down
        String url = API_URL + "?task=" + task + "&uid=" + uid;
        String json = getJsonAsStringFromUrl(url);
        EWallet eWallet = new EWallet();

        try {
            JSONObject jsonObject = new JSONObject(json);
            String status = jsonObject.getString("status");
            eWallet.setStatus(status);
            if (status.equals("Sucessfull")) {
                eWallet.setWalletAmount(jsonObject.getInt("currentBalance"));
                return eWallet;
            } else {
                eWallet.setFAILED_REASON(jsonObject.getString("reason"));
                return eWallet;
            }

        } catch (JSONException je) {
            je.printStackTrace();
        }
        return eWallet;
    }

    public UserDetail getUserInfo(String uid) {
        UserDetail userDetail = new UserDetail();
        String url = API_URL + "?task=getUser" + "&uid=" + uid;
        String json = getJsonAsStringFromUrl(url);

        try {
            JSONObject jsonObject = new JSONObject(json);

            userDetail.setDataFetch(jsonObject.getString("dataFetch"));
            if (userDetail.getDataFetch().equals(SUCCESS)) {
                userDetail.setUid(jsonObject.getString("uid"));
                userDetail.setOauth_uid(jsonObject.getString("oauth_uid"));
                userDetail.setFname(jsonObject.getString("fname"));
                userDetail.setMobileno(jsonObject.getString("mobileno"));
                userDetail.setEmail(jsonObject.getString("email"));
                userDetail.setUpass(jsonObject.getString("upass"));
                return userDetail;
            } else {
                //get the reason for fail result
                userDetail.setFAILED_REASON(jsonObject.getString("reason"));
                return userDetail;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userDetail;
    }

    public String changePassWord(String task, String uid, String newPassword) {
        String url = API_URL + "?task=" + task + "&uid=" + uid + "&password=" + newPassword;
        Log.d(uid, url);
        String json = getJsonAsStringFromUrl(url);
        try {
            JSONObject jsonObject = new JSONObject(json);
            String passwordUpdateStatus = jsonObject.getString("passwordupdate");
            if (passwordUpdateStatus.equals("success")) {
                return CHANGE_PASSWORD_SUCCESS;
            } else {
                return CHANGE_PASSWORD_FAILED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return CHANGE_PASSWORD_FAILED;
    }

    public String logOutOfCurrentSession() {
        SharedPreferences.Editor editor = prefs.edit();
        Toast.makeText(context, "Logging Out " +
                prefs.getString(Constants.PREF_USERNAME, ""), Toast.LENGTH_SHORT)
                .show();
        editor.putBoolean(Constants.PREF_LOGIN, false);
        editor.putString(Constants.PREF_USER, null);
        editor.putString(Constants.PREF_PASS, null);
        editor.putString(Constants.PREF_USERNAME, null);
        editor.putString(Constants.PREF_UID, null);
        editor.commit();
        return SUCCESS;
    }

    public OrderDetail[] getOrderDetails(String uid) {
        JSONArray jsonArray = new JSONArray();
        OrderDetail orderDetail[] = new OrderDetail[]{};
        String url = API_URL + "?task=orderDetails&uid=" + uid;
        String json = getJsonAsStringFromUrl(url);
        if (json != null) {
            //Log.d("afraid to shoot strangers", json);
            try {
                jsonArray = new JSONArray(json);
                orderDetail = new OrderDetail[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject.has("orderDetails")) {
                        if (jsonObject.getString("orderDetails").equals("failed")) {
                            //empty data
                            orderDetail[i] = new OrderDetail();
                            orderDetail[i].setDataStatus(OrderDetail.STATUS_FAIL);
                        }
                    } else {
                        //parse useful data
                        orderDetail[i] = new OrderDetail();
                        orderDetail[i].setDataStatus(OrderDetail.STATUS_SUCCESS);

                        orderDetail[i].setStatus(jsonObject.getString("status"));
                        orderDetail[i].setOrderId(jsonObject.getInt("order_id"));
                        orderDetail[i].setUserId(jsonObject.getString("user_id"));
                        orderDetail[i].setMobileNo(jsonObject.getString("mobile_no"));
                        orderDetail[i].setAmount(jsonObject.getInt("amount"));
                        orderDetail[i].setFinalAmount(jsonObject.getInt("finalAmount"));
                        orderDetail[i].setProvider(jsonObject.getString("provider"));
                        orderDetail[i].setOrderDate(jsonObject.getString("order_date"));

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                OrderDetail order[] = new OrderDetail[1];
                order[0] = new OrderDetail();
                order[0].setDataStatus(OrderDetail.STATUS_FAIL);
                return order;
            }
        }
        return orderDetail;
    }

    private String getJsonAsStringFromUrl(String url) {
        Log.d("App", url);
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 2000);
        HttpConnectionParams.setSoTimeout(httpParameters, 2000);
        DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);

        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (Exception s) {
                s.printStackTrace();
            }
        }
        return result;
    }

    public EWalletHistory[] getWalletHistory(String uid) {
        String url = API_URL + "?task=" + "EwalletTransactionDetails" + "&uid=" + uid;

        JSONArray jsonArray = new JSONArray();
        EWalletHistory[] walletHistory = new EWalletHistory[]{};
        String json = getJsonAsStringFromUrl(url);
        if (json != null) {
            try {
                jsonArray = new JSONArray(json);
                walletHistory = new EWalletHistory[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //parse useful data
                    walletHistory[i] = new EWalletHistory();
                    walletHistory[i].setStatus(OrderDetail.STATUS_SUCCESS);
                    walletHistory[i].seteTranId(jsonObject.getInt("etxnid"));
                    walletHistory[i].setUid(jsonObject.getString("uid"));
                    walletHistory[i].setAmount(jsonObject.getInt("amount"));
                    walletHistory[i].setDate(jsonObject.getString("date"));
                    walletHistory[i].setType(jsonObject.getString("type"));
                    walletHistory[i].setDetails(jsonObject.getString("details"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                walletHistory = new EWalletHistory[1];
                walletHistory[0] = new EWalletHistory();
                walletHistory[0].setStatus(OrderDetail.STATUS_FAIL);
                walletHistory[0].setFaileReason("no transaction found");
            }
        }
        return walletHistory;
    }


}
