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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.projectsling.fitconduit.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnknownWireDialog extends DialogFragment {
    private static final String LOG_TAG = UnknownWireDialog.class.getSimpleName();

    private int mPosition;

    private EditText mEditText;
    private NumberPicker mPicker;

    private OnWireCreateListener mCallback;
    private OnWireEditListener mEditCallback;

    public interface OnWireCreateListener {
        void onWireCreate(double od, int amount);
    }

    public interface OnWireEditListener {
        void onWireEdit(double od, int amount, int position);
    }

    public UnknownWireDialog() {}

    public static UnknownWireDialog newInstance() {
        UnknownWireDialog dialog = new UnknownWireDialog();

        return dialog;
    }

    public static UnknownWireDialog newInstance(double currentOd, int currentAmount, int position) {
        UnknownWireDialog dialog = new UnknownWireDialog();
        Bundle bundle = new Bundle();

        bundle.putDouble("currentOd", currentOd);
        bundle.putInt("currentAmount", currentAmount);
        bundle.putInt("position", position);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnWireCreateListener) context;
            mEditCallback = (OnWireEditListener) context;
        }
        catch (ClassCastException e) {
            Log.e(LOG_TAG, "Class cast", e);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle != null) {
            if (bundle.containsKey("position")) {
                mPosition = bundle.getInt("position");
            }
            else {
                mPosition = -1;
            }
        }
    }

    private View initDialogBody() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View rootView = inflater.inflate(R.layout.dialog_unknown_wire, null);

        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.unknownDialogLayoutRoot);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        mEditText = (EditText) rootView.findViewById(R.id.unknownDialogEditText);
        mEditText.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
        ));

        mPicker = (NumberPicker) rootView.findViewById(R.id.unknownDialogPicker);
        mPicker.setMinValue(0);
        mPicker.setMaxValue(99);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("currentOd")) {
                mEditText.setText(String.format(Locale.ENGLISH, "%f", bundle.getDouble("currentOd")));
            }
            if (bundle.containsKey("currentAmount")){
                mPicker.setValue(bundle.getInt("currentAmount"));
            }
        }

        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return rootView;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.unknownWireAdd)
                .setView(initDialogBody())
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getArguments() != null && getArguments().containsKey("currentOd")) {
                            if (mPosition == -1) {
                                throw new IndexOutOfBoundsException("mPosition not specified");
                            }

                            if (mEditText.getText().toString().equals("")) {
                                mEditCallback.onWireEdit(
                                        0.0,
                                        mPicker.getValue(),
                                        mPosition
                                );
                            }
                            else {
                                mEditCallback.onWireEdit(
                                        Double.parseDouble(mEditText.getText().toString()),
                                        mPicker.getValue(),
                                        mPosition
                                );
                            }
                        }
                        else {
                            if (mEditText.getText().toString().equals("")) {
                                mCallback.onWireCreate(
                                        0.0,
                                        mPicker.getValue());
                            }
                            else {
                                mCallback.onWireCreate(
                                        Double.parseDouble(mEditText.getText().toString()),
                                        mPicker.getValue());
                            }
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
