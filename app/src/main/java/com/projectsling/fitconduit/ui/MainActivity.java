package com.projectsling.fitconduit.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.projectsling.fitconduit.R;
import com.projectsling.fitconduit.model.InitWireData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements WireCreatorDialog.OnWireCreateListener, WireEditorDialog.OnWireEditListener,
WireMenuDialog.StartEditListener, WireDeleteDialog.OnWireDeleteListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String WIRE_DATA_FILE_NAME = "wireData.json";
    private static final String CONDUIT_DATA_FILE_NAME = "conduitData.json";

    //For use with initDataFile
    private static final int WIRE_FILE_SWITCH = 0;
    private static final int CONDUIT_FILE_SWITCH = 1;

    FragmentManager mFragmentManager;
    RelativeLayout mRelativeLayout;
    private String mWireChoiceFragmentTag = "WireChoiceTag";
    private String mResultsFragmentTag = "ResultsTag";
    private List<JSONObject> mWireList;
    private List<JSONObject> mConduitList;

    //WireCreatorDialog calls this
    @Override
    public void onWireCreate(String name, int amount) {
        Log.v(LOG_TAG, "Got new wire " + name + " and amount of " + amount);
        WireChoiceFragment frag =
                (WireChoiceFragment) mFragmentManager.findFragmentByTag(mWireChoiceFragmentTag);
        frag.createWire(name, amount);
    }

    @Override
    public void onWireEdit(String newWire, int amount, int position) {
        Log.v(LOG_TAG, "Got edit wire name " + newWire +
                " new amount " + amount + " at position " + position);
        WireChoiceFragment frag =
                (WireChoiceFragment) mFragmentManager.findFragmentByTag(mWireChoiceFragmentTag);
        frag.editWire(newWire, amount, position);
    }

    @Override
    public void onWireDelete(int wireListPosition) {
        WireChoiceFragment frag =
                (WireChoiceFragment) mFragmentManager.findFragmentByTag(mWireChoiceFragmentTag);
        frag.deleteWire(wireListPosition);
    }

    //Shows the wire edit dialog
    @Override
    public void startEdit(int wireListPosition) {
        Log.v(LOG_TAG, "Starting edit dialog");

        WireChoiceFragment frag =
                (WireChoiceFragment) mFragmentManager.findFragmentByTag(mWireChoiceFragmentTag);
        frag.startEditDialog(wireListPosition);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.mainActivityContainer);

        mFragmentManager = getSupportFragmentManager();

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);

        if(savedInstanceState == null) {
            //Log.v(LOG_TAG, "null");

            if (fileExists(WIRE_DATA_FILE_NAME)) {
                deleteFile(WIRE_DATA_FILE_NAME);
            }

            if (!fileExists(WIRE_DATA_FILE_NAME)) {
                initDataFile(WIRE_DATA_FILE_NAME, WIRE_FILE_SWITCH);
            }
            if (!fileExists(CONDUIT_DATA_FILE_NAME)) {
                initDataFile(CONDUIT_DATA_FILE_NAME, CONDUIT_FILE_SWITCH);
            }


            /*Holds the json for each wire
            * Should be in form of
            * {
            *   "name":<string>,
            *   "od":<double>
            * }
            * */
            mWireList = new ArrayList<>();
            getJsonFromFile(mWireList, WIRE_DATA_FILE_NAME);

            mConduitList = new ArrayList<>();
            getJsonFromFile(mConduitList, CONDUIT_DATA_FILE_NAME);

            WireChoiceFragment frag = WireChoiceFragment.newInstance(
                    (ArrayList<JSONObject>) mWireList, (ArrayList<JSONObject>) mConduitList);
            //Add the wire choice fragment
            mFragmentManager.beginTransaction()
                    .add(R.id.mainActivityContainer, frag, mWireChoiceFragmentTag)
                    .add(R.id.mainActivityContainer, new ResultsFragment(), mResultsFragmentTag)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.v(LOG_TAG, "Destroyed");
    }

    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        if (!outState.containsKey("keepFrag")) {
            outState.putBoolean("keepFrag", true);
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                openAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openAbout() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void getJsonFromFile(List<JSONObject> wireList, String fileName) {
        BufferedReader input = null;
        try {
            input = new BufferedReader(
                    new InputStreamReader(openFileInput(fileName)));
            String line;
            while ((line = input.readLine()) != null) {
                Log.v(LOG_TAG, line);
                wireList.add(new JSONObject(line));
            }

        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "File not found", e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException", e);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JsonException", e);
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "IOException", e);
                }
            }
        }
    }

    private void initDataFile(String fileName, int type) {
        InitWireData init = new InitWireData();
        PrintWriter output = null;

        try {
            output = new PrintWriter(
                    new OutputStreamWriter(
                            openFileOutput(fileName, Context.MODE_PRIVATE)));

            switch (type) {
                case WIRE_FILE_SWITCH:
                    for (int x = 0; x < init.getWireListAmount(); x++) {
                        output.println(init.getWireDataPairString(x));
                    }
                    break;
                case CONDUIT_FILE_SWITCH:
                    for (int x = 0; x < init.getConduitListAmount(); x++) {
                        output.println(init.getConduitDataPairString(x));
                    }
                    break;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (output != null) {
                output.close();
            }
        }
    }

    private boolean fileExists(String fileName) {
        File file = getFileStreamPath(fileName);
        return file.exists();
    }
}
