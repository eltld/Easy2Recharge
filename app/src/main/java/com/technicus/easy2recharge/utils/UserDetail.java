package com.technicus.easy2recharge.utils;

import java.io.Serializable;

/**
 * Created by rahul on Lemmy Kilmister.
 */
public class UserDetail implements Serializable {

    String uid;
    String oauth_uid;
    String fname, mobileno, email, upass, regdate, login;
    String signup;
    String dataFetch;
    String FAILED_REASON;


    //getter methods


    public String getDataFetch() {
        return dataFetch;
    }

    public void setDataFetch(String dataFetch) {
        this.dataFetch = dataFetch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOauth_uid() {
        return oauth_uid;
    }

    public void setOauth_uid(String oauth_uid) {
        this.oauth_uid = oauth_uid;
    }

    public String getFname() {
        return fname;
    }

    //setter methods

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getFAILED_REASON() {
        return FAILED_REASON;
    }

    public void setFAILED_REASON(String FAILED_REASON) {
        this.FAILED_REASON = FAILED_REASON;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getSignup() {
        return signup;
    }

    public void setSignup(String signup) {
        this.signup = signup;
    }
}
