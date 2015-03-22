package com.technicus.easy2recharge.utils;

/**
 * Created by rahul on 8/1/15.
 */
public class EWalletHistory {
    int eTranId;
    String uid;
    int amount;
    String details;
    String type;
    String date;

    String status;
    String failReason;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int geteTranId() {
        return eTranId;
    }

    public void seteTranId(int eTranId) {
        this.eTranId = eTranId;
    }

    public String getFaileReason() {
        return failReason;
    }

    public void setFaileReason(String failReason) {
        this.failReason = failReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
