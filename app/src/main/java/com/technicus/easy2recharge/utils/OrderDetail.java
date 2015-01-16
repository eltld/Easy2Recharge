package com.technicus.easy2recharge.utils;

public class OrderDetail {
    public static String STATUS_SUCCESS = "SUCCESS";
    public static String STATUS_FAIL = "FAILED";
    public static String STATUS_PAYMENT_PENDING = "Payment Not Done";
    String status;
    String orderDate;
    String orderDetail;
    String reasonForFail;
    String dataStatus;
    private int orderId;
    private String userId;
    private String mobileNo;
    private int amount;
    private int finalAmount;
    private String provider;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(int finalAmount) {
        this.finalAmount = finalAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setReasonForFail(String reasonForFail) {
        this.reasonForFail = reasonForFail;
    }
}

