package com.projectsling.fitconduit.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.projectsling.fitconduit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Stanley on 8/4/2016.
 */
public class WireAdapter extends BaseAdapter{
    private static final String LOG_TAG = WireAdapter.class.getSimpleName();
    private Context mContext;

    /*
     * Will hold data for each entry
     * should be in form of
     *
     * {
     *      "name":<string>,
     *      "amount":<int>
     * }
     * */
    private List<JSONObject> mModels;

    public WireAdapter(Context context, List<JSONObject> list) {
        mContext = context;
        mModels = list;
    }

    private class ViewHolder {
        TextView mWireNumber;
        TextView mWireName;
        TextView mWireAmount;
    }

    @Override
    public int getCount() {
        return mModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.row_wire_adapter, parent, false);

            holder = new ViewHolder();
            holder.mWireNumber  = (TextView) convertView.findViewById(R.id.rowWireNumber);
            holder.mWireName    = (TextView) convertView.findViewById(R.id.rowWireName);
            holder.mWireAmount  = (TextView) convertView.findViewById(R.id.rowWireAmount);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mWireNumber.setText(mContext.getResources().getString(
                R.string.rowWireNumber,
                position //+ 1
        ));
        try {
            holder.mWireName.setText(mContext.getResources().getString(
                    R.string.rowWireName,
                    ((JSONObject) getItem(position)).getString("name")
            ));
            holder.mWireAmount.setText(mContext.getResources().getString(
                    R.string.rowWireAmount,
                    ((JSONObject) getItem(position)).getInt("amount")
            ));
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSONException", e);
        }

        return convertView;
    }

    public void addEntry(JSONObject json) {
        mModels.add(json);
        notifyDataSetChanged();
    }
}
