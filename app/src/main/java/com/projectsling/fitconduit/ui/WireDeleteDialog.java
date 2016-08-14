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
import android.widget.TextView;

import com.projectsling.fitconduit.R;

public class WireDeleteDialog extends DialogFragment {
    public WireDeleteDialog() {}
    private static final String LOG_TAG = WireDeleteDialog.class.getSimpleName();
    private OnWireDeleteListener mCallback;
    private int mPosition;

    public interface OnWireDeleteListener {
        void onWireDelete(int wireListPosition);
    }

    public static WireDeleteDialog newInstance(int position) {
        WireDeleteDialog dialog =  new WireDeleteDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);

        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnWireDeleteListener) context;
        }
        catch (ClassCastException e) {
            Log.e(LOG_TAG, "Class cast exception", e);
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
        builder.setMessage(getActivity().getResources().getString(R.string.deletePrompt, mPosition))
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v(LOG_TAG, "Deleting wire");

                        mCallback.onWireDelete(mPosition);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v(LOG_TAG, "Cancelled delete");
                    }
                });

        return builder.create();
    }
}
