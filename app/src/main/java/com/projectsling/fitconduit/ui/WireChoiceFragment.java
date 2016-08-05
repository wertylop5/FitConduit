package com.projectsling.fitconduit.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.projectsling.fitconduit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WireChoiceFragment extends Fragment {
    private static final String LOG_TAG = WireChoiceFragment.class.getSimpleName();
    private ListView mListView;
    private Button mButton;

    public WireChoiceFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_wire_choice, container, false);

        List<JSONObject> temp = new ArrayList<>();
        try {
            temp.add(new JSONObject("{\"name\":\"uno\"}"));
            temp.add(new JSONObject("{\"name\":\"dos\"}"));
            temp.add(new JSONObject("{\"name\":\"tres\"}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mListView = (ListView) root.findViewById(R.id.wireList);
        mButton = (Button) root.findViewById(R.id.addWireButton);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new WireCreatorDialog()).show(getActivity().getSupportFragmentManager(), "WireCreator");
            }
        });


        //AN object used to apply attributes to views dynamically
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        //Dynamically add attributes
        params.addRule(RelativeLayout.BELOW, R.id.mainToolbar);

        //Apply to the root (i.e. the FrameLayout)
        root.setLayoutParams(params);
        return root;

    }

    public void createWire(String name, int amount) {
        Log.v(LOG_TAG, "Got " + name + " with " + amount);
    }
}
