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
 * TODO
 * Create a map from list of jsonobjects
 * create a model to store the name and amount of an entry to pass to adapter
 * */
public class WireChoiceFragment extends Fragment {
    private static final String LOG_TAG = WireChoiceFragment.class.getSimpleName();

    private List<JSONObject> mWireList;

    private ListView mListView;
    private Button mButton;

    public WireChoiceFragment() {}

    public static WireChoiceFragment newInstance(ArrayList<JSONObject> wireList) {
        WireChoiceFragment frag = new WireChoiceFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("wireList", wireList);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        mWireList = (ArrayList<JSONObject>) bundle.getSerializable("wireList");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_wire_choice, container, false);

        /*List<JSONObject> temp = new ArrayList<>();
        try {
            temp.add(new JSONObject("{\"name\":\"uno\"}"));
            temp.add(new JSONObject("{\"name\":\"dos\"}"));
            temp.add(new JSONObject("{\"name\":\"tres\"}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        mListView = (ListView) root.findViewById(R.id.wireList);
        mButton = (Button) root.findViewById(R.id.addWireButton);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WireCreatorDialog.newInstance(makeWireNameList())
                        .show(getActivity().getSupportFragmentManager(), "wireCreator");
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

    //MainActivity calls this
    public void createWire(String name, int amount) {
        Log.v(LOG_TAG, "Got " + name + " with " + amount);
    }

    private ArrayList<CharSequence> makeWireNameList() {
        ArrayList<CharSequence> result = new ArrayList<>();

        String temp;
        for (JSONObject json : mWireList) {
            try {
                temp = json.getString("name");
                if (temp != null && temp.length() != 0) {
                    result.add(temp.subSequence(0, temp.length()));
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JsonException", e);
            }
        }

        return result;
    }
}
