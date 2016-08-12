package com.projectsling.fitconduit.ui;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.projectsling.fitconduit.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WireEditorDialog extends DialogFragment {
    public WireEditorDialog() {}

    private static final String LOG_TAG = WireEditorDialog.class.getSimpleName();
    private List<CharSequence> mWireNames;
    private Map<String, Integer> mWireMap;      //Maps the wire names to spinner position
    private String mWire;                       //Name of wire to edit
    private int mAmount;                        //Amount to init with
    private int mPosition;                      //Position of edit wire in listview
    private Spinner mSpinner;
    private NumberPicker mPicker;

    public static WireEditorDialog newInstance(
            ArrayList<CharSequence> wireNames, String initWire, int initAmount, int position) {
        WireEditorDialog dialog = new WireEditorDialog();
        Bundle bundle = new Bundle();

        bundle.putCharSequenceArrayList("wireNames", wireNames);
        bundle.putCharSequence("initWire", initWire);
        bundle.putInt("initAmount", initAmount);
        bundle.putInt("position", position);
        dialog.setArguments(bundle);

        return dialog;
    }

    private View initDialogBody() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_wire_creator, null);

        //Orientation is only settable from linearLayout
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialogLayoutRoot);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        mPicker = (NumberPicker) view.findViewById(R.id.dialogPicker);
        mPicker.setMinValue(0);
        mPicker.setMaxValue(99);
        mPicker.setGravity(Gravity.CENTER_VERTICAL);
        mPicker.setPadding(dpToPixel(2), 0, dpToPixel(20), 0);
        mPicker.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        mPicker.setValue(mAmount);

        mSpinner = (Spinner) view.findViewById(R.id.dialogSpinner);
        mSpinner.setAdapter(new ArrayAdapter<CharSequence>(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                mWireNames
        ));

        mSpinner.setGravity(Gravity.CENTER_VERTICAL);
        mSpinner.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        mSpinner.setPadding(dpToPixel(5), 0, dpToPixel(2), 0);
        if (mWireMap.containsKey(mWire)) {
            mSpinner.setSelection(mWireMap.get(mWire));
        }

        return view;
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnWireCreateListener)context;
        }
        catch (ClassCastException e) {
            Log.e(LOG_TAG, "Class cast ", e);
        }
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle.containsKey("wireNames")) {
            mWireNames = bundle.getCharSequenceArrayList("wireNames");
            mWireMap = makeWireMap(mWireNames);
        }
        if (bundle.containsKey("initWire")) {
            CharSequence temp = bundle.getCharSequence("initWire");
            if (temp != null) {
                mWire = temp.toString();
            }
        }
        if (bundle.containsKey("initAmount")) {
            mAmount = bundle.getInt("initAmount");
        }
        mPosition = bundle.getInt("position");
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getActivity().getResources().getString(R.string.wireEdit, mPosition))
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v(LOG_TAG, "Editing wire");
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v(LOG_TAG, "Cancelled wire edit");
                    }
                })
                .setView(initDialogBody());

        return builder.create();
    }

    private int dpToPixel(float dp) {
        float scale = getActivity().getResources().getDisplayMetrics().density;

        return Math.round(dp * scale + 0.5f);
    }

    private Map<String, Integer> makeWireMap(List<CharSequence> wireList) {
        Map<String, Integer> res = new HashMap<>();
        for (int x = 0; x < wireList.size(); x++) {
            res.put(wireList.get(x).toString(), x);
        }

        return res;
    }

    /*//Pass in the name of the wire to have selected
    public WireEditorDialog setSpinnerPos(String name) {
        if (name == null) {
            Log.v(LOG_TAG, "null stirng");
            return this;
        }
        if (mWireMap.containsKey(name)) {
            mSpinner.setSelection(mWireMap.get(name));
        }

        //Log.v(LOG_TAG, name);

        return this;
    }

    //Set the number for the picker
    public WireEditorDialog setPicker(int amount) {
        //if (amount >= mPicker.getMinValue() && amount <= mPicker.getMaxValue()) {
        mPicker.setValue(amount);
        //}

        //Log.v(LOG_TAG, amount + "");

        return this;
    }*/
}
