package com.technicus.easy2recharge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.utils.ApiUtils;
import com.technicus.easy2recharge.utils.OrderDetail;

/**
 * Created by Kerry King on 7/1/15.
 */
public class OrderHistoryAdapter extends BaseAdapter {
    OrderDetail[] orderDetail;
    Context context;
    LayoutInflater inflater;
    String uid;

    public OrderHistoryAdapter(Context ctx, String uid) {
        this.context = ctx;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.uid = uid;

        orderDetail = new ApiUtils(context).getOrderDetails(uid);


    }

    @Override
    public int getCount() {
        return orderDetail.length;
    }

    @Override
    public Object getItem(int i) {
        return orderDetail[i];
    }

    @Override
    public long getItemId(int i) {
        return orderDetail[i].getOrderId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = inflater
                .inflate(R.layout.order_history_listitem, viewGroup, false);

        TextView orderId = (TextView) row.findViewById(R.id.order_id);
        TextView dateTime = (TextView) row.findViewById(R.id.date_of_recharge);
        TextView phoneNumber = (TextView) row.findViewById(R.id.phone_number_1);
        TextView operator = (TextView) row.findViewById(R.id.operator_name);
        TextView rechargeStatus = (TextView) row.findViewById(R.id.recharge_status);
        TextView amountRecharged = (TextView) row.findViewById(R.id.amount_recharged);
        TextView rupees = (TextView) row.findViewById(R.id.rupess);

        if (orderDetail[i] != null) {
            if (orderDetail[i].getDataStatus().equals(OrderDetail.STATUS_SUCCESS)) {
                orderId.setText("Order id :" + orderDetail[i].getOrderId());
                dateTime.setText(orderDetail[i].getOrderDate());
                phoneNumber.setText(orderDetail[i].getMobileNo());
                operator.setText(orderDetail[i].getProvider());
                rechargeStatus.setText(orderDetail[i].getStatus());
                amountRecharged.setText(orderDetail[i].getAmount() + "");
            } else {

            }
        }


        return row;
    }
}
