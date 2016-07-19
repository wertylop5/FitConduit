package com.projectsling.fitconduit.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Stanley on 7/18/2016.
 */
public class WireAdapter extends BaseAdapter {
    Context mContext;
    List<JSONObject> mList;


    public WireAdapter(Context context, List<JSONObject> list) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0L;
    }

    /*
    * position is the current item the adapter is on
    * convertView is the view (ex: a row). May be new or recycled
    * parent is the ViewGroup it will be attached to
    * */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
