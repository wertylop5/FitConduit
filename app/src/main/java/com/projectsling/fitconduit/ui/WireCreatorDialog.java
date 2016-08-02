package com.projectsling.fitconduit.ui;


import android.app.Dialog;
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

public class WireCreatorDialog extends DialogFragment {
    private static final String LOG_TAG = WireCreatorDialog.class.getSimpleName();

    private int dpToPixel(float dp) {
        float scale = getActivity().getResources().getDisplayMetrics().density;

        return Math.round(dp * scale + 0.5f);
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

        NumberPicker picker = (NumberPicker) view.findViewById(R.id.dialogPicker);
        picker.setMinValue(0);
        picker.setMaxValue(99);
        picker.setGravity(Gravity.CENTER_VERTICAL);
        picker.setPadding(dpToPixel(2), 0, dpToPixel(20), 0);
        picker.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        ));

        Spinner spinner = (Spinner) view.findViewById(R.id.dialogSpinner);
        spinner.setAdapter(ArrayAdapter.createFromResource(
                getActivity(), R.array.wireNames, android.R.layout.simple_spinner_dropdown_item));
        spinner.setGravity(Gravity.CENTER_VERTICAL);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        spinner.setPadding(dpToPixel(5), 0, dpToPixel(2), 0);

        return view;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.wireAdd)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v(LOG_TAG, "Created new wire");
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v(LOG_TAG, "Cancelled wire creation");
                    }
                })
                .setView(initDialogBody());

        return builder.create();
    }
}
