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
import android.widget.NumberPicker;
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
    List<RowState> mStates;                 //A state object for each row
    //List<HashMap<Integer, Integer>> mStates;
    HashMap<Integer, Integer> mStateMap;    //Holds state of each spinner <Row, Spinnerstate>

    static class ViewHolder {
        Spinner mSpinner;
        TextView mTextView;
        EditText mEditText;
        //RowState mState;
    }

    public WireAdapter(Context context, List<JSONObject> wireData, List<RowState> states) {
        mContext = context;
        mList = wireData;
        mStates = states;
    }

    public WireAdapter(Context context, List<JSONObject> wireData) {
        mContext = context;
        mList = wireData;
        mStates = null;
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
        return mStates.size();
    }

    @Override
    public Object getItem(int position) {
        return mStates.get(position);
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
        final ViewHolder holder;
        final RowState currentState = ((RowState)getItem(position));

        if (convertView == null) {
            //Both same ways to get the inflater
            /*LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.row_wireadapter, parent, false);

            mStateMap = new HashMap<>();

            //Using a view holder so no need to constantly find the view by id
            holder = new ViewHolder();
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
                    currentState.setSpinnerPos(itemPosition);
                    Log.v(LOG_TAG, "Row " + position + " saved spinner to " + itemPosition);
                    //holder.mState.setSpinnerPos(itemPosition);
                    /*Log.v(LOG_TAG, "Spinner pressed");
                    Log.v(LOG_TAG, "Spinner state: " + Integer.toString(mStates.get(position).getSpinnerPos()));
                    Log.v(LOG_TAG, "Position: " + Integer.toString(itemPosition));
                    Log.v(LOG_TAG, "Item id: " + Long.toString(id));*/
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });

            //setTag allows a view to store data
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Setting the view for each row, depending on if it is recycled
        holder.mTextView.setText(mContext.getResources().getString(R.string.wire, position));

        Log.v(LOG_TAG, currentState.toString());
        //if ( currentState.getSpinnerPos() != -1 /* holder.mState.getSpinnerPos() != -1*/) {
            //Log.v(LOG_TAG, "Triggered");
            //Log.v(LOG_TAG, "Row: " + position + "\nspinnerPos: " + currentState.getSpinnerPos());
            holder.mSpinner.setSelection(currentState.getSpinnerPos());
            Log.v(LOG_TAG, "Row " + position + " set spinner to " + currentState.getSpinnerPos());
            //holder.mSpinner.setSelection(mStates.get(position).getSpinnerPos());
            holder.mEditText.setText(Integer.toString(currentState.getAmount()), TextView.BufferType.EDITABLE);
            //holder.mEditText.setText("" + Integer.toString(holder.mState.getAmount()));
        //}
        //else {
            /*Log.v(LOG_TAG, "default");
            holder.mSpinner.setSelection(0);
            holder.mEditText.setText("0", TextView.BufferType.EDITABLE);*/
        //}

        return convertView;
    }
}
