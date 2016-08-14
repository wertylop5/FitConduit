package com.projectsling.fitconduit.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projectsling.fitconduit.R;


/**
 * TODO figure out how to open the editor dialogue cuz it needs args
 * use mainactivity somehow?
 * */
public class WireMenuDialog extends DialogFragment {
    public WireMenuDialog() {}
    private static final String LOG_TAG = WireMenuDialog.class.getSimpleName();
    private int mPosition;
    private StartEditListener mCallback;

    public interface StartEditListener {
        void startEdit(int wireListPosition);
    }

    public static WireMenuDialog newInstance(int position) {
        WireMenuDialog dialog = new WireMenuDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);

        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (StartEditListener) context;
        }
        catch (ClassCastException e) {
            Log.e(LOG_TAG, "Class cast", e);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPosition = getArguments().getInt("position");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getActivity().getResources().getString(R.string.rowWireNumber, mPosition))
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v(LOG_TAG, "Showing editing dialog");

                        /*
                        * Out of the three dialogs, only one needs the list of wire names
                        * Better to call a different function to get the data rather than
                        * Passing to this dialog
                        * */
                        mCallback.startEdit(mPosition);
                    }
                })
                .setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v(LOG_TAG, "Exiting menu");
                    }
                })
                .setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v(LOG_TAG, "Showing delete dialog");

                        WireDeleteDialog.newInstance(mPosition)
                                .show(
                                        getActivity().getSupportFragmentManager(),
                                        "wireDeleteDialog");
                    }
                });

        return builder.create();
    }
}
