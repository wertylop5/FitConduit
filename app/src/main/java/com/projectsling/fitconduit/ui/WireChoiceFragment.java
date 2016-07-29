package com.projectsling.fitconduit.ui;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.projectsling.fitconduit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WireChoiceFragment extends Fragment {
    private ListView mListView;

    public WireChoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

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

        List<RowState> another = new ArrayList<>();
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());
        another.add(new RowState());

        mListView = (ListView)root.findViewById(R.id.wireList);
        mListView.setAdapter(new WireAdapter(getActivity(), temp, another));

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
}
