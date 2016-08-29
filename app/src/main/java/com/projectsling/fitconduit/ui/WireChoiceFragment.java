package com.projectsling.fitconduit.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.projectsling.fitconduit.R;
import com.projectsling.fitconduit.model.WireAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class WireChoiceFragment extends Fragment {
    private static final String LOG_TAG = WireChoiceFragment.class.getSimpleName();
    private static final String UNKNOWN_WIRE_DIALOG_TAG = "unknownWireDialog";
    private static final String WIRE_MENU_DIALOG = "wireMenu";
    private static final String WIRE_CREATOR_DIALOG = "wireCreator";
    private static final String WIRE_EDITOR_DIALOG = "wireEditor";

    private List<JSONObject> mWireList;
    private List<JSONObject> mConduitList;

    private Map<String, JSONObject> mWireNameMap;       //Key is wire name, value is the JSONObject

    //For the listview
    private WireAdapter mWireAdapter;
    private ListView mListView;
    private Button mButton;
    private Button mCalcButton;
    private Button mUnknownWireButton;
    private Spinner mSpinner;
    private WireCreatorDialog mWireCreatorDialog;

    private OnCalculateListener mCallBack;

    public WireChoiceFragment() {}

    public interface OnCalculateListener {
        void onCalculate(List<JSONObject> selectedWires, List<Integer> selectedWireAmounts,
                         String selectedConduit, List<JSONObject> conduitList);
    }

    public static WireChoiceFragment newInstance(ArrayList<JSONObject> wireList,
                                                 ArrayList<JSONObject> conduitList) {
        WireChoiceFragment frag = new WireChoiceFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("wireList", wireList);
        bundle.putSerializable("conduitList", conduitList);
        frag.setArguments(bundle);

        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallBack = (OnCalculateListener) context;
        }
        catch (ClassCastException e) {
            Log.e(LOG_TAG, "Class cast", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if (bundle.containsKey("wireList")) {
            mWireList = (ArrayList<JSONObject>) bundle.getSerializable("wireList");
            mWireNameMap = makeWireMap(mWireList);
            getArguments().remove("wireList");
            mWireCreatorDialog = WireCreatorDialog.newInstance(makeWireNameList());
        }
        else {
            Log.e(LOG_TAG, "No wire list found");
        }

        if (bundle.containsKey("conduitList")) {
            mConduitList = (List<JSONObject>) bundle.getSerializable("conduitList");
            getArguments().remove("conduitList");
        }
        else {
            Log.e(LOG_TAG, "No conduit list found");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //This frag will be saved across activity recreation
        setRetainInstance(true);

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_wire_choice, container, false);

        mListView = (ListView) root.findViewById(R.id.wireList);
        if (savedInstanceState == null) {
            //Init adapter with empty list
            mWireAdapter = new WireAdapter(getActivity(), new ArrayList<JSONObject>());
        }
        mListView.setAdapter(mWireAdapter);

        //Long click allows user to edit the wire
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Log.v(LOG_TAG, position + "");

                /*try {
                    WireEditorDialog.newInstance(
                            makeWireNameList(),
                            ((JSONObject) mWireAdapter.getItem(position)).getString("name"),
                            ((JSONObject) mWireAdapter.getItem(position)).getInt("amount"),
                            position)
                            .show(getActivity().getSupportFragmentManager(), "wireEditor");
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "JSONException", e);
                }*/

                if (((JSONObject) mWireAdapter.getItem(position)).has("name")) {
                    WireMenuDialog.newInstance(position)
                            .show(getActivity().getSupportFragmentManager(), WIRE_MENU_DIALOG);
                }
                else if (((JSONObject) mWireAdapter.getItem(position)).has("od")) {
                    WireMenuDialog.newInstance(position, true)
                            .show(getActivity().getSupportFragmentManager(), WIRE_MENU_DIALOG);
                }

                return true;
            }
        });

        mButton = (Button) root.findViewById(R.id.addWireButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*WireCreatorDialog.newInstance(makeWireNameList())
                        .show(getActivity().getSupportFragmentManager(), "wireCreator");*/
                mWireCreatorDialog.show(getActivity().getSupportFragmentManager(), WIRE_CREATOR_DIALOG);
            }
        });

        mUnknownWireButton = (Button) root.findViewById(R.id.addUnknownWireButton);
        mUnknownWireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnknownWireDialog.newInstance()
                        .show(getActivity().getSupportFragmentManager(), UNKNOWN_WIRE_DIALOG_TAG);
            }
        });

        mSpinner = (Spinner) root.findViewById(R.id.conduitSpinner);
        mSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                new ArrayList<String>(new LinkedHashSet<String>(
                        getStringKeyFromJsonList(mConduitList, "type")))));

        mCalcButton = (Button) root.findViewById(R.id.wireCalcButton);
        mCalcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * Things that need to go to results frag
                * Selected conduit type
                * Total cable quantity
                * Total cable area
                * Conduit list
                * */

                //Map<JSONObject, Integer> selectedWires = new HashMap<JSONObject, Integer>();
                List<JSONObject> selectedWires = new ArrayList<>();
                List<Integer> selectedWireAmounts = new ArrayList<>();
                String temp;
                for (int x = 0; x < mWireAdapter.getCount(); x++) {
                    try {
                        /*selectedWires.put(
                                mWireNameMap.get(
                                        ((JSONObject) mWireAdapter.getItem(x))
                                                .getString("name")),
                                ((JSONObject) mWireAdapter.getItem(x))
                                        .getInt("amount")
                        );*/

                        /**Change this to check if null. if so, unnamed wire, take the od instead*/

                        if (((JSONObject) mWireAdapter.getItem(x)).has("name")) {
                            selectedWires.add(
                                    mWireNameMap.get(
                                            ((JSONObject) mWireAdapter.getItem(x))
                                                    .getString("name"))
                            );
                        }
                        else {
                            selectedWires.add(
                                    ((JSONObject) mWireAdapter.getItem(x))
                            );
                        }

                        selectedWireAmounts.add(
                                ((JSONObject) mWireAdapter.getItem(x))
                                        .getInt("amount")
                        );
                    } catch (JSONException e) {
                        Log.e(LOG_TAG, "JsonException", e);
                    }
                }

                mCallBack.onCalculate(selectedWires, selectedWireAmounts,
                        ((TextView) mSpinner.getSelectedView()).getText().toString(),
                        mConduitList);
            }
        });

        //An object used to apply attributes to views dynamically
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        //Dynamically add attributes
        params.addRule(RelativeLayout.BELOW, R.id.mainToolbar);

        //Apply to the root (i.e. the FrameLayout)
        root.setLayoutParams(params);

        return root;
    }

    //MainActivity calls this
    public void createWire(String name, int amount) {
        //Log.v(LOG_TAG, "Got " + name + " with " + amount);
        JSONObject json = new JSONObject();
        try {
            json.put("name", name)
                    .put("amount", amount);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSONException", e);
        }

        mWireAdapter.addEntry(json);
    }

    //MainActivity calls this
    public void createWire(double od, int amount) {
        //Log.v(LOG_TAG, "Got " + od + " with " + amount);
        JSONObject json = new JSONObject();
        try {
            json.put("od", od)
                    .put("amount", amount);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSONException", e);
        }

        mWireAdapter.addEntry(json);
    }

    //MainActivity calls this
    public void editWire(String newWire, int amount, int wireListPosition) {
        /*Log.v(LOG_TAG, "New name " + newWire + " new amount "
                + amount + " pos " + wireListPosition);*/
        JSONObject json = new JSONObject();
        try {
            json.put("name", newWire)
                    .put("amount", amount);
            mWireAdapter.editEntry(json, wireListPosition);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "JSONException", e);
        }
    }

    //MainActivity calls this
    public void editWire(double od, int amount, int wireListPosition) {
        /*Log.v(LOG_TAG, "New name " + newWire + " new amount "
                + amount + " pos " + wireListPosition);*/
        JSONObject json = new JSONObject();
        try {
            json.put("od", od)
                    .put("amount", amount);
            mWireAdapter.editEntry(json, wireListPosition);
        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "JSONException", e);
        }
    }

    //MainActivity calls this
    public void deleteWire(int wireListPosition) {
        mWireAdapter.deleteEntry(wireListPosition);
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

    //Position is the wire position in the list
    public void startEditDialog(int wireListPosition) {
        try {
            WireEditorDialog.newInstance(
                        makeWireNameList(),
                        ((JSONObject) mWireAdapter.getItem(wireListPosition)).getString("name"),
                        ((JSONObject) mWireAdapter.getItem(wireListPosition)).getInt("amount"),
                        wireListPosition)
                    .show(getActivity().getSupportFragmentManager(), WIRE_EDITOR_DIALOG);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSONException", e);
        }
    }

    //Position is the wire position in the list
    public void startEditDialog(int wireListPosition, boolean isUnknown) {
        Log.v(LOG_TAG, "starting unknown");
        if (isUnknown) {
            try {
                UnknownWireDialog.newInstance(
                        ((JSONObject) mWireAdapter.getItem(wireListPosition)).getDouble("od"),
                        ((JSONObject) mWireAdapter.getItem(wireListPosition)).getInt("amount"),
                        wireListPosition
                ).show(getActivity().getSupportFragmentManager(), UNKNOWN_WIRE_DIALOG_TAG);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JSONException", e);
            }
        }
    }

    private ArrayList<String> getStringKeyFromJsonList(List<JSONObject> itemList, String key) {
        ArrayList<String> res = new ArrayList<>();

        for (JSONObject json : itemList) {
            try {
                res.add(json.getString(key));
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Json", e);
            }
        }

        return res;
    }

    private HashMap<String, JSONObject> makeWireMap(List<JSONObject> wireList) {
        HashMap<String, JSONObject> res = new HashMap<>();
        for (JSONObject json : wireList) {
            try {
                res.put(json.getString("name"), json);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "JsonException", e);
            }
        }

        return res;
    }
}
