package com.projectsling.fitconduit.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.projectsling.fitconduit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Stanley on 7/18/2016.
 */
/**
 * TODO
 * Rewrite the adapter to have a list of numbers as the input
 *
 * */
public class WireAdapter extends BaseAdapter {
    private static final String LOG_TAG = WireAdapter.class.getSimpleName();

    Context mContext;
    List<JSONObject> mList;                 //Will need to make a list of names from this
    List<Integer> mItems;                   //A state object for each row
    HashMap<Integer, Integer> mSpinnerMap;  //Holds state of each spinner <Row, Spinnerstate>
    HashMap<Integer, String> mEditTextMap;  //Holds state of each spinner <Row, text>

    static class ViewHolder {
        //int id;
        Spinner mSpinner;
        TextView mTextView;
        EditText mEditText;
    }

    /*public WireAdapter(Context context, List<JSONObject> wireData, List<RowState> states) {
        mContext = context;
        mList = wireData;
        mStates = states;
    }*/

    public WireAdapter(Context context, List<JSONObject> wireData, List<Integer> entries) {
        mContext = context;
        mList = wireData;
        mItems = entries;

        mSpinnerMap = new HashMap<>();
        mEditTextMap = new HashMap<>();
    }

    List<String> getNames(List<JSONObject> json) {
        Log.v(LOG_TAG, "Getting names from JSON");
        List<String> result = new ArrayList<>();

        for (JSONObject j : json) {
            try {
                result.add(j.getString("name"));
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSONException", e);
            }
        }

        return result;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
    * position is the current item the adapter is on
    * convertView is the view (ex: a row). May be new or recycled
    * parent is the ViewGroup it will be attached to
    * */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.v(LOG_TAG, "On row: " + position);
        ViewHolder holder;
        //final RowState currentState = ((RowState)getItem(position));

        if (convertView == null) {
            //Both same ways to get the inflater
            /*LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.row_wireadapter, parent, false);

            //Using a view holder so no need to constantly find the view by id
            holder = new ViewHolder();
            //holder.id = position;
            holder.mTextView = (TextView) convertView.findViewById(R.id.wireNumber);
            holder.mSpinner = (Spinner) convertView.findViewById(R.id.wireSpinner);
            holder.mEditText = (EditText) convertView.findViewById(R.id.wireAmount);

            //Spinner initialization
            holder.mSpinner.setAdapter(
                    new ArrayAdapter<String>(
                            mContext, android.R.layout.simple_spinner_dropdown_item, getNames(mList)));
            holder.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int itemPosition, long id) {
                    //Store the current spinner position in the corresponding RowState
                    mSpinnerMap.put(position, itemPosition);
                    Log.v(LOG_TAG, "Row " + position + " saved spinner to " + itemPosition);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });

            //setTag allows a view to store data
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();

            //This apparently needs to be repeated here
            holder.mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int itemPosition, long id) {
                    //Store the current spinner position in the corresponding RowState
                    mSpinnerMap.put(position, itemPosition);
                    Log.v(LOG_TAG, "Row " + position + " saved spinner to " + itemPosition);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        }

        //Setting the view for each row, depending on if it is recycled
        holder.mTextView.setText(mContext.getResources().getString(R.string.wire, position));
        Log.v(LOG_TAG, "Row " + position + " set text to " + mContext.getResources().getString(R.string.wire, position));

        if (mSpinnerMap.containsKey(position)) {
            holder.mSpinner.setSelection(mSpinnerMap.get(position));
            Log.v(LOG_TAG, "Row " + position + " set spinner to " + mSpinnerMap.get(position));
        }
        else {
            holder.mSpinner.setSelection(0);
        }

        /*holder.mSpinner.setSelection(currentState.getSpinnerPos());
        Log.v(LOG_TAG, "Row " + position + " set spinner to " + currentState.getSpinnerPos());
        holder.mEditText.setText(Integer.toString(currentState.getAmount()), TextView.BufferType.EDITABLE);*/

        return convertView;
    }
}
