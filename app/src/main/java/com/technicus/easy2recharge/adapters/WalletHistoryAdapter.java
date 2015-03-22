package com.technicus.easy2recharge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.utils.ApiUtils;
import com.technicus.easy2recharge.utils.EWalletHistory;
import com.technicus.easy2recharge.utils.OrderDetail;

public class WalletHistoryAdapter extends BaseAdapter {

    EWalletHistory[] walletHistories;
    Context context;
    LayoutInflater inflater;
    String uid;


    public WalletHistoryAdapter(Context ctx, String uid) {
        this.context = ctx;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.uid = uid;
        walletHistories = new ApiUtils(context).getWalletHistory(uid);
    }


    @Override
    public int getCount() {
        return walletHistories.length;
    }

    @Override
    public Object getItem(int i) {
        return walletHistories[i];
    }

    @Override
    public long getItemId(int i) {
        return walletHistories[i].hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row = inflater
                .inflate(R.layout.wallet_history_list_item, viewGroup, false);
        TextView transactionId = (TextView) row.findViewById(R.id.transaction_id);
        TextView date = (TextView) row.findViewById(R.id.date_of_ewallet);
        TextView amount = (TextView) row.findViewById(R.id.ewallet_history_amount);
        TextView tranType = (TextView) row.findViewById(R.id.credit_or_debit);
        TextView details = (TextView) row.findViewById(R.id.details_wallet_history);

        if (walletHistories[i] != null) {
            if (walletHistories[i].getStatus().equals(OrderDetail.STATUS_SUCCESS)) {
                transactionId.setText("Transaction ID : " + walletHistories[i].geteTranId());
                date.setText(walletHistories[i].getDate());
                amount.setText(context.getString(R.string.rs) + walletHistories[i].getAmount());
                tranType.setText("Type : " + walletHistories[i].getType());
                details.setText(walletHistories[i].getDetails());
            }

        }

        return row;
    }
}
