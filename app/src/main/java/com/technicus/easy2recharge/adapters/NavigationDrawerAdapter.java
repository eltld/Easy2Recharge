package com.technicus.easy2recharge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technicus.easy2recharge.R;
import com.technicus.easy2recharge.utils.Constants;

/**
 * Created by Marty Friedman on 5/1/15.
 */
public class NavigationDrawerAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ImageView drawerItemIcon;
    TextView drawerItemName;
    String[] drawerItems;

    public NavigationDrawerAdapter(Context ctx) {
        this.context = ctx;
        drawerItems = Constants.drawerListItems;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return drawerItems.length;
    }

    @Override
    public Object getItem(int i) {
        return drawerItems[i];
    }

    @Override
    public long getItemId(int i) {
        return drawerItems[i].indexOf(i);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = inflater
                .inflate(R.layout.drawer_list_item, viewGroup, false);
        drawerItemName = (TextView) row.findViewById(R.id.drawer_item_name);
        drawerItemIcon = (ImageView) row.findViewById(R.id.drawer_item_image);

        drawerItemName.setText(Constants.drawerListItems[position]);
        drawerItemIcon.setImageResource(Constants.drawerListIcons[position]);
        return row;
    }
}
