package com.technicus.easy2recharge.utils;

/**
 * Created by dave mustaine on 4/1/15.
 */
public class EWallet {

    private int walletAmount;
    private String status;
    private String FAILED_REASON;

    public int getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(int walletAmount) {
        this.walletAmount = walletAmount;
    }

    public String getFAILED_REASON() {
        return FAILED_REASON;
    }

    public void setFAILED_REASON(String FAILED_REASON) {
        this.FAILED_REASON = FAILED_REASON;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
