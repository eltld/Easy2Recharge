package com.technicus.easy2recharge.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.technicus.easy2recharge.R;

public class RechargeFragment extends Fragment {
    RadioGroup radioGroup;
    Spinner operator;
    BootstrapEditText number, amount;
    BootstrapButton rechargeButton;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_recharge, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        operator = (Spinner) view.findViewById(R.id.operator1);
        number = (BootstrapEditText) view.findViewById(R.id.phn_number);
        amount = (BootstrapEditText) view.findViewById(R.id.amnt);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_grp1);
        rechargeButton = (BootstrapButton) view.findViewById(R.id.recharge_button);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (view.getContext(), android.R.layout.simple_dropdown_item_1line,
                        new String[]{"Select Provider", "airtel",
                                "aircel", "Reliance", "Vodafone"});
        operator.setAdapter(adapter);


    }
}
