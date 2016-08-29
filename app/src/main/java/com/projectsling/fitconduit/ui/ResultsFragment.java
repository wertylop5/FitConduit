package com.projectsling.fitconduit.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projectsling.fitconduit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultsFragment extends Fragment {
    private static final String LOG_TAG = ResultsFragment.class.getSimpleName();

    private TextView mTotalWires;
    private TextView mTotalArea;
    private TextView mSelectedConduit;
    private TextView mMinCondSize;
    private TextView mCondFill;

    public ResultsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);

        mTotalWires         = (TextView) rootView.findViewById(R.id.resultsTotalWires);
        mTotalArea          = (TextView) rootView.findViewById(R.id.resultsTotalArea);
        mSelectedConduit    = (TextView) rootView.findViewById(R.id.resultsSelectedConduit);
        mMinCondSize        = (TextView) rootView.findViewById(R.id.resultsMinCondSize);
        mCondFill           = (TextView) rootView.findViewById(R.id.resultsCondFill);

        if (savedInstanceState != null) {
            mTotalWires     .setText(savedInstanceState.getString("mTotalWires", ""));
            mTotalArea      .setText(savedInstanceState.getString("mTotalArea", ""));
            mSelectedConduit.setText(savedInstanceState.getString("mSelectedConduit", ""));
            mMinCondSize    .setText(savedInstanceState.getString("mMinCondSize", ""));
            mCondFill       .setText(savedInstanceState.getString("mCondFill", ""));
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW, R.id.choiceContainer);
        rootView.setLayoutParams(params);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mTotalWires.getText() != null) {
            outState.putString("mTotalWires", mTotalWires.getText().toString());
        }
        if (mTotalArea.getText() != null) {
            outState.putString("mTotalArea", mTotalArea.getText().toString());
        }
        if (mSelectedConduit.getText() != null) {
            outState.putString("mSelectedConduit", mSelectedConduit.getText().toString());
        }
        if (mMinCondSize.getText() != null) {
            outState.putString("mMinCondSize", mMinCondSize.getText().toString());
        }
        if (mCondFill.getText() != null) {
            outState.putString("mCondFill", mCondFill.getText().toString());
        }
    }

    public void calculate(List<JSONObject> selectedWires, List<Integer> selectedWireAmounts,
                          String selectedConduit, List<JSONObject> conduitList) {
        int totalWireAmount= 0;
        double totalArea = 0.0;

        int tempQuantity;
        for (int x = 0; x < selectedWires.size(); x++) {
            tempQuantity = selectedWireAmounts.get(x);
            try {
                if (tempQuantity > 0) {
                    totalWireAmount += tempQuantity;
                    totalArea += xArea(selectedWires.get(x).getDouble("od")) * tempQuantity;
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSONException", e);
            }
        }

        //Want to cut the function off early
        if (totalWireAmount == 0) {
            Toast.makeText(getActivity(), R.string.noWires, Toast.LENGTH_SHORT).show();
            return;
        }

        mTotalWires.setText(getActivity().getResources().getString(
                R.string.totalWires,
                totalWireAmount
        ));
        mTotalArea.setText(getActivity().getResources().getString(
                R.string.totalWireArea,
                totalArea
        ));

        //Will hold the conduits that have the same type as selectedConduit
        List<JSONObject> targetConduits = new ArrayList<>();
        for (JSONObject json : conduitList) {
            try {
                if (json.getString("type").equals(selectedConduit)) targetConduits.add(json);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSON", e);
            }
        }

        if (totalWireAmount == 1) {
            mSelectedConduit.setText(getActivity().getResources().getString(
                    R.string.selectedConduit,
                    selectedConduit + " 53% max fill"
                    ));
        }
        else if (totalWireAmount == 2) {
            mSelectedConduit.setText(getActivity().getResources().getString(
                    R.string.selectedConduit,
                    selectedConduit + " 31% max fill"
            ));
        }
        else if (totalWireAmount > 2){
            mSelectedConduit.setText(getActivity().getResources().getString(
                    R.string.selectedConduit,
                    selectedConduit + " 40% max fill"
            ));
        }

        double fillType[] = {0, 0.53, 0.31, 0.4};
        JSONObject currentConduit = null;
        boolean matchFound = false;
        int tempCounter;
        for (tempCounter = 0; tempCounter < targetConduits.size(); tempCounter++) {
            currentConduit = targetConduits.get(tempCounter);
            try {
                if (currentConduit.getDouble("area") * fillType[Math.min(totalWireAmount, 3)]
                        > totalArea) {
                    matchFound = true;
                    mMinCondSize.setText(getActivity().getResources().getString(
                            R.string.minConduitSize,
                            currentConduit.getString("type") + " " +
                                    currentConduit.getDouble("diameter") + " in " +
                                    "(area: " + currentConduit.getDouble("area") + ")"
                    ));

                    mCondFill.setText(getActivity().getResources().getString(
                            R.string.conduitFillPercent,
                            Math.round(totalArea / currentConduit.getDouble("area") * 100)
                    ));
                    break;
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSON", e);
            }
        }

        if (!matchFound) {
            Toast.makeText(getActivity(), R.string.tooManyWires, Toast.LENGTH_LONG).show();

            if (currentConduit == null) {
                mTotalWires     .setText(R.string.error);
                mTotalArea      .setText("");
                mSelectedConduit.setText("");
                mMinCondSize    .setText("");
                mCondFill       .setText("");
            }
            else {
                try {
                    mMinCondSize.setText(getActivity().getResources().getString(
                            R.string.minConduitSize,
                            currentConduit.getString("type") + " " +
                                    currentConduit.getDouble("diameter") + " in " +
                                    "(area: " + currentConduit.getDouble("area") + ")"
                    ));

                    mCondFill.setText(getActivity().getResources().getString(
                            R.string.conduitFillPercent,
                            Math.round(totalArea / currentConduit.getDouble("area") * 100)
                    ));
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "JSON", e);
                }
            }
        }
    }

    private double xArea(double diameter) {
        return Math.PI * diameter * (diameter / 4);
    }
}
